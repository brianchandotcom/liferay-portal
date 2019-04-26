import {EDITABLE_FRAGMENT_ENTRY_PROCESSOR} from '../utils/constants';
import {setIn, updateIn} from '../utils/FragmentsEditorUpdateUtils.es';
import {updateEditableValues} from '../utils/FragmentsEditorFetchUtils.es';
import {UPDATE_EDITABLE_VALUE_ERROR, UPDATE_EDITABLE_VALUE_LOADING, UPDATE_EDITABLE_VALUE_SUCCESS} from './actions.es';
import debouncedAlert from '../utils/debouncedAlert.es';

/**
 * @type {number}
 */
const UPDATE_EDITABLE_VALUES_DELAY = 1500;

/**
 * @param {function} dispatch
 * @param {string} fragmentEntryLinkId
 * @param {object} previousEditableValues
 * @param {object} nextEditableValues
 * @review
 */
const debouncedUpdateEditableValues = debouncedAlert(
	/**
	 * @param {function} dispatch
	 * @param {string} fragmentEntryLinkId
	 * @param {object} previousEditableValues
	 * @param {object} nextEditableValues
 	 * @review
	 */
	(
		dispatch,
		fragmentEntryLinkId,
		previousEditableValues,
		nextEditableValues
	) => {
		updateEditableValues(
			fragmentEntryLinkId,
			nextEditableValues
		).then(
			() => {
				dispatch(updateEditableValueSuccessAction());
			}
		).catch(
			() => {
				dispatch(
					updateEditableValueErrorAction(
						fragmentEntryLinkId,
						previousEditableValues
					)
				);
			}
		);
	},

	UPDATE_EDITABLE_VALUES_DELAY
);

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

		dispatch(
			updateEditableValueLoadingAction(
				fragmentEntryLinkId,
				nextEditableValues
			)
		);

		debouncedUpdateEditableValues(
			dispatch,
			fragmentEntryLinkId,
			editableValues,
			nextEditableValues
		);
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

export {
	updateEditableValueAction,
	updateEditableValueSuccessAction
};