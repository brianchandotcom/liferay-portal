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

import {SYMBOL_RAW, Schema} from './Schema.es';

export class DataLayoutRowSchema extends Schema {
	constructor(raw) {
		super('data_engine', 'dataLayoutRow', raw);
	}

	get dataLayoutColumns() {
		return this[SYMBOL_RAW].columns.map((column) => ({
			columnSize: column.size,
			fieldNames: column.fields.map(({fieldName}) => fieldName),
		}));
	}
}

export class DataLayoutPageSchema extends Schema {
	constructor(raw) {
		super('data_engine', 'dataLayoutPage', raw);
	}

	get dataLayoutRows() {
		return this[SYMBOL_RAW].rows.map((row) => new DataLayoutRowSchema(row));
	}

	get description() {
		return this[SYMBOL_RAW].localizedDescription;
	}

	get title() {
		return this[SYMBOL_RAW].localizedTitle;
	}
}

export class DataLayoutSchema extends Schema {
	static props = ['pages', 'rules'];

	constructor(raw) {
		super('data_engine', 'dataLayout', raw);
	}

	get dataRules() {
		return this[SYMBOL_RAW].rules;
	}

	get dataLayoutPages() {
		return this[SYMBOL_RAW].pages.map(
			(page) => new DataLayoutPageSchema(page)
		);
	}
}
