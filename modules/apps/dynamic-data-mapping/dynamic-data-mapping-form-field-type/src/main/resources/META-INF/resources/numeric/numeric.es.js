import Component from 'metal-component';
import Soy from 'metal-soy';

import createNumberMask from 'text-mask-addons/dist/createNumberMask';
import vanillaTextMask from 'vanilla-text-mask';

import templates from './numeric.soy';

/**
 * Numeric Component
 */

class Numeric extends Component {}

// Register component

Soy.register(Numeric, templates, 'render');

if (!window.DDMNumeric) {
	window.DDMNumeric = {

	};
}

window.DDMNumeric.createNumberMask = createNumberMask;
window.DDMNumeric.vanillaTextMask = vanillaTextMask;
window.DDMNumeric.render = Numeric;

export default Numeric;