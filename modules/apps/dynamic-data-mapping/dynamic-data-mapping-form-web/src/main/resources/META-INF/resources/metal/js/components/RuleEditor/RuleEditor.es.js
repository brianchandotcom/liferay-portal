import 'clay-button';
import Component from 'metal-component';
import Soy from 'metal-soy';
import templates from './RuleEditor.soy.js';

/**
 * RuleEditor.
 * @extends Component
 */

class RuleEditor extends Component {
	static STATE = {

	}

	attached() {
		const addButton = document.querySelector('#addFieldButton');

		addButton.classList.add('hide');
	}

}

Soy.register(RuleEditor, templates);

export default RuleEditor;