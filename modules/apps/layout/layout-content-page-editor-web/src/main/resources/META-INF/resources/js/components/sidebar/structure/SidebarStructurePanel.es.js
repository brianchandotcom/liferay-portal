import Component from 'metal-component';
import Soy from 'metal-soy';

import '../fragments/FragmentsEditorSidebarCard.es';
import {CLEAR_HOVERED_ITEM, REMOVE_FRAGMENT_ENTRY_LINK, REMOVE_SECTION, UPDATE_ACTIVE_ITEM, UPDATE_HOVERED_ITEM} from '../../../actions/actions.es';
import {focusItem, removeItem, setIn} from '../../../utils/FragmentsEditorUpdateUtils.es';
import {FRAGMENTS_EDITOR_ITEM_TYPES} from '../../../utils/constants';
import {getConnectedComponent} from '../../../store/ConnectedComponent.es';
import templates from './SidebarStructurePanel.soy';

/**
 * SidebarStructurePanel
 */
class SidebarStructurePanel extends Component {

	/**
	 * @param {Object} state
	 * @private
	 * @return {Object}
	 * @review
	 * @static
	 */
	static _addStructureToState(state) {
		return setIn(
			state,
			['structure'],
			SidebarStructurePanel._getTreeNode(
				state,
				{
					children: state.layoutData.structure.map(
						row => SidebarStructurePanel._getRowTree(
							state,
							row
						)
					),
					expanded: true,
					key: 'root',
					label: Liferay.Language.get('page')
				}
			)
		);
	}

	/**
	 * @param {object} state
	 * @param {object} column
	 * @return {Object}
	 * @review
	 * @static
	 */
	static _getColumnTree(state, column) {
		return SidebarStructurePanel._getTreeNode(
			state,
			{
				children: column.fragmentEntryLinkIds.map(
						fragmentEntryLinkId => state.fragmentEntryLinks[fragmentEntryLinkId]
					).filter(
						fragmentEntryLink => fragmentEntryLink
					).map(
						fragmentEntryLink => SidebarStructurePanel._getTreeNode(
							state,
							{
								elementId: fragmentEntryLink.fragmentEntryLinkId,
								elementType: FRAGMENTS_EDITOR_ITEM_TYPES.fragment,
								key: `fragment-entry-link-${fragmentEntryLink.fragmentEntryLinkId}`,
								label: fragmentEntryLink.name
							}
						)
					),
				key: `column-${column.columnId}`,
				label: Liferay.Language.get('column')
			}
		);
	}

	/**
	 * @param {object} state
	 * @param {object} row
	 * @return {Object}
	 * @review
	 * @static
	 */
	static _getRowTree(state, row) {
		return SidebarStructurePanel._getTreeNode(
			state,
			{
				children: row.columns.map(
					column => SidebarStructurePanel._getColumnTree(
						state,
						column
					)
				),
				elementId: row.rowId,
				elementType: FRAGMENTS_EDITOR_ITEM_TYPES.section,
				key: `row-${row.rowId}`,
				label: Liferay.Language.get('section')
			}
		);
	}

	/**
	 * @param {object} state
	 * @param {object} data
	 * @param {string} data.key
	 * @param {string} data.label
	 * @param {object[]} [data.children=[]]
	 * @param {string} [data.elementId='']
	 * @param {string} [data.elementType='']
	 * @param {boolean} [data.expanded=false]
	 * @private
	 * @return {object}
	 * @review
	 * @static
	 */
	static _getTreeNode(state, data) {
		return {
			active: (
				state.activeItemId === data.elementId &&
				state.activeItemType === data.elementType
			),
			children: data.children || [],
			elementId: data.elementId || '',
			elementType: data.elementType || '',
			expanded: data.expanded || false,
			hovered: (
				state.hoveredItemId === data.elementId &&
				state.hoveredItemType === data.elementType
			),
			key: data.key,
			label: data.label
		};
	}

	/**
	 * @inheritDoc
	 * @private
	 * @review
	 */
	prepareStateForRender(state) {
		return SidebarStructurePanel._addStructureToState(state);
	}

	/**
	 * Callback executed when an element name is clicked in the tree
	 * @param {object} event
	 * @private
	 * @review
	 */
	_handleElementClick(event) {
		const {elementId, elementType} = event.delegateTarget.dataset;

		this.store.dispatchAction(
			UPDATE_ACTIVE_ITEM,
			{
				activeItemId: elementId,
				activeItemType: elementType
			}
		);

		focusItem(elementId, elementType);
	}

	/**
	 * @param {MouseEvent} event
	 * @private
	 * @review
	 */
	_handleElementMouseEnter(event) {
		const {elementId, elementType} = event.delegateTarget.dataset;

		this.store.dispatchAction(
			UPDATE_HOVERED_ITEM,
			{
				hoveredItemId: elementId,
				hoveredItemType: elementType
			}
		);
	}

	/**
	 * @private
	 * @review
	 */
	_handleElementMouseLeave() {
		this.store.dispatchAction(
			CLEAR_HOVERED_ITEM
		);
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
		'hoveredItemId',
		'hoveredItemType',
		'layoutData',
		'spritemap'
	]
);

Soy.register(ConnectedSidebarStructurePanel, templates);

export {ConnectedSidebarStructurePanel, SidebarStructurePanel};
export default ConnectedSidebarStructurePanel;