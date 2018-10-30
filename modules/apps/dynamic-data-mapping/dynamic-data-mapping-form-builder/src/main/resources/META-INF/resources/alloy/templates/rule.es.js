import Component from 'metal-component';
import Soy from 'metal-soy';

import templates from './rule.soy';

const RuleTemplates = [];

if (!window.DDMRule) {
	window.DDMRule = {

	};
}

for (const template in templates) {
	if (template !== 'templates') {
		class C extends Component {}
		Soy.register(C, templates, template);
		RuleTemplates.push(
			{
				component: C,
				key: template
			}
		);
		window.DDMRule[template] = C;
	}
}

export default RuleTemplates;