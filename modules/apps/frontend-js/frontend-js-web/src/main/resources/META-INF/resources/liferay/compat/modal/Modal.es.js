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

import core from 'metal';
import Component from 'metal-component';
import dom from 'metal-dom';
import {EventHandler} from 'metal-events';
import Soy from 'metal-soy';

import templates from './Modal.soy';

const KEY_CODE_ESC = 27;

/**
 * Modal component.
 */

class Modal extends Component {
	/**
	 * @inheritDoc
	 */

	attached() {
		this._autoFocus(this.autoFocus);

		this.addListener('hide', this._defaultHideFn, true);
	}

	/**
	 * @inheritDoc
	 */

	created() {
		this._eventHandler = new EventHandler();
	}

	/**
	 * @inheritDoc
	 */

	detached() {
		super.detached();

		this._eventHandler.removeAllListeners();
	}

	/**
	 * @inheritDoc
	 */

	disposeInternal() {
		dom.exitDocument(this.overlayElement);

		this._unrestrictFocus();

		super.disposeInternal();
	}

	/**
	 * Emits a hide event.
	 */

	hide() {
		this.emit('hide');
	}

	/**
	 * Shows the modal, setting its `visible` state key to true.
	 */

	show() {
		this.visible = true;
	}

	/**
	 * Syncs the component according to the value of the `hideOnEscape` state key.
	 * @param {boolean} hideOnEscape
	 */

	syncHideOnEscape(hideOnEscape) {
		if (hideOnEscape) {
			this._eventHandler.add(
				dom.on(document, 'keyup', this._handleKeyup.bind(this))
			);
		} else {
			this._eventHandler.removeAllListeners();
		}
	}

	/**
	 * Syncs the component according to the value of the `overlay` state key.
	 * @param {boolean} overlay
	 */

	syncOverlay(overlay) {
		const willShowOverlay = overlay && this.visible;

		dom[willShowOverlay ? 'enterDocument' : 'exitDocument'](
			this.overlayElement
		);
	}

	/**
	 * Syncs the component according to the value of the `visible` state key.
	 */

	syncVisible() {
		this.syncOverlay(this.overlay);

		if (this.visible) {
			this._lastFocusedElement =
				this._lastFocusedElement || document.activeElement;

			this._autoFocus(this.autoFocus);
			this._restrictFocus();
		} else {
			this._unrestrictFocus();
			this._shiftFocusBack();
		}
	}

	/**
	 * Automatically focuses the element specified by the given selector.
	 * @param {boolean|string} autoFocusSelector The selector, or false if no
	 *   element should be automatically focused.
	 * @protected
	 */

	_autoFocus(autoFocusSelector) {
		if (this.inDocument && this.visible && autoFocusSelector) {
			const element = this.element.querySelector(autoFocusSelector);

			if (element) {
				element.focus();
			}
		}
	}

	/**
	 * Run only if no listener calls event.preventDefault().
	 */

	_defaultHideFn() {
		this.visible = false;
	}

	/**
	 * Handles a `focus` event on the document. If the focused element is
	 * outside the modal and an overlay is being used, focuses the modal back.
	 * @param {!Event} event
	 * @protected
	 */

	_handleDocumentFocus(event) {
		if (this.overlay && !this.element.contains(event.target)) {
			this._autoFocus('.modal-dialog');
		}
	}

	/**
	 * Handles document click in order to close the alert.
	 * @param {!Event} event
	 * @protected
	 */

	_handleKeyup(event) {
		if (event.keyCode === KEY_CODE_ESC) {
			this.hide();
		}
	}

	/**
	 * Restricts focus to the modal while it's visible.
	 * @protected
	 */

	_restrictFocus() {
		if (!this._restrictFocusHandle) {
			this._restrictFocusHandle = dom.on(
				document,
				'focus',
				this._handleDocumentFocus.bind(this),
				true
			);
		}
	}

	/**
	 * Shifts the focus back to the last element that had been focused before the
	 * modal was shown.
	 * @protected
	 */

	_shiftFocusBack() {
		if (this._lastFocusedElement) {
			this._lastFocusedElement.focus();
			this._lastFocusedElement = null;
		}
	}

	/**
	 * Removes the handler that restricts focus to elements inside the modal.
	 * @protected
	 */

	_unrestrictFocus() {
		if (this._restrictFocusHandle) {
			this._restrictFocusHandle.removeListener();
			this._restrictFocusHandle = null;
		}
	}

	/**
	 * Defines the default value for the `overlayElement` state key.
	 * @protected
	 * @return {Node}
	 */

	_valueOverlayElementFn() {
		return dom.buildFragment('<div class="modal-backdrop fade show"></div>')
			.firstChild;
	}
}

Modal.STATE = {
	/**
	 * A selector for the element that should be automatically focused when the modal
	 * becomes visible, or `false` if no auto focus should happen. Defaults to the
	 * modal's close button.
	 * @type {boolean|string}
	 */

	autoFocus: {
		validator: val => val === false || core.isString(val),
		value: '.close'
	},

	/**
	 * Content to be placed inside modal body. Can be either an html string or
	 * a function that calls incremental-dom to render the body.
	 * @type {string|function()}
	 */

	body: {},

	/**
	 * The id used by the body element.
	 * @type {string}
	 */

	bodyId: {
		valueFn: () => 'modal-body-' + core.getUid()
	},

	/**
	 * Classes that will be applied to the modal-dialog element.
	 * @type {string}
	 */

	dialogClasses: {
		validator: core.isString
	},

	/**
	 * Content to be placed inside modal footer. Can be either an html string or
	 * a function that calls incremental-dom to render the footer.
	 * @type {string|function()}
	 */

	footer: {},

	/**
	 * Content to be placed inside modal header. Can be either an html string or
	 * a function that calls incremental-dom to render the header.
	 * @type {string|function()}
	 */

	header: {},

	/**
	 * The id used by the header element.
	 * @type {string}
	 */

	headerId: {
		valueFn: () => 'modal-header-' + core.getUid()
	},

	/**
	 * Whether modal should hide on esc.
	 * @type {boolean}
	 * @default true
	 */

	hideOnEscape: {
		validator: core.isBoolean,
		value: true
	},

	/**
	 * Flag indicating if the default "x" button for closing the modal should be
	 * added or not.
	 * @type {boolean}
	 * @default false
	 */

	noCloseButton: {
		value: false
	},

	/**
	 * Whether overlay should be visible when modal is visible.
	 * @type {boolean}
	 * @default true
	 */

	overlay: {
		validator: core.isBoolean,
		value: true
	},

	/**
	 * Element to be used as overlay.
	 * @type {Element}
	 */

	overlayElement: {
		valueFn: '_valueOverlayElementFn',
		writeOnce: true
	},

	/**
	 * The ARIA role to be used for this modal.
	 * @type {string}
	 * @default 'dialog'
	 */

	role: {
		validator: core.isString,
		value: 'dialog'
	}
};

Soy.register(Modal, templates);

export {Modal};
export default Modal;
