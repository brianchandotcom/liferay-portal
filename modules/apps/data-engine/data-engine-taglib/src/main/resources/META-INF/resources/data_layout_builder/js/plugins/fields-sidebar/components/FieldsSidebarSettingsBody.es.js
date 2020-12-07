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

import {ClayIconSpriteContext} from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import {
	EVENT_TYPES,
	FormProvider,
	Pages,
} from 'dynamic-data-mapping-form-renderer';
import React, {useContext, useEffect, useMemo, useState} from 'react';

import {getFilteredSettingsContext} from '../../../utils/settingsForm.es';

function getSettingsContext(
	hasFocusedCustomObjectField,
	focusedCustomObjectField,
	focusedField
) {
	if (hasFocusedCustomObjectField(focusedCustomObjectField)) {
		return focusedCustomObjectField.settingsContext;
	}

	return focusedField.settingsContext;
}

/**
 * This component will override the Column from Form Renderer and will
 * check if field to be rendered has a custom field.
 * If the field has a custom field, render it instead of children.
 * @param {customFields} Object
 *
 * You can override fields passing as parameter the customFields:
 * const customFields = {
 *     required: (props) => <NewRequiredComponent {...props} />
 * }
 */
const getColumn = (customFields = {}) => ({children, column, index}) => {
	if (column.fields.length === 0) {
		return null;
	}

	return (
		<ClayLayout.Col key={index} md={column.size}>
			{column.fields.map((field, index) => {
				const customField = customFields[field.fieldName];

				if (customField) {
					return customField({AppContext, children, field, index});
				}

				return children({field, index});
			})}
		</ClayLayout.Col>
	);
};

export default function ({
	config,
	customFields,
	dataRules,
	dispatchEvent,
	editingLanguageId,
	focusedCustomObjectField,
	focusedField,
	hasFocusedCustomObjectField,
}) {
	const spritemap = useContext(ClayIconSpriteContext);
	const [activePage, setActivePage] = useState(0);

	const Column = useMemo(() => getColumn(customFields), [customFields]);

	const settingsContext = getSettingsContext(
		hasFocusedCustomObjectField,
		focusedCustomObjectField,
		focusedField
	);

	const filteredSettingsContext = useMemo(
		() =>
			getFilteredSettingsContext({
				config,
				editingLanguageId,
				settingsContext,
			}),
		[config, editingLanguageId, settingsContext]
	);

	useEffect(() => {
		if (activePage > filteredSettingsContext.pages.length - 1) {
			setActivePage(0);
		}
	}, [filteredSettingsContext, activePage, setActivePage]);

	return (
		<form onSubmit={(event) => event.preventDefault()}>
			<FormProvider
				onEvent={(type, payload) => {
					switch (type) {
						case EVENT_TYPES.CHANGE_ACTIVE_PAGE:
							setActivePage(payload.value);
							break;
						case EVENT_TYPES.FIELD_BLUR:
						case EVENT_TYPES.FIELD_CHANGE:
							dispatchEvent(type, {
								editingLanguageId:
									settingsContext.editingLanguageId,
								propertyName: payload.fieldInstance.fieldName,
								propertyValue: payload.value,
							});
							break;
						case EVENT_TYPES.FIELD_EVALUATED:
							dispatchEvent('focusedFieldEvaluationEnded', {
								settingsContext: {
									...settingsContext,
									pages: payload,
								},
							});
							break;
						default:
							break;
					}
				}}
				value={{
					...filteredSettingsContext,
					activePage,
					builderRules: dataRules,
					editable: true,
					editingLanguageId,
					spritemap,
				}}
			>
				{(props) => <Pages {...props} overrides={{Column}} />}
			</FormProvider>
		</form>
	);
}
