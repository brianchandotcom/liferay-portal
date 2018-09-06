import Component from 'metal-component';
import Soy from 'metal-soy';

import templates from './paragraph.soy';

/**
 * Paragraph Component
 */

class Paragraph extends Component {}

Paragraph.STATE = {
	text: {
		isHtml: true,
		value: ''
	}
};

// Register component

Soy.register(Paragraph, templates, 'render');

Paragraph.Soy = Soy;

if (!window.DDMParagraph) {
	window.DDMParagraph = {

	};
}

window.DDMParagraph.render = Paragraph;

export default Paragraph;