import Component from 'metal-component';
import Soy from 'metal-soy';

import templates from './field-types-sidebar.soy';

let FieldTypesSidebarTemplates = [];

if (!window.DDMFieldTypesSidebar) {
	window.DDMFieldTypesSidebar = {

	};
}

for (let template in templates) {
	if (template !== 'templates') {
		class C extends Component {};
		Soy.register(C, templates, template);
		C.Soy = Soy;
		FieldTypesSidebarTemplates.push({
			key: template,
			component: C
		});
		window.DDMFieldTypesSidebar[template] = C;
	}
}

export default FieldTypesSidebarTemplates;