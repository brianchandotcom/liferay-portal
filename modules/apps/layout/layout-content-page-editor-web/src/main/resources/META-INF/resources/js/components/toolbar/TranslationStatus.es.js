import Component from 'metal-component';
import {object} from 'metal';
import Soy from 'metal-soy';

import getConnectedComponent from '../../store/ConnectedComponent.es';
import {CHANGE_LANGUAGE_ID} from '../../actions/actions.es';
import templates from './TranslationStatus.soy';

/**
 * TranslationStatus
 */
class TranslationStatus extends Component {

	/**
	 * @inheritDoc
	 * @review
	 */
	prepareStateForRender(state) {
		const translationStatus = state.translationStatus || {};

		const languageValues = translationStatus.languageValues || [];
		const sortedLanguageValues = languageValues.sort(
			(languageA, languageB) => (languageB.values.length - languageA.values.length)
		);

		return object.mixin(
			{},
			state,
			{
				translationStatus: object.mixin(
					{},
					translationStatus,
					{
						languageValues: sortedLanguageValues
					}
				)
			}
		);
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

		this.store.dispatchAction(
			CHANGE_LANGUAGE_ID,
			{
				languageId: event.delegateTarget.getAttribute('data-languageid')
			}
		);
	}

}

const ConnectedTranslationStatus = getConnectedComponent(
	TranslationStatus,
	[
		'availableLanguages',
		'defaultLanguageId',
		'languageId',
		'spritemap',
		'translationStatus'
	]
);

Soy.register(ConnectedTranslationStatus, templates);

export {ConnectedTranslationStatus};
export default ConnectedTranslationStatus;