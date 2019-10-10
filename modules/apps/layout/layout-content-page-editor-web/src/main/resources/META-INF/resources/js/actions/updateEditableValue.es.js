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

import {
	disableSavingChangesStatusAction,
	enableSavingChangesStatusAction,
	updateLastSaveDateAction
} from './saveChanges.es';
import {FREEMARKER_FRAGMENT_ENTRY_PROCESSOR} from '../utils/constants';
import {setIn, updateIn} from '../utils/FragmentsEditorUpdateUtils.es';
import {
	UPDATE_EDITABLE_VALUE_ERROR,
	UPDATE_EDITABLE_VALUE_LOADING,
	UPDATE_FRAGMENT_ENTRY_LINK_CONTENT
} from './actions.es';
import {updateEditableValues} from '../utils/FragmentsEditorFetchUtils.es';
import {prefixSegmentsExperienceId} from '../utils/prefixSegmentsExperienceId.es';
import {getFragmentEntryLinkContent} from '../reducers/fragments.es';
import {updatePageContentsAction} from './updatePageContents.es';

/**
 * Sets the editable value content.
 * @param {string} fragmentEntryLinkId
 * @param {EDITABLE_FRAGMENT_ENTRY_PROCESSOR|BACKGROUND_IMAGE_FRAGMENT_ENTRY_PROCESSOR} processor
 * @param {string} editableId
 * @param {string} content
 */
const updateEditableValueContentAction = (
	fragmentEntryLinkId,
	processor,
	editableId,
	content
) => (dispatch, getState) =>
	_sendEditableValues(
		fragmentEntryLinkId,
		[
			...['classNameId', 'classPK', 'fieldId', 'mappedField'].map(
				field => ({
					path: [processor, editableId, field]
				})
			),
			{
				content,
				path: _getSegmentsExperienceId(getState)
					? [
							processor,
							editableId,
							_getSegmentsExperienceId(getState),
							_getLanguageId(getState)
					  ]
					: [processor, editableId, _getLanguageId(getState)]
			}
		],
		dispatch,
		getState
	);

/**
 * Sets the editable value mappedField.
 * @param {string} fragmentEntryLinkId
 * @param {EDITABLE_FRAGMENT_ENTRY_PROCESSOR|BACKGROUND_IMAGE_FRAGMENT_ENTRY_PROCESSOR} processor
 * @param {string} editableId
 * @param {string} mappedField
 */
const updateEditableValueMappedFieldAction = (
	fragmentEntryLinkId,
	processor,
	editableId,
	mappedField
) => (dispatch, getState) =>
	_sendEditableValues(
		fragmentEntryLinkId,
		[
			{
				path: [processor, editableId]
			},
			{
				content: mappedField,
				path: [processor, editableId, 'mappedField']
			}
		],
		dispatch,
		getState
	);

/**
 * Sets the editable value classNameId, classPK and fieldId.
 * @param {string} fragmentEntryLinkId
 * @param {EDITABLE_FRAGMENT_ENTRY_PROCESSOR|BACKGROUND_IMAGE_FRAGMENT_ENTRY_PROCESSOR} processor
 * @param {string} editableId
 * @param {object} mapData
 * @param {string} mapData.classNameId
 * @param {string} mapData.classPK
 * @param {string} mapData.fieldId
 */
const updateEditableValueFieldIdAction = (
	fragmentEntryLinkId,
	processor,
	editableId,
	mapData
) => (dispatch, getState) =>
	_sendEditableValues(
		fragmentEntryLinkId,
		[
			{
				path: [processor, editableId]
			},
			...Object.entries(mapData).map(([key, value]) => ({
				content: value,
				path: [processor, editableId, key]
			}))
		],
		dispatch,
		getState
	);

/**
 * @param {number} fragmentEntryLinkId
 * @param {object} configuration
 * @review
 */
const updateFragmentConfigurationAction = (
	fragmentEntryLinkId,
	configuration
) => (dispatch, getState) =>
	_sendEditableValues(
		fragmentEntryLinkId,
		[
			{
				content: configuration,
				path: _getSegmentsExperienceId(getState)
					? [
							FREEMARKER_FRAGMENT_ENTRY_PROCESSOR,
							_getSegmentsExperienceId(getState)
					  ]
					: [FREEMARKER_FRAGMENT_ENTRY_PROCESSOR]
			}
		],
		dispatch,
		getState
	)
		.then(() => {
			const state = getState();

			return dispatch(
				updateFragmentEntryLinkContent(
					fragmentEntryLinkId,
					state.segmentsExperienceId ||
						state.defaultSegmentsExperienceId
				)
			);
		})
		.then(() => dispatch(updatePageContentsAction()));

/**
 * @param {number} fragmentEntryLinkId
 * @param {number} segmentsExperienceId
 * @review
 */
function updateFragmentEntryLinkContent(
	fragmentEntryLinkId,
	segmentsExperienceId
) {
	return function(dispatch, getState) {
		const state = getState();

		const fragmentEntryLink = state.fragmentEntryLinks[fragmentEntryLinkId];

		getFragmentEntryLinkContent(
			state.renderFragmentEntryURL,
			fragmentEntryLink,
			state.portletNamespace,
			segmentsExperienceId
		).then(response => {
			const {fragmentEntryLinkId, content} = response;

			dispatch({
				fragmentEntryLinkContent: content,
				fragmentEntryLinkId,
				type: UPDATE_FRAGMENT_ENTRY_LINK_CONTENT
			});
		});
	};
}

/**
 * @param {function} getState
 */
const _getLanguageId = getState => {
	const state = getState();

	return state.languageId || state.defaultLanguageId;
};

/**
 * @param {function} getState
 */
const _getSegmentsExperienceId = getState => {
	const state = getState();

	return prefixSegmentsExperienceId(
		state.segmentsExperienceId || state.defaultSegmentsExperienceId
	);
};

/**
 *
 * @param {object} editableValues
 * @param {{path: string[], content: any}} change
 */
const _mergeChange = (editableValues, change) => {
	if (!change.content) {
		return updateIn(editableValues, change.path, editable => {
			let newEditable = undefined;

			if (editable && (editable.defaultValue || editable.config)) {
				newEditable = {
					config: editable.config,
					defaultValue: editable.defaultValue
				};
			}

			return newEditable;
		});
	}

	return setIn(editableValues, change.path, change.content);
};

/**
 *
 * @param {string} fragmentEntryLinkId
 * @param {Array<{path: string[], content: any}>} changes
 * @param {function} dispatch
 * @param {function} getState
 */
const _sendEditableValues = (
	fragmentEntryLinkId,
	changes,
	dispatch,
	getState
) => {
	const previousEditableValues = getState().fragmentEntryLinks[
		fragmentEntryLinkId
	].editableValues;

	const nextEditableValues = changes.reduce(
		(editableValues, change) => _mergeChange(editableValues, change),
		previousEditableValues
	);

	dispatch(
		_updateEditableValuesLoadingAction(
			fragmentEntryLinkId,
			nextEditableValues
		)
	);

	dispatch(enableSavingChangesStatusAction());

	return updateEditableValues(fragmentEntryLinkId, nextEditableValues)
		.then(() => {
			dispatch(disableSavingChangesStatusAction());
			dispatch(updateLastSaveDateAction());
		})
		.then(() => {
			const hasContentChanges = changes.some(
				change =>
					change.path.includes('mappedField') ||
					change.path.includes('fieldId')
			);

			if (hasContentChanges) {
				return dispatch(updatePageContentsAction());
			}
		})
		.catch(() => {
			dispatch(
				_updateEditableValuesErrorAction(
					fragmentEntryLinkId,
					previousEditableValues
				)
			);

			dispatch(disableSavingChangesStatusAction());
		});
};

/**
 * @param {string} fragmentEntryLinkId
 * @param {object} editableValues
 * @param {Date} [date=new Date()]
 * @return {object}
 * @review
 */
function _updateEditableValuesErrorAction(
	fragmentEntryLinkId,
	editableValues,
	date = new Date()
) {
	return {
		date,
		editableValues,
		fragmentEntryLinkId,
		type: UPDATE_EDITABLE_VALUE_ERROR
	};
}

/**
 * @param {string} fragmentEntryLinkId
 * @param {object} editableValues
 * @return {object}
 * @review
 */
function _updateEditableValuesLoadingAction(
	fragmentEntryLinkId,
	editableValues
) {
	return {
		editableValues,
		fragmentEntryLinkId,
		type: UPDATE_EDITABLE_VALUE_LOADING
	};
}

export {
	updateEditableValueContentAction,
	updateEditableValueMappedFieldAction,
	updateEditableValueFieldIdAction,
	updateFragmentConfigurationAction,
	updateFragmentEntryLinkContent
};
