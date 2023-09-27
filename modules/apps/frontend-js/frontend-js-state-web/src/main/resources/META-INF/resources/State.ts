/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import SubscriberMap from './SubscriberMap';
import deepFreeze from './deepFreeze';

import type {Immutable} from './types';

// In the future, these should be Symbol(); see:
// https://github.com/microsoft/TypeScript/issues/37888

const ATOM = 'Liferay.State.ATOM';
const SELECTOR = 'Liferay.State.SELECTOR';

interface Getter {
	<T>(atomOrSelector: Atom<T> | Selector<T>): Immutable<T>;
}

export type Atom<T> = Immutable<{
	[ATOM]: true;
	default: T;
	key: string;
}>;

export type Selector<T> = Immutable<{
	[SELECTOR]: true;
	deriveValue: (get: Getter) => T;
	key: string;
}>;

const atoms = new Map<string, Atom<unknown>>();

const dependencies = new Map<
	Atom<unknown> | Selector<unknown>,
	Set<Selector<unknown>>
>();

const selectors = new Map<string, Selector<unknown>>();

const subscribers = new SubscriberMap();

const values = new Map<Atom<unknown> | Selector<unknown>, unknown>();

const warnings = {
	readKey: new Set(),
	subscribeKey: new Set(),
	writeKey: new Set(),
};

const State = {
	__internal__: {
		debug: {
			get atoms() {
				return Array.from(atoms.values());
			},

			get selectors() {
				return Array.from(selectors.values());
			},
		},

		reset() {
			atoms.clear();
			dependencies.clear();
			selectors.clear();
			subscribers.clear();
			values.clear();
			warnings.readKey.clear();
			warnings.subscribeKey.clear();
			warnings.writeKey.clear();
		},
	},

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
		readKey(key: string): unknown {
			if (process.env.NODE_ENV === 'development') {
				if (!warnings.readKey.has(key)) {
					console.warn(
						`Liferay.State.__unsafe__.readKey(): access via string key ${JSON.stringify(
							key
						)} is not type-safe; prefer read(), readAtom() or readSelector() instead`
					);

					warnings.readKey.add(key);
				}
			}

			const atomOrSelector = atoms.get(key) || selectors.get(key);

			if (atomOrSelector) {
				return State.read(atomOrSelector);
			}
			else {
				throw new Error(
					`Liferay.State.__unsafe__.readKey(): no atom or selector exists for key ${JSON.stringify(
						key
					)}`
				);
			}
		},

		subscribeKey(
			key: string,
			callback: (value: unknown) => void
		): {dispose: () => void} {
			if (process.env.NODE_ENV === 'development') {
				if (!warnings.subscribeKey.has(key)) {
					console.warn(
						`Liferay.State.__unsafe__.subscribeWithKey(): access via string key ${JSON.stringify(
							key
						)} is not type-safe; prefer subscribe() instead`
					);

					warnings.subscribeKey.add(key);
				}
			}

			const atomOrSelector = atoms.get(key) || selectors.get(key);

			if (atomOrSelector) {
				return State.subscribe(atomOrSelector, callback);
			}
			else {
				throw new Error(
					`Liferay.State.__unsafe__.subscribeKey(): no atom or selector exists for key ${JSON.stringify(
						key
					)}`
				);
			}
		},

		writeKey(key: string, value: unknown): void {
			if (process.env.NODE_ENV === 'development') {
				if (!warnings.writeKey.has(key)) {
					console.warn(
						`Liferay.State.__unsafe__.writeKey(): access via string key ${JSON.stringify(
							key
						)} is not type-safe; prefer write() or writeAtom() instead`
					);

					warnings.writeKey.add(key);
				}
			}

			if (selectors.has(key)) {
				throw new Error(
					`Liferay.State.__unsafe__.writeKey(): expected atom but found selector for key ${JSON.stringify(
						key
					)}`
				);
			}
			else if (atoms.has(key)) {
				State.writeAtom(atoms.get(key)!, value);
			}
			else {
				throw new Error(
					`Liferay.State.__unsafe__.writeKey(): no atom exists for key ${JSON.stringify(
						key
					)}`
				);
			}
		},
	},

	_invalidateDependencies(
		atomOrSelector: Atom<unknown> | Selector<unknown>,
		invalidated: Array<Selector<unknown>>
	) {
		const directDependencies = dependencies.get(atomOrSelector);

		if (!directDependencies) {
			return;
		}

		for (const selector of directDependencies) {
			values.delete(selector);

			invalidated.push(selector);

			this._invalidateDependencies(selector, invalidated);
		}
	},

	_notify<T>(callback: (value: T) => void, value: T) {
		try {
			callback(value);
		}
		catch (error) {
			console.error(
				`Liferay.State._notify(): caught error during subscriber notification: ${error}`
			);

			if (process.env.NODE_ENV === 'development') {
				/* eslint-disable-next-line no-console */
				console.info(
					`Callback name: ${callback.name || 'undefined'}\n` +
						'Callback body:\n\n' +
						`${callback.toString()}\n\n`
				);
			}
		}
	},

	_readSelector<T>(
		selector: Selector<T>,
		seen: Set<Selector<unknown>>
	): Immutable<T> {
		if (seen.has(selector)) {
			const path = Array.from(seen.values());

			const index = path.indexOf(selector);

			path.splice(0, index);

			path.push(selector);

			const cycle = path.map(({key}) => JSON.stringify(key)).join(' -> ');

			throw new Error(
				`Liferay.State.readSelector(): cycle detected: ${cycle}`
			);
		}

		if (!values.has(selector)) {
			const getter: Getter = <V>(
				atomOrSelector: Atom<V> | Selector<V>
			): Immutable<V> => {
				if (!dependencies.has(atomOrSelector)) {
					dependencies.set(atomOrSelector, new Set());
				}

				dependencies.get(atomOrSelector)!.add(selector);

				if (isAtom(atomOrSelector)) {
					return State.readAtom(atomOrSelector);
				}
				else {
					return State._readSelector(atomOrSelector, seen);
				}
			};

			seen.add(selector);

			const value = deepFreeze(selector.deriveValue(getter));

			seen.delete(selector);

			values.set(selector, value);
		}

		return values.get(selector) as any;
	},

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
	atom<T>(key: string, value: T): Atom<T> {
		if (atoms.has(key)) {
			if (process.env.NODE_ENV === 'production') {

				// Unlike in development where there may be hot-reloading,
				// re-registering (overwriting) is not considered ok.

				throw new Error(
					`Liferay.State.atom(): key ${JSON.stringify(
						key
					)} already taken`
				);
			}
		}

		if (selectors.has(key)) {
			throw new Error(
				`Liferay.State.atom(): key ${JSON.stringify(
					key
				)} already taken by a selector`
			);
		}

		const atom = deepFreeze({
			[ATOM]: true,
			default: value,
			key,
		} as const);

		atoms.set(key, atom);

		return atom;
	},

	/**
	 * Read the current value associated with the provided atom or selector.
	 *
	 * This is a convenience wrapper around `readAtom()` and `readSelector()`.
	 */
	read<T>(atomOrSelector: Atom<T> | Selector<T>): Immutable<T> {
		if (isAtom(atomOrSelector)) {
			return State.readAtom(atomOrSelector);
		}
		else {
			return State.readSelector(atomOrSelector);
		}
	},

	/**
	 * Read the current value associated with the provided atom.
	 */
	readAtom<T>(atom: Atom<T>): Immutable<T> {
		if (values.has(atom)) {
			return values.get(atom) as any;
		}
		else {
			return atom.default;
		}
	},

	/**
	 * Read the current value associated with the provided selector.
	 */
	readSelector<T>(selector: Selector<T>): Immutable<T> {
		const seen = new Set<Selector<unknown>>();

		return State._readSelector(selector, seen);
	},

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
	selector<T>(key: string, deriveValue: (get: Getter) => T) {
		if (selectors.has(key)) {
			if (process.env.NODE_ENV === 'production') {

				// In development where there may be hot-reloading,
				// re-registering (overwriting) is considered ok.

				throw new Error(
					`Liferay.State.selector(): key ${JSON.stringify(
						key
					)} already taken`
				);
			}
		}

		if (atoms.has(key)) {
			throw new Error(
				`Liferay.State.selector(): key ${JSON.stringify(
					key
				)} already taken by an atom`
			);
		}

		const selector = deepFreeze({
			[SELECTOR]: true,
			deriveValue,
			key,
		} as const);

		selectors.set(key, selector);

		return selector;
	},

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
	subscribe<T extends any>(
		atomOrSelector: Atom<T> | Selector<T>,
		callback: (value: Immutable<T>) => void
	): {dispose: () => void} {
		const dispose = subscribers.addCallback(atomOrSelector, callback);

		if (!isAtom(atomOrSelector)) {

			// Read once in order to pre-populate dependency graph.

			State.readSelector(atomOrSelector);
		}

		return {dispose};
	},

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
	write<T>(atomOrSelector: Atom<T> | Selector<T>, value: T): void {
		if (isAtom(atomOrSelector)) {
			State.writeAtom(atomOrSelector, value);
		}
		else {
			throw new Error(
				`Liferay.State.write(): expected atom but received selector with key ${JSON.stringify(
					atomOrSelector.key
				)}`
			);
		}
	},

	/**
	 * Update the value associated with the provided atom, notifying any
	 * downstream subscribers (of the atom itself, or selectors that depend on
	 * it).
	 */
	writeAtom<T>(atom: Atom<T>, value: T): void {
		const previous = values.get(atom);

		if (Object.is(value, previous)) {
			return;
		}

		const frozen = deepFreeze(value);

		values.set(atom, frozen);

		for (const callback of subscribers.getCallbacks(atom).values()) {
			State._notify(callback, frozen);
		}

		const invalidatedSelectors: Array<Selector<unknown>> = [];

		this._invalidateDependencies(atom, invalidatedSelectors);

		for (const selector of invalidatedSelectors) {
			for (const callback of subscribers
				.getCallbacks(selector)
				.values()) {
				State._notify(callback, State.readSelector(selector));
			}
		}
	},
};

function isAtom(value: unknown): value is Atom<any> {
	return Object.hasOwnProperty.call(value, ATOM);
}

window.Liferay = window.Liferay || {};

/**
 * Boilerplate to satisfy TypeScript and prevent: "TS2669: Augmentations
 * for the global scope can only be directly nested in external modules or
 * ambient module declarations."
 */

export default State;
