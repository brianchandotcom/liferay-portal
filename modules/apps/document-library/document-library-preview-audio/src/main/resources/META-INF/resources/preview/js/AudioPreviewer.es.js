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
