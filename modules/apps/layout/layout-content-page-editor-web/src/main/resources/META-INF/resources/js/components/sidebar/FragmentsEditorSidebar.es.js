import Component from 'metal-component';
import {Config} from 'metal-state';
import Soy from 'metal-soy';

import './FragmentsEditorSidebarContent.es';
import {getConnectedComponent} from '../../store/ConnectedComponent.es';
import {HIDE_SIDEBAR} from '../../actions/actions.es';
import templates from './FragmentsEditorSidebar.soy';

/**
 * FragmentsEditorSidebar
 * @review
 */
class FragmentsEditorSidebar extends Component {

	/**
	 * @inheritDoc
	 * @review
	 */
	created() {
		this._productMenuToggle = $('.product-menu-toggle');
		this._productMenu = $(this._productMenuToggle.data('target'));
		this._handleHide = this._handleHide.bind(this);

		this._productMenu.on('openStart.lexicon.sidenav', this._handleHide);
	}

	/**
	 * @inheritDoc
	 * @review
	 */
	disposed() {
		this._productMenu.off('openStart.lexicon.sidenav', this._handleHide);
	}

	/**
	 * @inheritDoc
	 * @review
	 */
	rendered() {
		if (this.fragmentsEditorSidebarVisible) {
			this._productMenuToggle.sideNavigation('hide');
		}
	}

	/**
	 * @private
	 * @review
	 */
	_handleHide() {
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
	 * Synced ProductMenu sidebar.
	 * @default undefined
	 * @instance
	 * @memberOf FragmentsEditorSidebar
	 * @review
	 * @type {object}
	 */
	_productMenu: Config.internal(),

	/**
	 * Synced ProductMenu toggle button.
	 * @default undefined
	 * @instance
	 * @memberOf FragmentsEditorSidebar
	 * @review
	 * @type {object}
	 */
	_productMenuToggle: Config.internal()
};

const ConnectedFragmentsEditorSidebar = getConnectedComponent(
	FragmentsEditorSidebar,
	['fragmentsEditorSidebarVisible']
);

Soy.register(ConnectedFragmentsEditorSidebar, templates);

export {ConnectedFragmentsEditorSidebar, FragmentsEditorSidebar};
export default ConnectedFragmentsEditorSidebar;