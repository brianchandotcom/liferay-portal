import {ADD_FRAGMENT_ENTRY_LINK, CHANGE_LANGUAGE_ID, CREATE_SEGMENTS_EXPERIENCE, REMOVE_FRAGMENT_ENTRY_LINK, SELECT_SEGMENTS_EXPERIENCE, UPDATE_TRANSLATION_STATUS} from '../actions/actions.es';
import {setIn} from '../utils/FragmentsEditorUpdateUtils.es';
import {prefixSegmentsExperienceId} from '../utils/prefixSegmentsExperienceId.es';

const EDITABLE_VALUES_KEY = 'com.liferay.fragment.entry.processor.editable.EditableFragmentEntryProcessor';

/**
 * Reducer for changing languageId
 * @param {!object} state
 * @param {!string} actionType
 * @param {object} payload
 * @param {string} payload.languageId
 * @return {object}
 * @review
 */
function languageIdReducer(state, actionType, payload) {
	let nextState = state;

	if (actionType === CHANGE_LANGUAGE_ID) {
		nextState = setIn(nextState, ['languageId'], payload.languageId);
	}

	return nextState;
}

/**
 * @param {!object} state
 * @param {!string} actionType
 * @return {object}
 * @review
 */
function translationStatusReducer(state, actionType) {
	let nextState = state;

	if (
		actionType === ADD_FRAGMENT_ENTRY_LINK ||
		actionType === UPDATE_TRANSLATION_STATUS ||
		actionType === REMOVE_FRAGMENT_ENTRY_LINK ||
		actionType === SELECT_SEGMENTS_EXPERIENCE ||
		actionType === CREATE_SEGMENTS_EXPERIENCE
	) {
		const segmentsExperienceId = nextState.segmentsExperienceId || nextState.defaultSegmentsExperienceId;

		const nextTranslationStatus = _getTranslationStatus(
			_getLanguageKeys(nextState.availableLanguages),
			_getEditableValues(nextState.fragmentEntryLinks),
			prefixSegmentsExperienceId(segmentsExperienceId)
		);

		nextState = setIn(nextState, ['translationStatus'], nextTranslationStatus);
	}

	return nextState;
}

/**
 * Gets all editable values in the current fragment
 * @param {object[]} fragmentEntryLinks
 * @return {object[]}
 * @private
 * @review
 */
function _getEditableValues(fragmentEntryLinks) {
	return Object.values(fragmentEntryLinks)
		.filter(
			fragmentEntryLink => fragmentEntryLink.editableValues
		)
		.map(
			fragmentEntryLink => (
				fragmentEntryLink.editableValues[EDITABLE_VALUES_KEY]
			)
		)
		.filter(
			editableValues => editableValues
		);
}

/**
 * Return the language keys for a given language object
 * @param {object} availableLanguages
 * @return {string[]}
 */
function _getLanguageKeys(availableLanguages) {
	return Object
		.keys(availableLanguages)
		.filter(languageId => languageId !== '_INJECTED_DATA_');
}

/**
 * Gets the translation status for a given set of parameters
 * Translation status: {
 * 	languageValues: {{
 * 		languageId: string
 * 		values: Array<string>
 *  }},
 *  translationKeys: Array<string>
 * }
 *
 * @param {string[]} languageIds The set of languageIds to check
 * @param {{editableValues: object}[]} editableValues The current editable values state
 * @private
 * @return {object} A translation status object
 * @review
 */
function _getTranslationStatus(languageIds, editableValues, segmentsExperienceId) {
	const translationKeys = editableValues.map(
		editableValue => Object.keys(editableValue).map(
			editableValueId => editableValue[editableValueId].defaultValue
		)
	).reduce(
		(acc, val) => acc.concat(val),
		[]
	);

	const languageValues = languageIds.map(
		languageId => {
			const values = editableValues.map(
				editableValue => Object.keys(editableValue).map(
					editableValueId => {
						let result;
						if (!segmentsExperienceId) {
							result = editableValue &&
								editableValue[editableValueId] &&
								editableValue[editableValueId][languageId];
						}
						else {
							result = editableValue &&
								editableValue[editableValueId] &&
								editableValue[editableValueId][segmentsExperienceId] &&
								editableValue[editableValueId][segmentsExperienceId][languageId];
						}
						
						return result;
					}
				)
			)
				.reduce(
					(acc, val) => acc.concat(val),
					[]
				)
				.filter(
					localeValue => localeValue
				);

			return {
				languageId,
				values
			};
		}
	);

	return {
		languageValues,
		translationKeys
	};
}

export {
	languageIdReducer,
	translationStatusReducer
};