import Component from 'metal-component';
import Soy from 'metal-soy';

import './field-options-toolbar.soy';
import templates from './sidebar.soy';

const SidebarTemplates = [];

if (!window.DDMSidebar) {
	window.DDMSidebar = {

	};
}

for (const template in templates) {
	if (template !== 'templates') {
		class C extends Component {}
		Soy.register(C, templates, template);
		SidebarTemplates.push(
			{
				component: C,
				key: template
			}
		);
		window.DDMSidebar[template] = C;
	}
}

export default SidebarTemplates;