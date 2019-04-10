import Component from 'metal-component';
import {Config} from 'metal-state';
import Soy from 'metal-soy';

import './fragments/SidebarElementsPanel.es';
import './fragments/SidebarSectionsPanel.es';
import './layouts/SidebarLayoutsPanel.es';
import './mapping/SidebarMappingPanel.es';
import './structure/SidebarStructurePanel.es';
import './widgets/SidebarWidgetsPanel.es';
import {getConnectedComponent} from '../../store/ConnectedComponent.es';
import {HIDE_SIDEBAR, TOGGLE_SIDEBAR} from '../../actions/actions.es';
import {setIn} from '../../utils/FragmentsEditorUpdateUtils.es';
import templates from './FragmentsEditorSidebarContent.soy';

/**
 * @review
 * @type {string}
 */
const DEFAULT_SIDEBAR_PANEL_ID = 'sections';

/**
 * FragmentsEditorSidebarContent
 * @review
 */
class FragmentsEditorSidebarContent extends Component {

	/**
	 * @inheritdoc
	 * @param {object} state
	 * @review
	 */
	prepareStateForRender(state) {
		let nextState = state;

		if (state._selectedSidebarPanelId && state.sidebarPanels) {
			const selectedSidebarPanel = state.sidebarPanels.find(
				sidebarPanel => sidebarPanel.sidebarPanelId === state._selectedSidebarPanelId
			);

			if (selectedSidebarPanel) {
				nextState = setIn(
					nextState,
					['_selectedSidebarPanel'],
					selectedSidebarPanel
				);
			}

		}

		return nextState;
	}

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
			this._setActivePanel(data.sidebarPanelId);
		}
		else if (this._selectedSidebarPanelId === data.sidebarPanelId) {
			this._hideSidebar();
		}
		else {
			this._setActivePanel(data.sidebarPanelId);
		}
	}

	/**
	 * Hides sidebar
	 * @private
	 * @review
	 */
	_hideSidebar() {
		this.store.dispatchAction(HIDE_SIDEBAR);
		this._selectedSidebarPanelId = '';
	}

	/**
	 * Set as active the panel with the given sidebarPanelId and also set sidebar title
	 * @param {string} sidebarPanelId
	 * @private
	 * @review
	 */
	_setActivePanel(sidebarPanelId) {
		this._selectedSidebarPanelId = sidebarPanelId;
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
	 * Sidebar active panel ID
	 * @default sections
	 * @memberof FragmentsEditorSidebarContent
	 * @private
	 * @review
	 * @type {string}
	 */
	_selectedSidebarPanelId: Config
		.string()
		.internal()
		.value(DEFAULT_SIDEBAR_PANEL_ID)
};

const ConnectedFragmentsEditorSidebarContent = getConnectedComponent(
	FragmentsEditorSidebarContent,
	[
		'fragmentsEditorSidebarVisible',
		'sidebarPanels',
		'spritemap'
	]
);

Soy.register(ConnectedFragmentsEditorSidebarContent, templates);

export {ConnectedFragmentsEditorSidebarContent, FragmentsEditorSidebarContent};
export default ConnectedFragmentsEditorSidebarContent;