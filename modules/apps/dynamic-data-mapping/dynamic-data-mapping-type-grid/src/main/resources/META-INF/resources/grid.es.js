import Component from 'metal-component';
import Soy from 'metal-soy';

import templates from './grid.soy';

let GridTemplates = [];

if (!window.DDMGrid) {
	window.DDMGrid = {

	};
}

for (let template in templates) {
	if (template !== 'templates') {
		class C extends Component {};
		Soy.register(C, templates, template);
		GridTemplates.push(
			{
				key: template,
				component: C
			}
		);
		window.DDMGrid[template] = C;
	}
}

export default GridTemplates;