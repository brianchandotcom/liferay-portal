import './FieldsRuleEditorRegister.soy.js';
import Component from 'metal-component';
import Soy from 'metal-soy';
import templates from './FieldsRuleEditor.soy.js';

class FieldsRuleEditor extends Component {
	emitFieldEdited(value, mock, fn) {
		this.emit(
			'fieldEdited',
			{
				originalEvent: {
					delegateTarget: this.element.querySelector('p'),
					target: {
						getAttribute: () => {
							return mock;
						}
					},
				},
				value,
			}
		);
	}
}

Soy.register(FieldsRuleEditor, templates);

export default FieldsRuleEditor;