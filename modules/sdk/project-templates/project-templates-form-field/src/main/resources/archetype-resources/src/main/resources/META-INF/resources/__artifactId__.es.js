import Component from 'metal-component';
import Soy from 'metal-soy';

import templates from './${artifactId}.soy';

/**
 * ${className} Component
 */
class ${className} extends Component {}

// Register component
Soy.register(${className}, templates, 'render');

if (!window.DDM${className}) {
	window.DDM${className} = {

	};
}

window.DDM${className}.render = ${className};

export default ${className};