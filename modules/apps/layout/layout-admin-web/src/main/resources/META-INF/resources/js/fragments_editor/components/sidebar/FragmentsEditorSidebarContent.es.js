import Component from 'metal-component';
import {Config} from 'metal-state';
import Soy from 'metal-soy';

import './fragments/SidebarElementsPanel.es';
import './fragments/SidebarSectionsPanel.es';
import './layouts/SidebarLayoutsPanel.es';
import './mapping/SidebarMappingPanel.es';
import './structure/SidebarStructurePanel.es';
import {HIDE_SIDEBAR, TOGGLE_SIDEBAR} from '../../actions/actions.es';
import state from '../../store/state.es';
import {Store} from '../../store/store.es';
import templates from './FragmentsEditorSidebarContent.soy';

/**
 * FragmentsEditorSidebarContent
 * @review
 */
class FragmentsEditorSidebarContent extends Component {

	/**
	 * Updates active panel
	 * @param {!MouseEvent} event
	 * @private
	 * @review
	 */
	_handlePanelButtonClick(event) {
		const data = event.delegateTarget.dataset;

		if (!this.fragmentsEditorSidebarVisible) {
			this.store.dispatchAction(TOGGLE_SIDEBAR);
			this._setActivePanel(data.panelId, data.sidebarTitle);
		}
		else if (this._panelId === data.panelId) {
			this._hideSidebar();
		}
		else {
			this._setActivePanel(data.panelId, data.sidebarTitle);
		}
	}

	/**
	 * Hides sidebar
	 * @private
	 * @review
	 */
	_hideSidebar() {
		this.store.dispatchAction(HIDE_SIDEBAR);
		this._panelId = '';
		this._sidebarTitle = '';
	}

	/**
	 * Set as active the panel with the given panelId and also set sidebar title
	 * @param {string} panelId
	 * @param {string} title
	 * @private
	 * @review
	 */
	_setActivePanel(panelId, title) {
		this._panelId = panelId;
		this._sidebarTitle = title;
	}

}

/**
 * State definition.
 * @review
 * @static
 * @type {!Object}
 */
FragmentsEditorSidebarContent.STATE = {

	/**
	 * Determines if sidebar should show or not
	 * @default true
	 * @memberOf FragmentsEditorSidebarContent
	 * @private
	 * @review
	 * @type {boolean}
	 */
	fragmentsEditorSidebarVisible: state.fragmentsEditorSidebarVisible,

	/**
	 * Store instance
	 * @default undefined
	 * @instance
	 * @memberOf SidebarAvailableElements
	 * @review
	 * @type {Store}
	 */
	store: Config.instanceOf(Store),

	/**
	 * Sidebar active panel ID
	 * @default sections
	 * @memberof FragmentsEditorSidebarContent
	 * @private
	 * @review
	 * @type {string}
	 */
	_panelId: Config
		.string()
		.internal()
		.value('sections'),

	/**
	 * Sidebar active panel title
	 * @default Sections
	 * @memberof FragmentsEditorSidebarContent
	 * @private
	 * @review
	 * @type {string}
	 */
	_sidebarTitle: Config
		.string()
		.internal()
		.value('Sections')
};

Soy.register(FragmentsEditorSidebarContent, templates);

export {FragmentsEditorSidebarContent};
export default FragmentsEditorSidebarContent;