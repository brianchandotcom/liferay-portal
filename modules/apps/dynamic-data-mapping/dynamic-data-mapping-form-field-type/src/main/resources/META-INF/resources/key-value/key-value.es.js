import Component from 'metal-component';
import Soy from 'metal-soy';

import templates from './key-value.soy';

/**
 * KeyValue Component
 */

class KeyValue extends Component {}

// Register component

Soy.register(KeyValue, templates, 'render');

if (!window.DDMKeyValue) {
	window.DDMKeyValue = {

	};
}

window.DDMKeyValue.render = KeyValue;

export default KeyValue;