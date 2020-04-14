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

import {FormSupport} from 'dynamic-data-mapping-form-renderer';

import {addField} from './fieldAddedHandler.es';
import handleFieldDeleted from './fieldDeletedHandler.es';
import handleSectionAdded from './sectionAddedHandler.es';

export default (props, state, event) => {
	const {
		sourceFieldName,
		targetFieldName,
		targetIndexes,
		targetParentFieldName,
	} = event;
	const deletedState = handleFieldDeleted(props, state, {
		fieldName: sourceFieldName,
	});
	const sourceField = FormSupport.findFieldByFieldName(
		state.pages,
		sourceFieldName
	);

	if (targetFieldName) {
		return {
			...handleSectionAdded(
				props,
				{
					...state,
					pages: deletedState.pages,
				},
				{
					data: {
						fieldName: targetFieldName,
						parentFieldName: targetParentFieldName,
					},
					indexes: targetIndexes,
					newField: sourceField,
				}
			),
		};
	}

	return {
		...addField(props, {
			indexes: targetIndexes,
			newField: sourceField,
			pages: deletedState.pages,
			parentFieldName: targetParentFieldName,
		}),
	};
};
