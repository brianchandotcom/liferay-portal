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

import {isFunction, isObject} from 'metal';
import Component from 'metal-component';
import {closest, globalEval} from 'metal-dom';
import Soy from 'metal-soy';
import {Config} from 'metal-state';

import templates from './MasterFragmentEntryLinkContent.soy';

class MasterFragmentEntryLinkContent extends Component {
	rendered() {
		requestAnimationFrame(() => {
			if (this.content) {
				this._renderContent(this.content);
			}
		});
	}

	_handleMasterFragmentEntryLinkContentClick(event) {
		const element = event.srcElement;

		if (closest(element, '[href]')) {
			event.preventDefault();
		}
	}

	_renderContent(content) {
		if (content && this.refs.content) {
			this.refs.content.innerHTML = content;

			globalEval.runScriptsInElement(this.refs.content);
		}
	}
}

MasterFragmentEntryLinkContent.STATE = {
	/**
	 * Fragment content to be rendered.
	 * @default ''
	 * @instance
	 * @memberOf MasterFragmentEntryLinkContent
	 * @type {string}
	 */
	content: Config.any()
		.setter(content => {
			return !isFunction(content) && isObject(content)
				? content.value.content
				: content;
		})
		.value('')
};

Soy.register(MasterFragmentEntryLinkContent, templates);

export {MasterFragmentEntryLinkContent};
export default MasterFragmentEntryLinkContent;
