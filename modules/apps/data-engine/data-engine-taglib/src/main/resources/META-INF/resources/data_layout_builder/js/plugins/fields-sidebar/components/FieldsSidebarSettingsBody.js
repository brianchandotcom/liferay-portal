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
	EVENT_TYPES as CORE_EVENT_TYPES,
	FormFieldSettings,
	Pages,
	useConfig,
	useForm,
	useFormState,
} from 'data-engine-js-components-web';
import React, {useMemo} from 'react';

import {useSettingsContextFilter} from '../../../utils/settingsForm.es';

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
					!!objectFields.length &&
					(fieldName === 'repeatable' || fieldName === 'indexType')
				) {
					return <React.Fragment key={index} />;
				}

				return children({field, index});
			})}
		</ClayLayout.Col>
	);
};

export default function FieldsSidebarSettingsBody({field}) {
	const {
		defaultLanguageId,
		editingLanguageId,
		objectFields,
		pages,
		rules,
	} = useFormState();
	const {submitButtonId} = useConfig();
	const dispatch = useForm();

	const Column = useMemo(() => getColumn({objectFields}), [objectFields]);

	const filteredSettingsContext = useSettingsContextFilter(
		field.settingsContext
	);

	return (
		<form onSubmit={(event) => event.preventDefault()}>
			<FormFieldSettings
				{...filteredSettingsContext}
				builderPages={pages}
				builderRules={rules}
				defaultLanguageId={defaultLanguageId}
				displayable={true}
				editable={false}
				editingLanguageId={editingLanguageId}
				focusedField={field}
				objectFields={objectFields}
				onAction={({payload, type}) => {
					switch (type) {
						case CORE_EVENT_TYPES.FIELD.BLUR:
						case CORE_EVENT_TYPES.FIELD.CHANGE: {
							dispatch({
								payload: {
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
				submitButtonId={submitButtonId}
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
