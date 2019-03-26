import Component from 'metal-component';
import {Config} from 'metal-state';
import Soy from 'metal-soy';

import '../fragments/FragmentsEditorSidebarCard.es';
import {REMOVE_FRAGMENT_ENTRY_LINK, REMOVE_SECTION} from '../../../actions/actions.es';
import {EDITABLE_FRAGMENT_ENTRY_PROCESSOR} from '../../fragment_entry_link/FragmentEntryLinkContent.es';
import {removeItem, setIn} from '../../../utils/FragmentsEditorUpdateUtils.es';
import {FRAGMENTS_EDITOR_ITEM_TYPES} from '../../../utils/constants';
import {getConnectedComponent} from '../../../store/ConnectedComponent.es';
import {getItemPath} from '../../../utils/FragmentsEditorGetUtils.es';
import templates from './SidebarStructurePanel.soy';

/**
 * SidebarStructurePanel
 */
class SidebarStructurePanel extends Component {

	/**
	 * @param {Object} state
	 * @param {{itemId: string, itemType: string}[]} activePath
	 * @private
	 * @return {Object}
	 * @review
	 * @static
	 */
	static _addStructureToState(state, activePath) {
		return setIn(
			state,
			['structure'],
			SidebarStructurePanel._getTreeNode(
				state,
				activePath,
				{
					children: state.layoutData.structure.map(
						row => SidebarStructurePanel._getRowTree(
							state,
							activePath,
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
	 * @param {{itemId: string, itemType: string}[]} activePath
	 * @param {object} column
	 * @return {Object}
	 * @review
	 * @static
	 */
	static _getColumnTree(state, activePath, column) {
		return SidebarStructurePanel._getTreeNode(
			state,
			activePath,
			{
				children: column.fragmentEntryLinkIds.map(
					fragmentEntryLinkId => state.fragmentEntryLinks[fragmentEntryLinkId]
				).filter(
					fragmentEntryLink => fragmentEntryLink
				).map(
					fragmentEntryLink => SidebarStructurePanel._getFragmentEntryLinkTree(
						state,
						activePath,
						fragmentEntryLink
					)
				),
				expanded: column.fragmentEntryLinkIds.some(
					fragmentEntryLinkId => activePath.some(
						item => (
							item.itemType === FRAGMENTS_EDITOR_ITEM_TYPES.fragment &&
							item.itemId === fragmentEntryLinkId
						)
					)
				),
				key: `column-${column.columnId}`,
				label: Liferay.Language.get('column')
			}
		);
	}

	/**
	 * @param {object} state
	 * @param {{itemId: string, itemType: string}[]} activePath
	 * @param {object} fragmentEntryLink
	 * @return {Object}
	 * @review
	 * @static
	 */
	static _getFragmentEntryLinkTree(state, activePath, fragmentEntryLink) {
		return SidebarStructurePanel._getTreeNode(
			state,
			activePath,
			{
				children: Object.keys(
					fragmentEntryLink.editableValues[EDITABLE_FRAGMENT_ENTRY_PROCESSOR]
				).map(
					editableValueKey => SidebarStructurePanel._getTreeNode(
						state,
						activePath,
						{
							elementId: `${fragmentEntryLink.fragmentEntryLinkId}-${editableValueKey}`,
							elementType: FRAGMENTS_EDITOR_ITEM_TYPES.editable,
							key: `editable-value-${fragmentEntryLink.fragmentEntryLinkId}-${editableValueKey}`,
							label: editableValueKey
						}
					)
				),
				elementId: fragmentEntryLink.fragmentEntryLinkId,
				elementType: FRAGMENTS_EDITOR_ITEM_TYPES.fragment,
				key: `fragment-entry-link-${fragmentEntryLink.fragmentEntryLinkId}`,
				label: fragmentEntryLink.name
			}
		);
	}

	/**
	 * @param {object} state
	 * @param {{itemId: string, itemType: string}[]} activePath
	 * @param {object} row
	 * @return {Object}
	 * @review
	 * @static
	 */
	static _getRowTree(state, activePath, row) {
		return SidebarStructurePanel._getTreeNode(
			state,
			activePath,
			{
				children: row.columns.map(
					column => SidebarStructurePanel._getColumnTree(
						state,
						activePath,
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
	 * @param {{itemId: string, itemType: string}[]} activePath
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
	static _getTreeNode(state, activePath, data) {
		return {
			active: (
				state.activeItemId === data.elementId &&
				state.activeItemType === data.elementType
			),
			children: data.children || [],
			elementId: data.elementId || '',
			elementType: data.elementType || '',
			expanded: data.expanded ||
				(state._expandedNodes.indexOf(data.key) !== -1) ||
				activePath.some(
					item => (
						(data.elementId === item.itemId) &&
						(data.elementType === item.itemType)
					)
				),
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
		return SidebarStructurePanel._addStructureToState(
			state,
			getItemPath(
				state.activeItemId,
				state.activeItemType,
				state.layoutData.structure
			)
		);
	}

	/**
	 * @param {MouseEvent} event
	 * @private
	 * @review
	 */
	_handleElementCollapseButtonClick(event) {
		const {nodeKey} = event.delegateTarget.dataset;
		const nodeKeyIndex = this._expandedNodes.indexOf(nodeKey);

		if (nodeKeyIndex === -1) {
			this._expandedNodes = [...this._expandedNodes, nodeKey];
		}
		else {
			this._expandedNodes.splice(nodeKeyIndex, 1);

			this._expandedNodes = this._expandedNodes;
		}
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

/**
 * State definition.
 * @review
 * @static
 * @type {!Object}
 */
SidebarStructurePanel.STATE = {

	/**
	 * List of expanded nodes.
	 * @default ['root']
	 * @instance
	 * @memberOf SidebarStructurePanel
	 * @review
	 * @type {string[]}
	 */
	_expandedNodes: Config
		.arrayOf(Config.string())
		.internal()
		.value(['root'])
};

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