import {Drag, DragDrop} from 'metal-drag-drop';
import State from 'metal-state';

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
				sources: '.layouts-drag-section',
				targets: '.layouts-drop-target'
			}
		);
	}

}

export default SidebarLayoutsDragDrop;