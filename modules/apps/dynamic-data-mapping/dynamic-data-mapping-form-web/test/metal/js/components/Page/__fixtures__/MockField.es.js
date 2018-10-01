import './FieldsRegister.soy.js';
import Component from 'metal-component';
import Soy from 'metal-soy';
import templates from './MockField.soy.js';

class MockField extends Component {
	emitFieldEdited() {
		this.emit(
			'fieldEdited',
			{
				key: 'Bar',
				originalEvent: {},
				value: 'Foo'
			}
		);
	}
}

Soy.register(MockField, templates);

export default MockField;