import Component from 'metal-component';
import Soy from 'metal-soy';

import templates from './data-provider-parameter.soy';

const DataProviderParameterTemplates = [];

if (!window.DDMDataProviderParameter) {
	window.DDMDataProviderParameter = {

	};
}

for (const template in templates) {
	if (template !== 'templates') {
		class C extends Component {}
		Soy.register(C, templates, template);
		DataProviderParameterTemplates.push(
			{
				component: C,
				key: template
			}
		);
		window.DDMDataProviderParameter[template] = C;
	}
}

export default DataProviderParameterTemplates;