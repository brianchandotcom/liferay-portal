import Component from 'metal-component';
import Soy from 'metal-soy';

import templates from './autocomplete.soy';

let AutoCompleteTemplates = [];

if (!window.DDMAutoComplete) {
	window.DDMAutoComplete = {
	};
}

for (let template in templates) {
	if (template !== 'templates') {
		class C extends Component {};
		Soy.register(C, templates, template);
		AutoCompleteTemplates.push(
			{
				key: template,
				component: C
			}
		);

		window.DDMAutoComplete[template] = C;
	}
}

export default AutoCompleteTemplates;