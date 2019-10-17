/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import Component from 'metal-component';
import {DragDrop} from 'metal-drag-drop';
import position from 'metal-position';
import Soy from 'metal-soy';
import {Config} from 'metal-state';

import '../floating_toolbar/FloatingToolbar.es';

import './FragmentEntryLink.es';

import './FragmentEntryLinkListRow.es';
import {
	CLEAR_DROP_TARGET,
	MOVE_FRAGMENT_ENTRY_LINK,
	MOVE_ROW,
	UPDATE_DROP_TARGET
} from '../../actions/actions.es';
import getConnectedComponent from '../../store/ConnectedComponent.es';
import {shouldUpdatePureComponent} from '../../utils/FragmentsEditorComponentUtils.es';
import {initializeDragDrop} from '../../utils/FragmentsEditorDragDrop.es';
import {
	moveItem,
	setDraggingItemPosition,
	setIn
} from '../../utils/FragmentsEditorUpdateUtils.es';
import {
	FRAGMENTS_EDITOR_ITEM_BORDERS,
	FRAGMENTS_EDITOR_ITEM_TYPES,
	FRAGMENTS_EDITOR_ROW_TYPES
} from '../../utils/constants';
import templates from './FragmentEntryLinkList.soy';

/**
 * FragmentEntryLinkList
 * @review
 */
class FragmentEntryLinkList extends Component {
	/**
	 * Adds drop target types to state
	 * @param {Object} state
	 * @private
	 * @return {Object}
	 * @static
	 */
	static _addDropTargetItemTypesToState(state) {
		let nextState = state;

		nextState = setIn(
			nextState,
			['dropTargetItemTypes'],
			FRAGMENTS_EDITOR_ITEM_TYPES
		);

		nextState = setIn(
			nextState,
			['fragmentsEditorRowTypes'],
			FRAGMENTS_EDITOR_ROW_TYPES
		);

		return nextState;
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

		if (sourceItemData.itemType === FRAGMENTS_EDITOR_ITEM_TYPES.row) {
			dropValid =
				targetItemData.itemType === FRAGMENTS_EDITOR_ITEM_TYPES.row &&
				sourceItemData.itemId !== targetItemData.itemId;
		} else if (
			sourceItemData.itemType === FRAGMENTS_EDITOR_ITEM_TYPES.fragment
		) {
			if (
				sourceItemData.fragmentEntryLinkRowType ===
				FRAGMENTS_EDITOR_ROW_TYPES.sectionRow
			) {
				dropValid =
					targetItemData.itemType &&
					sourceItemData.itemId !== targetItemData.itemId &&
					targetItemData.itemType !==
						FRAGMENTS_EDITOR_ITEM_TYPES.column &&
					targetItemData.itemType !==
						FRAGMENTS_EDITOR_ITEM_TYPES.fragment;
			} else {
				dropValid =
					targetItemData.itemType &&
					sourceItemData.itemId !== targetItemData.itemId;
			}
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
		let itemData = {};

		if (itemDataset) {
			if ('columnId' in itemDataset) {
				itemData = {
					itemId: itemDataset.columnId,
					itemType: FRAGMENTS_EDITOR_ITEM_TYPES.column
				};
			} else if ('fragmentEntryLinkId' in itemDataset) {
				itemData = {
					fragmentEntryLinkRowType:
						itemDataset.fragmentEntryLinkRowType,
					itemId: itemDataset.fragmentEntryLinkId,
					itemType: FRAGMENTS_EDITOR_ITEM_TYPES.fragment
				};
			} else if ('layoutRowId' in itemDataset) {
				itemData = {
					itemId: itemDataset.layoutRowId,
					itemType: FRAGMENTS_EDITOR_ITEM_TYPES.row
				};
			} else if ('fragmentEmptyList' in itemDataset) {
				itemData = {
					itemType: FRAGMENTS_EDITOR_ITEM_TYPES.fragmentList
				};
			}
		}

		return itemData;
	}

	/**
	 * Checks wether a row is empty or not, sets empty parameter
	 * and returns a new state
	 * @param {Object} _state
	 * @private
	 * @return {Object}
	 * @static
	 */
	static _setEmptyRows(_state) {
		return setIn(
			_state,
			['layoutData', 'structure'],
			_state.layoutData.structure.map(row =>
				setIn(
					row,
					['empty'],
					row.columns.every(
						column => column.fragmentEntryLinkIds.length === 0
					)
				)
			)
		);
	}

	/**
	 * @inheritdoc
	 * @private
	 * @review
	 */
	attached() {
		this._initializeDragAndDrop();
	}

	/**
	 * @inheritdoc
	 * @private
	 * @review
	 */
	disposed() {
		this._dragDrop.dispose();
	}

	/**
	 * @inheritdoc
	 * @private
	 * @return {Object}
	 * @review
	 */
	prepareStateForRender(nextState) {
		let _state = FragmentEntryLinkList._addDropTargetItemTypesToState(
			nextState
		);

		_state = FragmentEntryLinkList._setEmptyRows(_state);

		return _state;
	}

	/**
	 * @inheritdoc
	 * @private
	 * @review
	 */
	rendered() {
		if (
			this.activeItemType === FRAGMENTS_EDITOR_ITEM_TYPES.fragmentList &&
			this.element
		) {
			this.element.focus();
		}
	}

	/**
	 * @inheritdoc
	 * @return {boolean}
	 * @review
	 */
	shouldUpdate(changes) {
		return shouldUpdatePureComponent(changes);
	}

	/**
	 * Handle layoutData changed
	 * @inheritDoc
	 * @review
	 */
	syncLayoutData() {
		this._initializeDragAndDrop();
	}

	/**
	 * Callback that is executed when an item is being dragged.
	 * @param {Object} eventData
	 * @param {MouseEvent} eventData.originalEvent
	 * @private
	 * @review
	 */
	_handleDrag(eventData) {
		setDraggingItemPosition(eventData.originalEvent);

		if (FragmentEntryLinkList._dropValid(eventData)) {
			const mouseY = eventData.originalEvent.clientY;
			const targetItem = eventData.target;
			const targetItemRegion = position.getRegion(targetItem);

			const dropTargetItemData = FragmentEntryLinkList._getItemData(
				targetItem.dataset
			);

			let targetBorder = FRAGMENTS_EDITOR_ITEM_BORDERS.bottom;

			if (
				Math.abs(mouseY - targetItemRegion.top) <=
				Math.abs(mouseY - targetItemRegion.bottom)
			) {
				targetBorder = FRAGMENTS_EDITOR_ITEM_BORDERS.top;
			}

			this.store.dispatch({
				dropTargetBorder: targetBorder,
				dropTargetItemId: dropTargetItemData.itemId,
				dropTargetItemType: dropTargetItemData.itemType,
				type: UPDATE_DROP_TARGET
			});
		}
	}

	/**
	 * Callback that is executed when we leave a drag target.
	 * @private
	 * @review
	 */
	_handleDragEnd() {
		this.store.dispatch({
			type: CLEAR_DROP_TARGET
		});
	}

	/**
	 * Callback that is executed when an item is dropped.
	 * @param {Object} data
	 * @param {MouseEvent} event
	 * @private
	 * @review
	 */
	_handleDrop(data, event) {
		event.preventDefault();

		if (FragmentEntryLinkList._dropValid(data)) {
			requestAnimationFrame(() => {
				this._initializeDragAndDrop();
			});

			const itemData = FragmentEntryLinkList._getItemData(
				data.source.dataset
			);

			let moveItemAction = null;
			let moveItemPayload = null;

			if (itemData.itemType === FRAGMENTS_EDITOR_ITEM_TYPES.row) {
				moveItemAction = MOVE_ROW;
				moveItemPayload = {
					rowId: itemData.itemId,
					targetBorder: this.dropTargetBorder,
					targetItemId: this.dropTargetItemId
				};
			} else if (
				itemData.itemType === FRAGMENTS_EDITOR_ITEM_TYPES.fragment
			) {
				moveItemAction = MOVE_FRAGMENT_ENTRY_LINK;
				moveItemPayload = {
					fragmentEntryLinkId: itemData.itemId,
					fragmentEntryLinkRowType: itemData.fragmentEntryLinkRowType,
					targetBorder: this.dropTargetBorder,
					targetItemId: this.dropTargetItemId,
					targetItemType: this.dropTargetItemType
				};
			}

			moveItem(this.store, moveItemAction, moveItemPayload);
		}
	}

	/**
	 * @private
	 * @review
	 */
	_initializeDragAndDrop() {
		if (this._dragDrop) {
			this._dragDrop.dispose();
		}

		this._dragDrop = initializeDragDrop({
			handles: '.fragments-editor__drag-handler',
			sources:
				'.fragments-editor__drag-source--fragment, .fragments-editor__drag-source--layout',
			targets:
				'.fragments-editor__drop-target--fragment, .fragments-editor__drop-target--layout'
		});

		this._dragDrop.on(DragDrop.Events.DRAG, this._handleDrag.bind(this));

		this._dragDrop.on(DragDrop.Events.END, this._handleDrop.bind(this));

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
	 * Internal DragDrop instance.
	 * @default null
	 * @instance
	 * @memberOf FragmentEntryLinkList
	 * @review
	 * @type {object|null}
	 */
	_dragDrop: Config.internal().value(null)
};

const ConnectedFragmentEntryLinkList = getConnectedComponent(
	FragmentEntryLinkList,
	[
		'activeItemId',
		'activeItemType',
		'dropTargetBorder',
		'dropTargetItemId',
		'dropTargetItemType',
		'hoveredItemId',
		'hoveredItemType',
		'layoutData',
		'selectedItems'
	]
);

Soy.register(ConnectedFragmentEntryLinkList, templates);

export {ConnectedFragmentEntryLinkList, FragmentEntryLinkList};
export default ConnectedFragmentEntryLinkList;
