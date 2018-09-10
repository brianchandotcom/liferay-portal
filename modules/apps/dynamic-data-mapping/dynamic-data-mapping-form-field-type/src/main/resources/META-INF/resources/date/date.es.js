import Component from 'metal-component';
import vanillaTextMask from 'vanilla-text-mask';
import Soy from 'metal-soy';
import templates from './date.soy';

/**
 * Date Component
 */

class Date extends Component {}

// Register component

Soy.register(Date, templates, 'render');

if (!window.DDMDate) {
	window.DDMDate = {

	};
}

window.DDMDate.vanillaTextMask = vanillaTextMask;
window.DDMDate.render = Date;

export default Date;