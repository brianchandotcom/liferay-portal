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

import '../FieldBase/FieldBase.es';

import './CaptchaRegister.soy';

import Component from 'metal-component';
import Soy from 'metal-soy';
import {Config} from 'metal-state';

import templates from './Captcha.soy';

/**
 * Captcha.
 * @extends Component
 */

class Captcha extends Component {
	rendered() {
		if (window.grecaptcha) {
			window.grecaptcha.ready(() => {
				try {
					window.grecaptcha.reset();
				} catch (e) {
					console.warn('Could not reset reCAPTCHA.');
				}
			});
		}
	}

	shouldUpdate() {
		return false;
	}
}

Soy.register(Captcha, templates);

Captcha.STATE = {
	/**
	 * @default false
	 * @memberof FieldBase
	 * @type {?bool}
	 */

	evaluable: Config.bool().value(false),

	/**
	 * @default undefined
	 * @memberof Captcha
	 * @type {?(string|undefined)}
	 */

	spritemap: Config.string(),

	/**
	 * @default 'captcha'
	 * @memberof Text
	 * @type {?(string|undefined)}
	 */

	type: Config.string().value('captcha')
};

export default Captcha;
