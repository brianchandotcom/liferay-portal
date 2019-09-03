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
import {
	EDITABLE_FRAGMENT_ENTRY_PROCESSOR,
	FREEMARKER_FRAGMENT_ENTRY_PROCESSOR
} from '../utils/constants';
import {
	deleteIn,
	setIn,
	updateIn
} from '../utils/FragmentsEditorUpdateUtils.es';
import {
	UPDATE_EDITABLE_VALUE_ERROR,
	UPDATE_EDITABLE_VALUE_LOADING,
	UPDATE_EDITABLE_VALUE_SUCCESS,
	UPDATE_FRAGMENT_ENTRY_LINK_CONTENT
} from './actions.es';
import {updateEditableValues} from '../utils/FragmentsEditorFetchUtils.es';
import {prefixSegmentsExperienceId} from '../utils/prefixSegmentsExperienceId.es';
import {getFragmentEntryLinkContent} from '../reducers/fragments.es';
import {updatePageContentsAction} from './updatePageContents.es';

/**
 * @param {number} fragmentEntryLinkId
 * @param {object} configurationValues
 * @param {number} segmentsExperienceId
 * @review
 */
function updateConfigurationValueAction(
	fragmentEntryLinkId,
	configurationValues,
	segmentsExperienceId
) {
	return function(dispatch, getState) {
		const state = getState();

		const prefixedSegmentsExperienceId = prefixSegmentsExperienceId(
			segmentsExperienceId
		);

		const previousEditableValues =
			state.fragmentEntryLinks[fragmentEntryLinkId].editableValues;

		const keyPath = prefixedSegmentsExperienceId
			? [
					FREEMARKER_FRAGMENT_ENTRY_PROCESSOR,
					prefixedSegmentsExperienceId
			  ]
			: [FREEMARKER_FRAGMENT_ENTRY_PROCESSOR];

		const nextEditableValues = setIn(
			previousEditableValues,
			keyPath,
			configurationValues
		);

		dispatch(
			updateEditableValueLoadingAction(
				fragmentEntryLinkId,
				nextEditableValues
			)
		);

		dispatch(enableSavingChangesStatusAction());

		return updateEditableValues(fragmentEntryLinkId, nextEditableValues)
			.then(() => {
				dispatch(updateEditableValueSuccessAction());
				dispatch(disableSavingChangesStatusAction());
				dispatch(updateLastSaveDateAction());
			})
			.then(() => {
				dispatch(
					updateFragmentEntryLinkContent(
						fragmentEntryLinkId,
						segmentsExperienceId
					)
				);
			})
			.catch(() => {
				dispatch(
					updateEditableValueErrorAction(
						fragmentEntryLinkId,
						previousEditableValues
					)
				);

				dispatch(disableSavingChangesStatusAction());
			});
	};
}

/**
 * @param {!object} data
 * @param {!string} data.fragmentEntryLinkId
 * @param {!string} data.editableValueContent
 * @param {!string} data.processor
 * @param {string} data.editableId
 * @param {string} data.editableValueId
 * @param {string} data.segmentsExperienceId
 * @return {function}
 * @review
 */
function updateEditableValueAction(data) {
	return updateEditableValuesAction(
		data.fragmentEntryLinkId,
		data.editableId,
		[
			{
				content: data.editableValueContent,
				editableValueId: data.editableValueId
			}
		],
		data.processor,
		data.segmentsExperienceId
	);
}

/**
 * @param {string} fragmentEntryLinkId
 * @param {string} editableId
 * @param {Array<{editableValueId: string, content: string}>} editableValues
 * @param {string} [processor=EDITABLE_FRAGMENT_ENTRY_PROCESSOR]
 * @param {string} [segmentsExperienceId='']
 * @return {function}
 * @review
 */
function updateEditableValuesMappingAction(
	fragmentEntryLinkId,
	editableId,
	editableValues,
	processor = EDITABLE_FRAGMENT_ENTRY_PROCESSOR,
	segmentsExperienceId = ''
) {
	return function(dispatch) {
		dispatch(
			updateEditableValuesAction(
				fragmentEntryLinkId,
				editableId,
				editableValues,
				processor,
				segmentsExperienceId
			)
		).dispatch(updatePageContentsAction());
	};
}

/**
 * @param {string} fragmentEntryLinkId
 * @param {string} editableId
 * @param {Array<{editableValueId: string, content: string}>} editableValues
 * @param {string} [processor=EDITABLE_FRAGMENT_ENTRY_PROCESSOR]
 * @param {string} [editableValueSegmentsExperienceId='']
 * @return {function}
 * @review
 */
function updateEditableValuesAction(
	fragmentEntryLinkId,
	editableId,
	editableValues,
	processor = EDITABLE_FRAGMENT_ENTRY_PROCESSOR,
	segmentsExperienceId = ''
) {
	return function(dispatch, getState) {
		const state = getState();

		const previousEditableValues =
			state.fragmentEntryLinks[fragmentEntryLinkId].editableValues;

		let keysTreeArray = [processor];

		if (editableId) {
			keysTreeArray = [...keysTreeArray, editableId];
		}

		if (segmentsExperienceId) {
			keysTreeArray = [...keysTreeArray, segmentsExperienceId];
		}

		let nextEditableValues = previousEditableValues;

		editableValues.forEach(editableValue => {
			if (!editableValue.content) {
				nextEditableValues = deleteIn(
					nextEditableValues,
					editableValue.editableValueId
						? [...keysTreeArray, editableValue.editableValueId]
						: keysTreeArray
				);
			} else {
				nextEditableValues = setIn(
					nextEditableValues,
					editableValue.editableValueId
						? [...keysTreeArray, editableValue.editableValueId]
						: keysTreeArray,
					editableValue.content
				);
			}

			if (editableValue.editableValueId === 'mappedField') {
				nextEditableValues = updateIn(
					nextEditableValues,
					keysTreeArray,
					previousEditableValue => {
						const nextEditableValue = Object.assign(
							{},
							previousEditableValue
						);

						[
							'config',
							state.defaultSegmentsEntryId,
							...Object.keys(state.availableLanguages),
							...Object.keys(state.availableSegmentsEntries)
						].forEach(key => {
							delete nextEditableValue[key];
						});

						return nextEditableValue;
					}
				);
			}
		});

		dispatch(
			updateEditableValueLoadingAction(
				fragmentEntryLinkId,
				nextEditableValues
			)
		);

		dispatch(enableSavingChangesStatusAction());

		return updateEditableValues(fragmentEntryLinkId, nextEditableValues)
			.then(() => {
				dispatch(updateEditableValueSuccessAction());
				dispatch(disableSavingChangesStatusAction());
				dispatch(updateLastSaveDateAction());
			})
			.catch(() => {
				dispatch(
					updateEditableValueErrorAction(
						fragmentEntryLinkId,
						previousEditableValues
					)
				);

				dispatch(disableSavingChangesStatusAction());
			});
	};
}

/**
 * @param {string} fragmentEntryLinkId
 * @param {object} editableValues
 * @param {Date} [date=new Date()]
 * @return {object}
 * @review
 */
function updateEditableValueErrorAction(
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
function updateEditableValueLoadingAction(fragmentEntryLinkId, editableValues) {
	return {
		editableValues,
		fragmentEntryLinkId,
		type: UPDATE_EDITABLE_VALUE_LOADING
	};
}

/**
 * @param {Date} [date=new Date()]
 * @return {object}
 * @review
 */
function updateEditableValueSuccessAction(date = new Date()) {
	return {
		date,
		type: UPDATE_EDITABLE_VALUE_SUCCESS
	};
}

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

export {
	updateConfigurationValueAction,
	updateFragmentEntryLinkContent,
	updateEditableValueAction,
	updateEditableValuesMappingAction,
	updateEditableValuesAction,
	updateEditableValueSuccessAction
};
