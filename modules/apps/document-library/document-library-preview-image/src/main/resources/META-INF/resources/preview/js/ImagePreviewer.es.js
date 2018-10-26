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

/**
 * ImagePreviewer
 * @review
 */

class ImagePreviewer extends Component {
	attached() {
		this.imageNaturalWidth = this.refs.image.naturalWidth;
		this.imageNaturalHeight = this.refs.image.naturalHeight;

		this._setDimensions();
	}

	rendered(isFirstRender) {
		if (!isFirstRender) {
			this._setScrollContainer();
		}
	}

	_caculateZoomActual(width) {
		this.zoomActual = width / this.imageNaturalWidth;
	}

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
		}

		this.zoomInDisabled = ZOOM_LEVELS_REVERSED[0] === zoomValue;
		this.zoomOutDisabled = ZOOM_LEVELS[0] === zoomValue;

		this.zoomRatio = zoomValue / this.zoomActual;
		this._setZoom(zoomValue);
	}

	_setDimensions() {
		this.imageContainerWidth = this.refs.imageContainer.clientWidth;
		this.imageContainerHeight = this.refs.imageContainer.clientHeight;

		this._caculateZoomActual(this.refs.image.width);
	}

	_setScrollContainer() {
		this.refs.imageContainer.scrollLeft = this.refs.imageContainer.clientWidth * (this.zoomRatio - 1) / 2
			+ this.refs.imageContainer.scrollLeft * this.zoomRatio;
		this.refs.imageContainer.scrollTop = this.refs.imageContainer.clientHeight * (this.zoomRatio - 1) / 2
			+ this.refs.imageContainer.scrollTop * this.zoomRatio;
	}

	_setZoom(zoomNumber) {
		if (typeof zoomNumber == 'undefined') {
			return;
		}

		this.imageHeight = this.imageNaturalHeight * zoomNumber;
		this.imageWidth = this.imageNaturalWidth * zoomNumber;
		this.imageMargin = `${
			this.imageHeight > this.imageContainerHeight ? 0 : 'auto'
		} ${
			this.imageWidth > this.imageContainerWidth ? 0 : 'auto'
		}`;

		this.zoomActual = zoomNumber;
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
	zoomOutDisabled: Config.bool()
};

Soy.register(ImagePreviewer, templates);

export {ImagePreviewer};
export default ImagePreviewer;