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

import React, {
	useContext,
	useEffect,
	useLayoutEffect,
	useRef,
	useState,
} from 'react';

import AppContext from '../../../AppContext.es';
import {EDIT_CUSTOM_OBJECT_FIELD} from '../../../actions.es';
import DataLayoutBuilderContext from '../../../data-layout-builder/DataLayoutBuilderContext.es';
import renderSettingsForm, {
	getEvents,
	getFilteredSettingsContext,
} from '../../../utils/renderSettingsForm.es';

export default function () {
	const [dataLayoutBuilder] = useContext(DataLayoutBuilderContext);
	const [state, dispatch] = useContext(AppContext);
	const {focusedCustomObjectField, focusedField} = state;
	const {
		settingsContext: customObjectFieldSettingsContext,
	} = focusedCustomObjectField;
	const {settingsContext: fieldSettingsContext} = focusedField;
	const formRef = useRef();
	const [form, setForm] = useState(null);
	const hasFocusedCustomObjectField = !!customObjectFieldSettingsContext;
	const settingsContext = hasFocusedCustomObjectField
		? customObjectFieldSettingsContext
		: fieldSettingsContext;

	useEffect(() => {
		const filteredSettingsContext = getFilteredSettingsContext({
			config: state.config,
			settingsContext,
		});

		const dispatchEvent = (type, payload) => {
			if (hasFocusedCustomObjectField && type === 'fieldEdited') {
				dispatch({payload, type: EDIT_CUSTOM_OBJECT_FIELD});
			}
			else if (!hasFocusedCustomObjectField) {
				dataLayoutBuilder.dispatch(type, payload);
			}
		};

		if (form === null || form.isDisposed()) {
			setForm(
				renderSettingsForm(
					getEvents(dispatchEvent, settingsContext),
					filteredSettingsContext,
					formRef.current
				)
			);
		}
		else {
			const {pages, rules} = filteredSettingsContext;
			let newState = {pages, rules};

			if (form.activePage > pages.length - 1) {
				newState = {
					...newState,
					activePage: 0,
				};
			}

			form.setState({
				...newState,
				events: getEvents(dispatchEvent, settingsContext),
			});
		}
	}, [
		dataLayoutBuilder,
		dispatch,
		focusedField,
		form,
		formRef,
		hasFocusedCustomObjectField,
		settingsContext,
		state.config,
	]);

	useEffect(() => {
		return () => form && form.dispose();
	}, [form]);

	const focusedFieldName = focusedField.name;

	useLayoutEffect(() => {
		if (!form) {
			return;
		}

		form.once('rendered', () => {
			const firstInput = form.element.querySelector('input');

			if (firstInput && !form.element.contains(document.activeElement)) {
				firstInput.focus();

				if (firstInput.select) {
					firstInput.select();
				}
			}
		});
	}, [focusedFieldName, form]);

	return (
		<form onSubmit={(event) => event.preventDefault()} ref={formRef}></form>
	);
}
