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

import ClayLayout from '@clayui/layout';
import {
	FormFieldSettings,
	Pages,
	useConfig,
	useForm,
	useFormState,
} from 'data-engine-js-components-web';
import {EVENT_TYPES as CORE_EVENT_TYPES} from 'data-engine-js-components-web/js/core/actions/eventTypes.es';
import React, {useEffect, useMemo, useState} from 'react';

import {getFilteredSettingsContext} from '../../../../js/utils/settingsForm.es';

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
const getColumn = ({customFields = {}}) => ({children, column, index}) => {
	if (column.fields.length === 0) {
		return null;
	}

	return (
		<ClayLayout.Col key={index} md={column.size}>
			{column.fields.map((field, index) => {
				const {fieldName} = field;
				const CustomField = customFields[fieldName];

				if (CustomField) {
					return (
						<CustomField field={field} index={index} key={index}>
							{children}
						</CustomField>
					);
				}

				return children({field, index});
			})}
		</ClayLayout.Col>
	);
};

export default function FieldsSidebarSettingsBody() {
	const [activePage, setActivePage] = useState(0);
	const {
		customFields,
		defaultLanguageId,
		editingLanguageId,
		focusedField,
		rules,
	} = useFormState();
	const config = useConfig();
	const dispatch = useForm();

	const Column = useMemo(() => getColumn({customFields}), [customFields]);

	const {settingsContext} = focusedField;

	const filteredSettingsContext = useMemo(
		() =>
			getFilteredSettingsContext({
				config,
				defaultLanguageId,
				editingLanguageId,
				settingsContext,
			}),
		[config, defaultLanguageId, editingLanguageId, settingsContext]
	);

	useEffect(() => {
		if (activePage > filteredSettingsContext.pages.length - 1) {
			setActivePage(0);
		}
	}, [filteredSettingsContext, activePage, setActivePage]);

	return (
		<form onSubmit={(event) => event.preventDefault()}>
			<FormFieldSettings
				{...filteredSettingsContext}
				activePage={activePage}
				builderRules={rules}
				defaultLanguageId={defaultLanguageId}
				displayable={true}
				editable={false}
				editingLanguageId={editingLanguageId}
				onAction={({payload, type}) => {
					switch (type) {
						case CORE_EVENT_TYPES.PAGE.CHANGE:
							setActivePage(payload.activePage);
							break;
						case CORE_EVENT_TYPES.FIELD.BLUR:
						case CORE_EVENT_TYPES.FIELD.CHANGE: {
							dispatch({
								payload: {
									editingLanguageId:
										settingsContext.editingLanguageId,
									propertyName:
										payload.fieldInstance.fieldName,
									propertyValue: payload.value,
								},
								type,
							});

							break;
						}
						case CORE_EVENT_TYPES.FIELD.EVALUATE:
							dispatch({
								payload: {settingsContextPages: payload},
								type,
							});
							break;
						default:
							break;
					}
				}}
			>
				<Pages editable={false} overrides={{Column}} />
			</FormFieldSettings>
		</form>
	);
}
