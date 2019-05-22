import Component from 'metal-component';
import Soy from 'metal-soy';

import getConnectedComponent from '../../store/ConnectedComponent.es';
import {CHANGE_LANGUAGE_ID} from '../../actions/actions.es';
import {
	BACKGROUND_IMAGE_FRAGMENT_ENTRY_PROCESSOR,
	EDITABLE_FRAGMENT_ENTRY_PROCESSOR
} from '../../utils/constants';
import {prefixSegmentsExperienceId} from '../../utils/prefixSegmentsExperienceId.es';
import {setIn} from '../../utils/FragmentsEditorUpdateUtils.es';
import templates from './TranslationStatus.soy';

/**
 * TranslationStatus
 */
class TranslationStatus extends Component {
	/**
	 * @param {object} editableValue
	 * @param {string} languageId
	 * @param {string} segmentExperienceId
	 * @return {boolean}
	 */
	static _editableValueIsTranslated(
		editableValue,
		languageId,
		segmentExperienceId
	) {
		return (
			editableValue[languageId] ||
			(segmentExperienceId in editableValue &&
				editableValue[segmentExperienceId][languageId])
		);
	}

	/**
	 * @inheritDoc
	 * @review
	 */
	prepareStateForRender(state) {
		let nextState = state;

		const editableValues = Object.values(state.fragmentEntryLinks)
			.filter(fragmentEntryLink => fragmentEntryLink.editableValues)
			.map(fragmentEntryLink => [
				...Object.values(
					fragmentEntryLink.editableValues[
						EDITABLE_FRAGMENT_ENTRY_PROCESSOR
					] || {}
				),
				...Object.values(
					fragmentEntryLink.editableValues[
						BACKGROUND_IMAGE_FRAGMENT_ENTRY_PROCESSOR
					] || {}
				)
			])
			.reduce(
				(editableValuesA, editableValuesB) => [
					...editableValuesA,
					...editableValuesB
				],
				[]
			);

		nextState = setIn(nextState, ['translationStatus'], {
			languageValues: Object.keys(state.availableLanguages).map(
				languageId => ({
					languageId,
					values: editableValues.filter(editableValue =>
						TranslationStatus._editableValueIsTranslated(
							editableValue,
							languageId,
							prefixSegmentsExperienceId(
								state.segmentsExperienceId ||
									state.defaultSegmentsExperienceId
							)
						)
					)
				})
			),

			translationKeys: editableValues
		});

		nextState.translationStatus.languageValues.sort(
			(languageValueA, languageValueB) =>
				languageValueB.values.length - languageValueA.values.length
		);

		return nextState;
	}

	/**
	 * Handles a click on a language item to notify parent components that a
	 * change in the selected language needs to be initiated.
	 * @param {Event} event
	 * @private
	 * @review
	 */
	_handleLanguageChange(event) {
		event.preventDefault();

		this.store.dispatch({
			languageId: event.delegateTarget.getAttribute('data-languageid'),
			type: CHANGE_LANGUAGE_ID
		});
	}
}

const ConnectedTranslationStatus = getConnectedComponent(TranslationStatus, [
	'availableLanguages',
	'defaultLanguageId',
	'defaultSegmentsExperienceId',
	'fragmentEntryLinks',
	'languageId',
	'segmentsExperienceId',
	'spritemap'
]);

Soy.register(ConnectedTranslationStatus, templates);

export {ConnectedTranslationStatus};
export default ConnectedTranslationStatus;
