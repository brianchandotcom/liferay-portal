import Component from 'metal-component';
import {Config} from 'metal-state';
import Soy from 'metal-soy';

import './TranslationStatus.es';
import {Store} from '../../store/store.es';
import templates from './FragmentsEditorToolbar.soy';
import {
	TOGGLE_SIDEBAR,
	UPDATE_TRANSLATION_STATUS
} from '../../actions/actions.es';

/**
 * FragmentsEditorToolbar
 * @review
 */
class FragmentsEditorToolbar extends Component {

	/**
	 * @inheritDoc
	 */
	created() {
		this.once(
			'storeChanged',
			() => {
				this.store.dispatchAction(
					UPDATE_TRANSLATION_STATUS
				);
			}
		);
	}

	/**
	 * @private
	 * @review
	 */
	_handleToggleContextualSidebarButtonClick() {
		this.store.dispatchAction(TOGGLE_SIDEBAR);
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