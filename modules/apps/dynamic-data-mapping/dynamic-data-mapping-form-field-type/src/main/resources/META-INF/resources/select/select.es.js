import Component from 'metal-component';
import Soy from 'metal-soy';
import {Align} from 'metal-position';

import templates from './select.soy';

const SelectTemplates = [];

if (!window.DDMSelect) {
	window.DDMSelect = {

	};
}

for (const template in templates) {
	if (template !== 'templates') {
		class C extends Component {};
		Soy.register(C, templates, template);
		C.Soy = Soy;
		SelectTemplates.push(
			{
				key: template,
				component: C
			}
		);
		window.DDMSelect[template] = C;
	}
}

window.DDMSelect.Align = Align;

export default SelectTemplates;