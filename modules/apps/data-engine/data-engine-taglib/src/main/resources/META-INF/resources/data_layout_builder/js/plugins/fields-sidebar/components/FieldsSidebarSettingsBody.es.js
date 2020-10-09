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
import {
	EVENT_TYPES,
	FormProvider,
	Pages,
} from 'dynamic-data-mapping-form-renderer';
import React, {useContext, useEffect, useMemo, useState} from 'react';

import AppContext from '../../../AppContext.es';
import {EDIT_CUSTOM_OBJECT_FIELD} from '../../../actions.es';
import DataLayoutBuilderContext from '../../../data-layout-builder/DataLayoutBuilderContext.es';
import {getFilteredSettingsContext} from '../../../utils/settingsForm.es';

export default function () {
	const spritemap = useContext(ClayIconSpriteContext);

	const [dataLayoutBuilder] = useContext(DataLayoutBuilderContext);
	const [
		{
			config,
			dataLayout: {dataRules},
			editingLanguageId,
			focusedCustomObjectField,
			focusedField,
		},
		dispatch,
	] = useContext(AppContext);
	const [activePage, setActivePage] = useState(0);

	const {
		settingsContext: customObjectFieldSettingsContext,
	} = focusedCustomObjectField;
	const {settingsContext: fieldSettingsContext} = focusedField;
	const hasFocusedCustomObjectField = !!customObjectFieldSettingsContext;
	const settingsContext = hasFocusedCustomObjectField
		? customObjectFieldSettingsContext
		: fieldSettingsContext;

	const filteredSettingsContext = useMemo(
		() =>
			getFilteredSettingsContext({
				config,
				editingLanguageId,
				settingsContext,
			}),
		[config, editingLanguageId, settingsContext]
	);

	const dispatchEvent = (type, payload) => {
		if (hasFocusedCustomObjectField && type === 'fieldEdited') {
			dispatch({payload, type: EDIT_CUSTOM_OBJECT_FIELD});
		}
		else if (!hasFocusedCustomObjectField) {
			dataLayoutBuilder.dispatch(type, payload);
		}
	};

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
				{(props) => <Pages {...props} />}
			</FormProvider>
		</form>
	);
}
