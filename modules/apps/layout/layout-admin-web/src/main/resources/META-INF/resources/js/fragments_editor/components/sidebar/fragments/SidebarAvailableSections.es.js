import Component from 'metal-component';
import {Config} from 'metal-state';
import {Drag, DragDrop} from 'metal-drag-drop';
import position from 'metal-position';
import Soy from 'metal-soy';

import './FragmentsEditorSidebarCard.es';
import {
	ADD_FRAGMENT_ENTRY_LINK,
	CLEAR_DROP_TARGET,
	UPDATE_DROP_TARGET,
	UPDATE_LAST_SAVE_DATE,
	UPDATE_SAVING_CHANGES_STATUS
} from '../../../actions/actions.es';
import {
	DROP_TARGET_BORDERS,
	DROP_TARGET_ITEM_TYPES
} from '../../../reducers/placeholders.es';
import {Store} from '../../../store/store.es';
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
	dispose() {
		this._dragDrop.dispose();
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
		const targetIsSection = targetItem && ('layoutSectionId' in data);

		if (targetIsColumn || targetIsFragment || targetIsSection) {
			const mouseY = eventData.originalEvent.clientY;
			const targetItemRegion = position.getRegion(targetItem);

			let nearestBorder = DROP_TARGET_BORDERS.bottom;

			if (
				Math.abs(mouseY - targetItemRegion.top) <=
				Math.abs(mouseY - targetItemRegion.bottom)
			) {
				nearestBorder = DROP_TARGET_BORDERS.top;
			}

			let dropTargetItemId = null;
			let dropTargetItemType = null;

			if (targetIsColumn) {
				dropTargetItemId = data.columnId;
				dropTargetItemType = DROP_TARGET_ITEM_TYPES.column;
			}
			else if (targetIsFragment) {
				dropTargetItemId = data.fragmentEntryLinkId;
				dropTargetItemType = DROP_TARGET_ITEM_TYPES.fragment;
			}
			else if (targetIsSection) {
				dropTargetItemId = data.layoutSectionId;
				dropTargetItemType = DROP_TARGET_ITEM_TYPES.section;
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
						fragmentEntryId: itemId,
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
					fragmentEntryId: event.itemId,
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

		this._dragDrop = new DragDrop(
			{
				autoScroll: true,
				dragPlaceholder: Drag.Placeholder.CLONE,
				handles: '.drag-handler',
				sources: '.drag-card',
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
 * @type {!Object}
 * @static
 */
SidebarAvailableSections.STATE = {

	/**
	 * Available sections that can be dragged inside the existing Page Template,
	 * organized by fragment categories.
	 * @default undefined
	 * @instance
	 * @memberOf SidebarAvailableSections
	 * @type {!Array<{
	 *   fragmentCollectionId: !string,
	 *   fragmentEntries: Array<{
	 *     fragmentEntryId: !string,
	 *     imagePreviewURL: string,
	 *     name: !string
	 *   }>,
	 *   name: !string
	 * }>}
	 */
	sections: Config.arrayOf(
		Config.shapeOf(
			{
				fragmentCollectionId: Config.string().required(),
				fragmentEntries: Config.arrayOf(
					Config.shapeOf(
						{
							fragmentEntryId: Config.string().required(),
							imagePreviewURL: Config.string(),
							name: Config.string().required()
						}
					).required()
				).required(),
				name: Config.string().required()
			}
		)
	),

	/**
	 * Path of the available icons.
	 * @default undefined
	 * @instance
	 * @memberOf SidebarAvailableSections
	 * @type {!string}
	 */
	spritemap: Config.string().required(),

	/**
	 * Store instance
	 * @default undefined
	 * @instance
	 * @memberOf SidebarAvailableSections
	 * @review
	 * @type {Store}
	 */
	store: Config.instanceOf(Store),

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

Soy.register(SidebarAvailableSections, templates);

export {SidebarAvailableSections};
export default SidebarAvailableSections;