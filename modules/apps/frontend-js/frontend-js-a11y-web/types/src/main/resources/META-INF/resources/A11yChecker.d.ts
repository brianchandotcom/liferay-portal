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

import {AxeResults, RunOptions} from 'axe-core';
declare global {
	interface Window {
		requestIdleCallback(callback: Function): any;
		cancelIdleCallback(handle: number): void;
	}
}
declare type Task<T> = {
	id: number;
	callback: Function;
	target: T;
};
declare type Selector<T> = (
	this: void,
	value: T,
	index: number,
	object: Array<T>
) => unknown;

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
export declare class Scheduler<T> {
	private taskIdCounter;
	private readonly queue;
	private currentTask;
	private scheduledHostHandle;
	private isCallbackScheduled;
	private isPerformingWork;

	/**
	 * The frame phases can be recognized as lanes which are a space of time
	 * where the scheduler yields up the main thread to the host in that time
	 * interval before taking up again.
	 */
	private nextFramePhase;

	/**
	 * The yield interval is defined based on the RAIL model, this value is
	 * static and means that we yield 100ms to the host and define the deadline
	 * to maximize the chances of hitting 60 fps.
	 */
	private readonly yieldInterval;
	deadline: number;
	cancelHostCallback(): void;
	hasCallback(selector: Selector<Task<T>>): Task<T> | undefined;
	private flushWork;
	private requestHostCallback;
	private shouldYieldToHost;
	private workLoop;
	scheduleCallback(callback: Function, target: T): void;
}

/**
 * Attributes are the attributes of a node. The array is considered a
 * conditional `or`, the same for the values of an attribute.
 */
declare type Attributes = Record<string, Array<string>>;
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
export declare class A11yChecker {
	private callback;
	private scheduler;
	private observers;
	private mutations;
	readonly axeOptions: RunOptions;
	readonly denylist?: Array<Array<string>>;
	constructor({
		axeOptions,
		callback,
		denylist,
		mutations,
		targets,
	}: A11yCheckerOptions);
	private run;
	private recordCallback;

	/**
	 * Search for any iframe that is within the element to monitor mutations
	 * within the iframe that may trigger further analysis.
	 *
	 * Searching for iframes when the checker is initialized is not good
	 * because iframes can appear at any time on the page, like opening a
	 * Modal, we need to monitor iframes as they appear during their lifecycle.
	 */
	private observeIframes;
	private mutationCallback;
	observe(): void;
	unobserve(): void;
}
export {};
