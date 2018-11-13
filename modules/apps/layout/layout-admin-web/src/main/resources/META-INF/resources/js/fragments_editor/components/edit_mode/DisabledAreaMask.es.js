import Component from 'metal-component';
import {Config} from 'metal-state';

import DisabledAreaPopover from './DisabledAreaPopover.es';

/**
 * Applies a mask on edit mode for disabling some areas.
 * Starting from "origin" selector, it looks for elements that are
 * not parent of that element or inside "whitelist".
 * @review
 */
class DisabledAreaMask extends Component {

	/**
	 * Checks if the given element can be disabled.
	 * @param {HTMLElement} element
	 * @private
	 * @return {boolean}
	 * @review
	 */
	static _elementCanBeDisabled(element) {
		const {height} = element.getBoundingClientRect();
		const {position} = window.getComputedStyle(element);

		const hasAbsolutePosition = DisabledAreaMask.STATIC_POSITIONS.indexOf(position) === -1;
		const hasZeroHeight = height === 0;

		return (!hasZeroHeight && !hasAbsolutePosition);
	}

	/**
	 * Checks if the given element matches all necesary
	 * rules to be disabled.
	 * @param {HTMLElement} element
	 * @param {string} selector
	 * @private
	 * @return {boolean}
	 * @review
	 */
	static _elementMatchesSelector(element, selector) {
		const childMatchesSelector = element.querySelector(selector);
		const matchesSelector = element.matches(selector);

		return (matchesSelector || childMatchesSelector);
	}

	/**
	 * @inheritdoc
	 * @review
	 */
	created() {
		this._markDisabledAreas();
		this._createDisabledAreaPopover();
	}

	/**
	 * @inheritdoc
	 * @review
	 */
	disposed() {
		if (this._disabledAreaPopover) {
			this._disabledAreaPopover.dispose();
		}
	}

	/**
	 * Looks for disabled areas and marks them with
	 * disabledAreaClass.
	 * @private
	 * @review
	 */
	_markDisabledAreas() {
		let element = document.querySelector(this.origin);

		while (element.parentElement && element !== document.body) {
			Array.from(element.parentElement.children).forEach(
				childElement => this._markDisabledElement(childElement)
			);

			element = element.parentElement;
		}
	}

	/**
	 * Marks the given element as disabled if it
	 * does not fit any element from the whitelist.
	 * @param {HTMLElement} element
	 */
	_markDisabledElement(element) {
		const disable = (
			(element.tagName !== 'SCRIPT') &&
			DisabledAreaMask._elementCanBeDisabled(element) &&
			!this.whitelist.some(
				selector => DisabledAreaMask._elementMatchesSelector(element, selector)
			)
		);

		if (disable) {
			element.classList.add(this.disabledAreaClass);
		}
	}

	/**
	 * Creates a DisabledAreaPopover instance for
	 * existing disabled areas.
	 * @private
	 * @review
	 */
	_createDisabledAreaPopover() {
		if (this._disabledAreaPopover) {
			this._disabledAreaPopover.dispose();
		}

		this._disabledAreaPopover = new DisabledAreaPopover(
			{
				selector: '.lfr-edit-mode__disabled-area'
			}
		);
	}

}

/**
 * @review
 * @see DisabledAreaMask.disabledAreaClass
 * @type {string}
 */
DisabledAreaMask.DEFAULT_DISABLED_AREA_CLASS = 'lfr-edit-mode__disabled-area';

/**
 * @review
 * @see DisabledAreaMask.origin
 */
DisabledAreaMask.DEFAULT_ORIGIN = '.layout-content';

/**
 * @review
 * @see DisabledAreaMask.whitelist
 */
DisabledAreaMask.DEFAULT_WHITELIST = [
	DisabledAreaMask.DEFAULT_ORIGIN,
	`.${DisabledAreaMask.DEFAULT_DISABLED_AREA_CLASS}`,
	'.control-menu',
	'.lfr-add-panel',
	'.lfr-product-menu-panel'
];

/**
 * List of CSS positions that define
 * an static-like positioned element
 * @review
 * @type {string[]}
 */
DisabledAreaMask.STATIC_POSITIONS = [
	'',
	'static',
	'relative'
];

/**
 * State definition
 * @review
 * @static
 * @type {!object}
 */
DisabledAreaMask.STATE = {

	/**
	 * HTMLElement where the disabling process starts
	 * @default DEFAULT_ORIGIN
	 * @instance
	 * @memberOf DisabledAreaMask
	 * @review
	 * @type {string}
	 */
	origin: Config
		.string()
		.value(DisabledAreaMask.DEFAULT_ORIGIN),

	/**
	 * CSS class added to elements that are going to
	 * be disabled.
	 * @default DEFAULT_DISABLED_AREA_CLASS
	 * @instance
	 * @memberOf DisabledAreaMask
	 * @review
	 * @type {string}
	 */
	disabledAreaClass: Config
		.string()
		.value(DisabledAreaMask.DEFAULT_DISABLED_AREA_CLASS),

	/**
	 * List of selectors that are ignored when disabling
	 * elements. Any element matching any of these selector
	 * will NOT be disabled.
	 * @default DEFAULT_WHITELIST
	 * @instance
	 * @memberOf DisabledAreaMask
	 * @review
	 * @type {string[]}
	 */
	whitelist: Config
		.arrayOf(Config.string())
		.value(DisabledAreaMask.DEFAULT_WHITELIST),

	/**
	 * Popover instance used internally
	 * @default null
	 * @instance
	 * @memberOf DisabledAreaMask
	 * @review
	 * @type {object}
	 */
	_disabledAreaPopover: Config
		.object()
		.value(null)
};

export {DisabledAreaMask};
export default DisabledAreaMask;