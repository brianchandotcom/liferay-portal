import Component from 'metal-component';
import {Config} from 'metal-state';
import {Drag, DragDrop} from 'metal-drag-drop';
import position from 'metal-position';
import Soy from 'metal-soy';

import './FragmentsEditorSidebarCard.es';
import {ADD_FRAGMENT_ENTRY_LINK, CLEAR_DROP_TARGET, UPDATE_DROP_TARGET, UPDATE_LAST_SAVE_DATE, UPDATE_SAVING_CHANGES_STATUS} from '../../../actions/actions.es';
import {FRAGMENTS_EDITOR_DRAGGING_CLASS, FRAGMENTS_EDITOR_ITEM_BORDERS, FRAGMENTS_EDITOR_ITEM_TYPES} from '../../../utils/constants';
import {getConnectedComponent} from '../../../store/ConnectedComponent.es';
import {initializeDragDrop} from '../../../utils/FragmentsEditorDragDrop.es';
import templates from './SidebarAvailableElements.soy';

/**
 * SidebarAvailableElements
 */
class SidebarAvailableElements extends Component {

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
	 * @param {MouseEvent} data.originalEvent
	 * @private
	 * @review
	 */
	_handleDrag(eventData) {
		const targetItem = eventData.target;

		const data = targetItem ? targetItem.dataset : null;
		const targetIsColumn = targetItem && ('columnId' in data);
		const targetIsFragment = targetItem && ('fragmentEntryLinkId' in data);
		const targetIsRow = targetItem && ('layoutRowId' in data);

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

			this.store.dispatchAction(
				UPDATE_DROP_TARGET,
				{
					dropTargetBorder: nearestBorder,
					dropTargetItemId,
					dropTargetItemType
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
	 * @param {!object} data
	 * @param {!MouseEvent} event
	 * @private
	 * @review
	 */
	_handleDrop(data, event) {
		event.preventDefault();

		if (data.target) {
			const {itemId, itemName} = data.source.dataset;

			requestAnimationFrame(
				() => {
					this._initializeDragAndDrop();
				}
			);

			this.store
				.dispatchAction(
					UPDATE_SAVING_CHANGES_STATUS,
					{
						savingChanges: true
					}
				)
				.dispatchAction(
					ADD_FRAGMENT_ENTRY_LINK,
					{
						fragmentEntryKey: itemId,
						fragmentName: itemName
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
				)
				.dispatchAction(
					CLEAR_DROP_TARGET
				);
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
			.dispatchAction(
				UPDATE_SAVING_CHANGES_STATUS,
				{
					savingChanges: true
				}
			)
			.dispatchAction(
				ADD_FRAGMENT_ENTRY_LINK,
				{
					fragmentEntryKey: event.itemId,
					fragmentName: event.itemName
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

	/**
	 * @private
	 * @review
	 */
	_initializeDragAndDrop() {
		if (this._dragDrop) {
			this._dragDrop.dispose();
		}

		this._dragDrop = initializeDragDrop(
			{
				autoScroll: true,
				draggingClass: FRAGMENTS_EDITOR_DRAGGING_CLASS,
				dragPlaceholder: Drag.Placeholder.CLONE,
				handles: '.fragments-editor__drag-handler',
				sources: '.fragments-editor__drag-source--sidebar-fragment',
				targets: '.fragments-editor__drop-target--sidebar-fragment'
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
 * @type {!Object}
 * @static
 */
SidebarAvailableElements.STATE = {

	/**
	 * Internal DragDrop instance.
	 * @default null
	 * @instance
	 * @memberOf SidebarAvailableElements
	 * @review
	 * @type {object|null}
	 */
	_dragDrop: Config.internal().value(null)
};

const ConnectedSidebarAvailableElements = getConnectedComponent(
	SidebarAvailableElements,
	[
		'layoutData',
		'elements',
		'spritemap'
	]
);

Soy.register(ConnectedSidebarAvailableElements, templates);

export {ConnectedSidebarAvailableElements, SidebarAvailableElements};
export default ConnectedSidebarAvailableElements;