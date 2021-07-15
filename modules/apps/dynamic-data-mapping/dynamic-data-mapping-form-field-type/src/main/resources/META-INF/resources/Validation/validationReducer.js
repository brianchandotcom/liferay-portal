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

import {StringUtils} from 'data-engine-js-components-web';

export const EVENT_TYPES = {
	CHANGE_ERROR_MESSAGE: 'changeErrorMessage',
	ENABLE_VALIDATION: 'enableValidation',
	SELECTED_VALIDATION: 'changeSelectedValidation',
	SET_PARAMETER: 'setParameter',
};

const reducer = ({editingLanguageId, fieldName, onChange}) => (
	state,
	action
) => {
	const onSave = (state) => {

		// Expression is mounted in the frontend, so we need to update
		// the expression every time we update the state

		let expression = {};

		if (state.enableValidation) {
			expression = {
				name: state.selectedValidation.name,
				value: StringUtils.subWords(state.selectedValidation.template, {
					name: fieldName,
				}),
			};
		}

		// Sends these values up using onChange, this way the data is saved

		const {enableValidation, errorMessage, parameter} = state;

		onChange({
			enableValidation,
			errorMessage,
			expression,
			parameter,
		});

		return {
			...state,
			expression,
		};
	};

	switch (action.type) {
		case EVENT_TYPES.ENABLE_VALIDATION: {
			const {enableValidation} = action.payload;

			return onSave({
				...state,
				enableValidation,
			});
		}
		case EVENT_TYPES.CHANGE_ERROR_MESSAGE: {
			const {errorMessage} = action.payload;

			return onSave({
				...state,
				errorMessage: {
					...state.errorMessage,
					[editingLanguageId]: errorMessage,
				},
			});
		}
		case EVENT_TYPES.CHANGE_SELECTED_VALIDATION: {
			const {selectedValidation} = action.payload;

			return onSave({
				...state,
				selectedValidation,
			});
		}
		case EVENT_TYPES.SET_PARAMETER: {
			const {parameter} = action.payload;

			return onSave({
				...state,
				parameter: {
					...state.parameter,
					[editingLanguageId]: parameter,
				},
			});
		}
		default:
			return state;
	}
};

export default reducer;
