import Component from 'metal-component';
import Soy from 'metal-soy';

import templates from './field-options-toolbar.soy';

const OptionsToolbarTemplates = [];

if (!window.DDMFieldSettingsToolbar) {
	window.DDMFieldSettingsToolbar = {

	};
}

for (const template in templates) {
	if (template !== 'templates') {
		class C extends Component {}
		Soy.register(C, templates, template);
		OptionsToolbarTemplates.push(
			{
				component: C,
				key: template
			}
		);
		window.DDMFieldSettingsToolbar[template] = C;
	}
}

export default OptionsToolbarTemplates;