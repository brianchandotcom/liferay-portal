import Component from 'metal-component';
import {Config} from 'metal-state';
import {Drag, DragDrop} from 'metal-drag-drop';
import position from 'metal-position';
import Soy from 'metal-soy';

import './FragmentEntryLink.es';
import {
	CLEAR_DRAG_TARGET,
	MOVE_FRAGMENT_ENTRY_LINK,
	UPDATE_DRAG_TARGET,
	UPDATE_LAST_SAVE_DATE,
	UPDATE_SAVING_CHANGES_STATUS
} from '../../actions/actions.es';
import {DRAG_POSITIONS} from '../../reducers/placeholders.es';
import templates from './FragmentEntryLinkList.soy';

/**
 * FragmentEntryLinkList
 * @review
 */

class FragmentEntryLinkList extends Component {

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
	 * @param {!MouseEvent} event
	 * @private
	 * @review
	 */

	_handleDrag(data, event) {
		const targetItem = data.target;

		if (targetItem && 'fragmentEntryLinkId' in targetItem.dataset) {
			const mouseY = event.target.mousePos_.y;
			const targetItemRegion = position.getRegion(targetItem);

			this._targetBorder = DRAG_POSITIONS.bottom;

			if (Math.abs(mouseY - targetItemRegion.top) <= Math.abs(mouseY - targetItemRegion.bottom)) {
				this._targetBorder = DRAG_POSITIONS.top;
			}

			this.store.dispatchAction(
				UPDATE_DRAG_TARGET,
				{
					hoveredFragmentEntryLinkBorder: this._targetBorder,
					hoveredFragmentEntryLinkId: targetItem.dataset.fragmentEntryLinkId
				}
			);
		}
	}

	/**
	* Callback that is executed when we leave a drag target.
	* @param {!MouseEvent} event
	* @private
	* @review
	*/

	_handleDragEnd(data, event) {
		this.store.dispatchAction(
			CLEAR_DRAG_TARGET
		);
	}

	/**
	 * Callback that is executed when an item is dropped.
	 * @param {!MouseEvent} event
	 * @private
	 * @review
	 */

	_handleDrop(data, event) {
		event.preventDefault();

		if (data.target) {
			const placeholderId = data.source.dataset.fragmentEntryLinkId;
			const targetId = data.target.dataset.fragmentEntryLinkId;

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
					MOVE_FRAGMENT_ENTRY_LINK,
					{
						placeholderId: placeholderId,
						targetBorder: this._targetBorder,
						targetId: targetId
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
					CLEAR_DRAG_TARGET
				);
		}
	}

	/**
	 * @param {object} event
	 * @private
	 * @review
	 */

	_handleFragmentMove(event) {
		const placeholderId = event.fragmentEntryLinkId;
		const placeholderIndex = this.layoutData.structure.indexOf(placeholderId);
		const targetId = this.layoutData.structure[placeholderIndex + event.direction];

		if (event.direction === 1) {
			this._targetBorder = DRAG_POSITIONS.bottom;
		}
		else {
			this._targetBorder = DRAG_POSITIONS.top;
		}

		if (targetId) {
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
						placeholderId: placeholderId,
						targetBorder: this._targetBorder,
						targetId: targetId
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
	 * @private
	 * @review
	 */

	_initializeDragAndDrop() {
		if (this._dragDrop) {
			this._dragDrop.dispose();
		}

		this._dragDrop = new DragDrop(
			{
				dragPlaceholder: Drag.Placeholder.CLONE,
				handles: '.drag-handler',
				sources: '.drag-fragment',
				targets: `.${this.dropTargetClass}`
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
	 * CSS class for the fragments drop target.
	 * @default undefined
	 * @instance
	 * @memberOf FragmentsEditor
	 * @review
	 * @type {!string}
	 */

	dropTargetClass: Config.string(),

	/**
	 * Nearest border of the hovered fragment entry link when dragging.
	 * @default undefined
	 * @instance
	 * @memberOf FragmentEntryLinkList
	 * @review
	 * @type {!string}
	 */

	hoveredFragmentEntryLinkBorder: Config.string(),

	/**
	 * Id of the hovered fragment entry link when dragging.
	 * @default undefined
	 * @instance
	 * @memberOf FragmentEntryLinkList
	 * @review
	 * @type {!string}
	 */

	hoveredFragmentEntryLinkId: Config.string(),

	/**
	 * Data associated to the layout
	 * @default {structure: []}
	 * @instance
	 * @memberOf FragmentEntryLinkList
	 * @review
	 * @type {{structure: Array<string>}}
	 */

	layoutData: Config
		.shapeOf(
			{
				structure: Config.arrayOf(Config.string())
			}
		)
		.value(
			{
				structure: []
			}
		),

	/**
	 * Internal DragDrop instance.
	 * @default null
	 * @instance
	 * @memberOf FragmentEntryLinkList
	 * @review
	 * @type {object|null}
	 */

	_dragDrop: Config.internal().value(null),

	/**
	 * Nearest border of the hovered fragment while dragging
	 * @default undefined
	 * @instance
	 * @memberOf FragmentEntryLinkList
	 * @review
	 * @type {!string}
	 */

	_targetBorder: Config.internal().string()
};

Soy.register(FragmentEntryLinkList, templates);

export {FragmentEntryLinkList};
export default FragmentEntryLinkList;