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

import {setIn} from '../utils/FragmentsEditorUpdateUtils.es';

/**
 * @param {object} state
 * @return {object}
 * @review
 */
function openAssetTypeDialogReducer(state) {
	return setIn(state, ['selectMappingTypeDialogVisible'], true);
}

/**
 * @param {object} state
 * @param {object} action
 * @param {string} action.editableId
 * @param {string} action.editableType
 * @param {string} action.fragmentEntryLinkId
 * @param {string} action.mappedFieldId
 * @param {string} action.type
 * @return {object}
 * @review
 */
function openMappingFieldsDialogReducer(state, action) {
	let nextState = state;

	nextState = setIn(
		nextState,
		['selectMappingDialogEditableId'],
		action.editableId
	);

	nextState = setIn(
		nextState,
		['selectMappingDialogEditableType'],
		action.editableType
	);

	nextState = setIn(
		nextState,
		['selectMappingDialogMappedFieldId'],
		action.mappedFieldId
	);

	nextState = setIn(
		nextState,
		['selectMappingDialogFragmentEntryLinkId'],
		action.fragmentEntryLinkId
	);

	if (nextState.selectedMappingTypes && nextState.selectedMappingTypes.type) {
		nextState = setIn(nextState, ['selectMappingDialogVisible'], true);
	} else {
		nextState = setIn(nextState, ['selectMappingTypeDialogVisible'], true);
	}

	return nextState;
}

/**
 * @param {object} state
 * @param {object} action
 * @param {{}} action.mappingTypes
 * @param {string} action.selectedMappingSubtypeId
 * @param {string} action.selectedMappingTypeId
 * @param {string} action.type
 * @return {object}
 * @review
 */
function selectMappeableTypeReducer(state, action) {
	return new Promise(resolve => {
		let nextState = state;

		_selectMappingType(
			nextState.classPK,
			nextState.portletNamespace,
			action.selectedMappingSubtypeId,
			action.selectedMappingTypeId,
			nextState.updateLayoutPageTemplateEntryAssetTypeURL
		)
			.then(() => {
				nextState = setIn(
					nextState,
					['selectedMappingTypes'],
					action.mappingTypes
				);

				if (
					nextState.selectMappingDialogFragmentEntryLinkId &&
					nextState.selectMappingDialogEditableId
				) {
					nextState = setIn(
						nextState,
						['selectMappingDialogVisible'],
						true
					);
				}

				resolve(nextState);
			})
			.catch(() => {
				resolve(nextState);
			});
	});
}

/**
 * @param {object} state
 * @return {object}
 * @review
 */
function hideMappingDialogReducer(state) {
	return setIn(state, ['selectMappingDialogVisible'], false);
}

/**
 * @param {object} state
 * @return {object}
 * @review
 */
function hideMappingTypeDialogReducer(state) {
	return setIn(state, ['selectMappingTypeDialogVisible'], false);
}

/**
 * @param {string} classPK
 * @param {string} portletNamespace
 * @param {string} selectedMappingSubtypeId
 * @param {string} selectedMappingTypeId
 * @param {string} updateLayoutPageTemplateEntryAssetTypeURL
 * @return {object}
 * @review
 */
function _selectMappingType(
	classPK,
	portletNamespace,
	selectedMappingSubtypeId,
	selectedMappingTypeId,
	updateLayoutPageTemplateEntryAssetTypeURL
) {
	const formData = new FormData();

	formData.append(`${portletNamespace}classTypeId`, selectedMappingSubtypeId);
	formData.append(`${portletNamespace}classNameId`, selectedMappingTypeId);
	formData.append(`${portletNamespace}classPK`, classPK);

	return fetch(updateLayoutPageTemplateEntryAssetTypeURL, {
		body: formData,
		credentials: 'include',
		method: 'POST'
	}).then(response => response.json());
}

export {
	hideMappingDialogReducer,
	hideMappingTypeDialogReducer,
	openAssetTypeDialogReducer,
	openMappingFieldsDialogReducer,
	selectMappeableTypeReducer
};
