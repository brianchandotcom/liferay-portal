import Component from 'metal-component';
import {Config} from 'metal-state';
import Soy from 'metal-soy';

import '../fragments/FragmentsEditorSidebarCard.es';
import {
	REMOVE_FRAGMENT_ENTRY_LINK,
	REMOVE_ROW
} from '../../../actions/actions.es';
import {removeItem, setIn} from '../../../utils/FragmentsEditorUpdateUtils.es';
import {
	EDITABLE_FRAGMENT_ENTRY_PROCESSOR,
	FRAGMENTS_EDITOR_ITEM_TYPES,
	FRAGMENTS_EDITOR_ROW_TYPES
} from '../../../utils/constants';
import {getConnectedComponent} from '../../../store/ConnectedComponent.es';
import {
	getItemPath,
	getRowFragmentEntryLinkIds
} from '../../../utils/FragmentsEditorGetUtils.es';
import templates from './SidebarPageStructurePanel.soy';

/**
 * SidebarPageStructurePanel
 */
class SidebarPageStructurePanel extends Component {
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
			SidebarPageStructurePanel._getTreeNode(state, {
				children: state.layoutData.structure.map(row =>
					SidebarPageStructurePanel._getRowTree(state, row)
				),
				expanded: true,
				key: 'root',
				label: Liferay.Language.get('page')
			})
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
		return SidebarPageStructurePanel._getTreeNode(state, {
			children: column.fragmentEntryLinkIds
				.map(
					fragmentEntryLinkId =>
						state.fragmentEntryLinks[fragmentEntryLinkId]
				)
				.filter(fragmentEntryLink => fragmentEntryLink)
				.map(fragmentEntryLink =>
					SidebarPageStructurePanel._getFragmentEntryLinkTree(
						state,
						fragmentEntryLink
					)
				),
			key: `${FRAGMENTS_EDITOR_ITEM_TYPES.column}-${column.columnId}`,
			label: Liferay.Language.get('column')
		});
	}

	/**
	 * @param {object} state
	 * @param {object} fragmentEntryLink
	 * @return {Object}
	 * @review
	 * @static
	 */
	static _getFragmentEntryLinkTree(state, fragmentEntryLink) {
		return SidebarPageStructurePanel._getTreeNode(state, {
			children: Object.keys(
				fragmentEntryLink.editableValues[
					EDITABLE_FRAGMENT_ENTRY_PROCESSOR
				]
			).map(editableValueKey =>
				SidebarPageStructurePanel._getTreeNode(state, {
					elementId: `${
						fragmentEntryLink.fragmentEntryLinkId
					}-${editableValueKey}`,
					elementType: FRAGMENTS_EDITOR_ITEM_TYPES.editable,
					key: `${FRAGMENTS_EDITOR_ITEM_TYPES.editable}-${
						fragmentEntryLink.fragmentEntryLinkId
					}-${editableValueKey}`,
					label: editableValueKey
				})
			),
			elementId: fragmentEntryLink.fragmentEntryLinkId,
			elementType: FRAGMENTS_EDITOR_ITEM_TYPES.fragment,
			key: `${FRAGMENTS_EDITOR_ITEM_TYPES.fragment}-${
				fragmentEntryLink.fragmentEntryLinkId
			}`,
			label: fragmentEntryLink.name,
			removable: true
		});
	}

	/**
	 * @param {object} state
	 * @param {object} row
	 * @return {Object}
	 * @review
	 * @static
	 */
	static _getRowTree(state, row) {
		let treeNode;

		if (row.type === FRAGMENTS_EDITOR_ROW_TYPES.sectionRow) {
			const [fragmentEntryLinkId] = getRowFragmentEntryLinkIds(row);

			const fragmentEntryLink =
				state.fragmentEntryLinks[fragmentEntryLinkId];

			if (fragmentEntryLink) {
				treeNode = SidebarPageStructurePanel._getFragmentEntryLinkTree(
					state,
					fragmentEntryLink
				);
			}
		}

		if (!treeNode) {
			treeNode = SidebarPageStructurePanel._getTreeNode(state, {
				children: row.columns.map(column =>
					SidebarPageStructurePanel._getColumnTree(state, column)
				),
				elementId: row.rowId,
				elementType: FRAGMENTS_EDITOR_ITEM_TYPES.row,
				key: `${FRAGMENTS_EDITOR_ITEM_TYPES.row}-${row.rowId}`,
				label: Liferay.Language.get('section'),
				removable: true
			});
		}

		return treeNode;
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
	 * @param {boolean} [data.removable=false]
	 * @private
	 * @return {object}
	 * @review
	 * @static
	 */
	static _getTreeNode(state, data) {
		return {
			active:
				state.activeItemId === data.elementId &&
				state.activeItemType === data.elementType,
			children: data.children || [],
			elementId: data.elementId || '',
			elementType: data.elementType || '',
			expanded:
				data.expanded || state._expandedNodes.indexOf(data.key) !== -1,
			hovered:
				state.hoveredItemId === data.elementId &&
				state.hoveredItemType === data.elementType,
			key: data.key,
			label: data.label,
			removable: data.removable || false
		};
	}

	/**
	 * @inheritDoc
	 * @private
	 * @review
	 */
	prepareStateForRender(state) {
		return SidebarPageStructurePanel._addStructureToState(state);
	}

	/**
	 * @private
	 * @review
	 */
	syncActiveItemId() {
		if (this.activeItemId && this.activeItemType) {
			getItemPath(
				this.activeItemId,
				this.activeItemType,
				this.layoutData.structure
			)
				.filter(
					activeItem =>
						this.activeItemId !== activeItem.itemId &&
						this.activeItemType !== activeItem.itemType
				)
				.forEach(activeItem => {
					const key = `${activeItem.itemType}-${activeItem.itemId}`;

					if (this._expandedNodes.indexOf(key) === -1) {
						this._expandedNodes = [...this._expandedNodes, key];
					}
				});
		}
	}

	/**
	 * @param {MouseEvent} event
	 * @private
	 * @review
	 */
	_handleElementClick(event) {
		const {nodeKey} = event.delegateTarget.dataset;

		if (nodeKey) {
			const nodeKeyIndex = this._expandedNodes.indexOf(nodeKey);

			if (nodeKeyIndex === -1) {
				this._expandedNodes.push(nodeKey);
			} else {
				this._expandedNodes.splice(nodeKeyIndex, 1);
			}

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

		if (itemType === FRAGMENTS_EDITOR_ITEM_TYPES.row) {
			removeItemAction = REMOVE_ROW;

			removeItemPayload = {
				rowId: itemId
			};
		} else if (itemType === FRAGMENTS_EDITOR_ITEM_TYPES.fragment) {
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
SidebarPageStructurePanel.STATE = {
	/**
	 * List of expanded nodes.
	 * @default ['root']
	 * @instance
	 * @memberOf SidebarPageStructurePanel
	 * @review
	 * @type {string[]}
	 */
	_expandedNodes: Config.arrayOf(Config.string())
		.internal()
		.value(['root'])
};

const ConnectedSidebarPageStructurePanel = getConnectedComponent(
	SidebarPageStructurePanel,
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

Soy.register(ConnectedSidebarPageStructurePanel, templates);

export {ConnectedSidebarPageStructurePanel, SidebarPageStructurePanel};
export default ConnectedSidebarPageStructurePanel;
