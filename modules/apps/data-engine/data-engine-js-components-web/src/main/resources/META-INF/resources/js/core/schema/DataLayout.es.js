/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	serialize() {
		return {
			dataLayoutColumns: this.dataLayoutColumns,
		};
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
		const defaultLanguageId = this[SYMBOL_RAW].defaultLanguageId;

		return (
			this[SYMBOL_RAW].localizedDescription ?? {[defaultLanguageId]: ''}
		);
	}

	get title() {
		const defaultLanguageId = this[SYMBOL_RAW].defaultLanguageId;

		return this[SYMBOL_RAW].localizedTitle ?? {[defaultLanguageId]: ''};
	}

	serialize() {
		return {
			dataLayoutRows: this.dataLayoutRows.map((row) => row.serialize()),
			description: this.description,
			title: this.title,
		};
	}
}

export class DataLayoutSchema extends Schema {
	static props = [
		'dataLayout',
		'id',
		'name',
		'pages',
		'paginationMode',
		'rules',
	];

	constructor(raw) {
		super('data_engine', 'dataLayout', raw);
	}

	get dataRules() {
		return this[SYMBOL_RAW].rules.map((rule) => ({
			...rule,
			name: {
				en_US: rule.name,
			},
		}));
	}

	get dataLayoutFields() {
		return this[SYMBOL_RAW].dataLayout.dataLayoutFields;
	}

	get dataLayoutPages() {
		return this[SYMBOL_RAW].pages.map(
			(page) => new DataLayoutPageSchema(page)
		);
	}

	get name() {
		return this[SYMBOL_RAW].name;
	}

	get id() {
		const {dataLayout, id: dataLayoutId} = this[SYMBOL_RAW];
		const id = dataLayoutId ?? dataLayout?.dataLayout?.id;

		return id;
	}

	get paginationMode() {
		return this[SYMBOL_RAW].paginationMode;
	}

	serialize() {
		return {
			dataLayoutFields: this.dataLayoutFields,
			dataLayoutPages: this.dataLayoutPages.map((page) =>
				page.serialize()
			),
			dataRules: this.dataRules,
			id: this.id,
			name: this.name,
			paginationMode: this.paginationMode,
		};
	}
}
