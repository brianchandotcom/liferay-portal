/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

interface Props {
	defaultLanguageId: Locale;
	fields: Field[];
	name: string;
}

interface Field {
	dataType?: 'double' | 'html' | 'integer' | 'string';
	displayStyle?: 'singleline' | 'multiline';
	fieldType?:
		| 'document_library'
		| 'geolocation'
		| 'image'
		| 'journal_article'
		| 'numeric'
		| 'rich_text'
		| 'select'
		| 'text';
	localizable?: boolean;
	name: string;
	options?: Options;
	repeatable?: boolean;
	required?: boolean;
}

export default function getDataStructureDefinition({
	defaultLanguageId,
	fields,
	name,
}: Props): DataDefinition {
	return {
		availableLanguageIds: [defaultLanguageId],
		dataDefinitionFields: fields.map(
			({
				dataType,
				displayStyle = 'singleline',
				fieldType = 'text',
				localizable = true,
				name: fieldName,
				options,
				repeatable = false,
				required = false,
			}) => {
				const customProperties: DefinitionField['customProperties'] = {
					dataType: 'string',
					displayStyle,
					fieldReference: fieldName,
					options,
				};

				let indexType: DefinitionField['indexType'] = 'keyword';

				if (fieldType === 'geolocation') {
					delete customProperties.displayStyle;
				}
				else if (fieldType === 'numeric') {
					customProperties.dataType = dataType || 'integer';

					delete customProperties.displayStyle;
				}
				else if (fieldType === 'rich_text') {
					customProperties.dataType = 'html';

					delete customProperties.displayStyle;

					indexType = 'text';
				}

				return {
					customProperties,
					defaultValue: {},
					fieldType,
					indexType,
					label: {
						[defaultLanguageId]: fieldName,
					},
					localizable,
					name: fieldName,
					repeatable,
					required,
					showLabel: true,
				};
			}
		),
		defaultDataLayout: {
			dataLayoutPages: [
				{
					dataLayoutRows: fields.map((field) => {
						return {
							dataLayoutColumns: [
								{
									columnSize: 12,
									fieldNames: [field.name],
								},
							],
						};
					}),
					description: {
						[defaultLanguageId]: '',
					},
					title: {
						[defaultLanguageId]: '',
					},
				},
			],
			name: {
				[defaultLanguageId]: name,
			},
			paginationMode: 'single-page',
		},
		defaultLanguageId,
		id: '',
		name: {
			[defaultLanguageId]: name,
		},
	};
}
