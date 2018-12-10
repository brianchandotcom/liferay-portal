import Component from 'metal-component';
import {Config} from 'metal-state';
import {Drag, DragDrop} from 'metal-drag-drop';
import position from 'metal-position';
import Soy from 'metal-soy';

import './FragmentEntryLink.es';
import {
	CLEAR_ACTIVE_ITEM,
	CLEAR_DROP_TARGET,
	CLEAR_HOVERED_ITEM,
	MOVE_FRAGMENT_ENTRY_LINK,
	MOVE_SECTION,
	REMOVE_SECTION,
	UPDATE_ACTIVE_ITEM,
	UPDATE_DROP_TARGET,
	UPDATE_HOVERED_ITEM,
	UPDATE_LAST_SAVE_DATE,
	UPDATE_SAVING_CHANGES_STATUS
} from '../../actions/actions.es';
import {
	DROP_TARGET_BORDERS,
	DROP_TARGET_ITEM_TYPES
} from '../../reducers/placeholders.es';
import {
	getFragmentRowIndex,
	getItemMoveDirection,
	getSectionIndex,
	getTargetBorder
} from '../../utils/FragmentsEditorGetUtils.es';
import {
	focusItem,
	moveItem,
	setIn
} from '../../utils/FragmentsEditorUpdateUtils.es';
import state from '../../store/state.es';
import templates from './FragmentEntryLinkList.soy';

/**
 * FragmentEntryLinkList
 * @review
 */
class FragmentEntryLinkList extends Component {

	/**
	 * Adds drop target types to state
	 * @param {Object} _state
	 * @private
	 * @return {Object}
	 * @static
	 */
	static _addDropTargetItemTypesToState(_state) {
		return setIn(_state, ['dropTargetItemTypes'], DROP_TARGET_ITEM_TYPES);
	}

	/**
	 * Returns whether a drop is valid or not
	 * @param {Object} eventData
	 * @private
	 * @return {boolean}
	 * @static
	 */
	static _dropValid(eventData) {
		const sourceItemData = FragmentEntryLinkList._getItemData(
			eventData.source.dataset
		);
		const targetItemData = FragmentEntryLinkList._getItemData(
			eventData.target ? eventData.target.dataset : null
		);

		let dropValid = false;

		if (sourceItemData.itemType === DROP_TARGET_ITEM_TYPES.section) {
			dropValid = (
				(targetItemData.itemType === DROP_TARGET_ITEM_TYPES.section) &&
				(sourceItemData.itemId !== targetItemData.itemId)
			);
		}
		else if (sourceItemData.itemType === DROP_TARGET_ITEM_TYPES.fragment) {
			dropValid = (
				(targetItemData.itemType) &&
				(sourceItemData.itemId !== targetItemData.itemId)
			);
		}

		return dropValid;
	}

	/**
	 * Get id and type of an item from its dataset
	 * @param {!Object} itemDataset
	 * @private
	 * @return {Object}
	 * @static
	 */
	static _getItemData(itemDataset) {
		let itemId = null;
		let itemType = null;

		if (itemDataset) {
			if ('columnId' in itemDataset) {
				itemId = itemDataset.columnId;
				itemType = DROP_TARGET_ITEM_TYPES.column;
			}
			else if ('fragmentEntryLinkId' in itemDataset) {
				itemId = itemDataset.fragmentEntryLinkId;
				itemType = DROP_TARGET_ITEM_TYPES.fragment;
			}
			else if ('layoutSectionId' in itemDataset) {
				itemId = itemDataset.layoutSectionId;
				itemType = DROP_TARGET_ITEM_TYPES.section;
			}
			else if ('fragmentEmptyList' in itemDataset) {
				itemType = DROP_TARGET_ITEM_TYPES.fragmentList;
			}
		}

		return {
			itemId,
			itemType
		};
	}

	/**
	 * Checks wether a section is empty or not, sets empty parameter
	 * and returns a new state
	 * @param {Object} _state
	 * @private
	 * @return {Object}
	 * @static
	 */
	static _setEmptySections(_state) {
		return setIn(
			_state,
			[
				'layoutData',
				'structure'
			],
			_state.layoutData.structure.map(
				section => setIn(
					section,
					['empty'],
					section.columns.every(
						column => column.fragmentEntryLinkIds.length === 0
					)
				)
			)
		);
	}

	/**
	 * @inheritDoc
	 * @private
	 * @review
	 */
	attached() {
		this._initializeDragAndDrop();
	}

	/**
	 * @inheritDoc
	 * @private
	 * @review
	 */
	rendered() {
		if (this.activeItemId) {
			requestAnimationFrame(
				() => {
					focusItem(
						this.activeItemId,
						this.activeItemType
					);
				}
			);
		}
	}

	/**
	 * @inheritDoc
	 * @private
	 * @review
	 */
	dispose() {
		this._dragDrop.dispose();
	}

	/**
	 * @inheritDoc
	 * @private
	 * @review
	 */
	prepareStateForRender(state) {
		let _state = FragmentEntryLinkList._addDropTargetItemTypesToState(state);

		_state = FragmentEntryLinkList._setEmptySections(_state);

		return _state;
	}

	/**
	 * Gives focus to the specified fragmentEntryLinkId
	 * @param {string} fragmentEntryLinkId
	 * @review
	 */
	focusFragmentEntryLink(fragmentEntryLinkId) {
		requestAnimationFrame(
			() => {
				const fragmentEntryLinkElement = this.refs[fragmentEntryLinkId];

				if (fragmentEntryLinkElement) {
					fragmentEntryLinkElement.focus();
					fragmentEntryLinkElement.scrollIntoView();
				}
			}
		);
	}

	/**
	 * Callback that is executed when an item is being dragged.
	 * @param {object} eventData
	 * @param {MouseEvent} eventData.originalEvent
	 * @private
	 * @review
	 */
	_handleDrag(eventData) {
		if (FragmentEntryLinkList._dropValid(eventData)) {
			const mouseY = eventData.originalEvent.clientY;
			const targetItem = eventData.target;
			const targetItemRegion = position.getRegion(targetItem);

			const dropTargetItemData = FragmentEntryLinkList._getItemData(
				targetItem.dataset
			);

			let targetBorder = DROP_TARGET_BORDERS.bottom;

			if (Math.abs(mouseY - targetItemRegion.top) <=
				Math.abs(mouseY - targetItemRegion.bottom)) {
				targetBorder = DROP_TARGET_BORDERS.top;
			}

			this.store.dispatchAction(
				UPDATE_DROP_TARGET,
				{
					dropTargetBorder: targetBorder,
					dropTargetItemId: dropTargetItemData.itemId,
					dropTargetItemType: dropTargetItemData.itemType
				}
			);
		}
	}

	/**
	 * Callback that is executed when we leave a drag target.
	 * @private
	 * @review
	 */
	_handleDragEnd() {
		this.store.dispatchAction(
			CLEAR_DROP_TARGET
		);
	}

	/**
	 * Callback that is executed when an item is dropped.
	 * @param {object} data
	 * @param {MouseEvent} event
	 * @private
	 * @review
	 */
	_handleDrop(data, event) {
		event.preventDefault();

		if (FragmentEntryLinkList._dropValid(data)) {
			requestAnimationFrame(
				() => {
					this._initializeDragAndDrop();
				}
			);

			const itemData = FragmentEntryLinkList._getItemData(
				data.source.dataset
			);

			let moveItemAction = null;
			let moveItemPayload = null;

			if (itemData.itemType === DROP_TARGET_ITEM_TYPES.section) {
				moveItemAction = MOVE_SECTION;
				moveItemPayload = {
					sectionId: itemData.itemId,
					targetBorder: this.dropTargetBorder,
					targetItemId: this.dropTargetItemId
				};
			}
			else if (itemData.itemType === DROP_TARGET_ITEM_TYPES.fragment) {
				moveItemAction = MOVE_FRAGMENT_ENTRY_LINK;
				moveItemPayload = {
					fragmentEntryLinkId: itemData.itemId
				};
			}

			moveItem(this.store, moveItemAction, moveItemPayload);
		}
	}

	/**
	 * @param {object} event
	 * @private
	 * @review
	 */
	_handleFragmentMove(event) {
		const placeholderId = event.fragmentEntryLinkId;

		const rowIndex = getFragmentRowIndex(
			this.layoutData.structure,
			placeholderId
		);

		let targetId;

		const targetRow = this
			.layoutData
			.structure[rowIndex + event.direction];

		if (targetRow && targetRow.columns.length) {
			targetId = targetRow.columns[0].fragmentEntryLinkIds[0];
		}

		if (event.direction === 1) {
			this._targetBorder = DROP_TARGET_BORDERS.bottom;
		}
		else {
			this._targetBorder = DROP_TARGET_BORDERS.top;
		}

		if (targetId && targetId !== placeholderId) {
			this.store
				.dispatchAction(
					UPDATE_SAVING_CHANGES_STATUS,
					{
						savingChanges: true
					}
				)
				.dispatchAction(
					MOVE_FRAGMENT_ENTRY_LINK,
					{
						originFragmentEntryLinkId: placeholderId,
						targetFragmentEntryLinkBorder: this._targetBorder,
						targetFragmentEntryLinkId: targetId
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

	/**
	 * Callback executed when a section lose the focus
	 * @private
	 */
	_handleSectionBlur() {
		this.store.dispatchAction(CLEAR_ACTIVE_ITEM);
	}

	/**
	 * Callback executed when a section is clicked.
	 * @param {object} event
	 * @private
	 */
	_handleSectionClick(event) {
		event.stopPropagation();

		this.store.dispatchAction(
			UPDATE_ACTIVE_ITEM,
			{
				activeItemId: event.delegateTarget.dataset.layoutSectionId,
				activeItemType: DROP_TARGET_ITEM_TYPES.section
			}
		);
	}

	/**
	 * Callback executed when a section starts being hovered.
	 * @param {object} event
	 * @private
	 */
	_handleSectionHoverStart(event) {
		if (this.store) {
			this.store.dispatchAction(
				UPDATE_HOVERED_ITEM,
				{
					hoveredItemId: event.delegateTarget.dataset.layoutSectionId,
					hoveredItemType: DROP_TARGET_ITEM_TYPES.section
				}
			);
		}
	}

	/**
	 * Callback executed when a section ends being hovered.
	 * @private
	 */
	_handleSectionHoverEnd() {
		if (this.store) {
			this.store.dispatchAction(CLEAR_HOVERED_ITEM);
		}
	}

	/**
	 * Callback executed when a key is pressed on focused section
	 * @private
	 * @param {object} event
	 */
	_handleSectionKeyUp(event) {
		const direction = getItemMoveDirection(event.which);
		const sectionId = document.activeElement.dataset.layoutSectionId;
		const sectionIndex = getSectionIndex(
			this.layoutData.structure,
			sectionId
		);
		const targetItem = this.layoutData.structure[
			sectionIndex + direction
		];

		if (direction && targetItem) {
			const moveItemPayload = {
				sectionId,
				targetBorder: getTargetBorder(direction),
				targetItemId: targetItem.rowId
			};

			this.store.dispatchAction(
				UPDATE_ACTIVE_ITEM,
				{
					activeItemId: sectionId,
					activeItemType: DROP_TARGET_ITEM_TYPES.section
				}
			);

			moveItem(this.store, MOVE_SECTION, moveItemPayload);
		}
	}

	/**
	 * Callback executed when the remove section button is clicked
	 * @private
	 */
	_handleSectionRemoveButtonClick() {
		event.stopPropagation();

		this.store
			.dispatchAction(
				REMOVE_SECTION,
				{
					sectionId: this.hoveredItemId
				}
			)
			.dispatchAction(CLEAR_HOVERED_ITEM);
	}

	/**
	 * @private
	 * @review
	 */
	_initializeDragAndDrop() {
		if (this._dragDrop) {
			this._dragDrop.dispose();
		}

		this._dragDrop = new DragDrop(
			{
				autoScroll: true,
				dragPlaceholder: Drag.Placeholder.CLONE,
				handles: '.drag-handler',
				sources: '.drag-fragment, .drag-section',
				targets: '.fragment-entry-link-drop-target'
			}
		);

		this._dragDrop.on(
			DragDrop.Events.DRAG,
			this._handleDrag.bind(this)
		);

		this._dragDrop.on(
			DragDrop.Events.END,
			this._handleDrop.bind(this)
		);

		this._dragDrop.on(
			DragDrop.Events.TARGET_LEAVE,
			this._handleDragEnd.bind(this)
		);
	}

}

/**
 * State definition.
 * @review
 * @static
 * @type {!Object}
 */
FragmentEntryLinkList.STATE = {

	/**
	 * Id of the active element
	 * @default {string}
	 * @instance
	 * @memberOf FragmentEntryLinkList
	 * @review
	 * @type {string}
	 */
	activeItemId: state.activeItemId,

	/**
	 * Type of the active element
	 * @default {string}
	 * @instance
	 * @memberOf FragmentEntryLinkList
	 * @review
	 * @type {string}
	 */
	activeItemType: state.activeItemType,

	/**
	 * Border of the target item where another item is being dragged to
	 * @default {string}
	 * @instance
	 * @memberOf FragmentEntryLinkList
	 * @review
	 * @type {string}
	 */
	dropTargetBorder: state.dropTargetBorder,

	/**
	 * Id of the element where a fragment is being dragged over
	 * @default {string}
	 * @instance
	 * @memberOf FragmentEntryLinkList
	 * @review
	 * @type {string}
	 */
	dropTargetItemId: state.dropTargetItemId,

	/**
	 * Id of the last element that was hovered
	 * @default {string}
	 * @instance
	 * @memberOf FragmentEntryLinkList
	 * @review
	 * @type {string}
	 */
	hoveredItemId: state.hoveredItemId,

	/**
	 * Data associated to the layout
	 * @default {object}
	 * @instance
	 * @memberOf FragmentEntryLinkList
	 * @review
	 * @type {object}
	 */
	layoutData: state.layoutData,

	/**
	 * Internal DragDrop instance.
	 * @default null
	 * @instance
	 * @memberOf FragmentEntryLinkList
	 * @review
	 * @type {object|null}
	 */
	_dragDrop: Config.internal().value(null)
};

Soy.register(FragmentEntryLinkList, templates);

export {FragmentEntryLinkList};
export default FragmentEntryLinkList;