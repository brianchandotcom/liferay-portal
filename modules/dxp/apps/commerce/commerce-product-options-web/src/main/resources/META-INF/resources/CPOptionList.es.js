import Component from 'metal-component';
import {Config} from 'metal-state';
import Soy from 'metal-soy';
import {dom, globalEval} from 'metal-dom';
import {CancellablePromise} from 'metal-promise';
import {async, core} from 'metal';

import templates from './CPOptionList.soy';

/**
 * CPOptionList
 *
 */

class CPOptionList extends Component {

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

CPOptionList.STATE = {
	currentCPOptionId: Config.string(),
	options: Config.array().value([])
};

// Register component

Soy.register(CPOptionList, templates);

export default CPOptionList;