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

import type {Atom, Selector} from './State';
import type {Immutable} from './types';

/**
 * `Map` wrapper for type safety. While a vanilla `Map` is totally adequate for
 * our needs in terms of implementation, at the type-system level the declared
 * type of `Map<K, V>` isn't expressive enough to capture what we want (which
 * would be something like the following -- sadly, not valid TypeScript -- where
 * "T" would be any arbitrary subtype of "unknown", and we don't care what the
 * concrete type is, only that it is the same within each key/value pair).
 */
export default class SubscriberMap {
	_id: number;
	_subscribers: Map<
		Atom<unknown> | Selector<unknown>,
		Map<number, (value: unknown) => void>
	>;

	constructor() {
		this._id = 0;
		this._subscribers = new Map();
	}

	clear() {
		this._subscribers.clear();
	}

	getCallbacks<T>(
		atomOrSelector: Atom<T> | Selector<T>
	): Map<number, (value: Immutable<T>) => void> {
		if (!this._subscribers.has(atomOrSelector)) {
			this._subscribers.set(
				atomOrSelector,
				new Map<number, (value: unknown) => void>()
			);
		}

		return this._subscribers.get(atomOrSelector)!;
	}

	addCallback<T extends unknown>(
		atomOrSelector: Atom<T> | Selector<T>,
		callback: (value: Immutable<T>) => void
	): () => void {
		const subscriberId = this._id++;

		const callbacks = this.getCallbacks(atomOrSelector);

		callbacks.set(subscriberId, callback);

		return () => callbacks.delete(subscriberId);
	}
}
