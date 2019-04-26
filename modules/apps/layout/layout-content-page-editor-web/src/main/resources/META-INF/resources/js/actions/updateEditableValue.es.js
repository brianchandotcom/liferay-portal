import {EDITABLE_FRAGMENT_ENTRY_PROCESSOR} from '../utils/constants';
import {enableSavingChangesStatusAction, disableSavingChangesStatusAction, updateLastSaveDateAction} from './saveChanges.es';
import {setIn, updateIn} from '../utils/FragmentsEditorUpdateUtils.es';
import {updateEditableValues} from '../utils/FragmentsEditorFetchUtils.es';
import {UPDATE_EDITABLE_VALUE_SUCCESS} from './actions.es';

/**
 * @param {string} fragmentEntryLinkId
 * @param {string} editableId
 * @param {string} editableValueId
 * @param {string} editableValue
 * @param {string} [editableValueSegmentsExperienceId='']
 * @return {function}
 * @review
 */
function updateEditableValueAction(
	fragmentEntryLinkId,
	editableId,
	editableValueId,
	editableValue,
	editableValueSegmentsExperienceId = ''
) {
	/**
	 * @param {function} dispatch
	 * @param {function} getState
	 */
	return function(dispatch, getState) {
		const state = getState();

		const {editableValues} = state.fragmentEntryLinks[
			fragmentEntryLinkId
		];

		const keysTreeArray = editableValueSegmentsExperienceId ? [
			EDITABLE_FRAGMENT_ENTRY_PROCESSOR,
			editableId,
			editableValueSegmentsExperienceId
		] : [
			EDITABLE_FRAGMENT_ENTRY_PROCESSOR,
			editableId
		];

		let nextEditableValues = setIn(
			editableValues,
			[...keysTreeArray, editableValueId],
			editableValue
		);

		if (editableValueId === 'mappedField') {
			nextEditableValues = updateIn(
				nextEditableValues,
				keysTreeArray,
				editableValue => {
					const nextEditableValue = Object.assign({}, editableValue);

					[
						'config',
						state.defaultSegmentsEntryId,
						...Object.keys(state.availableLanguages),
						...Object.keys(state.availableSegmentsEntries)
					].forEach(
						key => {
							delete nextEditableValue[key];
						}
					);

					return nextEditableValue;
				}
			);
		}

		dispatch(enableSavingChangesStatusAction());

		updateEditableValues(
			fragmentEntryLinkId,
			nextEditableValues
		).then(
			() => {
				dispatch(
					updateEditableValueSuccessAction(
						fragmentEntryLinkId,
						editableValues
					)
				);

				dispatch(updateLastSaveDateAction());
				dispatch(disableSavingChangesStatusAction());
			}
		).catch(
			() => {
				dispatch(disableSavingChangesStatusAction());
				dispatch(updateLastSaveDateAction());
			}
		);
	};
}

/**
 * @param {string} fragmentEntryLinkId
 * @param {object} editableValues
 * @return {object}
 * @review
 */
function updateEditableValueSuccessAction(fragmentEntryLinkId, editableValues) {
	return {
		editableValues,
		fragmentEntryLinkId,
		type: UPDATE_EDITABLE_VALUE_SUCCESS
	};
}

export {
	updateEditableValueAction,
	updateEditableValueSuccessAction
};