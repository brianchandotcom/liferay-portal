import State, {Config} from 'metal-state';
import {Drag, DragDrop} from 'metal-drag-drop';

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

	_dragDrop: Config.internal().value(null)
};

export {LayoutDragDrop};
export default LayoutDragDrop;