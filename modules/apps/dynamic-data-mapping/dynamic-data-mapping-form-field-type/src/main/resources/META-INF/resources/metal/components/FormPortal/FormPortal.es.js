import 'clay-icon';
import Component from 'metal-component';
import Soy from 'metal-soy';
import {Config} from 'metal-state';

import templates from './FormPortal.soy.js';

class FormPortal extends Component {}

FormPortal.STATE = {
	/**
	 * @default input
	 * @instance
	 * @memberof FormPortal
	 * @type {?html}
	 */

	contentRenderer: Config.any()
};

Soy.register(FormPortal, templates);

export default FormPortal;
