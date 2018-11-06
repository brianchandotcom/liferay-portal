import Component from 'metal-component';
import {Config} from 'metal-state';
import Soy from 'metal-soy';

import templates from './ImagePreviewer.soy';

const ZOOM_LEVELS = [
	0.1,
	0.2,
	0.3,
	0.4,
	0.5,
	0.6,
	0.7,
	0.8,
	0.9,
	1
];

const ZOOM_LEVELS_REVERSED = ZOOM_LEVELS.slice().reverse();

const MIN_ZOOM_RATIO_AUTOCENTER = 3;

/**
 * Component that create an image preview to allow zoom
 * @review
 */
class ImagePreviewer extends Component {

	/**
	 * @inheritDoc
	 */
	attached() {
		this.imageNaturalWidth = this.refs.image.naturalWidth;
		this.imageNaturalHeight = this.refs.image.naturalHeight;

		this._updateDimensions();
		this._updateDimensions = this._updateDimensions.bind(this);

		window.addEventListener('resize', this._updateDimensions);
	}

	/**
	 * @inheritDoc
	 */
	detached() {
		window.removeEventListener('resize', this._updateDimensions);
	}

	/**
	 * @inheritDoc
	 */
	rendered() {
		if (this.zoomRatio) {
			this._setScrollContainer();
		}

		if (this.reCalculateZoomActual) {
			this.reCalculateZoomActual = false;

			this._calculateZoomActual();
		}
	}

	/**
	 * Calculate actual zoom based in image rendered
	 * @private
	 * @review
	 */
	_calculateZoomActual() {
		this.zoomActual = this.refs.image.width / this.imageNaturalWidth;

		this._setToolbar();
	}

	/**
	 * Clear zoom and allow the image fit the container in natural way
	 * @private
	 * @review
	 */
	_clearZoom() {
		this.imageHeight = null;
		this.imageWidth = null;
		this.imageMargin = null;
		this.reCalculateZoomActual = true;
	}

	/**
	 * Event handler executed when zoom changed
	 * @param {!Event} event
	 * @private
	 * @review
	 */
	_handleZoom(event) {
		const value = event.currentTarget.value;

		let zoomValue;

		switch (value) {
		case 'in':
			zoomValue = ZOOM_LEVELS
				.find((zoom) => zoom > this.zoomActual);
			break;
		case 'out':
			zoomValue = ZOOM_LEVELS_REVERSED
				.find((zoom) => zoom < this.zoomActual);
			break;
		case 'real':
			zoomValue = 1;
			break;
		case 'fit':
			this._clearZoom();
		break;
		}

		if (zoomValue) {
			this._setZoom(zoomValue);
		}
	}

	/**
	 * Calculate actual dimensions basen in container rendered
	 * @private
	 * @review
	 */
	_updateDimensions() {
		this.imageContainerWidth = this.refs.imageContainer.clientWidth;
		this.imageContainerHeight = this.refs.imageContainer.clientHeight;

		this._calculateZoomActual();
	}

	/**
	 * Move the scroll of the cointainer based in the actual position or center
	 * @private
	 * @review
	 */
	_setScrollContainer() {
		if (this.zoomRatio < MIN_ZOOM_RATIO_AUTOCENTER) {
			this.refs.imageContainer.scrollLeft = this.refs.imageContainer.clientWidth * (this.zoomRatio - 1) / 2
				+ this.refs.imageContainer.scrollLeft * this.zoomRatio;
			this.refs.imageContainer.scrollTop = this.refs.imageContainer.clientHeight * (this.zoomRatio - 1) / 2
				+ this.refs.imageContainer.scrollTop * this.zoomRatio;
		} else {
			this.refs.imageContainer.scrollTop = (this.imageHeight - this.refs.imageContainer.clientHeight) / 2;
			this.refs.imageContainer.scrollLeft = (this.imageWidth - this.refs.imageContainer.clientWidth) / 2;
		}

		this.zoomRatio = null;
	}

	/**
	 * Set the toolbar buttons states based in actual state
	 * @private
	 * @review
	 */
	_setToolbar() {
		this.zoomInDisabled = ZOOM_LEVELS_REVERSED[0] === this.zoomActual;
		this.zoomOutDisabled = ZOOM_LEVELS[0] >= this.zoomActual;
		this.zoomFitToggle = this.zoomActual === 1;
	}

	/**
	 * Set the zoom based in multiplier
	 * @param {number} zoomNumber
	 * @private
	 * @review
	 */
	_setZoom(zoomNumber) {
		this.imageHeight = this.imageNaturalHeight * zoomNumber;
		this.imageWidth = this.imageNaturalWidth * zoomNumber;
		this.imageMargin = `${
			this.imageHeight > this.imageContainerHeight ? 0 : 'auto'
		} ${
			this.imageWidth > this.imageContainerWidth ? 0 : 'auto'
		}`;
		this.zoomRatio = zoomNumber / this.zoomActual;
		this.zoomActual = zoomNumber;

		this._setToolbar();
	}
}

/**
 * State definition.
 * @review
 * @static
 * @type {!Object}
 */

ImagePreviewer.STATE = {
	imageHeight: Config.number(),
	imageMargin: Config.string(),
	imageURL: Config.string().required(),
	imageWidth: Config.number(),
	spritemap: Config.string().required(),
	zoomActual: Config.number(),
	zoomInDisabled: Config.bool(),
	zoomFitToggle: Config.bool(),
	zoomOutDisabled: Config.bool()
};

Soy.register(ImagePreviewer, templates);

export {ImagePreviewer};
export default ImagePreviewer;