import Component from 'metal-component';
import Soy from 'metal-soy';

import templates from './checkbox-multiple.soy';

/**
 * CheckboxMultiple Component
 */

class CheckboxMultiple extends Component {}

// Register component

Soy.register(CheckboxMultiple, templates, 'render');

if (!window.DDMCheckboxMultiple) {
	window.DDMCheckboxMultiple = {

	};
}

window.DDMCheckboxMultiple.render = CheckboxMultiple;

export default CheckboxMultiple;