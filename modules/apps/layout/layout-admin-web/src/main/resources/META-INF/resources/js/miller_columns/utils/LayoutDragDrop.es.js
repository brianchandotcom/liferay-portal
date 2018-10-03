import {Drag, DragDrop} from 'metal-drag-drop';
import position from 'metal-position';
import State, {Config} from 'metal-state';

/**
 * Borders where elements can be dragged to
 * @review
 */

const DRAG_BORDERS = {
	bottom: 'layout-column-item-drag-bottom',
	top: 'layout-column-item-drag-top'
};

/**
 * LayoutDragDrop
 */

class LayoutDragDrop extends State {

	/**
	 * @inheritDoc
	 * @review
	 */

	constructor(config, ...args) {
		super(config, ...args);

		this._initializeDragAndDrop();
	}

	/**
	 * Callback that is executed when an item is being dragged.
	 * @param {!object} data
	 * @param {!MouseEvent} data.originalEvent
	 * @param {!HTMLElement} data.target
	 * @private
	 * @review
	 */

	_handleDrag(data) {
		const targetItem = data.target;

		if (targetItem) {
			const mouseY = data.originalEvent.clientY;
			const sourceItemPlid = data.source.dataset.layoutColumnItemPlid;
			const targetItemPlid = targetItem.dataset.layoutColumnItemPlid;
			const targetItemRegion = position.getRegion(targetItem);

			this._targetBorder = DRAG_BORDERS.bottom;

			if (Math.abs(mouseY - targetItemRegion.top) <= Math.abs(mouseY - targetItemRegion.bottom)) {
				this._targetBorder = DRAG_BORDERS.top;
			}

			this.emit(
				'dragLayoutColumnItem',
				{
					border: this._targetBorder,
					sourceItemPlid,
					targetItemPlid
				}
			);
		}
	}

	/**
	 * Callback that is executed when a target is leaved.
	 * @private
	 * @review
	 */

	_handleDragEnd() {
		this.emit('leaveLayoutColumnItem');
	}

	/**
	 * Callback that is executed when an item is dropped.
	 * @param {!object} data
	 * @param {!HTMLElement} data.source
	 * @param {HTMLElement} data.target
	 * @param {!MouseEvent} event
	 * @private
	 * @review
	 */

	_handleDrop(data, event) {
		event.preventDefault();

		if (data.target) {
			const sourceItemPlid = data.source.dataset.layoutColumnItemPlid;

			this.emit(
				'moveLayoutColumnItem',
				{
					sourceItemPlid
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
				handles: '.layout-drag-handler',
				sources: '.drag-layout-column-item',
				targets: '.layout-drop-target'
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

LayoutDragDrop.STATE = {

	/**
	 * Internal DragDrop instance.
	 * @default null
	 * @instance
	 * @memberOf LayoutDragDrop
	 * @review
	 * @type {object|null}
	 */

	_dragDrop: Config.internal().value(null),

	/**
	 * Nearest border of the hovered item while dragging
	 * @default undefined
	 * @instance
	 * @memberOf LayoutDragDrop
	 * @review
	 * @type {!string}
	 */

	_targetBorder: Config.internal().string()
};

export {
	DRAG_BORDERS,
	LayoutDragDrop
};
export default LayoutDragDrop;