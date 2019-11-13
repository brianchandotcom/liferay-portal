/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

'use strict';

import core from 'metal';
import EventEmitter from 'metal-events';

/**
 * Listens to keyboard events and uses them to move focus between different
 * elements from a component (via the arrow keys for example).
 * By default `KeyboardFocusManager` will assume that all focusable elements
 * in the component will have refs that follow the pattern in
 * KeyboardFocusManager.REF_REGEX, which includes a position number. The arrow
 * keys will then automatically move between elements by
 * incrementing/decrementing this position.
 * It's possible to fully customize this behavior by passing a function to
 * `setFocusHandler`. For more details check this function's docs.
 */
class KeyboardFocusManager extends EventEmitter {
	/**
	 * Constructor for `KeyboardFocusManager`.
	 * @param {!Component} component
	 * @param {string=} opt_selector
	 */
	constructor(component, opt_selector) {
		super();
		this.component_ = component;
		this.selector_ = opt_selector || '*';
		this.handleKey_ = this.handleKey_.bind(this);
	}

	/**
	 * Builds a ref string for the given position.
	 * @param {string} prefix
	 * @param {number|string} position
	 * @return {string}
	 * @protected
	 */
	buildRef_(prefix, position) {
		return prefix + position;
	}

	/**
	 * @inheritDoc
	 */
	disposeInternal() {
		super.disposeInternal();
		this.stop();
		this.component_ = null;
		this.selector_ = null;
	}

	/**
	 * Gets the next focusable element, that is, the next element that doesn't
	 * have the `data-unfocusable` attribute set to `true`.
	 * @param {string} prefix
	 * @param {number} position
	 * @param {number} increment
	 * @return {string}
	 * @protected
	 */
	getNextFocusable_(prefix, position, increment) {
		const initialPosition = position;
		let element;
		let ref;
		do {
			position = this.increment_(position, increment);
			ref = this.buildRef_(prefix, position);
			element = this.component_.refs[ref];
		} while (this.isFocusable_(element) && position !== initialPosition);
		return element ? ref : null;
	}

	/**
	 * Handles a `keydown` event. Decides if a new element should be focused
	 * according to the key that was pressed.
	 * @param {!Event} event
	 * @protected
	 */
	handleKey_(event) {
		let element = this.focusHandler_ && this.focusHandler_(event);
		if (!this.focusHandler_ || element === true) {
			element = this.handleKeyDefault_(event);
		}

		const originalValue = element;
		if (!core.isElement(element)) {
			element = this.component_.refs[element];
		}
		if (element) {
			element.focus();
			this.emit(KeyboardFocusManager.EVENT_FOCUSED, {
				element,
				ref: core.isString(originalValue) ? originalValue : null
			});
		}
	}

	/**
	 * Handles a key press according to the default behavior. Assumes that all
	 * focusable elements in the component will have refs that follow the pattern
	 * in KeyboardFocusManager.REF_REGEX, which includes a position number. The
	 * arrow keys will then automatically move between elements by
	 * incrementing/decrementing the position.
	 * @param {!Event} event
	 * @protected
	 */
	handleKeyDefault_(event) {
		const ref = event.delegateTarget.getAttribute('ref');
		const matches = KeyboardFocusManager.REF_REGEX.exec(ref);
		if (!matches) {
			return;
		}

		const position = parseInt(matches[1], 10);
		const prefix = ref.substr(0, ref.length - matches[1].length);
		switch (event.keyCode) {
			case 37:
			case 38:
				// Left/up arrow keys will focus the previous element.
				return this.getNextFocusable_(prefix, position, -1);
			case 39:
			case 40:
				// Right/down arrow keys will focus the next element.
				return this.getNextFocusable_(prefix, position, 1);
			default:
				return;
		}
	}

	/**
	 * Increments the given position, making sure to follow circular rules if
	 * enabled.
	 * @param {number} position
	 * @param {number} increment
	 * @return {number}
	 * @protected
	 */
	increment_(position, increment) {
		const size = this.circularLength_;
		position += increment;
		if (core.isNumber(size)) {
			if (position < 0) {
				position = size - 1;
			} else if (position >= size) {
				position = 0;
			}
		}
		return position;
	}

	/**
	 * Checks if the given element is focusable.
	 * @param {Element} element
	 * @return {boolean}
	 * @protected
	 */
	isFocusable_(element) {
		return element && element.getAttribute('data-unfocusable') === 'true';
	}

	/**
	 * Sets the length of the focusable elements. If a number is passed, the
	 * default focusing behavior will follow a circular pattern, going from the
	 * last to the first element, and vice versa.
	 * @param {?number} circularLength
	 * @chainable
	 */
	setCircularLength(circularLength) {
		this.circularLength_ = circularLength;
		return this;
	}

	/**
	 * Sets a handler function that will be called to decide which element should
	 * be focused according to the key that was pressed. It will receive the key
	 * event and should return one of the following:
	 *   - `true`, if the default behavior should be triggered instead.
	 *   - A string, representing a `ref` to the component element that should be
	 *       focused.
	 *   - The element itself that should be focused.
	 *   - Anything else, if nothing should be focused (skipping default behavior
	 *       too).
	 * @param {function} focusHandler
	 * @chainable
	 */
	setFocusHandler(focusHandler) {
		this.focusHandler_ = focusHandler;
		return this;
	}

	/**
	 * Starts listening to keyboard events and handling element focus.
	 * @chainable
	 */
	start() {
		if (!this.handle_) {
			this.handle_ = this.component_.delegate(
				'keydown',
				this.selector_,
				this.handleKey_
			);
		}
		return this;
	}

	/**
	 * Stops listening to keyboard events and handling element focus.
	 * @chainable
	 */
	stop() {
		if (this.handle_) {
			this.handle_.removeListener();
			this.handle_ = null;
		}
		return this;
	}
}

// Event emitted when a selected element was focused via the keyboard.
KeyboardFocusManager.EVENT_FOCUSED = 'focused';

// The regex used to extract the position from an element's ref.
KeyboardFocusManager.REF_REGEX = /.+-(\d+)$/;

export default KeyboardFocusManager;
