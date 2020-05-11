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

import {PagesVisitor} from 'dynamic-data-mapping-form-renderer';

import handleFieldEdited from './fieldEditedHandler.es';

const handleFocusedFieldEvaluationEnded = (
	props,
	state,
	instanceId,
	settingsContext
) => {
	const visitor = new PagesVisitor(settingsContext.pages);
	const {focusedField} = state;

	state = {
		...state,
		focusedField: {
			...focusedField,
			instanceId: instanceId || focusedField.instanceId,
			settingsContext,
		},
	};

	visitor.mapFields(({fieldName, value}) => {
		state = handleFieldEdited(
			{
				...props,
				shouldAutoGenerateName: () => false,
			},
			state,
			{
				propertyName: fieldName,
				propertyValue: value,
			}
		);
	});

	return state;
};

export default handleFocusedFieldEvaluationEnded;
