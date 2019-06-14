import '../FieldBase/FieldBase.es';
import './CaptchaRegister.soy.js';
import Component from 'metal-component';
import Soy from 'metal-soy';
import templates from './Captcha.soy.js';
import {Config} from 'metal-state';

/**
 * Captcha.
 * @extends Component
 */

class Captcha extends Component {
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
