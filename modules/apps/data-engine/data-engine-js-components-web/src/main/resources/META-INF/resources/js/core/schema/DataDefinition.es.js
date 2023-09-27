/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fieldToDataDefinition} from '../../utils/dataConverter';
import {PagesVisitor} from '../../utils/visitors.es';
import {SYMBOL_CACHE, SYMBOL_RAW, Schema} from './Schema.es';

export class DataDefinitionSchema extends Schema {
	static props = [
		'availableLanguageIds',
		'dataDefinition',
		'defaultLanguageId',
		'id',
		'name',
		'pages',
	];

	constructor(raw) {
		super('data_engine', 'dataDefinition', raw);
	}

	get availableLanguageIds() {
		return this[SYMBOL_RAW].availableLanguageIds;
	}

	get contentType() {
		return this[SYMBOL_RAW].dataDefinition.contentType;
	}

	get dataDefinitionFields() {
		const {pages} = this[SYMBOL_RAW];

		// This operation will happen only once and the next calls are from the cache,
		// the value will be revalidated by Schema that makes a comparison by reference
		// of the Schema's props with the state, any changes in these properties the
		// Schema is recreated.

		if (this[SYMBOL_CACHE].dataDefinitionFields) {
			return this[SYMBOL_CACHE].dataDefinitionFields;
		}
		else {
			const fields = [];
			const visitor = new PagesVisitor(pages);

			visitor.mapFields((field) => {
				const dataDefinitionField = fieldToDataDefinition(field);
				fields.push(dataDefinitionField);
			});

			this[SYMBOL_CACHE].dataDefinitionFields = fields;

			return this[SYMBOL_CACHE].dataDefinitionFields;
		}
	}

	get defaultLanguageId() {
		return this[SYMBOL_RAW].defaultLanguageId;
	}

	get id() {
		const {
			dataDefinition: {id},
		} = this[SYMBOL_RAW];

		return id;
	}

	get name() {
		return this[SYMBOL_RAW].name;
	}

	serialize() {
		return {
			availableLanguageIds: this.availableLanguageIds,
			contentType: this.contentType,
			dataDefinitionFields: this.dataDefinitionFields,
			defaultLanguageId: this.defaultLanguageId,
			id: this.id,
			name: this.name,
		};
	}
}
