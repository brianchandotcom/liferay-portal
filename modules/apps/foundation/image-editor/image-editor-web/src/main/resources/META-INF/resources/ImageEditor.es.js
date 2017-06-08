import Component from 'metal-component/src/Component';
import Soy from 'metal-soy/src/Soy';

import { CancellablePromise } from 'metal-promise/src/promise/Promise';
import Dropdown from 'metal-dropdown/src/Dropdown';

import ImageStateUtils from './ImageEditorUtils.es';

import templates from './ImageEditor.soy';

/**
 * ImageEditor Component
 */
class ImageEditor extends Component {
	/**
	 * @inheritDoc
	 */
	constructor(opt_config) {
		super(opt_config);

		/**
	 	 * This index points to the current state in the history.
	 	 *
		 * @type {Number}
		 * @protected
		 */
		this.historyIndex_ = 0;

		/**
		 * History of the different image states during edition. Every
		 * state entry represents a change to the image on top of the
		 * previous image state.
		 * - Images are represented as URIs or base64 encoded urls.
		 * - State entries are objects with
		 *     - url: the url representing the image
		 *     - utils: image utils for this image
	 	 *
		 * @type {Array.<Object>}
		 * @protected
		 */
		this.history_ = [{
			url: this.image,
			utils: new ImageStateUtils(this.image, this.element)
		}];
	}

	/**
	 * Accepts the current changes applied by the active control and creates
	 * a new entry in the history stack. Doing this will wipe out any
	 * stale redo states.
	 */
	accept() {
		let selectedControl = this.components[this.id + '_selected_control_' + this.selectedControl.variant];
		let utils = this.history_[this.historyIndex_].utils;

		utils.getImageData()
			.then((imageData) => selectedControl.process(imageData))
			.then((imageURL) => this.createHistoryEntry_(imageURL))
			.then(() => {
				this.imagePreview = false;
				this.selectedControl = null;
				this.selectedTool = null;
				this.syncHistory_();
			});
	}

	/**
	 * Creates a new history entry state.
	 *
	 * @param  {String} imageURL The URL representing the image new state.
	 */
	createHistoryEntry_(imageURL) {
		// Push new state and discard stale redo states
		this.historyIndex_++;
		this.history_.length = this.historyIndex_ + 1;
		this.history_[this.historyIndex_] = {
			url: imageURL,
			utils: new ImageStateUtils(imageURL, this.element)
		};

		return CancellablePromise.resolve();
	}

	/**
	 * Discards the current changes applied by the active control and reverts
	 * the image to its state before the control activation.
	 */
	discard() {
		this.selectedControl = null;
		this.selectedTool = null;
		this.syncHistory_();
	}

	/**
	 * Retrieves the editor image DOM node.
	 *
	 * @return {Element} The image element.
	 */
	getEditorImage() {
		return this.element.querySelector('.lfr-image-editor-image-container img');
	}

	/**
	 * Retrieves the editor image container DOM node.
	 *
	 * @return {Element} The image container element.
	 */
	getEditorImageContainer() {
		return this.element.querySelector('.lfr-image-editor-image-container');
	}

	/**
	 * Updates the image back to a previously undone state in the history.
	 * Redoing an recovers the undone image changes and enables the undo
	 * stack in case the user wants to undo the changes again.
	 */
	redo() {
		this.historyIndex_++;
		this.syncHistory_();
	}

	/**
	 * Selects a control and starts the edition phase for it.
	 *
	 * @param  {MouseEvent} event
	 */
	requestEditorEdit(event) {
		let controls = this.capabilities.tools.reduce(
			(prev, curr) => prev.concat(curr.controls), []);

		let target = event.delegateTarget || event.currentTarget;
		let targetControl = target.getAttribute('data-control');
		let targetTool = target.getAttribute('data-tool');

		this.selectedControl = controls.filter(tool => tool.variant === targetControl)[0];
		this.selectedTool = targetTool;

		this.syncHistory_();

		this.imagePreview = true;
		this.history_[this.historyIndex_].utils.enablePreview();
	}

	/**
	 * Queues a request for a preview process of the current image by the
	 * currently selected control.
	 */
	requestEditorPreview() {
		let selectedControl = this.components[this.id + '_selected_control_' + this.selectedControl.variant];
		let utils = this.history_[this.historyIndex_].utils;

		utils.getImageData()
			.then((imageData) => selectedControl.preview(imageData))
			.then((imageData) => utils.preview(imageData));
	}

	/**
	 * Discards all changes and restores the original state of the image.
	 * Unlike the undo/redo methods, reset will wipe out all the history.
	 */
	reset() {
		this.historyIndex_ = 0;
		this.history_.length = 1;
		this.syncHistory_();
	}

	/**
	 * Syncs the image and history values after changes to the
	 * history stack.
	 *
	 * @protected
	 */
	syncHistory_() {
		this.image = this.history_[this.historyIndex_].url;

		this.history = {
			canRedo: this.historyIndex_ < this.history_.length - 1,
			canReset: this.history_.length > 1,
			canUndo: this.historyIndex_ > 0
		};
	}

	/**
	 * Reverts the image to the previous state in the history. Undoing
	 * an action brings back the previous version of the image and enables
	 * the redo stack in case the user wants to reapply the change again.
	 */
	undo() {
		this.historyIndex_--;
		this.syncHistory_();
	}
}

// Register component
Soy.register(ImageEditor, templates);

export default ImageEditor;