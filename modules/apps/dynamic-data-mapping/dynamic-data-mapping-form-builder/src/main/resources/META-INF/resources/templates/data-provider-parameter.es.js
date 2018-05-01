import Component from 'metal-component';
import Soy from 'metal-soy';

import templates from './data-provider-parameter.soy';

let DataProviderParameterTemplates = [];

if (!window.DDMDataProviderParameter) {
	window.DDMDataProviderParameter = {

	};
}

for (let template in templates) {
	if (template !== 'templates') {
		class C extends Component {};
		Soy.register(C, templates, template);
		DataProviderParameterTemplates.push(
			{
				key: template,
				component: C
			}
		);
		window.DDMDataProviderParameter[template] = C;
	}
}

export default DataProviderParameterTemplates;