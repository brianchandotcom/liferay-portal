import Component from 'metal-component';
import Soy from 'metal-soy';

import templates from './grid.soy';

const GridTemplates = [];

if (!window.DDMGrid) {
	window.DDMGrid = {

	};
}

for (const template in templates) {
	if (template !== 'templates') {
		class C extends Component {}
		Soy.register(C, templates, template);
		GridTemplates.push(
			{
				component: C,
				key: template
			}
		);
		window.DDMGrid[template] = C;
	}
}

export default GridTemplates;