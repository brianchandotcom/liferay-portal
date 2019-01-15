import Component from 'metal-component';
import Soy from 'metal-soy';

import '../fragments/FragmentsEditorSidebarCard.es';
import {FRAGMENTS_EDITOR_ITEM_TYPES} from '../../../reducers/placeholders.es';
import {focusItem, setIn} from '../../../utils/FragmentsEditorUpdateUtils.es';
import {getConnectedComponent} from '../../../store/ConnectedComponent.es';
import {
	REMOVE_FRAGMENT_ENTRY_LINK,
	UPDATE_ACTIVE_ITEM,
	UPDATE_LAST_SAVE_DATE,
	UPDATE_SAVING_CHANGES_STATUS
} from '../../../actions/actions.es';
import templates from './SidebarStructurePanel.soy';

/**
 * SidebarStructurePanel
 */
class SidebarStructurePanel extends Component {

	/**
	 * Adds item types to state
	 * @param {Object} _state
	 * @private
	 * @return {Object}
	 * @static
	 */
	static _addItemTypesToState(state) {
		return setIn(state, ['itemTypes'], FRAGMENTS_EDITOR_ITEM_TYPES);
	}

	/**
	 * @inheritDoc
	 * @private
	 * @review
	 */
	prepareStateForRender(nextState) {
		return SidebarStructurePanel._addItemTypesToState(nextState);
	}

	/**
	 * Callback executed when an element name is clicked in the tree
	 * @param {object} event
	 * @private
	 * @review
	 */
	_handleElementClick(event) {
		const itemId = event.delegateTarget.dataset.elementId;
		const itemType = event.delegateTarget.dataset.elementType;

		this.store.dispatchAction(
			UPDATE_ACTIVE_ITEM,
			{
				activeItemId: itemId,
				activeItemType: itemType
			}
		);

		focusItem(itemId, itemType);
	}

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