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

const SYMBOL_CONTEXT = Symbol('schema.context');

const SYMBOL_TYPE = Symbol('schema.type');

export const SYMBOL_CACHE = Symbol('schema.cache');

export const SYMBOL_RAW = Symbol('schema.raw');

export class Schema {
	constructor(context, type, raw) {
		this[SYMBOL_CONTEXT] = context;
		this[SYMBOL_TYPE] = type;
		this[SYMBOL_RAW] = raw;
		this[SYMBOL_CACHE] = {};
	}
}
