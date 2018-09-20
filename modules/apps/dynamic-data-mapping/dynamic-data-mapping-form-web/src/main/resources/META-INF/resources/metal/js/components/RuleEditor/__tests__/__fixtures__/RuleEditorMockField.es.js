import './RuleEditorMockFieldRegister.soy.js';
import Component from 'metal-component';
import Soy from 'metal-soy';
import templates from './RuleEditorMockField.soy.js';

class RuleEditorMockField extends Component {
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
					}
				},
				value
			}
		);
	}
}

Soy.register(RuleEditorMockField, templates);

export default RuleEditorMockField;