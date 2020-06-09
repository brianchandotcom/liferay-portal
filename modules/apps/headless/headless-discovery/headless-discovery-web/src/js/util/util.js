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

const REQUEST_BODY_TYPES = ['application/json', 'multipart/form-data'];

export const getSchemaType = (requestBody) => {
	let schemaType = '';

	if (requestBody) {
		const {content} = requestBody;

		let schema = {};

		REQUEST_BODY_TYPES.forEach((type) => {
			if (content[type]) {
				schema = content[type].schema;
			}
		});

		if (schema['$ref']) {
			schemaType = schema['$ref'].replace('#/components/schemas/', '');
		}
		else {
			schemaType = schema.type;
		}
	}

	return schemaType;
};

export const stringify = (json) => {
	return JSON.stringify(json, null, 4);
};
