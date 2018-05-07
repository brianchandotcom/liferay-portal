import Component from 'metal-component';
import Soy from 'metal-soy';

import templates from './calculate.soy';

let CalculateTemplates = [];

if (!window.DDMCalculate) {
	window.DDMCalculate = {

	};
}

for (let template in templates) {
	if (template !== 'templates') {
		class C extends Component {};
		Soy.register(C, templates, template);
		CalculateTemplates.push(
			{
				key: template,
				component: C
			}
		);
		window.DDMCalculate[template] = C;
	}
}

export default CalculateTemplates;