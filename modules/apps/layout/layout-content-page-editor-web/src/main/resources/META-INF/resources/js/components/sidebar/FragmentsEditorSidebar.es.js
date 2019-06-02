import Component from 'metal-component';
import {Config} from 'metal-state';
import Soy from 'metal-soy';

import './FragmentsEditorSidebarContent.es';
import {getConnectedComponent} from '../../store/ConnectedComponent.es';
import {UPDATE_SELECTED_SIDEBAR_PANEL_ID} from '../../actions/actions.es';
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
		this._handleHide = this._handleHide.bind(this);
		const sidenav = Liferay.SideNavigation.instance(this._productMenuToggle);
		this._toggleHandle = sidenav.on('openStart.lexicon.sidenav', this._handleHide);
	}

	/**
	 * @inheritDoc
	 * @review
	 */
	disposed() {
		this._toggleHandle.removeListener('openStart.lexicon.sidenav', this._handleHide);
	}

	/**
	 * @inheritDoc
	 * @review
	 */
	rendered() {
		if (this.selectedSidebarPanelId) {
			Liferay.SideNavigation.hide(this._productMenuToggle);
		}
	}

	/**
	 * @private
	 * @review
	 */
	_handleHide() {
		this.store.dispatch({
			sidebarPanelId: '',
			type: UPDATE_SELECTED_SIDEBAR_PANEL_ID
		});
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
	 * Internal property for subscribing to sidenav events.
	 * @default undefined
	 * @instance
	 * @memberOf FragmentsEditorSidebar
	 * @review
	 * @type {EventHandle}
	 */
	_toggleHandle: Config.internal(),

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
	['selectedSidebarPanelId']
);

Soy.register(ConnectedFragmentsEditorSidebar, templates);

export {ConnectedFragmentsEditorSidebar, FragmentsEditorSidebar};
export default ConnectedFragmentsEditorSidebar;
