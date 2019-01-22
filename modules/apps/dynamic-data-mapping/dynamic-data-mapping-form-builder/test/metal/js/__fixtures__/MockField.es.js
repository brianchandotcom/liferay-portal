import './MockFieldRegister.soy.js';
import Component from 'metal-component';
import Soy from 'metal-soy';
import templates from './MockField.soy.js';
import Config from 'metal-state/lib/Config';

class MockField extends Component {
	static STATE = {
		fieldName: Config.string(),
		label: Config.string(),
		options: Config.array(),
		readOnly: Config.bool(),
		type: Config.string(),
		value: Config.any()
	};

	emitFieldEdited(value, fieldName) {
		this.value = value;

		this.emit(
			'fieldEdited',
			{
				fieldInstance: this,
				originalEvent: {
					delegateTarget: this.element.querySelector('p'),
					target: {
						getAttribute: () => {
							return fieldName;
						}
					}
				},
				value
			}
		);
	}
}

Soy.register(MockField, templates);

export default MockField;