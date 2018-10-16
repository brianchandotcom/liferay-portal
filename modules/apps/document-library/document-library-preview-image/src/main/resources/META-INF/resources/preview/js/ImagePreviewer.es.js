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
	1,
];

const ZOOM_LEVELS_REVERSED = ZOOM_LEVELS.slice().reverse();

/**
 * ImagePreviewer
 * @review
 */

class ImagePreviewer extends Component {
	attached() {
		this.previewImageNaturalWidth = this.refs.previewImage.naturalWidth;
		this.previewImageNaturalHeight = this.refs.previewImage.naturalHeight;

		this._setDimensions();
	}

	_setDimensions() {
		this.previewImageContainerWidth = this.refs.previewImageContainer.clientWidth;
		this.previewImageContainerHeight = this.refs.previewImageContainer.clientHeight;
		this._setZoomNumber(this.refs.previewImage.width);
	}

	_handleZoom(event) {
		const value = event.delegateTarget.value;

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
			case '1':
				zoomValue = 1;
				break;
		}

		this.zoomInDisabled = ZOOM_LEVELS_REVERSED[0] === zoomValue;
		this.zoomOutDisabled = ZOOM_LEVELS[0] === zoomValue;

		this._setZoom(zoomValue);
	}

	_setZoom(zoomNumber) {
		if (typeof zoomNumber == 'undefined') return;

		this.imageHeight = this.previewImageNaturalHeight * zoomNumber;
		this.imageWidth = this.previewImageNaturalWidth * zoomNumber;
		this.imageMargin = `${
			this.imageHeight > this.previewImageContainerHeight ? 0 : 'auto'
		} ${
			this.imageWidth > this.previewImageContainerWidth ? 0 : 'auto'
		}`;

		this._setZoomNumber(this.imageWidth);
	}

	_setZoomNumber(width) {
		this.zoomActual = width / this.previewImageNaturalWidth;
	}
}

/**
 * State definition.
 * @review
 * @static
 * @type {!Object}
 */

ImagePreviewer.STATE = {
	imageMargin: Config.string(),
	imageHeight: Config.number(),
	imageURL: Config.string().required(),
	imageWidth: Config.number(),
	zoomActual: Config.number(),
	zoomInDisabled: Config.bool(),
	zoomOutDisabled: Config.bool(),
};

Soy.register(ImagePreviewer, templates);

export {ImagePreviewer};
export default ImagePreviewer;