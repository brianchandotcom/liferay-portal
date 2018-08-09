import './FieldsRegister.soy.js';
import Component from 'metal-component';
import Soy from 'metal-soy';
import templates from './Fields.soy.js';

class Fields extends Component {
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

Soy.register(Fields, templates);

export default Fields;