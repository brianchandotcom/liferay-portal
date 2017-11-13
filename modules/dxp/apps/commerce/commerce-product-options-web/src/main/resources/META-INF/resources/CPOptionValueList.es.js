import Component from 'metal-component';
import { Config } from 'metal-state';
import Dropdown from 'metal-dropdown';
import Soy from 'metal-soy';
import {dom, globalEval} from 'metal-dom';
import { CancellablePromise } from 'metal-promise';
import { async, core } from 'metal';

import templates from './CPOptionValueList.soy';

/**
 * CPOptionValueList
 *
 */
class CPOptionValueList extends Component {

	_handleAddOptionValueClick(event) {
		event.stopImmediatePropagation();
		event.preventDefault();

		this.emit('addOptionValue');
	}

	_handleSelectOptionValueClick(event) {
		var target = event.target;

		var row = dom.closest(target, 'tr');

		var cpOptionValueId = row.getAttribute('data-id');

		this.emit('optionValueSelected', cpOptionValueId);
	}
}

/**
 * State definition.
 * @type {!Object}
 * @static
 */
CPOptionValueList.STATE = {
	optionValues: Config.array().value([]),
	currentCPOptionValueId: Config.string()
};

// Register component
Soy.register(CPOptionValueList, templates);

export default CPOptionValueList;