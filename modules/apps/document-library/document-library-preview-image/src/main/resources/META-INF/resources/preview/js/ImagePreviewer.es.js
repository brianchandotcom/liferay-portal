import Component from 'metal-component';
import {Config} from 'metal-state';
import Soy from 'metal-soy';

import templates from './ImagePreviewer.soy';

/**
 * ImagePreviewer
 * @review
 */

class ImagePreviewer extends Component {
	attached() {
		const previewImage = this.refs.previewImage;
		this.previewImageNaturalWidth = previewImage.naturalWidth;
		this.previewImageNaturalHeight = previewImage.naturalHeight;

		this._setZoomNumber(previewImage.width);
	}

	_setZoomNumber(width) {
		this.zoomActual = Math.round(
			(width / this.previewImageNaturalWidth) * 100
		);
	}
}

/**
 * State definition.
 * @review
 * @static
 * @type {!Object}
 */

ImagePreviewer.STATE = {
	imageURL: Config.string().required(),
	zoomActual: Config.number(),
};

Soy.register(ImagePreviewer, templates);

export {ImagePreviewer};
export default ImagePreviewer;