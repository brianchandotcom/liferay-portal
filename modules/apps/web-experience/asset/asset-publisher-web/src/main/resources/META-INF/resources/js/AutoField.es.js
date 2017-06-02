import Component from 'metal-component';
import Soy from 'metal-soy';

import dom from 'metal-dom';
import core from 'metal/src/core';
import templates from './AutoField.soy';
import { Config } from 'metal-state';

import CategorySelector from './CategorySelector.es'
import TagSelector from './TagSelector.es';

/**
 * AutoField
 *
 */
class AutoField extends Component {

	/**
	* Add new rule
	*/
	addNewRule() {
		let list = this.rules;

		list.push({type: 'assetTags', queryContains: true});

		this.rules = list;
	}

	/**
	* Change Selector
	*/
	changeSelector(event) {
		let itemIndex = event.target.getAttribute('data-item-index');

		let rules = this.rules;

		rules[itemIndex] = {
			type: event.target.value,
			queryAndOperator: 'all',
			queryContains: true
		};

		this.rules = rules;
	}

	/**
	* Remove rule
	*/
	removeRule(event) {
		let button = dom.closest(event.target, 'button');
		let indexToRemove = button.getAttribute('data-card-id');
		let list = this.rules;

		list.splice(indexToRemove, 1);

		this.rules = list;
	}

	/**
	* Setter rules
	*
	* Update queryLogicIndexes depend on length of rules
	*/
	setterSaveRules_(rules) {
		let queryLogicIndexes;

		queryLogicIndexes = rules.reduce((logicIndexes, item, index) => {
			if (index === 0) {
				return '0';
			}

			return logicIndexes + ',' + index;

		}, '');

		this.queryLogicIndexes = queryLogicIndexes;

		return rules;
	}

}

AutoField.STATE = {
	/**
	 * rules
	 * @type {array}
	 */
	rules: {
		setter: 'setterSaveRules_',
		value: [{type: 'assetTags', queryAndOperator: 'all'}],
	},

	groupIds: {
		value: []
	},

	queryLogicIndexes: {
		value: '0'
	}
};

Soy.register(AutoField, templates);

export default AutoField;