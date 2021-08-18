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

import {getFilteredSettingsContext} from '../../../utils/settingsForm.es';

/**
 * This component will override the Column from Form Renderer.
 */
const getColumn = ({objectFields}) => ({children, column, index}) => {
	if (column.fields.length === 0) {
		return null;
	}

	return (
		<ClayLayout.Col key={index} md={column.size}>
			{column.fields.map((field, index) => {
				const {fieldName} = field;

				// Avoid using repeatable and searchable fields when object storage type is selected

				if (
					objectFields.length &&
					(fieldName === 'repeatable' || fieldName === 'indexType')
				) {
					return <React.Fragment key={index} />;
				}

				return children({field, index});
			})}
		</ClayLayout.Col>
	);
};

export default function FieldsSidebarSettingsBody() {
	const [activePage, setActivePage] = useState(0);
	const {
		defaultLanguageId,
		editingLanguageId,
		focusedField,
		objectFields,
		pages,
		rules,
	} = useFormState();
	const config = useConfig();
	const dispatch = useForm();

	const Column = useMemo(() => getColumn({objectFields}), [objectFields]);

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
				builderPages={pages}
				builderRules={rules}
				defaultLanguageId={defaultLanguageId}
				displayable={true}
				editable={false}
				editingLanguageId={editingLanguageId}
				focusedField={focusedField}
				objectFields={objectFields}
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
				submitButtonId={config.submitButtonId}
			>
				<Pages
					editable={false}
					overrides={{
						...(objectFields && {Column}),
					}}
				/>
			</FormFieldSettings>
		</form>
	);
}
