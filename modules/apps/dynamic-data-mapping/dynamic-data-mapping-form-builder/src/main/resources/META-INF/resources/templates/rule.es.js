import Component from 'metal-component';
import Soy from 'metal-soy';

import templates from './rule.soy';

let RuleTemplates = [];

if (!window.DDMRule) {
	window.DDMRule = {

	};
}

for (let template in templates) {
	if (template !== 'templates') {
		class C extends Component {};
		Soy.register(C, templates, template);
		RuleTemplates.push(
			{
				key: template,
				component: C
			}
		);
		window.DDMRule[template] = C;
	}
}

export default RuleTemplates;