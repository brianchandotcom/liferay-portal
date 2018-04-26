import Component from 'metal-component';
import Soy from 'metal-soy';

import templates from './TranslationStatus.soy';

/**
 * TranslationStatus
 */

class TranslationStatus extends Component {

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