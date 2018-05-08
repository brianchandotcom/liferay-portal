import Component from 'metal-component';
import Position from 'metal-position';
import Soy from 'metal-soy';
import dom from 'metal-dom';
import { Drag } from 'metal-drag-drop';
import { async, core } from 'metal';

import handlesTemplates from './CropHandles.soy';

/**
 * Crop Handles
 * @review
 */
class CropHandles extends Component {

	/**
	 * @inheritDoc
	 * @review
	 */
	attached() {
		this.parentNode_ = this.element.parentNode;

		this.resizer = this.element.querySelector('.resize-handle');

		this.selectionBorderWidth_ = parseInt(this.element.style.borderWidth, 10);

		this.croppedPreview_ = this.element.querySelector('.cropped-image-preview');

		this.croppedPreviewContext_ = this.croppedPreview_.getContext('2d');

		async.nextTick(() => {
			let canvas = this.getImageEditorCanvas();

			this.setSelectionInitialStyle_();

			this.initializeDrags_();

			dom.removeClasses(this.element, 'hide');
			dom.append(canvas.parentElement, this.element);
		});
	}

	/**
	 * @inheritDoc
	 * @review
	 */
	detached() {
		let canvas = this.getImageEditorCanvas();

		canvas.style.opacity = 1;
	}

	/**
	 * Bind actions for the mover and the size handler.
	 * @protected
	 * @review
	 */
	bindDrags_() {
		this.resizer.addEventListener('mousedown', (event) => event.stopPropagation());

		this.bindSelectionDrag_();
		this.bindSizeDrag_();
	}

	/**
	 * Bind actions for the mover.
	 * @protected
	 * @review
	 */
	bindSelectionDrag_() {
		let canvas = this.getImageEditorCanvas();

		this.selectionDrag_.on(Drag.Events.DRAG, (data, event) => {
			let left = data.relativeX - canvas.offsetLeft + this.selectionBorderWidth_;
			let top = data.relativeY - canvas.offsetTop + this.selectionBorderWidth_;

			this.element.style.left = left + 'px';
			this.element.style.top = top + 'px';

			this.croppedPreviewContext_.drawImage(
				canvas,
				left,
				top,
				this.croppedPreview_.width,
				this.croppedPreview_.height,
				0,
				0,
				this.croppedPreview_.width,
				this.croppedPreview_.height
			);
		});
	}

	/**
	 * Bind actions for the size handler.
	 * @protected
	 * @review
	 */
	bindSizeDrag_() {
		let canvas = this.getImageEditorCanvas();

		this.sizeDrag_.on(Drag.Events.DRAG, (data, event) => {
			let width = data.relativeX + this.resizer.offsetWidth / 2;
			let height = data.relativeY + this.resizer.offsetHeight / 2;

			this.element.style.width = width + this.selectionBorderWidth_ * 2 + 'px';
			this.element.style.height = height + this.selectionBorderWidth_ * 2 + 'px';

			this.croppedPreview_.width = width;
			this.croppedPreview_.height = height;

			this.croppedPreviewContext_.drawImage(
				canvas,
				this.element.offsetLeft - canvas.offsetLeft + this.selectionBorderWidth_,
				this.element.offsetTop - canvas.offsetTop + this.selectionBorderWidth_,
				width,
				height,
				0,
				0,
				width,
				height
			);
			this.croppedPreview_.style.width = width + 'px';
			this.croppedPreview_.style.height = height + 'px';
		});
	}

	/**
	 * Calculates the constrain region for the selection drag and resize.
	 * @protected
	 * @review
	 */
	getSizeDragConstrain_(region) {
		let canvas = this.getImageEditorCanvas();

		let constrain = Position.getRegion(canvas);

		let selection = Position.getRegion(this.element);

		constrain.left = selection.left + this.resizer.offsetWidth + this.selectionBorderWidth_ * 2;
		constrain.top = selection.top + this.resizer.offsetHeight + this.selectionBorderWidth_ * 2;
		constrain.width = constrain.right - constrain.left;
		constrain.height = constrain.bottom - constrain.top;
		constrain.right += this.resizer.offsetWidth / 2 - this.selectionBorderWidth_;
		constrain.bottom += this.resizer.offsetHeight / 2 - this.selectionBorderWidth_;

		if (region.left < constrain.left) {
			region.left = constrain.left;
		}
		else if (region.right > constrain.right) {
			region.left -= region.right - constrain.right;
		}

		if (region.top < constrain.top) {
			region.top = constrain.top;
		}
		else if (region.bottom > constrain.bottom) {
			region.top -= region.bottom - constrain.bottom;
		}

		region.right = region.left + region.width;
		region.bottom = region.top + region.height;
	}

	/**
	 * Initializes the mover and the size handler.
	 * @protected
	 * @review
	 */
	initializeDrags_() {
		let canvas = this.getImageEditorCanvas();

		this.selectionDrag_ = new Drag(
			{
				constrain: canvas,
				handles: this.element,
				sources: this.element
			}
		);

		this.sizeDrag_ = new Drag(
			{
				constrain: this.getSizeDragConstrain_.bind(this),
				handles: this.resizer,
				sources: this.resizer
			}
		);

		this.bindDrags_();
	}

	/**
	 * Sets the initial style for the selection and preview.
	 * @protected
	 * @review
	 */
	setSelectionInitialStyle_() {
		let canvas = this.getImageEditorCanvas();

		canvas.style.opacity = 0.5;

		this.element.style.width = canvas.offsetWidth + 'px';
		this.element.style.height = canvas.offsetHeight + 'px';
		this.element.style.left = canvas.offsetLeft + 'px';
		this.element.style.top = canvas.offsetTop + 'px';

		this.croppedPreview_.width = canvas.offsetWidth;
		this.croppedPreview_.height = canvas.offsetHeight;

		this.croppedPreviewContext_.drawImage(
			canvas,
			this.selectionBorderWidth_,
			this.selectionBorderWidth_,
			canvas.width - this.selectionBorderWidth_ * 2,
			canvas.height - this.selectionBorderWidth_ * 2,
			0,
			0,
			canvas.width - this.selectionBorderWidth_ * 2,
			canvas.height - this.selectionBorderWidth_ * 2
		);

		this.croppedPreview_.style.width = this.croppedPreview_.width + 'px';
		this.croppedPreview_.style.height = this.croppedPreview_.height + 'px';
	}
}

/**
 * State definition.
 * @review
 * @static
 * @type {!Object}
 */
CropHandles.STATE = {
	/**
	 * Injected helper to get the editor canvas element
	 * @review
	 * @type {Function}
	 */
	getImageEditorCanvas: {
		validator: core.isFunction
	}
};

Soy.register(CropHandles, handlesTemplates);

export default CropHandles;