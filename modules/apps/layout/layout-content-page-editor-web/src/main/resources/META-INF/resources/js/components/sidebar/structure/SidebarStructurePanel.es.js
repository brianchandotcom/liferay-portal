import Component from 'metal-component';
import Soy from 'metal-soy';

import '../fragments/FragmentsEditorSidebarCard.es';
import {FRAGMENTS_EDITOR_ITEM_TYPES} from '../../../utils/constants';
import {focusItem, removeItem, setIn} from '../../../utils/FragmentsEditorUpdateUtils.es';
import {getConnectedComponent} from '../../../store/ConnectedComponent.es';
import {
	REMOVE_FRAGMENT_ENTRY_LINK,
	REMOVE_SECTION,
	UPDATE_ACTIVE_ITEM
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
	 * Callback executed when the element remove button is clicked.
	 * @param {object} event
	 * @private
	 * @review
	 */
	_handleElementRemoveButtonClick(event) {
		const itemId = event.delegateTarget.dataset.elementId;
		const itemType = event.delegateTarget.dataset.elementType;

		let removeItemAction = null;
		let removeItemPayload = null;

		if (itemType === FRAGMENTS_EDITOR_ITEM_TYPES.section) {
			removeItemAction = REMOVE_SECTION;

			removeItemPayload = {
				sectionId: itemId
			};
		}
		else if (itemType === FRAGMENTS_EDITOR_ITEM_TYPES.fragment) {
			removeItemAction = REMOVE_FRAGMENT_ENTRY_LINK;

			removeItemPayload = {
				fragmentEntryLinkId: itemId
			};
		}

		removeItem(this.store, removeItemAction, removeItemPayload);
	}

}

const ConnectedSidebarStructurePanel = getConnectedComponent(
	SidebarStructurePanel,
	[
		'activeItemId',
		'activeItemType',
		'fragmentEntryLinks',
		'layoutData',
		'spritemap'
	]
);

Soy.register(ConnectedSidebarStructurePanel, templates);

export {ConnectedSidebarStructurePanel, SidebarStructurePanel};
export default ConnectedSidebarStructurePanel;