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

import {core} from 'metal';
import Component from 'metal-component';
import Soy from 'metal-soy';

import componentTemplates from './ResizeComponent.soy';

import './ResizeControls.soy';

/**
 * Creates a Resize component.
 */
class ResizeComponent extends Component {
	/**
	 * @inheritDoc
	 */
	attached() {
		this.getImageEditorImageData().then(imageData => {
			this.imageWidth = imageData.width;
			this.imageHeight = imageData.height;

			this.imageRatio_ = this.imageWidth / this.imageHeight;

			this.imageHeightInput_ = this.element.querySelector(
				'#' + this.ref + 'Height'
			);
			this.imageWidthInput_ = this.element.querySelector(
				'#' + this.ref + 'Width'
			);

			this.lockProportions = true;
		});
	}

	/**
	 * Executes the resize operation to get the final version of the image.
	 *
	 * @param  {ImageData} imageData The image data representation of the image.
	 * @return {Promise} A promise that resolves with the resized
	 * image data representation.
	 */
	process(imageData) {
		return Promise.resolve(this.resizeImageData_(imageData));
	}

	/**
	 * Resizes image data to the user's selected width and height values.
	 *
	 * @param  {ImageData} imageData The original image data.
	 * @return {ImageData} The resized image data with the component width and
	 * height values specified by the user.
	 */
	resizeImageData_(imageData) {
		const rawCanvas = document.createElement('canvas');
		rawCanvas.width = imageData.width;
		rawCanvas.height = imageData.height;

		rawCanvas.getContext('2d').putImageData(imageData, 0, 0);

		const canvas = document.createElement('canvas');
		canvas.width = this.imageWidth;
		canvas.height = this.imageHeight;

		const context = canvas.getContext('2d');
		context.drawImage(rawCanvas, 0, 0, this.imageWidth, this.imageHeight);

		return context.getImageData(0, 0, this.imageWidth, this.imageHeight);
	}

	/**
	 * Keeps the width/height ratio in sync when <code>lockProportions</code> is
	 * <code>true</code>.
	 *
	 * @param  {InputEvent} event The input event.
	 */
	syncDimensions(event) {
		const newValue = parseInt(event.delegateTarget.value, 10);

		if (event.delegateTarget === this.imageWidthInput_) {
			this.imageWidth = newValue;

			if (this.lockProportions) {
				this.imageHeight = parseInt(newValue / this.imageRatio_, 10);
				this.imageHeightInput_.value = this.imageHeight;
			}
		}
		else {
			this.imageHeight = newValue;

			if (this.lockProportions) {
				this.imageWidth = parseInt(newValue * this.imageRatio_, 10);
				this.imageWidthInput_.value = this.imageWidth;
			}
		}
	}

	/**
	 * Toggles the value of the <code>lockProportions</code> attribute. When
	 * enabled, changes in one of the dimensions cascades changes to the other
	 * to keep the original image ratio.
	 */
	toggleLockProportions() {
		this.lockProportions = !this.lockProportions;
	}
}

/**
 * State definition.
 *
 * @static
 * @type {!Object}
 */
ResizeComponent.STATE = {
	/**
	 * Injected helper that retrieves the editor image data.
	 *
	 * @type {Function}
	 */
	getImageEditorImageData: {
		validator: core.isFunction
	}
};

Soy.register(ResizeComponent, componentTemplates);

export default ResizeComponent;
