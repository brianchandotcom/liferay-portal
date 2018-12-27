import Component from 'metal-component';
import {Config} from 'metal-state';
import {object} from 'metal';
import Soy from 'metal-soy';

import {CHANGE_LANGUAGE_ID} from '../../actions/actions.es';
import {Store} from '../../store/store.es';
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

/**
 * State definition.
 * @review
 * @static
 * @type {!Object}
 */
TranslationStatus.STATE = {

	/**
	 * Store instance
	 * @default undefined
	 * @instance
	 * @memberOf TranslationStatus
	 * @private
	 * @review
	 * @type {Store}
	 */
	store: Config.instanceOf(Store)
};

Soy.register(TranslationStatus, templates);

export {TranslationStatus};
export default TranslationStatus;