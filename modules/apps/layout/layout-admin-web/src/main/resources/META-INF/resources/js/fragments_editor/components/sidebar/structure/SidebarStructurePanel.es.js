import Component from 'metal-component';
import Soy from 'metal-soy';

import '../fragments/FragmentsEditorSidebarCard.es';
import {getConnectedComponent} from '../../../store/ConnectedComponent.es';
import {
	REMOVE_FRAGMENT_ENTRY_LINK,
	UPDATE_LAST_SAVE_DATE,
	UPDATE_SAVING_CHANGES_STATUS
} from '../../../actions/actions.es';
import templates from './SidebarStructurePanel.soy';

/**
 * SidebarStructurePanel
 */
class SidebarStructurePanel extends Component {

	/**
	 * Callback executed when the fragment remove button is clicked.
	 * It emits a 'fragmentRemoveButtonClick' event with the fragment index.
	 * @param {{itemId: !string}} event
	 * @private
	 * @review
	 */
	_handleFragmentRemoveButtonClick(event) {
		this.store
			.dispatchAction(
				UPDATE_SAVING_CHANGES_STATUS,
				{
					savingChanges: true
				}
			)
			.dispatchAction(
				REMOVE_FRAGMENT_ENTRY_LINK,
				{
					fragmentEntryLinkId: event.itemId
				}
			)
			.dispatchAction(
				UPDATE_LAST_SAVE_DATE,
				{
					lastSaveDate: new Date()
				}
			)
			.dispatchAction(
				UPDATE_SAVING_CHANGES_STATUS,
				{
					savingChanges: false
				}
			);
	}

}

const ConnectedSidebarStructurePanel = getConnectedComponent(
	SidebarStructurePanel,
	[
		'fragmentEntryLinks',
		'layoutData'
	]
);

Soy.register(ConnectedSidebarStructurePanel, templates);

export {ConnectedSidebarStructurePanel, SidebarStructurePanel};
export default ConnectedSidebarStructurePanel;