import core from 'metal';
import Component from 'metal-component';
import Soy from 'metal-soy';
import templates from './ProgressBar.soy';

/**
 * UI Component that renders a progress bar.
 */
class ProgressBar extends Component {
	/**
	 * Setter function for the `value` state key. Makes sure the value
	 * is between the current `min` and `max` state keys.
	 * @param {number} value
	 * @return {number}
	 * @protected
	 */
	setterValueFn_(value) {
		if (value < this.min) {
			value = this.min;
		}
		if (value > this.max) {
			value = this.max;
		}
		return value;
	}

	/**
	 * Synchronization logic for the `max` state.
	 * @param {number} max
	 */
	syncMax(max) {
		if (max < this.value) {
			this.value = max;
		}
	}

	/**
	 * Synchronization logic for the `min` state.
	 * @param {number} min
	 */
	syncMin(min) {
		if (min > this.value) {
			this.value = min;
		}
	}
}

/**
 * State definition.
 * @type {!Object}
 * @static
 */
ProgressBar.STATE = {
	/**
	 * Optional CSS classes to be added to the inner progress bar element,
	 * like 'progress-bar-danger'.
	 * @type {string}
	 */
	barClass: {
		validator: core.isString
	},

	/**
	 * An optional label to be rendered inside the progress bar. Can be either
	 * a string (with raw text or html) or an incremental dom function.
	 * @type {function()|string?}
	 */
	label: {
		validator: label => {
			return !core.isDefAndNotNull(label) ||
				core.isString(label) ||
				core.isFunction(label);
		}
	},

	/**
	 * The maximum value of the progress bar. When the value is at its
	 * max, the bar will be fully extended.
	 * @type {number}
	 */
	max: {
		validator: core.isNumber,
		value: 100
	},

	/**
	 * The minimum value of the progress bar. When the value is at its
	 * max, the bar will be fully collapsed.
	 * @type {number}
	 */
	min: {
		validator: core.isNumber,
		value: 0
	},

	/**
	 * The current value of the progress bar.
	 * @type {number}
	 */
	value: {
		setter: 'setterValueFn_',
		validator: core.isNumber,
		value: 0
	}
};
Soy.register(ProgressBar, templates);

export { ProgressBar };
export default ProgressBar;