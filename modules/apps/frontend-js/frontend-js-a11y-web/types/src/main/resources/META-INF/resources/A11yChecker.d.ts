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
		requestIdleCallback(callback: Function): number;
		cancelIdleCallback(handle: number): void;
	}
}
declare type Mutation = {
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
export declare class A11yChecker {
	private callback;
	private scheduler;
	private observers;
	private mutations?;
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
	private mutationCallback;
	observe(): void;
	unobserve(): void;
}
export {};
