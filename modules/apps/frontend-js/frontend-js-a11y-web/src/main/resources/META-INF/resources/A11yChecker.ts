/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import axe, {AxeResults, RunOptions} from 'axe-core';

declare global {
	interface Window {
		requestIdleCallback(callback: Function): any;
		cancelIdleCallback(handle: number): void;
	}
}

type Task<T> = {
	id: number;
	callback: Function;
	target: T;
};

type Selector<T> = (
	this: void,
	value: T,
	index: number,
	object: Array<T>
) => unknown;

class Queue<T> {
	queue: Array<T> = [];

	enqueue(value: T) {
		return this.queue.push(value);
	}

	dequeue() {
		return this.queue.shift();
	}

	peek() {
		return this.queue.length === 0 ? null : this.queue[0];
	}

	has(selector: Selector<T>) {
		return this.queue.find(selector);
	}

	size() {
		return this.queue.length;
	}
}

/**
 * Scheduler deals with scheduling a callback to A11y that runs axe-core at the
 * end, many of the logics and algorithms in the scheduler are restricted to
 * A11y but partially generic.
 *
 * The implementation is based on Queue. Each new scheduled callback is added
 * end of the queue, the scheduler works with the main callback which is
 * responsible for emptying the queue and executing the queue callbacks in the
 * work loop respecting the frame deadline.
 *
 * Main thread
 * ------------------------------------------------------------------------->
 *
 * +------------+ ++ ++ ++ +--------+            +--------+    +------+
 * |    Busy    | || || || |  Idle  |            |  Busy  |    | Idle |
 * +------------+ ++ ++ ++ +--------+            +--------+    +------+
 *
 *                         +----------+ +---+                  +-----------+
 *                         | Job Exec.| |   |   Yield to host  | Job Exec. |
 *                         +----------+ +---+ <--------------> +-----------+
 *                        |                  |
 *                        +------------------+
 *                              Deadline
 *
 * When the main thread is idle the main callback is called to empty the queue
 * then a work loop is executed to ensure that we can execute more than one
 * callback until the defined deadline but it is interrupted to avoid loss
 * of frame and at the end of each execution it defines the next frame phase
 * that avoids abusing cycles and yields up control of the main thread to
 * the host. The main callback will be rescheduled again until the queue is
 * empty.
 */
export class Scheduler<T> {
	private taskIdCounter = 0;
	private readonly queue = new Queue<Task<T>>();
	private currentTask: Task<T> | null = null;
	private scheduledHostHandle = 0;
	private isCallbackScheduled = false;
	private isPerformingWork = false;

	/**
	 * The frame phases can be recognized as lanes which are a space of time
	 * where the scheduler yields up the main thread to the host in that time
	 * interval before taking up again.
	 */
	private nextFramePhase = 0;

	/**
	 * The yield interval is defined based on the RAIL model, this value is
	 * static and means that we yield 100ms to the host and define the deadline
	 * to maximize the chances of hitting 60 fps.
	 */
	private readonly yieldInterval = 100;
	public deadline = 0;

	cancelHostCallback() {
		window.cancelIdleCallback(this.scheduledHostHandle);
	}

	hasCallback(selector: Selector<Task<T>>) {
		return this.queue.has(selector);
	}

	private async flushWork() {

		// We'll need a host callback the next time work is scheduled.

		this.isCallbackScheduled = false;

		this.isPerformingWork = true;
		try {
			return await this.workLoop();
		}
		finally {
			this.currentTask = null;
			this.isPerformingWork = false;
		}
	}

	private requestHostCallback() {
		this.scheduledHostHandle = window.requestIdleCallback(async () => {
			const currentTime = performance.now();

			// Defines a deadline only when the next phase of the execution
			// frame arrives.

			if (
				this.nextFramePhase === 0 ||
				currentTime >= this.nextFramePhase
			) {
				this.deadline = currentTime + this.yieldInterval;
			}

			const hasMoreWork = await this.flushWork();

			if (hasMoreWork) {

				// If there's more work, schedule the next callback at the
				// end of the preceding one.

				this.isCallbackScheduled = true;
				this.requestHostCallback();
			}
		});
	}

	private shouldYieldToHost(): boolean {
		const currentTime = performance.now();

		if (currentTime >= this.deadline) {

			// We no longer have time to continue. We yield control of the main
			// thread, so that the browser can perform high-priority tasks for
			// a fixed period of time before starting to perform new tasks.

			return this.nextFramePhase >= currentTime;
		}

		// There's still time left in the frame.

		return false;
	}

	private async workLoop() {
		this.currentTask = this.queue.peek();

		while (this.currentTask !== null) {
			if (this.shouldYieldToHost()) {

				// We no longer have time to run currentTask now

				break;
			}

			// Catch the exception of some error in the callback to avoid
			// breaking the loop flow.

			try {
				await this.currentTask.callback(this.currentTask.target);
			}
			catch (error) {
				console.error(error);
			}

			this.queue.dequeue();

			this.currentTask = this.queue.peek();

			this.nextFramePhase = performance.now() + this.yieldInterval;
		}

		// Return whether there's additional work

		return this.currentTask !== null;
	}

	scheduleCallback(callback: Function, target: T) {
		const newTask = {
			callback,
			id: this.taskIdCounter++,
			target,
		};

		this.queue.enqueue(newTask);

		if (!this.isCallbackScheduled && !this.isPerformingWork) {
			this.isCallbackScheduled = true;
			this.requestHostCallback();
		}
	}
}

type ObserverObject = {

	/**
	 * Element is the reference of an element in the DOM when the object is
	 * initialized.
	 */
	element: Element | null | undefined;

	mutation: MutationObserver;

	/**
	 * Observed is a flag of the monitoring status. If the target and element
	 * do not exist when the observation is started then `observed` is false.
	 */
	observed: boolean;

	/**
	 * Target is the CSS selector that matches the element in the DOM.
	 */
	target: string;
};

type ObserverCallback = (records: Array<MutationRecord>) => void;

/**
 * Observer Pool is to maintain a list of elements being monitored, avoid
 * duplicates and add elements on demand.
 */
class ObserverPool {
	private observers: Array<ObserverObject>;
	private callback: ObserverCallback;

	constructor(targets: Array<string>, callback: ObserverCallback) {
		this.callback = callback;
		this.observers = targets.map((target: string) => ({
			element: document.querySelector(target),
			mutation: new MutationObserver(this.callback),
			observed: false,
			target,
		}));
	}

	/**
	 * Observe mutation in the element. If the element already exists and is
	 * stale it will save the new reference and start the observation otherwise
	 * it will add a new one.
	 */
	add(target: string) {
		const hasTarget = this.observers.find(
			(observer) => observer.target === target
		);

		const element = document.querySelector(target);

		if (hasTarget && element !== hasTarget.element) {
			hasTarget.mutation.disconnect();
			hasTarget.observed = false;
			hasTarget.element = element;
		}
		else if (!hasTarget) {
			this.observers.push({
				element,
				mutation: new MutationObserver(this.callback),
				observed: false,
				target,
			});
		}

		this.observe();
	}

	observe() {
		this.observers = this.observers.map(
			({element, mutation, observed, target}) => {
				if (observed) {
					return {
						element,
						mutation,
						observed,
						target,
					};
				}

				element = element ?? document.querySelector(target);

				// If the element is an iframe it monitors the body instead of
				// monitoring the iframe element which will not detect
				// mutations.

				element =
					element?.nodeName === 'IFRAME'
						? (element as HTMLIFrameElement).contentDocument?.body
						: element;

				if (element) {
					observed = true;

					mutation.observe(element, {
						attributes: true,
						childList: true,
						subtree: true,
					});
				}
				else {
					console.error(
						`A11y: Target ${target} was not found in the DOM.`
					);
				}

				return {
					element,
					mutation,
					observed,
					target,
				};
			}
		);
	}

	unobserve() {
		this.observers.forEach(({mutation}) => mutation.disconnect());
	}
}

/**
 * Attributes are the attributes of a node. The array is considered a
 * conditional `or`, the same for the values of an attribute.
 */
type Attributes = Record<string, Array<string>>;

export interface A11yCheckerOptions {

	/**
	 * AxeOptions is the set of settings that are passed to axe-core.
	 */
	axeOptions?: RunOptions;

	/**
	 * Callback for when the analysis is finished.
	 */
	callback: (violations: AxeResults) => void;

	/**
	 * Denylist is an optional list with CSS selectors that will be excluded
	 * from the analysis.
	 */
	denylist?: Array<string>;

	/**
	 * Mutation is an optional list of criteria on which a new analysis will be
	 * ignored.
	 */
	mutations: Record<string, Attributes>;

	/**
	 * Targets is a list or element that represents the subtree(s) to be
	 * analyzed.
	 */
	targets: Array<string>;
}

export class A11yChecker {
	private callback: A11yCheckerOptions['callback'];
	private scheduler: Scheduler<Node>;
	private observers: ObserverPool;
	private mutations: Record<string, Attributes>;
	readonly axeOptions: RunOptions;
	readonly denylist?: Array<Array<string>>;

	constructor({
		axeOptions,
		callback,
		denylist,
		mutations,
		targets,
	}: A11yCheckerOptions) {
		const defaultOptions = {
			reporter: 'v2',
		} as const;

		const {runOnly, ...options} = axeOptions ?? {};

		const runOnlyArray = Array.isArray(runOnly)
			? runOnly.filter(Boolean)
			: undefined;

		this.axeOptions = {
			...defaultOptions,
			...options,
			runOnly: runOnlyArray?.length ? runOnlyArray : undefined,
		};

		this.callback = callback;
		this.denylist = denylist
			? denylist.filter(Boolean).map((selector) => [selector])
			: denylist;

		// Scheduler can be a singleton to allow multiple instances of
		// A11yChecker to share the same queue to avoid bottlenecks and
		// performance issues.

		this.scheduler = new Scheduler();

		this.mutations = mutations;

		this.observers = new ObserverPool(
			targets,
			this.mutationCallback.bind(this)
		);
	}

	private async run(target: HTMLElement) {

		// Only perform the analysis if the element exists on the DOM

		if (!document.body.contains(target)) {
			return;
		}

		const context = this.denylist
			? {
					exclude: this.denylist,
					include: [target],
			  }
			: target;

		const results = await axe.run(

			// Disabling TypeScript here due to an axe-core typing error that a
			// context's `include` does not accept a node but accept.
			// https://github.com/dequelabs/axe-core/blob/4a01ffe1adf009745ea12a71f4a888843dc779da/doc/API.md#context-parameter-examples
			// @ts-ignore

			context,
			this.axeOptions
		);

		this.callback(results);
	}

	private recordCallback(target: Node) {
		const hasCallbackScheduled = this.scheduler.hasCallback(
			(scheduled) => scheduled.target === target
		);

		if (!hasCallbackScheduled) {
			this.scheduler.scheduleCallback(this.run.bind(this), target);
		}
	}

	/**
	 * Search for any iframe that is within the element to monitor mutations
	 * within the iframe that may trigger further analysis.
	 *
	 * Searching for iframes when the checker is initialized is not good
	 * because iframes can appear at any time on the page, like opening a
	 * Modal, we need to monitor iframes as they appear during their lifecycle.
	 */
	private observeIframes(node: Node) {

		// The Node can be a text element without properties or attributes, we
		// just ignored it for this case.

		const iframes = (node as Element).querySelectorAll
			? (node as Element).querySelectorAll('iframe')
			: [];

		iframes.forEach((iframe: HTMLIFrameElement) =>
			this.observers.add(`#${iframe.getAttribute('id')}`)
		);
	}

	private mutationCallback(records: Array<MutationRecord>) {
		const denylistTargets = this.denylist?.map((selector) => [
			...document.querySelectorAll(selector.join(' ')),
		]);

		records.forEach((record) => {
			const {
				addedNodes,
				attributeName,
				removedNodes,
				target,
				type,
			} = record;

			let node =
				type === 'attributes' || removedNodes.length > 0
					? target
					: addedNodes[0];

			if (type === 'attributes') {
				const attributes =
					this.mutations[node.nodeName.toLowerCase()] ??
					this.mutations?.any;

				const attributeValue = (node as Element).getAttribute(
					attributeName as string
				);

				if (
					attributes[attributeName as string]?.some((value) =>
						value === '*' ? true : attributeValue?.includes(value)
					)
				) {
					return;
				}
			}

			// Ignores the mutation if the target was through some element
			// within the denylist.

			const hasTargetInDenylist = denylistTargets?.some((targets) =>
				targets?.some((target) => target.contains(node))
			);

			if (hasTargetInDenylist) {
				return;
			}

			// If the node belongs to an iframe analyze the iframe instead of
			// the element. `axe-core` cannot directly analyze the element.

			if (node.ownerDocument !== document) {
				node = ((node.ownerDocument as Document).defaultView as Window)
					.frameElement as Node;
			}

			this.observeIframes(node);

			this.recordCallback(node);
		});
	}

	observe() {
		this.observers.observe();
	}

	unobserve() {
		this.scheduler.cancelHostCallback();
		this.observers.unobserve();
	}
}
