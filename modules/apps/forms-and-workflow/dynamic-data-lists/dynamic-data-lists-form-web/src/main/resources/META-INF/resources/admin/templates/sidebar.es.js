import Component from 'metal-component';
import Soy from 'metal-soy';

import './field-options-toolbar.soy';
import templates from './sidebar.soy';

let SidebarTemplates = [];

for (let template in templates) {
	if (template !== 'templates') {
		class C extends Component {};
		Soy.register(C, templates, template);
		SidebarTemplates.push({
			key: template,
			component: C
		});
	}
}

export default SidebarTemplates;