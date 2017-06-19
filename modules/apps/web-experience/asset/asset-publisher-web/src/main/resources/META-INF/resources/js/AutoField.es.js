import Component from 'metal-component';
import Soy from 'metal-soy';
import core from 'metal';
import dom from 'metal-dom';
import { Config } from 'metal-state';

import CategorySelector from './CategorySelector.es'
import TagSelector from './TagSelector.es';
import templates from './AutoField.soy';

const DEFAULT_RULE = {type: 'assetTags', queryContains: true};

/**
 * AutoField
 *
 */
class AutoField extends Component {
	/**
	 * @inheritDoc
	 */
	created() {
		this.on('rulesChanged', this.onRulesChanged_);
	}

	/**
	 * Adds a new rule of type Tags (by default) to the current list of rules.
	 * @protected
	 */
	addRule_() {
		this.rules = this.rules.concat(DEFAULT_RULE);
	}

	/**
	 * Updates a given rule when the user changes the type of selection (from Tags
	 * to Categories) that wants to apply to it
	 * @param {Event} event
	 * @protected
	 */
	changeSelector_(event) {
		let itemIndex = event.delegateTarget.getAttribute('data-item-index');
		let rules = this.rules;

		rules[itemIndex] = {
			type: event.target.value,
			queryAndOperator: 'all',
			queryContains: true
		};

		this.rules = rules;
	}

	/**
	 * Deletes a rule from the current list. This change can not be undone. Changes
	 * made to the deleted rule will be lost.
	 * @param {Event} event
	 * @protected
	 */
	deleteRule_(event) {
		let itemIndex = event.delegateTarget.getAttribute('data-rule-id');
		let list = this.rules;

		list.splice(itemIndex, 1);

		this.rules = this.rules;
	}

	/**
	 * Updates the queryLogicIndexes whenever the list of rules change. The value
	 * of this property is as follow:
	 * - rules: [{}, {}, {}]
	 * - queryLogicIndexes: "0,1,2";
	 */
	onRulesChanged_() {
		this.queryLogicIndexes = Object.keys(this.rules).toString();
	}
}

AutoField.STATE = {
	/**
	 * rules
	 * @type {array}
	 */
	rules: {
		value: [DEFAULT_RULE],
	},

	groupIds: {
		value: []
	},

	queryLogicIndexes: {
		value: '0'
	}
};

Soy.register(AutoField, templates);

export { AutoField };
export default AutoField;