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

import './FragmentsEditorSidebarCard.es';
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
import {getConnectedComponent} from '../../../store/ConnectedComponent.es';
import {initializeDragDrop} from '../../../utils/FragmentsEditorDragDrop.es';
import {setDraggingItemPosition} from '../../../utils/FragmentsEditorUpdateUtils.es';
import {
	FRAGMENTS_EDITOR_ITEM_BORDERS,
	FRAGMENTS_EDITOR_ITEM_TYPES,
	FRAGMENTS_EDITOR_ROW_TYPES
} from '../../../utils/constants';
import templates from './SidebarAvailableSections.soy';

/**
 * SidebarAvailableSections
 */
class SidebarAvailableSections extends Component {
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
	disposed() {
		this._dragDrop.dispose();
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
	 * @param {object} eventData
	 * @param {MouseEvent} eventData.originalEvent
	 * @private
	 * @review
	 */
	_handleDrag(eventData) {
		const targetItem = eventData.target;

		const data = targetItem ? targetItem.dataset : null;
		const targetIsFragment = targetItem && 'fragmentEntryLinkId' in data;
		const targetIsRow = targetItem && 'layoutRowId' in data;

		setDraggingItemPosition(eventData.originalEvent);

		if (targetIsFragment || targetIsRow) {
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

			if (targetIsFragment) {
				dropTargetItemId = data.fragmentEntryLinkId;
				dropTargetItemType = FRAGMENTS_EDITOR_ITEM_TYPES.fragment;
			} else if (targetIsRow) {
				dropTargetItemId = data.layoutRowId;
				dropTargetItemType = FRAGMENTS_EDITOR_ITEM_TYPES.row;
			}

			if (dropTargetItemId && dropTargetItemType) {
				this.store.dispatch({
					dropTargetBorder: nearestBorder,
					dropTargetItemId,
					dropTargetItemType,
					type: UPDATE_DROP_TARGET
				});
			}
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
	 * @param {!object} data
	 * @param {!MouseEvent} event
	 * @private
	 * @review
	 */
	_handleDrop(data, event) {
		event.preventDefault();

		if (data.target) {
			const {itemGroupId, itemId, itemName} = data.source.dataset;

			requestAnimationFrame(() => {
				this._initializeDragAndDrop();
			});

			this.store
				.dispatch(enableSavingChangesStatusAction())
				.dispatch({
					fragmentEntryKey: itemId,
					fragmentEntryLinkRowType:
						FRAGMENTS_EDITOR_ROW_TYPES.sectionRow,
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
	 * Callback that is executed when a fragment entry is clicked.
	 * @param {{
	 *   itemId: !string,
	 *   itemName: !string
	 * }} event
	 * @private
	 */
	_handleEntryClick(event) {
		this.store
			.dispatch(enableSavingChangesStatusAction())
			.dispatch({
				fragmentEntryKey: event.itemId,
				fragmentEntryLinkRowType: FRAGMENTS_EDITOR_ROW_TYPES.sectionRow,
				fragmentName: event.itemName,
				groupId: event.itemGroupId,
				type: ADD_FRAGMENT_ENTRY_LINK
			})
			.dispatch(updateLastSaveDateAction())
			.dispatch(disableSavingChangesStatusAction());
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
			sources: '.fragments-editor__drag-source--sidebar-fragment',
			targets: '.fragments-editor__drop-target--sidebar-section'
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
 * @type {!Object}
 * @static
 */
SidebarAvailableSections.STATE = {
	/**
	 * Internal DragDrop instance.
	 * @default null
	 * @instance
	 * @memberOf SidebarAvailableSections
	 * @review
	 * @type {object|null}
	 */
	_dragDrop: Config.internal().value(null)
};

const ConnectedSidebarAvailableSections = getConnectedComponent(
	SidebarAvailableSections,
	['layoutData', 'sections', 'spritemap']
);

Soy.register(ConnectedSidebarAvailableSections, templates);

export {ConnectedSidebarAvailableSections, SidebarAvailableSections};
export default ConnectedSidebarAvailableSections;
