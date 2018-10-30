import Component from 'metal-component';
import Soy from 'metal-soy';

import templates from './field-types-sidebar.soy';

const FieldTypesSidebarTemplates = [];

if (!window.DDMFieldTypesSidebar) {
	window.DDMFieldTypesSidebar = {

	};
}

for (const template in templates) {
	if (template !== 'templates') {
		class C extends Component {}
		Soy.register(C, templates, template);
		C.Soy = Soy;
		FieldTypesSidebarTemplates.push(
			{
				component: C,
				key: template
			}
		);
		window.DDMFieldTypesSidebar[template] = C;
	}
}

export default FieldTypesSidebarTemplates;