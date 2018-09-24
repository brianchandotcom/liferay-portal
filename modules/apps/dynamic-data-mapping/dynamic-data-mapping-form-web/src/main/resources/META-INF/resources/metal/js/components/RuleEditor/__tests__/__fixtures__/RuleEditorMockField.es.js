import './RuleEditorMockFieldRegister.soy.js';
import Component from 'metal-component';
import Soy from 'metal-soy';
import templates from './RuleEditorMockField.soy.js';

class RuleEditorMockField extends Component {
	emitFieldEdited(value, fieldName) {
		this.emit(
			'fieldEdited',
			{
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

Soy.register(RuleEditorMockField, templates);

export default RuleEditorMockField;