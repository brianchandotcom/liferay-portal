import Component from 'metal-component';
import {Config} from 'metal-state';
import {Drag, DragDrop} from 'metal-drag-drop';
import position from 'metal-position';
import Soy from 'metal-soy';

import './FragmentEntryLink.es';
import {
	CLEAR_DRAG_TARGET,
	UPDATE_DRAG_TARGET
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

			let nearestBorder = DRAG_POSITIONS.bottom;

			if (Math.abs(mouseY - targetItemRegion.top) <= Math.abs(mouseY - targetItemRegion.bottom)) {
				nearestBorder = DRAG_POSITIONS.top;
			}

			this.store.dispatchAction(
				UPDATE_DRAG_TARGET,
				{
					hoveredFragmentEntryLinkBorder: nearestBorder,
					hoveredFragmentEntryLinkId: targetItem.dataset.fragmentEntryLinkId
				}
			);
		}
	}

	/**
	 * Callback that is executed when an item is dropped.
	 * @param {!MouseEvent} event
	 * @private
	 * @review
	 */

	_handleDrop(data, event) {
		event.preventDefault();

		this.store
			.dispatchAction(
				CLEAR_DRAG_TARGET
			);
	}

	/**
	 * @param {object} event
	 * @private
	 * @review
	 */

	_handleEditableChanged(event) {
		this.emit('editableChanged', event);
	}

	/**
	 * @param {object} event
	 * @private
	 * @review
	 */

	_handleFragmentMove(event) {
		this.emit('move', event);
	}

	/**
	 * @param {object} event
	 * @private
	 * @review
	 */

	_handleMappeableFieldClicked(event) {
		this.emit('mappeableFieldClicked', event);
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

	hoveredFragmentEntryLinkId: Config.string()
};

Soy.register(FragmentEntryLinkList, templates);

export {FragmentEntryLinkList};
export default FragmentEntryLinkList;