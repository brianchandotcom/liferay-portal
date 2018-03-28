import Component from 'metal-component';
import Soy from 'metal-soy';
import {Config} from 'metal-state';

import templates from './CPDefinitionOptionList.soy';

/**
 * CPDefinitionOptionList
 *
 */

class CPDefinitionOptionList extends Component {

	_handleEditValues(event) {
		var target = event.currentTarget;

		var cpOptionId = target.getAttribute('data-id');

		this.emit('optionSelected', cpOptionId);
		this.emit('editValues', cpOptionId);
	}

	_handleAddOptionClick(event) {
		event.stopImmediatePropagation();
		event.preventDefault();

		this.emit('addOption');
	}

	_handleSelectOptionClick(event) {
		var target = event.currentTarget;

		var cpOptionId = target.getAttribute('data-id');

		this.emit('optionSelected', cpOptionId);
	}
}

/**
 * State definition.
 * @type {!Object}
 * @static
 */

CPDefinitionOptionList.STATE = {
	cpDefinitionOptions: Config.array().value([]),
	currentCPDefinitionOptionId: Config.string()
};

// Register component

Soy.register(CPDefinitionOptionList, templates);

export default CPDefinitionOptionList;