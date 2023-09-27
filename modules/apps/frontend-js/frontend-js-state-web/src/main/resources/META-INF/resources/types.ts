/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

type Primitive = bigint | boolean | null | number | string | symbol | undefined;

type Builtin = Date | Error | Function | Primitive | RegExp;

/**
 * A local "DeepReadonly" until TypeScript bundles one out of the box.
 *
 * See: https://github.com/microsoft/TypeScript/issues/13923
 */
export type Immutable<T> = T extends Builtin
	? T
	: T extends Map<infer K, infer V>
	? ReadonlyMap<Immutable<K>, Immutable<V>>
	: T extends ReadonlyMap<infer K, infer V>
	? ReadonlyMap<Immutable<K>, Immutable<V>>
	: T extends WeakMap<infer K, infer V>
	? WeakMap<Immutable<K>, Immutable<V>>
	: T extends Set<infer U>
	? ReadonlySet<Immutable<U>>
	: T extends ReadonlySet<infer U>
	? ReadonlySet<Immutable<U>>
	: T extends WeakSet<infer U>
	? WeakSet<Immutable<U>>
	: T extends Promise<infer U>
	? Promise<Immutable<U>>
	: T extends {}
	? {readonly [K in keyof T]: Immutable<T[K]>}
	: Readonly<T>;
