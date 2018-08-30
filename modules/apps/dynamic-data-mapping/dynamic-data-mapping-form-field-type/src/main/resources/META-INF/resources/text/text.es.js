import Component from 'metal-component';
import Soy from 'metal-soy';

import templates from './text.soy';

/**
 * Text Component
 */

class Text extends Component {}

// Register component

Soy.register(Text, templates, 'render');

if (!window.DDMText) {
	window.DDMText = {

	};
}

window.DDMText.render = Text;

export default Text;