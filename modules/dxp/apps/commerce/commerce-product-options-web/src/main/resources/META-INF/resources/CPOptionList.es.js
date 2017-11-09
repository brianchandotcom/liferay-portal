import Component from 'metal-component';
import { Config } from 'metal-state';
import Dropdown from 'metal-dropdown';
import Soy from 'metal-soy';
import {dom, globalEval} from 'metal-dom';
import { CancellablePromise } from 'metal-promise';
import { async, core } from 'metal';

import templates from './CPOptionList.soy';

/**
 * CPOptionList
 *
 */
class CPOptionList extends Component {

	/**
	 * @inheritDoc
	 */
	constructor(opt_config, opt_element) {
		super(opt_config, opt_element);
	}

	/**
	 * @inheritDoc
	 */
	attached() {

	}

	rendered() {

	}

	created() {

	}

	_handleEditValues(event) {
		var target = event.target;

		var row = dom.closest(target, 'tr');

		var  cpOptionId =  row.getAttribute('data-id');

		this.emit('optionSelected', cpOptionId);
		this.emit('editValues', cpOptionId);
	}

	_handleAddOptionClick(event) {

		event.stopImmediatePropagation();
		event.preventDefault();

		this.emit('addOption');
	}

	_handleSelectOptionClick(event) {
		var target = event.target;

		var row = dom.closest(target, 'tr');

		var  cpOptionId =  row.getAttribute('data-id');

		this.emit('optionSelected', cpOptionId);
	}
}

/**
 * State definition.
 * @type {!Object}
 * @static
 */
CPOptionList.STATE = {

	options : Config.array().value([]),

	currentCPOptionId  : Config.string()

};

// Register component
Soy.register(CPOptionList, templates);

export default CPOptionList;