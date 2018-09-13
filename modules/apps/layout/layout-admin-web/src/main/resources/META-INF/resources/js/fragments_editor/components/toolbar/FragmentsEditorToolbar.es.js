import Component from 'metal-component';
import {Config} from 'metal-state';
import Soy from 'metal-soy';

import './TranslationStatus.es';
import {TOGGLE_SIDEBAR} from '../../actions/actions.es';
import {Store} from '../../store/store.es';
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
		this.store.dispatchAction(TOGGLE_SIDEBAR);
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

FragmentsEditorToolbar.STATE = {

	/**
	 * Store instance
	 * @default undefined
	 * @instance
	 * @memberOf FragmentsEditorToolbar
	 * @review
	 * @type {Store}
	 */

	store: Config.instanceOf(Store)
};

Soy.register(FragmentsEditorToolbar, templates);

export {FragmentsEditorToolbar};
export default FragmentsEditorToolbar;