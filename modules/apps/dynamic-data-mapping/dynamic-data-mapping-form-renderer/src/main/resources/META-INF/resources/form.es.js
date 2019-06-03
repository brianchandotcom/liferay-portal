import Component from 'metal-component';
import Soy from 'metal-soy';

import templates from './form.soy';

let FormTemplates = [];

if (!window.ddm) {
	window.ddm = {};
}

for (let template in templates) {
	if (template !== 'templates') {
		class C extends Component {}

		Soy.register(C, templates, template);

		FormTemplates.push({
			component: C,
			key: template
		});

		window.ddm[template] = C;
	}
}

window.MetalComponent = Component;

export default FormTemplates;
