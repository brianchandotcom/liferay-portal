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

import {PagesVisitor} from 'data-engine-js-components-web';
import {DataConverter} from 'data-engine-taglib';

import {SYMBOL_CACHE, SYMBOL_RAW, Schema} from './Schema.es';

export class DataDefinitionSchema extends Schema {
	static props = [
		'availableLanguageIds',
		'dataDefinition',
		'defaultLanguageId',
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
				const dataDefinitionField = DataConverter.getDataDefinitionField(
					field
				);
				fields.push(dataDefinitionField);
			});

			this[SYMBOL_CACHE].dataDefinitionFields = fields;

			return this[SYMBOL_CACHE].dataDefinitionFields;
		}
	}

	get defaultLanguageId() {
		return this[SYMBOL_RAW].defaultLanguageId;
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
			name: this.name,
		};
	}
}
