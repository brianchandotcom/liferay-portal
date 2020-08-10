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

import {
	dropCustomObjectField,
	dropFieldSet,
	dropLayoutBuilderField,
} from '../actions.es';
import {
	DRAG_DATA_DEFINITION_FIELD,
	DRAG_FIELDSET,
	DRAG_FIELD_TYPE,
} from './dragTypes.es';

export const getDropHandler = ({dataDefinition, dataLayoutBuilder}) => {
	return ({item, monitor, sourceItem}) => {
		const {data, type} = item;
		const {fieldName, origin, parentField, ...indexes} = sourceItem;

		if (monitor.didDrop()) {
			return;
		}

		switch (type) {
			case DRAG_FIELD_TYPE: {
				if (
					parentField &&
					parentField.nestedFields &&
					parentField.type !== 'fieldset'
				) {
					throw new Error(
						Liferay.Language.get(
							'you-cannot-drop-new-fields-to-a-deprecated-field-group'
						)
					);
				}

				const payload = dropLayoutBuilderField({
					dataLayoutBuilder,
					fieldName,
					fieldTypeName: data.name,
					indexes,
					parentFieldName: parentField?.fieldName,
				});

				dataLayoutBuilder.dispatch(
					origin === 'empty' ? 'fieldAdded' : 'sectionAdded',
					payload
				);
				break;
			}
			case DRAG_DATA_DEFINITION_FIELD: {
				const payload = dropCustomObjectField({
					dataDefinition,
					dataDefinitionFieldName: data.name,
					dataLayoutBuilder,
					fieldName,
					indexes,
					parentFieldName: parentField?.fieldName,
				});

				dataLayoutBuilder.dispatch(
					origin === 'empty' ? 'fieldAdded' : 'sectionAdded',
					payload
				);
				break;
			}
			case DRAG_FIELDSET:
				dataLayoutBuilder.dispatch(
					'fieldSetAdded',
					dropFieldSet({
						dataLayoutBuilder,
						fieldName,
						fieldSet: data.fieldSet,
						indexes,
						parentFieldName: parentField?.fieldName,
						properties: data.properties,
						useFieldName: data.useFieldName,
					})
				);
				break;
			default:
				break;
		}
	};
};
