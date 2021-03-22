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
