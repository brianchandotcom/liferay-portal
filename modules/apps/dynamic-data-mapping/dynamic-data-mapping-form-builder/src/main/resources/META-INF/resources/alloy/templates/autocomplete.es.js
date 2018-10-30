import Component from 'metal-component';
import Soy from 'metal-soy';

import templates from './autocomplete.soy';

const AutoCompleteTemplates = [];

if (!window.DDMAutoComplete) {
	window.DDMAutoComplete = {
	};
}

for (const template in templates) {
	if (template !== 'templates') {
		class C extends Component {}
		Soy.register(C, templates, template);
		AutoCompleteTemplates.push(
			{
				component: C,
				key: template
			}
		);

		window.DDMAutoComplete[template] = C;
	}
}

export default AutoCompleteTemplates;