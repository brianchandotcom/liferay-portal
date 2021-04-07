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

import {addField} from '../../../util/fieldSupport.es';
import {createFieldSet} from '../util/fieldset.es';
import {updateField} from '../util/settingsContext.es';
import handleSectionAdded from './sectionAddedHandler.es';

const handleFieldSetAdded = (props, state, event) => {
	const {
		defaultLanguageId,
		fieldName,
		fieldSet,
		indexes,
		parentFieldName,
		properties,
		rows,
		useFieldName,
	} = event;
	const {pages} = state;
	const visitor = new PagesVisitor(fieldSet.pages);
	const nestedFields = [];

	visitor.mapFields((nestedField) => {
		nestedFields.push(
			updateField(
				{...props, defaultLanguageId},
				nestedField,
				'label',
				nestedField.label
			)
		);
	});

	let fieldSetField = createFieldSet(
		props,
		{skipFieldNameGeneration: false, useFieldName},
		nestedFields
	);

	if (properties) {
		Object.keys(properties).forEach((key) => {
			fieldSetField = updateField(
				props,
				fieldSetField,
				key,
				properties[key]
			);
		});
	}

	if (fieldSet.id) {
		fieldSetField = updateField(
			props,
			fieldSetField,
			'ddmStructureId',
			fieldSet.id
		);
	}

	if (rows && rows.length) {
		fieldSetField = updateField(props, fieldSetField, 'rows', rows);
	}

	if (fieldName) {
		return handleSectionAdded(
			props,
			{
				...state,
				pages,
			},
			{
				data: {
					fieldName,
					parentFieldName,
				},
				indexes,
				newField: updateField(
					props,
					fieldSetField,
					'label',
					fieldSet.localizedTitle
				),
			}
		);
	}

	return addField({
		...props,
		indexes,
		newField: updateField(
			{...props, defaultLanguageId},
			fieldSetField,
			'label',
			fieldSet.localizedTitle
		),
		pages,
		parentFieldName,
	});
};

export default handleFieldSetAdded;
