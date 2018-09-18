import 'frontend-taglib/contextual_sidebar/ContextualSidebar.es';
import Component from 'metal-component';
import Soy from 'metal-soy';

import './FragmentsEditorSidebarContent.es';
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
		this.emit('hide');
	}

	/**
	 * @private
	 * @review
	 */

	_handleHideSidebarButtonClick() {
		this.emit('hide');
	}
}

/**
 * State definition.
 * @review
 * @static
 * @type {!Object}
 */

FragmentsEditorSidebar.STATE = {};

Soy.register(FragmentsEditorSidebar, templates);

export {FragmentsEditorSidebar};
export default FragmentsEditorSidebar;