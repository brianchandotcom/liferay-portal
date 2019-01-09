import position from 'metal-position';
import {Drag, DragDrop} from 'metal-drag-drop';
import State from 'metal-state';

import {DROP_TARGET_BORDERS} from '../../../../reducers/placeholders.es';

/**
 * SidebarLayoutsDragDrop
 */
class SidebarLayoutsDragDrop extends State {

	/**
	 * @inheritDoc
	 * @review
	 */
	constructor(config, ...args) {
		super(config, ...args);

		this._initializeDragAndDrop();
	}

	/**
	 * @inheritDoc
	 * @review
	 */
	dispose() {
		this._dragDrop.dispose();
	}

	/**
	 * Callback that is executed when a layout is being dragged.
	 * @param {object} data
	 * @param {MouseEvent} data.originalEvent
	 * @private
	 * @review
	 */
	_handleDrag(data) {
		const targetItem = data.target;

		if (targetItem && 'layoutSectionId' in targetItem.dataset) {
			const mouseY = data.originalEvent.clientY;
			const targetItemRegion = position.getRegion(targetItem);

			let nearestBorder = DROP_TARGET_BORDERS.bottom;

			if (
				Math.abs(mouseY - targetItemRegion.top) <=
				Math.abs(mouseY - targetItemRegion.bottom)
			) {
				nearestBorder = DROP_TARGET_BORDERS.top;
			}

			this.emit(
				'dragLayout',
				{
					hoveredSectionBorder: nearestBorder,
					hoveredSectionId: targetItem.dataset.layoutSectionId
				}
			);
		}
	}

	/**
	 * Callback that is executed when a drag target is leaved.
	 * @private
	 * @review
	 */
	_handleDragEnd() {
		this.emit('leaveLayoutTarget');
	}

	/**
	 * Callback that is executed when a layout is dropped.
	 * @param {!object} data
	 * @param {!HTMLElement} data.source
	 * @param {!MouseEvent} event
	 * @private
	 * @review
	 */
	_handleDrop(data, event) {
		event.preventDefault();

		if (data.target) {
			this.emit(
				'dropLayout',
				{
					layoutIndex: data.source.dataset.layoutIndex
				}
			);
		}
	}

	/**
	 * @private
	 * @review
	 */
	_initializeDragAndDrop() {
		this._dragDrop = new DragDrop(
			{
				autoScroll: true,
				dragPlaceholder: Drag.Placeholder.CLONE,
				sources: '.layouts-drag-section',
				targets: '.fragments-editor__drop-target--layout'
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

export default SidebarLayoutsDragDrop;