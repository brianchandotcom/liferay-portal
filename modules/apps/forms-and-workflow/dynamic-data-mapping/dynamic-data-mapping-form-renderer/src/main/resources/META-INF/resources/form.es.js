import Component from 'metal-component';
import Soy from 'metal-soy';
import {renderToString} from 'incremental-dom-string';

import templates from './form.soy';

let FormTemplates = [];

if (!window.ddm) {
	window.ddm = {

	};
}

for (let template in templates) {
	if (template !== 'templates') {
		class C extends Component {};
		Soy.register(C, templates, template);
		FormTemplates.push(
			{
				key: template,
				component: C
			}
		);
		window.ddm[template] = C;
		// window.ddm[template] = function(data, element) {
		// 	const renderer = templates[template];
		// 	Object.keys(data).forEach((key) => {
		// 		if (data[key] && data[key].contentKind === 'HTML') {
		// 			data[key] = Soy.toIncDom(data[key].content);
		// 		}
		// 	});
		// 	return renderToString(() => renderer(data));
		// };
	}
}

window.MetalComponent = Component;

export default FormTemplates;