import {Config} from 'metal-state';
import Component from 'metal-component';
import Soy from 'metal-soy';

import templates from './VideoPreviewer.soy';

/**
 * Component that create an video player
 * @review
 */
class VideoPreviewer extends Component {}

/**
 * State definition.
 * @review
 * @static
 * @type {!Object}
 */
VideoPreviewer.STATE = {
	/**
	 * List of of video sources
	 * @instance
	 * @memberof VideoPreviewer
	 * @review
	 * @type {!Array<object>}
	 */
	videoSources: Config.arrayOf(
		Config.shapeOf({
			type: Config.string().required(),
			url: Config.string().required()
		})
	).required(),

	/**
	 * The "poster" attribute of the <video> element
	 * @instance
	 * @memberof VideoPreviewer
	 * @review
	 * @type {String}
	 */
	videoPosterURL: Config.string()
};

Soy.register(VideoPreviewer, templates);
export {VideoPreviewer};
export default VideoPreviewer;
