/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
