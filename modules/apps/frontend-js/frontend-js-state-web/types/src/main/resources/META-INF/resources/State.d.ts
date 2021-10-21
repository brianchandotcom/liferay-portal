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

import type {Immutable} from './types';
declare const ATOM = 'Liferay.State.ATOM';
declare const SELECTOR = 'Liferay.State.SELECTOR';
interface Getter {
	<T>(atomOrSelector: Atom<T> | Selector<T>): Immutable<T>;
}
export declare type Atom<T> = Immutable<{
	[ATOM]: true;
	default: T;
	key: string;
}>;
export declare type Selector<T> = Immutable<{
	[SELECTOR]: true;
	deriveValue: (get: Getter) => T;
	key: string;
}>;
declare const State: {
	__internal__: {
		debug: {
			readonly atoms: {
				readonly 'default': Readonly<unknown>;
				readonly 'key': string;
				readonly 'Liferay.State.ATOM': true;
			}[];
			readonly selectors: {
				readonly 'deriveValue': (get: Getter) => unknown;
				readonly 'key': string;
				readonly 'Liferay.State.SELECTOR': true;
			}[];
		};
		reset(): void;
	};

	/**
	 * Methods in the `__unsafe__` namespace are provided as an escape hatch to
	 * allow interacting with global state from places where it may not be
	 * practical or possible to obtain a reference to a concrete atom or
	 * selector (for example, from within JSPs). These methods all take a string
	 * key and use it to look up the corresponding atom or selector.
	 *
	 * This usage is considered unsafe because it bypasses the static type
	 * checking provided by the main API (`read()`, `write()` etc). Given that
	 * the integrity of global shared state largely depends on the values being
	 * of a predictable and correct type, you should use the `__unsafe__` API
	 * only as a last resort. Instead, consider moving legacy code out of JSPs
	 * and into places where it can fully participate in the module-loading
	 * system and thus access concrete atoms and selectors by importing them. At
	 * the same time, moving legacy code into React components provides access
	 * to the `useLiferayState()` hook and other conveniences.
	 */
	__unsafe__: {
		readKey(key: string): unknown;
		subscribeKey(
			key: string,
			callback: (value: unknown) => void
		): {
			dispose: () => void;
		};
		writeKey(key: string, value: unknown): void;
	};
	_invalidateDependencies(
		atomOrSelector: Atom<unknown> | Selector<unknown>,
		invalidated: Array<Selector<unknown>>
	): void;
	_notify<T>(callback: (value: T) => void, value: T): void;
	_readSelector<T_1>(
		selector: {
			readonly 'deriveValue': (get: Getter) => T_1;
			readonly 'key': string;
			readonly 'Liferay.State.SELECTOR': true;
		},
		seen: Set<Selector<unknown>>
	): Immutable<T_1>;

	/**
	 * Register a unit of shared state called an "atom". Atoms have a unique
	 * string key, a type `T`, and a default value. Atoms themselves are
	 * immutable, but are each associated with a corresponding "current" value
	 * that can be read, updated, and observed.
	 *
	 * Given an atom, you can interact with the current value associated with it
	 * in these ways:
	 *
	 * - Read it with `read()` or `readAtom()`.
	 * - Update it with `write()` or `writeAtom()`.
	 * - Subscribe to be notified of changes with `subscribe()`.
	 * - In React components, the `useLiferayState()` hook does all the above.
	 */
	atom<T_2>(
		key: string,
		value: T_2
	): {
		readonly 'default': Immutable<T_2>;
		readonly 'key': string;
		readonly 'Liferay.State.ATOM': true;
	};

	/**
	 * Read the current value associated with the provided atom or selector.
	 *
	 * This is a convenience wrapper around `readAtom()` and `readSelector()`.
	 */
	read<T_3>(
		atomOrSelector:
			| {
					readonly 'default': Immutable<T_3>;
					readonly 'key': string;
					readonly 'Liferay.State.ATOM': true;
			  }
			| {
					readonly 'deriveValue': (get: Getter) => T_3;
					readonly 'key': string;
					readonly 'Liferay.State.SELECTOR': true;
			  }
	): Immutable<T_3>;

	/**
	 * Read the current value associated with the provided atom.
	 */
	readAtom<T_4>(atom: {
		readonly 'default': Immutable<T_4>;
		readonly 'key': string;
		readonly 'Liferay.State.ATOM': true;
	}): Immutable<T_4>;

	/**
	 * Read the current value associated with the provided selector.
	 */
	readSelector<T_5>(selector: {
		readonly 'deriveValue': (get: Getter) => T_5;
		readonly 'key': string;
		readonly 'Liferay.State.SELECTOR': true;
	}): Immutable<T_5>;

	/**
	 * Register a shared unit of derived state called a "selector", identified
	 * by a unique string key. Selectors derive their values via a "pure"
	 * function that reads atoms and/or other selectors. Selectors form a
	 * dependency graph, which means that when upstream atoms or selectors
	 * change, the dependent selectors get automatically and efficiently
	 * recomputed.
	 *
	 * Given a selector, you can interact with the current value associated with
	 * it in these ways:
	 *
	 * - Read it with `read()` or `readSelector()`.
	 * - Subscribe to be notified of changes with `subscribe()`.
	 * - In React components, the `useLiferayState()` hook does all the above.
	 *
	 * Note that, unlike atoms, you cannot `write()` directly to a selector;
	 * instead, you update them by changing their upstream atoms, which causes
	 * the affected selectors to re-derive their updated values.
	 */
	selector<T_6>(
		key: string,
		deriveValue: (get: Getter) => T_6
	): {
		readonly 'Liferay.State.SELECTOR': true;
		readonly 'deriveValue': (get: Getter) => T_6;
		readonly 'key': string;
	};

	/**
	 * Subscribe to be notified of changes to an atom or selector.
	 *
	 * The supplied `callback()` function will be invoked with the new value
	 * whenever it changes. To unsubscribe, use the `dispose()` function that is
	 * returned.
	 *
	 * In the context of a React component, the `useLiferayState()` hook can be
	 * used to observe and trigger state changes in a way that is analagous to
	 * the built-in `useState()` hook.
	 */
	subscribe<T_7 extends unknown>(
		atomOrSelector:
			| {
					readonly 'default': Immutable<T_7>;
					readonly 'key': string;
					readonly 'Liferay.State.ATOM': true;
			  }
			| {
					readonly 'deriveValue': (get: Getter) => T_7;
					readonly 'key': string;
					readonly 'Liferay.State.SELECTOR': true;
			  },
		callback: (value: Immutable<T_7>) => void
	): {
		dispose: () => void;
	};

	/**
	 * Attempt to update the value associated with the provided atom or
	 * selector.
	 *
	 * Note that this is just a convenience wrapper around `writeAtom()` that
	 * serves principally to streamline calls (for example, from the
	 * `useLiferayState()` hook), and in practice it is an error to attempt a
	 * direct update to a selector (instead, the upstream atoms that it depends
	 * on should be updated).
	 */
	write<T_8>(
		atomOrSelector:
			| {
					readonly 'default': Immutable<T_8>;
					readonly 'key': string;
					readonly 'Liferay.State.ATOM': true;
			  }
			| {
					readonly 'deriveValue': (get: Getter) => T_8;
					readonly 'key': string;
					readonly 'Liferay.State.SELECTOR': true;
			  },
		value: T_8
	): void;

	/**
	 * Update the value associated with the provided atom, notifying any
	 * downstream subscribers (of the atom itself, or selectors that depend on
	 * it).
	 */
	writeAtom<T_9>(
		atom: {
			readonly 'default': Immutable<T_9>;
			readonly 'key': string;
			readonly 'Liferay.State.ATOM': true;
		},
		value: T_9
	): void;
};

/**
 * Boilerplate to satisfy TypeScript and prevent: "TS2669: Augmentations
 * for the global scope can only be directly nested in external modules or
 * ambient module declarations."
 */
export default State;
declare global {
	interface Window {
		Liferay: {
			State: typeof State;
		};
	}
}
