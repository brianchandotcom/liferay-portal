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

import {DataDefinitionSchema} from '../schema/DataDefinition.es';
import {DataLayoutSchema} from '../schema/DataLayout.es';
import {SYMBOL_RAW} from '../schema/Schema.es';

const SCHEMAS_DICTIONARY = {
	dataDefinition: DataDefinitionSchema,
	dataLayout: DataLayoutSchema,
};

/**
 * Context stores all instances of schemas created by key to avoid recreating
 * a new instance of the same Schema in different components.
 */
const context = {};

const getProps = (props, keys) =>
	keys.reduce((prev, key) => {
		prev[key] = props[key];

		return prev;
	}, {});

/**
 * Compare the values of the properties at the reference level, the properties
 * should only be for readonly, the raw object and props will always be different
 * objects because props contain all the properties of the store but raw only
 * the properties that the schema needs.
 */
const isStaleRaw = (key, props, schema) =>
	SCHEMAS_DICTIONARY[key].props.some(
		(key) => props[key] !== schema[SYMBOL_RAW][key]
	);

const createSchema = (key, props) => {
	const Schema = SCHEMAS_DICTIONARY[key];

	const raw = getProps(props, Schema.props);
	const instance = new Schema(raw);

	context[key] = instance;

	return instance;
};

const getSchema = (key, props) => {
	if (key in context) {
		if (isStaleRaw(key, props, context[key])) {
			delete context[key];

			return createSchema(key, props);
		}

		return context[key];
	}
	else {
		return createSchema(key, props);
	}
};

const parseSchema = (props, schema) =>
	schema.reduce((prev, key) => {
		const Schema = getSchema(key, props);

		prev[key] = Schema;

		return prev;
	}, {});

/**
 * UseDataView creates a representation of the data based on the passed
 * schema otherwise nothing is done. It should not be used directly, it
 * is exposed through `useFormState`.
 */
export const useDataView = (props, schema) =>
	schema ? parseSchema(props, schema) : props;
