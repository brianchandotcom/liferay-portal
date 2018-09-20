import 'frontend-taglib/contextual_sidebar/ContextualSidebar.es';
import Component from 'metal-component';
import {Config} from 'metal-state';
import Soy from 'metal-soy';

import './FragmentsEditorSidebarContent.es';
import {HIDE_SIDEBAR} from '../../actions/actions.es';
import {Store} from '../../store/store.es';
import templates from './FragmentsEditorSidebar.soy';

/**
 * FragmentsEditorSidebar
 * @review
 */

class FragmentsEditorSidebar extends Component {

	/**
	 * Disallow setting element display to none
	 * @inheritDoc
	 * @review
	 */

	syncVisible() {}

	/**
	 * @private
	 * @review
	 */

	_handleHide() {
		this.store.dispatchAction(HIDE_SIDEBAR);
	}

	/**
	 * @private
	 * @review
	 */

	_handleHideSidebarButtonClick() {
		this.store.dispatchAction(HIDE_SIDEBAR);
	}
}

/**
 * State definition.
 * @review
 * @static
 * @type {!Object}
 */

FragmentsEditorSidebar.STATE = {

	/**
	 * Store instance
	 * @default undefined
	 * @instance
	 * @memberOf FragmentsEditorSidebar
	 * @review
	 * @type {Store}
	 */

	store: Config.instanceOf(Store)
};

Soy.register(FragmentsEditorSidebar, templates);

export {FragmentsEditorSidebar};
export default FragmentsEditorSidebar;