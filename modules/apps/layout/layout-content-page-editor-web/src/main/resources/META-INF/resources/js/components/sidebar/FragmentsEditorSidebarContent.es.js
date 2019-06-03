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
				sidebarPanel =>
					sidebarPanel.sidebarPanelId === state.selectedSidebarPanelId
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
	 * Opens look and feel configuration window
	 * @param {!MouseEvent} event
	 * @private
	 * @review
	 */
	_handleLookAndFeeldButtonClick(event) {
		Liferay.Util.navigate(this.lookAndFeelURL);
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
			this.store.dispatch({
				sidebarPanelId: '',
				type: UPDATE_SELECTED_SIDEBAR_PANEL_ID
			});
		} else {
			this.store.dispatch({
				sidebarPanelId,
				type: UPDATE_SELECTED_SIDEBAR_PANEL_ID
			});
		}
	}

	/**
	 * Hides sidebar
	 * @private
	 * @review
	 */
	_hideSidebar() {
		this.store.dispatch({
			sidebarPanelId: '',
			type: UPDATE_SELECTED_SIDEBAR_PANEL_ID
		});
	}
}

const ConnectedFragmentsEditorSidebarContent = getConnectedComponent(
	FragmentsEditorSidebarContent,
	['lookAndFeelURL', 'selectedSidebarPanelId', 'sidebarPanels', 'spritemap']
);

Soy.register(ConnectedFragmentsEditorSidebarContent, templates);

export {ConnectedFragmentsEditorSidebarContent, FragmentsEditorSidebarContent};
export default ConnectedFragmentsEditorSidebarContent;
