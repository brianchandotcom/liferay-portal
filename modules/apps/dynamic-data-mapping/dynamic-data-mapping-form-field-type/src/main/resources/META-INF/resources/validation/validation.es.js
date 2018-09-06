import Component from 'metal-component';
import Soy from 'metal-soy';

import templates from './validation.soy';

/**
 * Validation Component
 */

class Validation extends Component {}

// Register component

Soy.register(Validation, templates, 'render');

if (!window.DDMValidation) {
	window.DDMValidation = {

	};
}

window.DDMValidation.render = Validation;

export default Validation;