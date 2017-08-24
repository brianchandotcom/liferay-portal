import Component from 'metal-component';
import Soy from 'metal-soy';

import templates from './field-options-toolbar.soy';

let OptionsToolbarTemplates = [];

for (let template in templates) {
	if (template !== 'templates') {
		class C extends Component {};
		Soy.register(C, templates, template);
		OptionsToolbarTemplates.push({
			key: template,
			component: C
		});
	}
}

export default OptionsToolbarTemplates;