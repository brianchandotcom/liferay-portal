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
		requestIdleCallback(callback: Function): number;
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
	obj: Array<T>
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
		return this.queue.length === 0 ? undefined : this.queue[0];
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
class Scheduler<T> {
	private taskIdCounter: number = 0;
	private readonly queue: Queue<Task<T>>;
	private currentTask: Task<T> | undefined = undefined;
	private scheduledHostHandle: number = 0;
	private isCallbackScheduled: boolean = false;
	private isPerformingWork: boolean = false;

	/**
	 * The frame phases can be recognized as lanes which are a space of time
	 * where the scheduler yields up the main thread to the host in that time
	 * interval before taking up again.
	 */
	private nextFramePhase: number = 0;

	/**
	 * The yield interval is defined based on the RAIL model, this value is
	 * static and means that we yield 100ms to the host and define the deadline
	 * to maximize the chances of hitting 60 fps.
	 */
	private readonly yieldInterval: number = 100;
	private deadline: number = 0;

	constructor() {
		this.queue = new Queue();
	}

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
			this.currentTask = undefined;
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

		while (this.currentTask !== undefined) {
			if (this.shouldYieldToHost()) {

				// We no longer have time to run currentTask now

				break;
			}

			await this.currentTask.callback(this.currentTask.target);

			this.queue.dequeue();

			this.currentTask = this.queue.peek();

			this.nextFramePhase = performance.now() + this.yieldInterval;
		}

		// Return whether there's additional work

		return this.currentTask !== undefined;
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

type Mutation = {
	attributes: Record<string, string>;
	nodeName: string;
};

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
	 * triggered.
	 */
	mutations?: Record<MutationRecordType, Mutation>;

	/**
	 * Targets is a list or element that represents the subtree(s) to be
	 * analyzed.
	 */
	targets: Array<string>;
}

export class A11yChecker {
	private callback: A11yCheckerOptions['callback'];
	private scheduler: Scheduler<Node>;
	private observers: Array<{
		target: string;
		mutation: MutationObserver;
	}>;
	private mutations?: Record<MutationRecordType, Mutation>;
	readonly axeOptions: RunOptions;
	readonly denylist: A11yCheckerOptions['denylist'];

	constructor({
		axeOptions,
		callback,
		denylist,
		mutations,
		targets,
	}: A11yCheckerOptions) {
		const defaultOptions = {
			absolutePaths: true,
			reporter: 'v2',
		} as const;

		this.axeOptions = axeOptions ? axeOptions : defaultOptions;

		this.callback = callback;
		this.denylist = denylist;

		// Scheduler can be a singleton to allow multiple instances of
		// A11yChecker to share the same queue to avoid bottlenecks and
		// performance issues.

		this.scheduler = new Scheduler();

		this.mutations = mutations;

		this.observers = targets.map((target: string) => ({
			mutation: new MutationObserver(this.mutationCallback.bind(this)),
			target,
		}));
	}

	private async run(target: HTMLElement) {

		// Only perform the analysis if the element exists on the DOM

		if (!document.body.contains(target)) {
			return;
		}

		const results = await axe.run(target, this.axeOptions);

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

	private mutationCallback(records: Array<MutationRecord>) {
		records.forEach((record) => {
			if (this.mutations) {
				const condition = this.mutations[record.type];

				if (condition && hasValidMutation(record, condition)) {
					this.recordCallback(record.target);
				}
			}
			else {
				this.recordCallback(record.target);
			}
		});
	}

	observe() {
		this.observers.forEach(({mutation, target}) => {
			const element = document.querySelector(target);

			if (element) {
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
		});
	}

	unobserve() {
		this.scheduler.cancelHostCallback();
		this.observers.forEach(({mutation}) => mutation.disconnect());
	}
}

function hasValidMutation(record: MutationRecord, mutation: Mutation) {
	const {addedNodes, removedNodes, target} = record;
	const {attributes, nodeName} = mutation;

	// Is a removal or added mutation with type childList

	if (removedNodes.length > 0 || addedNodes.length > 0) {
		const [node] = removedNodes.length > 0 ? removedNodes : addedNodes;

		return (
			node.nodeName === nodeName &&
			compareAttributes(node as Element, attributes)
		);
	}
	else {
		return compareAttributes(target as Element, attributes);
	}
}

function compareAttributes(node: Element, attributes: Record<string, string>) {
	const attributesNames = Object.keys(attributes);

	return attributesNames.every((name) =>
		node.getAttribute(name)?.includes(attributes[name])
	);
}
