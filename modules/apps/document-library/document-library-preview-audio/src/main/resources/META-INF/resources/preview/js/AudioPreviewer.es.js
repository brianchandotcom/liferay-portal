import {Config} from 'metal-state';
import Component from 'metal-component';
import Soy from 'metal-soy';

import templates from './AudioPreviewer.soy';

/**
 * Component that create an audio player
 * @review
 */
class AudioPreviewer extends Component {}

/**
 * State definition.
 * @review
 * @static
 * @type {!Object}
 */
AudioPreviewer.STATE = {
	/**
	 * The max witdh of audio player based in
	 * video player width
	 * @instance
	 * @memberof AudioPreviewer
	 * @review
	 * @type {!number}
	 */
	audioMaxWidth: Config.number().required(),

	/**
	 * List of of audio sources
	 * @instance
	 * @memberof AudioPreviewer
	 * @review
	 * @type {!Array<object>}
	 */
	audioSources: Config.arrayOf(
		Config.shapeOf({
			type: Config.string().required(),
			url: Config.string().required()
		})
	).required()
};

Soy.register(AudioPreviewer, templates);
export {AudioPreviewer};
export default AudioPreviewer;
