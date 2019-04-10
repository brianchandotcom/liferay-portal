import Component from 'metal-component';
import Soy from 'metal-soy';

import './fragments/SidebarElementsPanel.es';
import './fragments/SidebarSectionsPanel.es';
import './layouts/SidebarLayoutsPanel.es';
import './mapping/SidebarMappingPanel.es';
import './page_structure/SidebarPageStructurePanel.es';
import './widgets/SidebarWidgetsPanel.es';
import {getConnectedComponent} from '../../store/ConnectedComponent.es';
import {UPDATE_SELECTED_SIDEBAR_PANEL_ID} from '../../actions/actions.es';
import {setIn} from '../../utils/FragmentsEditorUpdateUtils.es';
import templates from './FragmentsEditorSidebarContent.soy';

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

		if (state.selectedSidebarPanelId && state.sidebarPanels) {
			const selectedSidebarPanel = state.sidebarPanels.find(
				sidebarPanel => sidebarPanel.sidebarPanelId === state.selectedSidebarPanelId
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
		const {sidebarPanelId} = event.delegateTarget.dataset;

		if (this.selectedSidebarPanelId === sidebarPanelId) {
			this.store.dispatchAction(
				UPDATE_SELECTED_SIDEBAR_PANEL_ID,
				{sidebarPanelId: ''}
			);
		}
		else {
			this.store.dispatchAction(
				UPDATE_SELECTED_SIDEBAR_PANEL_ID,
				{sidebarPanelId}
			);
		}
	}

	/**
	 * Hides sidebar
	 * @private
	 * @review
	 */
	_hideSidebar() {
		this.store.dispatchAction(
			UPDATE_SELECTED_SIDEBAR_PANEL_ID,
			{sidebarPanelId: ''}
		);
	}

}

const ConnectedFragmentsEditorSidebarContent = getConnectedComponent(
	FragmentsEditorSidebarContent,
	[
		'selectedSidebarPanelId',
		'sidebarPanels',
		'spritemap'
	]
);

Soy.register(ConnectedFragmentsEditorSidebarContent, templates);

export {ConnectedFragmentsEditorSidebarContent, FragmentsEditorSidebarContent};
export default ConnectedFragmentsEditorSidebarContent;