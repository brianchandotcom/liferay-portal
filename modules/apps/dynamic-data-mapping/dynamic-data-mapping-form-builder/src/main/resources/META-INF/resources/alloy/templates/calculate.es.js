import Component from 'metal-component';
import Soy from 'metal-soy';

import templates from './calculate.soy';

const CalculateTemplates = [];

if (!window.DDMCalculate) {
	window.DDMCalculate = {

	};
}

for (const template in templates) {
	if (template !== 'templates') {
		class C extends Component {}
		Soy.register(C, templates, template);
		CalculateTemplates.push(
			{
				component: C,
				key: template
			}
		);
		window.DDMCalculate[template] = C;
	}
}

export default CalculateTemplates;