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
import DragScroller from '../../utils/DragScroller.es';
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
		this._initializeDragScroller();
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
	 * @param {object} data
	 * @param {MouseEvent} data.originalEvent
	 * @private
	 * @review
	 */

	_handleDrag(data) {
		const targetItem = data.target;

		if (targetItem && 'fragmentEntryLinkId' in targetItem.dataset) {
			const mouseY = data.originalEvent.clientY;
			const targetItemRegion = position.getRegion(targetItem);

			const documentHeight = document.body.offsetHeight;
			let placeholderItemRegion = position.getRegion(data.placeholder);

			this._dragScroller.scrollOnDrag(placeholderItemRegion, documentHeight);

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

	_handleDragEnd() {
		this.store.dispatchAction(
			CLEAR_DRAG_TARGET
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

		if (data.target) {
			const placeholderId = data.source.dataset.fragmentEntryLinkId;
			const targetId = data.target.dataset.fragmentEntryLinkId;

			requestAnimationFrame(
				() => {
					this._initializeDragAndDrop();
					this._initializeDragScroller();
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

	/**
	 * @private
	 * @review
	 */

	_initializeDragScroller() {
		const controlMenu = document.querySelector('.control-menu');
		const controlMenuHeight = controlMenu ? controlMenu.offsetHeight : 0;
		const managementBar = document.querySelector('.management-bar');
		const managementBarHeight = managementBar ? managementBar.offsetHeight : 0;

		this._dragScroller = new DragScroller(
			{
				upOffset: controlMenuHeight + managementBarHeight
			}
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
	 * Internal DragScroller instance
	 * @default null
	 * @instance
	 * @memberOf FragmentEntryLinkList
	 * @review
	 * @type {object|null}
	 */

	_dragScroller: Config.internal().value(null),

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