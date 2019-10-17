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

import {debounce} from 'frontend-js-web';
import Component from 'metal-component';
import Soy from 'metal-soy';
import {Config} from 'metal-state';

import 'clay-button';

import templates from './ImagePreviewer.soy';

/**
 * Zoom ratio limit that fire the autocenter
 * @type {number}
 */
const MIN_ZOOM_RATIO_AUTOCENTER = 3;

/**
 * Available zoom sizes
 * @type {Array<number>}
 */
const ZOOM_LEVELS = [0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1];

/**
 * Available reversed zoom sizes
 * @type {Array<number>}
 */
const ZOOM_LEVELS_REVERSED = ZOOM_LEVELS.slice().reverse();

/**
 * Component that create an image preview to allow zoom
 * @review
 */
class ImagePreviewer extends Component {
	/**
	 * @inheritDoc
	 */
	attached() {
		this._imageNaturalHeight = this.refs.image.naturalHeight;
		this._imageNaturalWidth = this.refs.image.naturalWidth;
		this._isPreviewFit = true;

		this._updateDimensions();

		this._updateDimensionsDebounced = debounce(
			this._updateDimensions.bind(this),
			250
		);
		window.addEventListener('resize', this._updateDimensionsDebounced);
	}

	/**
	 * @inheritDoc
	 */
	detached() {
		window.removeEventListener('resize', this._updateDimensionsDebounced);
	}

	/**
	 * @inheritDoc
	 */
	rendered() {
		if (this._zoomRatio) {
			this._setScrollContainer();
		}

		if (this._reCalculateCurrentZoom) {
			this._reCalculateCurrentZoom = false;

			this._calculateCurrentZoom();
		}
	}

	/**
	 * @inheritDoc
	 */
	syncCurrentZoom() {
		this.zoomInDisabled = ZOOM_LEVELS_REVERSED[0] === this.currentZoom;
		this.zoomOutDisabled = ZOOM_LEVELS[0] >= this.currentZoom;
	}

	/**
	 * Set the zoom based in multiplier
	 * @param {number} zoomNumber
	 * @private
	 * @review
	 */
	_applyZoom(zoomNumber) {
		this.imageHeight = this._imageNaturalHeight * zoomNumber;
		this.imageWidth = this._imageNaturalWidth * zoomNumber;
		this._zoomRatio = zoomNumber / this.currentZoom;
		this._isPreviewFit = false;
		this.currentZoom = zoomNumber;
		this._updateDimensions();
	}

	/**
	 * Calculate actual zoom based in image rendered
	 * @private
	 * @review
	 */
	_calculateCurrentZoom() {
		this.currentZoom = this.refs.image.width / this._imageNaturalWidth;
	}

	/**
	 * Clear zoom and allow the image fit the container in natural way
	 * @private
	 * @review
	 */
	_clearZoom() {
		this.imageHeight = null;
		this.imageMargin = null;
		this.imageWidth = null;
		this._isPreviewFit = true;
		this._reCalculateCurrentZoom = true;
	}

	/**
	 * Event handler executed when zoom changed
	 * @param {!Event} event
	 * @private
	 * @review
	 */
	_handleToolbarClick(event) {
		const value = event.currentTarget.value;

		let zoomValue;

		if (value === 'in') {
			zoomValue = ZOOM_LEVELS.find(zoom => zoom > this.currentZoom);
		} else if (value === 'out') {
			zoomValue = ZOOM_LEVELS_REVERSED.find(
				zoom => zoom < this.currentZoom
			);
		} else if (value === 'real') {
			zoomValue = 1;
		} else if (value === 'fit') {
			this._clearZoom();
		}

		if (zoomValue) {
			this._applyZoom(zoomValue);
		}
	}

	/**
	 * Move the scroll of the cointainer based in the actual position or center
	 * @private
	 * @review
	 */
	_setScrollContainer() {
		const imageContainer = this.refs.imageContainer;
		let scrollLeft;
		let scrollTop;

		if (this._zoomRatio < MIN_ZOOM_RATIO_AUTOCENTER) {
			scrollLeft =
				(imageContainer.clientWidth * (this._zoomRatio - 1)) / 2 +
				imageContainer.scrollLeft * this._zoomRatio;
			scrollTop =
				(imageContainer.clientHeight * (this._zoomRatio - 1)) / 2 +
				imageContainer.scrollTop * this._zoomRatio;
		} else {
			scrollTop = (this.imageHeight - imageContainer.clientHeight) / 2;
			scrollLeft = (this.imageWidth - imageContainer.clientWidth) / 2;
		}

		imageContainer.scrollLeft = scrollLeft;
		imageContainer.scrollTop = scrollTop;

		this._zoomRatio = null;
	}

	/**
	 * Calculate actual dimensions based in container rendered
	 * @private
	 * @review
	 */
	_updateDimensions() {
		this.imageMargin = `${
			this.imageHeight > this.refs.imageContainer.clientHeight
				? 0
				: 'auto'
		} ${
			this.imageWidth > this.refs.imageContainer.clientWidth ? 0 : 'auto'
		}`;

		if (this._isPreviewFit) {
			this._calculateCurrentZoom();
		}
	}
}

/**
 * State definition.
 * @review
 * @static
 * @type {!Object}
 */
ImagePreviewer.STATE = {
	/**
	 * The current zoom value that is shown in the toolbar.
	 * @type {Number}
	 */
	currentZoom: Config.number().internal(),

	/**
	 * The height of the <img> element.
	 * @type {Number}
	 */
	imageHeight: Config.number().internal(),

	/**
	 * The margin of the <img> element
	 * @type {String}
	 */
	imageMargin: Config.string().internal(),

	/**
	 * The "src" attribute of the <img> element
	 * @type {String}
	 */
	imageURL: Config.string().required(),

	/**
	 * The width of the <img> element.
	 * @type {Number}
	 */
	imageWidth: Config.number().internal(),

	/**
	 * Path to icon images.
	 * @type {String}
	 */
	spritemap: Config.string().required(),

	/**
	 * Flag that indicate if 'zoom in' is disabled.
	 * @type {Boolean}
	 */
	zoomInDisabled: Config.bool().internal(),

	/**
	 * Flag that indicate if 'zoom out' is disabled.
	 * @type {Boolean}
	 */
	zoomOutDisabled: Config.bool().internal()
};

Soy.register(ImagePreviewer, templates);

export {ImagePreviewer};
export default ImagePreviewer;
