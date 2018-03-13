import Component from 'metal-component';
import { Config } from 'metal-state';
import Soy from 'metal-soy';
import {dom, globalEval} from 'metal-dom';
import { CancellablePromise } from 'metal-promise';
import { async, core } from 'metal';

import templates from './CPDefinitionOptionValueList.soy';

/**
 * CPDefinitionOptionValueList
 *
 */
class CPDefinitionOptionValueList extends Component {

	_handleAddOptionValueClick(event) {
		event.stopImmediatePropagation();
		event.preventDefault();

		this.emit('addOptionValue');
	}

	_handleSelectCPDefinitionOptionValueRelClick(event) {
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
CPDefinitionOptionValueList.STATE = {
	currentCPDefinitionOptionValueRelId: Config.string(),
	cpDefinitionOptionValueRels: Config.array().value([])
};

// Register component
Soy.register(CPDefinitionOptionValueList, templates);

export default CPDefinitionOptionValueList;