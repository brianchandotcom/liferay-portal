/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	if (!column.fields.length) {
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
		focusedField,
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
				builderRules={rules}
				defaultLanguageId={defaultLanguageId}
				displayable={true}
				editable={false}
				editingLanguageId={editingLanguageId}
				focusedField={field}
				formBuilder={{pages}}
				objectFields={objectFields}
				onAction={({payload, type}) => {
					switch (type) {
						case CORE_EVENT_TYPES.FIELD.BLUR:
						case CORE_EVENT_TYPES.FIELD.CHANGE: {
							if (payload.fieldInstance.fieldName !== 'options') {
								dispatch({
									payload: {
										propertyName:
											payload.fieldInstance.fieldName,
										propertyValue: payload.value,
									},
									type,
								});
							}
							else {
								dispatch({
									payload: {
										propertyName: focusedField.fieldName,
										propertyValue: focusedField.options,
									},
									type,
								});
							}

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
