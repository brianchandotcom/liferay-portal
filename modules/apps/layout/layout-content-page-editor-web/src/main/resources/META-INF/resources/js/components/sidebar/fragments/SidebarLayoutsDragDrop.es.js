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
	ADD_ROW,
	CLEAR_DROP_TARGET,
	UPDATE_DROP_TARGET
} from '../../../actions/actions.es';
import {
	FRAGMENTS_EDITOR_ITEM_BORDERS,
	FRAGMENTS_EDITOR_ITEM_TYPES
} from '../../../utils/constants';
import {initializeDragDrop} from '../../../utils/FragmentsEditorDragDrop.es';
import {setDraggingItemPosition} from '../../../utils/FragmentsEditorUpdateUtils.es';

/**
 * SidebarLayoutsDragDrop
 */
class SidebarLayoutsDragDrop extends State {
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

		setDraggingItemPosition(eventData.originalEvent);

		if (targetItem && 'layoutRowId' in targetItem.dataset) {
			const mouseY = eventData.originalEvent.clientY;
			const targetItemRegion = position.getRegion(targetItem);

			let nearestBorder = FRAGMENTS_EDITOR_ITEM_BORDERS.bottom;

			if (
				Math.abs(mouseY - targetItemRegion.top) <=
				Math.abs(mouseY - targetItemRegion.bottom)
			) {
				nearestBorder = FRAGMENTS_EDITOR_ITEM_BORDERS.top;
			}

			this.dispatch({
				dropTargetBorder: nearestBorder,
				dropTargetItemId: targetItem.dataset.layoutRowId,
				dropTargetItemType: FRAGMENTS_EDITOR_ITEM_TYPES.row,
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

		if (data.target) {
			const layoutColumns = this.layouts[data.source.dataset.layoutIndex]
				.columns;

			this.dispatch({
				layoutColumns,
				type: ADD_ROW
			}).dispatch({
				type: CLEAR_DROP_TARGET
			});

			requestAnimationFrame(() => {
				this._initializeDragAndDrop();
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
			sources: '.fragments-editor__drag-source--sidebar-layout',
			targets: '.fragments-editor__drop-target--sidebar-layout'
		});

		this._dragDrop.on(DragDrop.Events.DRAG, this._handleDrag.bind(this));

		this._dragDrop.on(DragDrop.Events.END, this._handleDrop.bind(this));

		this._dragDrop.on(
			DragDrop.Events.TARGET_LEAVE,
			this._handleDragEnd.bind(this)
		);
	}
}

SidebarLayoutsDragDrop.STATE = {
	/**
	 * Internal DragDrop instance.
	 * @default null
	 * @instance
	 * @memberOf SidebarLayoutsDragDrop
	 * @review
	 * @type {object|null}
	 */
	_dragDrop: Config.object().internal(),

	/**
	 * @instance
	 * @memberOf SidebarLayoutsDragDrop
	 * @review
	 * @type {function}
	 */
	dispatch: Config.func().required(),

	/**
	 * @instance
	 * @memberOf SidebarLayoutsDragDrop
	 * @review
	 * @type {array}
	 */
	layouts: Config.array().required()
};

export default SidebarLayoutsDragDrop;
