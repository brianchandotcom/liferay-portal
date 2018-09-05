import Component from 'metal-component';
import {object} from 'metal';
import Soy from 'metal-soy';

import templates from './TranslationStatus.soy';

/**
 * TranslationStatus
 */

class TranslationStatus extends Component {

	/**
	 * @inheritDoc
	 * @param state
	 * @review
	 * @return state
	 */

	prepareStateForRender(state) {
		const translationStatus = state.translationStatus || {};

		const languageValues = translationStatus.languageValues || [];
		const sortedLanguageValues = languageValues.sort(
			(languageA, languageB) => {
				return languageB.values.length - languageA.values.length;
			}
		);

		return object.mixin(
			{},
			state,
			{
				translationStatus: object.mixin(
					{},
					translationStatus,
					{languageValues: sortedLanguageValues}
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

		this.emit(
			'languageChange',
			{languageId: event.delegateTarget.getAttribute('data-languageid')}
		);
	}

}

Soy.register(TranslationStatus, templates);

export {TranslationStatus};
export default TranslationStatus;