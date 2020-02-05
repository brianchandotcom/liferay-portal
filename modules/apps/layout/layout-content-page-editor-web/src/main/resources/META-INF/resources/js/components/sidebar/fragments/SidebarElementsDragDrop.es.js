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

import {DragDrop} from 'metal-drag-drop';
import position from 'metal-position';
import State, {Config} from 'metal-state';

import {
	ADD_FRAGMENT_ENTRY_LINK,
	CLEAR_DROP_TARGET,
	UPDATE_DROP_TARGET
} from '../../../actions/actions.es';
import {
	disableSavingChangesStatusAction,
	enableSavingChangesStatusAction,
	updateLastSaveDateAction
} from '../../../actions/saveChanges.es';
import {initializeDragDrop} from '../../../utils/FragmentsEditorDragDrop.es';
import {setDraggingItemPosition} from '../../../utils/FragmentsEditorUpdateUtils.es';
import {
	FRAGMENTS_EDITOR_ITEM_BORDERS,
	FRAGMENTS_EDITOR_ITEM_TYPES,
	FRAGMENTS_EDITOR_ROW_TYPES
} from '../../../utils/constants';

/**
 * SidebarElementsDragDrop
 */
class SidebarElementsDragDrop extends State {
	/**
	 * @inheritdoc
	 * @private
	 * @review
	 */
	constructor(props) {
		super(props);

		this._initializeDragAndDrop();
	}

	/**
	 * @inheritdoc
	 * @private
	 * @review
	 */
	dispose() {
		this._dragDrop.dispose();
		super.dispose();
	}

	/**
	 * Callback that is executed when an item is being dragged.
	 * @param {Object} eventData
	 * @param {MouseEvent} eventData.originalEvent
	 * @private
	 * @review
	 */
	_handleDrag(eventData) {
		const targetItem = eventData.target;

		const data = targetItem ? targetItem.dataset : null;
		const targetIsColumn = targetItem && 'columnId' in data;
		const targetIsFragment = targetItem && 'fragmentEntryLinkId' in data;
		const targetIsRow = targetItem && 'layoutRowId' in data;

		setDraggingItemPosition(eventData.originalEvent);

		const sourceItem = eventData.source;

		const sourceData = sourceItem ? sourceItem.dataset : null;

		if (sourceData && !this._targetSet) {
			this._targetSet = true;

			this._dragDrop.targets =
				sourceData.itemType === FRAGMENTS_EDITOR_ROW_TYPES.sectionRow
					? '.fragments-editor__drop-target--sidebar-section'
					: '.fragments-editor__drop-target--sidebar-fragment';
		}

		if (targetIsColumn || targetIsFragment || targetIsRow) {
			const mouseY = eventData.originalEvent.clientY;
			const targetItemRegion = position.getRegion(targetItem);

			let nearestBorder = FRAGMENTS_EDITOR_ITEM_BORDERS.bottom;

			if (
				Math.abs(mouseY - targetItemRegion.top) <=
				Math.abs(mouseY - targetItemRegion.bottom)
			) {
				nearestBorder = FRAGMENTS_EDITOR_ITEM_BORDERS.top;
			}

			let dropTargetItemId = null;
			let dropTargetItemType = null;

			if (targetIsColumn) {
				dropTargetItemId = data.columnId;
				dropTargetItemType = FRAGMENTS_EDITOR_ITEM_TYPES.column;
			}
			else if (targetIsFragment) {
				dropTargetItemId = data.fragmentEntryLinkId;
				dropTargetItemType = FRAGMENTS_EDITOR_ITEM_TYPES.fragment;
			}
			else if (targetIsRow) {
				dropTargetItemId = data.layoutRowId;
				dropTargetItemType = FRAGMENTS_EDITOR_ITEM_TYPES.row;
			}

			this.dispatch({
				dropTargetBorder: nearestBorder,
				dropTargetItemId,
				dropTargetItemType,
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
		this.dispatch({
			type: CLEAR_DROP_TARGET
		});
	}

	/**
	 * Callback that is executed when an item is dropped.
	 * @param {!Object} data
	 * @param {!MouseEvent} event
	 * @private
	 * @review
	 */
	_handleDrop(data, event) {
		event.preventDefault();

		this._targetSet = false;

		if (data.target) {
			const {
				itemGroupId,
				itemId,
				itemName,
				itemType
			} = data.source.dataset;

			requestAnimationFrame(() => {
				this._initializeDragAndDrop();
			});

			this.dispatch(enableSavingChangesStatusAction())
				.dispatch({
					fragmentEntryKey: itemId,
					fragmentEntryLinkRowType: itemType,
					fragmentName: itemName,
					groupId: itemGroupId,
					type: ADD_FRAGMENT_ENTRY_LINK
				})
				.dispatch(updateLastSaveDateAction())
				.dispatch(disableSavingChangesStatusAction())
				.dispatch({
					type: CLEAR_DROP_TARGET
				});
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
			sources: '.fragments-editor__drag-source--sidebar-fragment'
		});

		this._dragDrop.on(DragDrop.Events.DRAG, this._handleDrag.bind(this));

		this._dragDrop.on(DragDrop.Events.END, this._handleDrop.bind(this));

		this._dragDrop.on(
			DragDrop.Events.TARGET_LEAVE,
			this._handleDragEnd.bind(this)
		);
	}
}

SidebarElementsDragDrop.STATE = {
	/**
	 * Internal DragDrop instance.
	 * @default null
	 * @instance
	 * @memberOf SidebarElementsDragDrop
	 * @review
	 * @type {object|null}
	 */
	_dragDrop: Config.object().internal(),

	/**
	 * Controls whether the target has been set.
	 * @default false
	 * @instance
	 * @memberOf SidebarElementsDragDrop
	 * @review
	 * @type {boolean}
	 */
	_targetSet: Config.bool().value(false),

	/**
	 * @instance
	 * @memberOf SidebarElementsDragDrop
	 * @review
	 * @type {function}
	 */
	dispatch: Config.func().required()
};

export default SidebarElementsDragDrop;
