/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
