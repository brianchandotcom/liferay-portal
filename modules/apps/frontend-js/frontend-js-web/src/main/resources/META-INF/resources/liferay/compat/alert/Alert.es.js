'use strict';

import { core } from 'metal';
import dom from 'metal-dom';
import Anim from 'metal-anim';
import Component from 'metal-component';
import { EventHandler } from 'metal-events';
import Soy from 'metal-soy';

import templates from './Alert.soy';

/**
 * Alert component.
 */
class Alert extends Component {
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
		clearTimeout(this.delay_);
	}

	/**
	 * Closes the alert, disposing it once the animation ends.
	 */
	close() {
		dom.once(this.element, 'animationend', this.dispose.bind(this));
		dom.once(this.element, 'transitionend', this.dispose.bind(this));
		this.eventHandler_.removeAllListeners();
		this.syncVisible(false);
	}

	/**
	 * Handles document click in order to hide the alert.
	 * @param {!Event} event
	 * @protected
	 */
	handleDocClick_(event) {
		if (!this.element.contains(event.target)) {
			this.hide();
		}
	}

	/**
	 * Hide the alert.
	 */
	hide() {
		this.visible = false;
	}

	/**
	 * Hides the alert completely (with display "none"). This is called after the
	 * hiding animation is done.
	 * @protected
	 */
	hideCompletely_() {
		if (!this.isDisposed() && !this.visible) {
			super.syncVisible(false);
		}
 	}

	/**
	 * Toggles the visibility of the alert.
	 */
	toggle() {
		this.visible = !this.visible;
	}

	/**
	 * Show the alert.
	 */
	show() {
		this.visible = true;
	}

	/**
	 * Synchronization logic for `dismissible` state. If the alert is dismissible,
	 * a click event handler will be attached.
	 * @param {boolean} dismissible
	 */
	syncDismissible(dismissible) {
		if (dismissible) {
			this.eventHandler_.add(dom.on(document, 'click', this.handleDocClick_.bind(this)));
		} else {
			this.eventHandler_.removeAllListeners();
		}
	}

	/**
	 * Synchronization logic for `hideDelay` state. When hideDelay has a valid value,
	 * the alert will hide in the time (ms) defined.
	 * @param {?number} hideDelay
	 */
	syncHideDelay(hideDelay) {
		if (core.isNumber(hideDelay) && this.visible) {
			clearTimeout(this.delay_);
			this.delay_ = setTimeout(this.hide.bind(this), hideDelay);
		}
	}

	/**
	 * Synchronization logic for `visible` state. Adds and removes show/hide classes
	 * based on the visibility of the alert.
	 * @param {boolean} visible
	 */
	syncVisible(visible, prevVisible) {
		var shouldAsync = false;
		if (!visible) {
			dom.once(this.element, 'animationend', this.hideCompletely_.bind(this));
			dom.once(this.element, 'transitionend', this.hideCompletely_.bind(this));
		} else if (core.isDef(prevVisible)) {
			shouldAsync = true;
			super.syncVisible(true);
		}

		var showOrHide = () => {
			if (this.isDisposed()) {
				return;
			}

			dom.removeClasses(this.element, this.animClasses[visible ? 'hide' : 'show']);
			dom.addClasses(this.element, this.animClasses[visible ? 'show' : 'hide']);

			// Some browsers do not fire transitionend events when running in background
			// tab, see https://bugzilla.mozilla.org/show_bug.cgi?id=683696.
			Anim.emulateEnd(this.element);

			if (visible && core.isNumber(this.hideDelay)) {
				this.syncHideDelay(this.hideDelay);
			}
		};

		if (shouldAsync) {
			// We need to start the animation asynchronously because of the possible
			// previous call to `super.syncVisible`, which doesn't allow the show
			// animation to work as expected.
			setTimeout(showOrHide, 0);
		} else {
			showOrHide();
		}
	}
}
Soy.register(Alert, templates);

/**
 * Alert state definition.
 * @type {!Object}
 * @static
 */
Alert.STATE = {
	/**
	 * The CSS classes that should be added to the alert when being shown/hidden.
	 * @type {!Object}
	 */
	animClasses: {
		validator: core.isObject,
		value: {
			show: 'fade in',
			hide: 'fade'
		}
	},

	/**
	 * The body content of the alert.
	 * @type {html|string}
	 */
	body: {
	},

	/**
	 * The content of close button of the alert.
	 * @type {html|string}
	 */
	closeButtonHtml: {
	},

	/**
	 * Flag indicating if the alert should be dismissable (closeable).
	 * @type {boolean}
	 * @default true
	 */
	dismissible: {
		validator: core.isBoolean,
		value: true
	},

	/**
	 * The CSS classes that should be added to the alert.
	 * @type {string}
	 * @default 'alert-success'
	 */
	elementClasses: {
		value: 'alert-success'
	},

	/**
	 * Delay hiding the alert (ms).
	 * @type {?number}
	 */
	hideDelay: {
		validator: core.isNumber
	},

	/**
	 * Flag indicating if the alert is visible or not.
	 * @type {boolean}
	 * @default false
	 */
	visible: {
		validator: core.isBoolean,
		value: false
	}
};

export { Alert };
export default Alert;