import Component from 'metal-component';
import Soy from 'metal-soy';

import templates from './fieldset.soy';

/**
 * Fieldset Component
 */

class Fieldset extends Component {}

// Register component

Soy.register(Fieldset, templates, 'render');

if (!window.DDMFieldset) {
	window.DDMFieldset = {

	};
}

window.DDMFieldset.render = Fieldset;

export default Fieldset;