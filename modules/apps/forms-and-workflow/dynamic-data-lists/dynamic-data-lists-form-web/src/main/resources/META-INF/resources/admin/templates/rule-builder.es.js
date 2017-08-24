import Component from 'metal-component';
import Soy from 'metal-soy';

import templates from './rule-builder.soy';

let RuleBuilderTemplates = [];

for (let template in templates) {
	if (template !== 'templates') {
		class C extends Component {};
		Soy.register(C, templates, template);
		RuleBuilderTemplates.push({
			key: template,
			component: C
		});
	}
}

export default RuleBuilderTemplates;