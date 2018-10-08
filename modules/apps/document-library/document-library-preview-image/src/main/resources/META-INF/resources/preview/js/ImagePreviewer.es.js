import Component from 'metal-component';

import {Config} from 'metal-state';

import Soy from 'metal-soy';

import templates from './ImagePreviewer.soy';

/**
 * ImagePreviewer
 * @review
 */

class ImagePreviewer extends Component {

}

/**
 * State definition.
 * @review
 * @static
 * @type {!Object}
 */

ImagePreviewer.STATE = {
	imageURL: Config.string().required()
};

Soy.register(ImagePreviewer, templates);

export {ImagePreviewer};
export default ImagePreviewer;