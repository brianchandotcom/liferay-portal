import Component from 'metal-component';
import Soy from 'metal-soy';
import dom from 'metal-dom';
import {Align} from 'metal-position';
import {EventHandler} from 'metal-events';
import {core, object} from 'metal';

import templates from './Dropdown.soy';

/**
 * Dropdown component.
 */
class Dropdown extends Component {
	/**
	 * @inheritDoc
	 */
	attached() {
		super.attached();
		this.eventHandler_.add(dom.on(document, 'click', this.handleDocClick_.bind(this)));
	}

	/**
	 * @inheritDoc
	 */
	created() {
		this.eventHandler_ = new EventHandler();
	}

	/**
	 * @inheritDoc
	 */
	detached() {
		super.detached();
		this.eventHandler_.removeAllListeners();
	}

	/**
	 * Closes the dropdown.
	 */
	close() {
		this.expanded = false;
	}

	/**
	 * Checks if the dropdown is currently open.
	 * @return {boolean}
	 */
	isOpen() {
		return this.expanded;
	}

	/**
	 * Handles document click in order to hide menu.
	 * @param {!Event} event
	 * @protected
	 */
	handleDocClick_(event) {
		if (this.element.contains(event.target)) {
			return;
		}
		this.close();
	}

	/**
	 * Opens the dropdown.
	 */
	open() {
		this.expanded = true;
	}

	/**
	 * The setter function for the `classMap` staet.
	 * @param {Object} val
	 * @return {!Object}
	 * @protected
	 */
	setterClassMapFn_(val) {
		return object.mixin(this.valueClassMapFn_(), val);
	}

	/**
	 * The setter function for the `position` state. Converts the supported
	 * string positions into the appropriate `Align` position constants.
	 * @param {string|number} val
	 * @return {number}
	 * @protected
	 */
	setterPositionFn_(val) {
		if (core.isNumber(val)) {
			return val;
		}

		return val.toLowerCase() === 'up' ? Align.TopLeft : Align.BottomLeft;
	}

	/**
	 * Synchronization logic for `expanded` state.
	 * @param {boolean} expanded
	 */
	syncExpanded(expanded) {
		if (expanded && this.alignElementSelector) {
			var alignElement = this.element.querySelector(this.alignElementSelector);

			if (alignElement) {
				var bodyElement = this.element.querySelector('.dropdown-menu');

				this.alignedPosition = Align.align(bodyElement, alignElement, this.position);
			}
		}
	}

	/**
	 * Toggles the dropdown, closing it when open or opening it when closed.
	 */
	toggle() {
		this.expanded = !this.expanded;
	}

	/**
	 * Validator for the `position` state.
	 * @param {string|number} position
	 * @return {boolean}
	 * @protected
	 */
	validatePosition_(position) {
		if (Align.isValidPosition(position)) {
			return true;
		}
		switch (position.toLowerCase()) {
			case 'up':
			case 'down':
				return true;
			default:
				return false;
		}
	}

	/**
	 * Gets the default value for the `body` state. Retrieves existing
	 * html for the body from the element, if there is any.
	 * @return {?string}
	 * @protected
	 */
	valueBodyFn_() {
		var dropdownMenu = this.element && this.element.querySelector('.dropdown-menu');

		return dropdownMenu ? dropdownMenu.innerHTML : '';
	}

	/**
	 * Gets the default value for the `classMap` state.
	 * @return {!Object}
	 * @protected
	 */
	valueClassMapFn_() {
		return {
			[Align.TopLeft]: 'dropup',
			[Align.TopCenter]: 'dropup',
			[Align.TopRight]: 'dropup',
			[Align.BottomLeft]: 'dropdown',
			[Align.BottomCenter]: 'dropdown',
			[Align.BottomRight]: 'dropdown',
			[Align.RightCenter]: 'dropright',
			[Align.LeftCenter]: 'dropleft'
		};
	}

	/**
	 * Gets the default value for the `header` state. Retrieves existing
	 * html for the header from the element, if there is any.
	 * @return {?string}
	 * @protected
	 */
	valueHeaderFn_() {
		if (this.element) {
			var wrapper = document.createElement('div');

			for (var i = 0; i < this.element.childNodes.length; i++) {
				if (dom.hasClass(this.element.childNodes[i], 'dropdown-menu')) {
					break;
				}
				wrapper.appendChild(this.element.childNodes[i].cloneNode(true));
			}

			return wrapper.innerHTML;
		}

		return '';
	}
}
Soy.register(Dropdown, templates);

/**
 * State definition.
 * @type {!Object}
 * @static
 */
Dropdown.STATE = {
	/**
	 * The current position of the tooltip after being aligned via `Align.align`.
	 * @type {number}
	 */
	alignedPosition: {
		validator: Align.isValidPosition
	},

	/**
	 * Optional selector for finding the element that the dropdown should be
	 * aligned to. If given, the dropdown will automatically find the best position
	 * to align, when the specified position doesn't work. Otherwise it will
	 * always just follow the given position, even if it's not ideal.
	 * @type {string}
	 */
	alignElementSelector: {
		validator: core.isString
	},

	/**
	 * The dropdown's body content.
	 * @type {string}
	 */
	body: {
		isHtml: true,
		valueFn: 'valueBodyFn_'
	},

	/**
	 * A map from `Align` position constants to the CSS class that should be
	 * added to the dropdown when it's aligned in that position.
	 * @type {!Object}
	 */
	classMap: {
		setter: 'setterClassMapFn_',
		validator: core.isObject,
		valueFn: 'valueClassMapFn_'
	},

	/**
	 * The dropdown's header content.
	 * @type {string}
	 */
	header: {
		isHtml: true,
		valueFn: 'valueHeaderFn_'
	},

	/**
	 * Flag indicating if the dropdown is expanded (open) or not.
	 * @type {boolean}
	 * @default false
	 */
	expanded: {
		value: false,
		internal: true
	},

	/**
	 * The position of the dropdown (either 'up', 'down' or any of the position
	 * constants available in `Align`).
	 * @type {string|number}
	 * @default Align.BottomLeft
	 */
	position: {
		setter: 'setterPositionFn_',
		value: Align.BottomLeft,
		validator: 'validatePosition_'
	},

	/**
	 * Flag indicating if the position class (specified by `classMap` state)
	 * should be added on the "dropdown-menu" element, instead of the main element.
	 * @type {boolean}
	 */
	positionClassOnMenu: {
		value: false
	}
};

export {Dropdown};
export default Dropdown;