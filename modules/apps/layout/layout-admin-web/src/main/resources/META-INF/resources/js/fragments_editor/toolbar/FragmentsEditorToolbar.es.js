import Component from 'metal-component';
import Soy from 'metal-soy';

import './TranslationStatus.es';
import templates from './FragmentsEditorToolbar.soy';

/**
 * FragmentsEditorToolbar
 * @review
 */

class FragmentsEditorToolbar extends Component {

	/**
	 * @private
	 * @review
	 */

	_handleToggleContextualSidebarButtonClick() {
		this.emit('toggleContextualSidebarButtonClick');
	}

	/**
	 * @param {{languageId: string}} event
	 * @private
	 * @review
	 */

	_handleTranslationLanguageChange(event) {
		const {languageId} = event;

		this.emit('translationLanguageChange', {languageId});
	}
}

/**
 * State definition.
 * @review
 * @static
 * @type {!Object}
 */

FragmentsEditorToolbar.STATE = {};

Soy.register(FragmentsEditorToolbar, templates);

export {FragmentsEditorToolbar};
export default FragmentsEditorToolbar;