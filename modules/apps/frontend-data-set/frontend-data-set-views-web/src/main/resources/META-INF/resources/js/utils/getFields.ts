/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	FDS_ARRAY_FIELD_NAME_DELIMITER,
	FDS_ARRAY_FIELD_NAME_PARENT_SUFFIX,
	FDS_NESTED_FIELD_NAME_DELIMITER,
	FDS_NESTED_FIELD_NAME_PARENT_SUFFIX,
} from '@liferay/frontend-data-set-web';
import {fetch} from 'frontend-js-web';

import {FDSViewType} from '../FDSViews';
import {OBJECT_RELATIONSHIP} from './constants';
import openDefaultFailureToast from './openDefaultFailureToast';
import {EFieldFormat, EFieldType, IField} from './types';

const INVALID_FIELDS = ['actions', 'scopeKey', 'x-class-name', 'x-schema-name'];

const LOCALIZABLE_PROPERTY_SUFFIX = '_i18n';

interface IProperty {
	$ref?: string;
	extensions?: any;
	format?: EFieldFormat;
	items?: any;
	type: EFieldType;
}

interface IProperties {
	[key: string]: IProperty;
}

interface ISchemas {
	[key: string]: {
		properties: IProperties;
		type: string;
	};
}

function getValidFields({
	contextPath,
	schemaName,
	schemas,
}: {
	contextPath: string;
	schemaName: string;
	schemas: ISchemas;
}): Array<IField> {
	const fields: Array<IField> = [];

	const properties: IProperties = schemas[schemaName]?.properties;

	properties &&
		Object.keys(properties).map((propertyKey) => {
			const propertyValue = properties[propertyKey];

			if (INVALID_FIELDS.includes(propertyKey)) {
				return;
			}

			if (propertyKey.includes(LOCALIZABLE_PROPERTY_SUFFIX)) {
				return;
			}

			const type = propertyValue.type;

			if (propertyValue.items?.$ref) {
				fields.push({
					children: getValidFields({
						contextPath: `${contextPath}${propertyKey}${FDS_ARRAY_FIELD_NAME_DELIMITER}`,
						schemaName: propertyValue.items.$ref.replace(
							/^.*\//,
							''
						),
						schemas,
					}),
					label: propertyKey,
					name: `${contextPath}${propertyKey}${FDS_ARRAY_FIELD_NAME_PARENT_SUFFIX}`,
					sortable: false,
					type: type ? type : 'array',
				});

				return;
			}

			if (propertyValue.$ref) {
				fields.push({
					children: getValidFields({
						contextPath: `${contextPath}${propertyKey}${FDS_NESTED_FIELD_NAME_DELIMITER}`,
						schemaName: propertyValue.$ref.replace(/^.*\//, ''),
						schemas,
					}),
					label: propertyKey,
					name: `${contextPath}${propertyKey}${FDS_NESTED_FIELD_NAME_PARENT_SUFFIX}`,
					sortable: false,
					type: type ? type : 'object',
				});

				return;
			}

			if (
				propertyValue.extensions &&
				propertyValue.extensions['x-parent-map'] === 'properties'
			) {
				const schemaNames = Object.keys(schemas);
				const parentSchemaName = schemaNames.filter((schemaName) => {
					return (
						schemaName.toLowerCase() ===
						propertyKey.toLocaleLowerCase()
					);
				});

				if (parentSchemaName.length) {
					fields.push({
						children: getValidFields({
							contextPath: `${contextPath}${propertyKey}${FDS_NESTED_FIELD_NAME_DELIMITER}`,
							schemaName: parentSchemaName[0],
							schemas,
						}),
						label: propertyKey,
						name: `${contextPath}${propertyKey}${FDS_NESTED_FIELD_NAME_PARENT_SUFFIX}`,
						sortable: false,
						type: schemas[parentSchemaName[0]]?.type || 'object',
					});

					return;
				}
			}

			fields.push({
				format: propertyValue.format,
				label: propertyKey,
				name: `${contextPath}${propertyKey}`,
				sortable:
					type !== 'object' &&
					type !== 'array' &&
					!contextPath.includes(FDS_NESTED_FIELD_NAME_DELIMITER) &&
					!contextPath.includes(FDS_ARRAY_FIELD_NAME_DELIMITER),
				type,
			});
		});

	return fields;
}

export default async function getFields(fdsView: FDSViewType) {
	const {restApplication, restSchema} = fdsView[
		OBJECT_RELATIONSHIP.FDS_ENTRY_FDS_VIEW
	];

	const response = await fetch(`/o${restApplication}/openapi.json`);

	if (!response.ok) {
		openDefaultFailureToast();

		return [];
	}

	const responseJSON = await response.json();

	const schemas = responseJSON?.components?.schemas;

	if (!schemas?.[restSchema]?.properties) {
		openDefaultFailureToast();

		return [];
	}

	return getValidFields({
		contextPath: '',
		schemaName: restSchema,
		schemas,
	});
}
