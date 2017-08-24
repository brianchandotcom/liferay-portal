import Component from 'metal-component';
import Soy from 'metal-soy';

import templates from './autocomplete.soy';

let AutoCompleteTemplates = [];

for (let template in templates) {
	if (template !== 'templates') {
		class C extends Component {};
		Soy.register(C, templates, template);
		AutoCompleteTemplates.push({
			key: template,
			component: C
		});
	}
}

export default AutoCompleteTemplates;