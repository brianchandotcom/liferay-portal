import Component from 'metal-component';
import Soy from 'metal-soy';

import templates from './checkbox.soy';

/**
 * Checkbox Component
 */

class Checkbox extends Component {}

// Register component

Soy.register(Checkbox, templates, 'render');

if (!window.DDMCheckbox) {
	window.DDMCheckbox = {

	};
}

window.DDMCheckbox.render = Checkbox;

export default Checkbox;