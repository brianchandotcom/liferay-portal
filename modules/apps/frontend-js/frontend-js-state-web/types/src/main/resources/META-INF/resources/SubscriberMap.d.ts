/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	constructor();
	clear(): void;
	getCallbacks<T>(
		atomOrSelector: Atom<T> | Selector<T>
	): Map<number, (value: Immutable<T>) => void>;
	addCallback<T extends unknown>(
		atomOrSelector: Atom<T> | Selector<T>,
		callback: (value: Immutable<T>) => void
	): () => void;
}
