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

import React, {useEffect, useRef} from 'react';

import {EVENT_TYPES as CORE_EVENT_TYPES} from '../../core/actions/eventTypes.es';
import {ConfigProvider} from '../../core/hooks/useConfig.es';
import {FormProvider, useForm} from '../../core/hooks/useForm.es';
import activePageReducer from '../../core/reducers/activePageReducer.es';
import fieldReducer from '../../core/reducers/fieldReducer.es';
import languageReducer from '../../core/reducers/languageReducer.es';
import pagesStructureReducer from '../../core/reducers/pagesStructureReducer.es';
import {getConnectedReactComponentAdapter} from '../../util/ReactComponentAdapter.es';
import {Form} from './FormView.es';
import {COMMON_INITIAL_CONFIG_STATE} from './config/initialConfigState.es';
import {COMMON_INITIAL_STATE} from './config/initialState.es';
import {pageValidationReducer, paginationReducer} from './reducers/index.es';
import {parseProps} from './util/parseProps.es';

/**
 * Updates the state of the FieldSettings when any value coming
 * from layers above changes.
 */
const StateSync = ({activePage, editingLanguageId, focusedField, pages}) => {
	const dispatch = useForm();

	useEffect(() => {
		dispatch({
			payload: pages,
			type: CORE_EVENT_TYPES.PAGE.UPDATE,
		});
	}, [dispatch, pages]);

	useEffect(() => {
		dispatch({payload: {activePage}, type: CORE_EVENT_TYPES.PAGE.CHANGE});
	}, [dispatch, activePage]);

	useEffect(() => {
		dispatch({
			payload: {
				activePage: focusedField?.settingsContext.currentPage,
				field: focusedField,
			},
			type: CORE_EVENT_TYPES.FIELD.CLICK,
		});
	}, [dispatch, focusedField]);

	useEffect(() => {
		dispatch({
			payload: {editingLanguageId},
			type: CORE_EVENT_TYPES.LANGUAGE.CHANGE,
		});
	}, [dispatch, editingLanguageId]);

	return null;
};

/**
 * Render a new form to be used in the Sidebar so that can edit the
 * properties of a field, a new FormProvider is needed to control
 * the reducers of a Field's settingsContext structure.
 */
export const FormFieldSettings = ({children, onAction, ...otherProps}) => {
	const {config, state} = parseProps(otherProps);

	return (
		<ConfigProvider
			config={config}
			initialConfig={COMMON_INITIAL_CONFIG_STATE}
		>
			<FormProvider
				initialState={COMMON_INITIAL_STATE}
				onAction={onAction}
				reducers={[
					activePageReducer,
					fieldReducer,
					languageReducer,
					pagesStructureReducer,
					pageValidationReducer,
					paginationReducer,
				]}
				value={state}
			>
				<StateSync {...state} />
				{children}
			</FormProvider>
		</ConfigProvider>
	);
};

FormFieldSettings.displayName = 'FormFieldSettings';

/**
 * This component is temporary and for exclusive use for Sidebar
 * in Metal.js, this creates a form for editing the properties
 * of a field in Form Builder.
 */
export const FormFieldSettingsAdapter = getConnectedReactComponentAdapter(
	React.forwardRef(({instance, ...otherProps}, ref) => {
		const defaultRef = useRef(null);

		return (
			<FormFieldSettings
				{...otherProps}
				onAction={({payload, type}) => instance.emit(type, payload)}
			>
				<Form ref={ref ?? defaultRef} />
			</FormFieldSettings>
		);
	})
);
