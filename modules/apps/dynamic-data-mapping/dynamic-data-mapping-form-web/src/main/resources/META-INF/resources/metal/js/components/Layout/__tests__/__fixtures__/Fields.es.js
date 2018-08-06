import Component from 'metal-component';
import Soy from 'metal-soy';

import FieldsRegister from './FieldsRegister.soy.js';
import templates from './Fields.soy.js';

class Fields extends Component {
    emitFieldEdit() {
        this.emit('fieldEdit', {
            value: 'Foo',
            key: 'Bar',
            originalEvent: {}
        });
    }
}

Soy.register(Fields, templates);

export default Fields;