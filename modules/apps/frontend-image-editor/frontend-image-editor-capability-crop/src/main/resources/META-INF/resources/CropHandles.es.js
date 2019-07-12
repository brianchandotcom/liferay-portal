/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import {async, core} from 'metal';
import Component from 'metal-component';
import dom from 'metal-dom';
import {Drag} from 'metal-drag-drop';
import Position from 'metal-position';
import Soy from 'metal-soy';

import handlesTemplates from './CropHandles.soy';

/**
 * Creates a Crop Handles Component.
 */
class CropHandles extends Component {
	/**
	 * @inheritDoc
	 */
	attached() {
		this.parentNode_ = this.element.parentNode;

		this.resizer = this.element.querySelector('.resize-handle');

		this.selectionBorderWidth_ = parseInt(
			this.element.style.borderWidth,
			10
		);

		this.croppedPreview_ = this.element.querySelector(
			'.cropped-image-preview'
		);

		this.croppedPreviewContext_ = this.croppedPreview_.getContext('2d');

		async.nextTick(() => {
			const canvas = this.getImageEditorCanvas();

			this.setSelectionInitialStyle_();

			this.initializeDrags_();

			dom.removeClasses(this.element, 'hide');
			dom.append(canvas.parentElement, this.element);
		});
	}

	/**
	 * @inheritDoc
	 */
	detached() {
		const canvas = this.getImageEditorCanvas();

		canvas.style.opacity = 1;
	}

	/**
	 * Binds actions for the mover and size handler.
	 *
	 * @protected
	 */
	bindDrags_() {
		this.resizer.addEventListener('mousedown', event =>
			event.stopPropagation()
		);

		this.bindSelectionDrag_();
		this.bindSizeDrag_();
	}

	/**
	 * Binds actions for the mover.
	 *
	 * @protected
	 */
	bindSelectionDrag_() {
		const canvas = this.getImageEditorCanvas();

		this.selectionDrag_.on(Drag.Events.DRAG, data => {
			const left =
				data.relativeX - canvas.offsetLeft + this.selectionBorderWidth_;
			const top =
				data.relativeY - canvas.offsetTop + this.selectionBorderWidth_;

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
	 * Binds actions for the size handler.
	 *
	 * @protected
	 */
	bindSizeDrag_() {
		const canvas = this.getImageEditorCanvas();

		this.sizeDrag_.on(Drag.Events.DRAG, data => {
			const width = data.relativeX + this.resizer.offsetWidth / 2;
			const height = data.relativeY + this.resizer.offsetHeight / 2;

			this.element.style.width =
				width + this.selectionBorderWidth_ * 2 + 'px';
			this.element.style.height =
				height + this.selectionBorderWidth_ * 2 + 'px';

			this.croppedPreview_.width = width;
			this.croppedPreview_.height = height;

			this.croppedPreviewContext_.drawImage(
				canvas,
				this.element.offsetLeft -
					canvas.offsetLeft +
					this.selectionBorderWidth_,
				this.element.offsetTop -
					canvas.offsetTop +
					this.selectionBorderWidth_,
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
	 *
	 * @protected
	 */
	getSizeDragConstrain_(region) {
		const canvas = this.getImageEditorCanvas();

		const constrain = Position.getRegion(canvas);

		const selection = Position.getRegion(this.element);

		constrain.left =
			selection.left +
			this.resizer.offsetWidth +
			this.selectionBorderWidth_ * 2;
		constrain.top =
			selection.top +
			this.resizer.offsetHeight +
			this.selectionBorderWidth_ * 2;
		constrain.width = constrain.right - constrain.left;
		constrain.height = constrain.bottom - constrain.top;
		constrain.right +=
			this.resizer.offsetWidth / 2 - this.selectionBorderWidth_;
		constrain.bottom +=
			this.resizer.offsetHeight / 2 - this.selectionBorderWidth_;

		if (region.left < constrain.left) {
			region.left = constrain.left;
		} else if (region.right > constrain.right) {
			region.left -= region.right - constrain.right;
		}

		if (region.top < constrain.top) {
			region.top = constrain.top;
		} else if (region.bottom > constrain.bottom) {
			region.top -= region.bottom - constrain.bottom;
		}

		region.right = region.left + region.width;
		region.bottom = region.top + region.height;
	}

	/**
	 * Initializes the mover and size handler.
	 *
	 * @protected
	 */
	initializeDrags_() {
		const canvas = this.getImageEditorCanvas();

		this.selectionDrag_ = new Drag({
			constrain: canvas,
			handles: this.element,
			sources: this.element
		});

		this.sizeDrag_ = new Drag({
			constrain: this.getSizeDragConstrain_.bind(this),
			handles: this.resizer,
			sources: this.resizer
		});

		this.bindDrags_();
	}

	/**
	 * Sets the initial style for the selection and preview.
	 *
	 * @protected
	 */
	setSelectionInitialStyle_() {
		const canvas = this.getImageEditorCanvas();

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
 *
 * @static
 * @type {!Object}
 */
CropHandles.STATE = {
	/**
	 * Injected helper that retrieves the editor canvas element.
	 *
	 * @type {Function}
	 */
	getImageEditorCanvas: {
		validator: core.isFunction
	}
};

Soy.register(CropHandles, handlesTemplates);

export default CropHandles;
