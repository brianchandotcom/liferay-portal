import {
	ADD_FRAGMENT_ENTRY_LINK,
	REMOVE_FRAGMENT_ENTRY_LINK,
	UPDATE_TRANSLATION_STATUS
} from '../actions/actions.es';

const EDITABLE_VALUES_KEY = 'com.liferay.fragment.entry.processor.editable.EditableFragmentEntryProcessor';

/**
 * @param {!object} state
 * @param {!string} actionType
 * @return {object}
 * @review
 */

function translationStatusReducer(state, actionType) {
	const nextState = Object.assign({}, state);

	if (
		actionType === ADD_FRAGMENT_ENTRY_LINK ||
		actionType === UPDATE_TRANSLATION_STATUS ||
		actionType === REMOVE_FRAGMENT_ENTRY_LINK
	) {
		nextState.translationStatus = _getTranslationStatus(
			_getLanguageKeys(state.availableLanguages),
			_getEditableValues(state.fragmentEntryLinks)
		);
	}

	return nextState;
}

/**
 * Gets all editable values in the current fragment
 * @returns Array<Object>
 * @private
 * @review
 */

function _getEditableValues(fragmentEntryLinks) {
	return fragmentEntryLinks
		.map(
			fragmentEntryLink => (
				fragmentEntryLink.editableValues[EDITABLE_VALUES_KEY]
			)
		)
		.filter(editableValues => editableValues);
}

function _getLanguageKeys(availableLanguages) {
	return Object
		.keys(availableLanguages)
		.filter(languageId => languageId !== '_INJECTED_DATA_');
}

/**
 * Gets the translation status for a given set of parameters
 * @param languageIds The set of languageIds to check
 * @param editableValues The current editable values state
 * @private
 * @returns {{
 * 	languageValues: {{
 * 		languageId: string
 * 		values: Array<string>
 *  }},
 *  translationKeys: Array<string>
 * }} A translation status object
 * @review
 */

function _getTranslationStatus(languageIds, editableValues) {
	const translationKeys = editableValues.map(
		editableValue => {
			return Object.keys(editableValue).map(
				editableValueId => editableValue[editableValueId].defaultValue
			);
		}
	).reduce(
		(acc, val) => acc.concat(val),
		[]
	);

	const languageValues = languageIds.map(
		languageId => {
			const values = editableValues.map(
				editableValue => {
					return Object.keys(editableValue).map(
						editableValueId => editableValue[editableValueId][languageId]
					);
				}
			).reduce(
				(acc, val) => acc.concat(val),
				[]
			).filter(
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

export {translationStatusReducer};