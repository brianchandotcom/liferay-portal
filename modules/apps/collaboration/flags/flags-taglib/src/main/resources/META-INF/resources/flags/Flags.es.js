import { core } from 'metal';
import Component from 'metal-component';
import dom from 'metal-dom';
import { EventHandler } from 'metal-events';
import Modal from 'metal-modal';

import Soy from 'metal-soy';

import templates from './Flags.soy';

import PortletBase from 'frontend-js-web/liferay/PortletBase.es';

/**
 * Flags
 *
 * It opens a dialog where the user can flag the page.
 *
 * @abstract
 * @extends {PortletBase}
 */
class Flags extends PortletBase {
	/**
	 * @inheritDoc
	 */
	attached() {
		this._reportDialogOpen = false;
		this.namespace = this.portletNamespace;
		this.rootNode = this.one('.taglib-flags');
		this._showConfirmationMessage = false;
		this._showErrorMessage = false;
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
	 * Closes the dialog to flag the page.
	 */
	closeReportDialog() {
		this._reportDialogOpen = false;
		this._showConfirmationMessage = false;
		this._showErrorMessage = false;
	}

	/**
	 * Opens a dialog where the user can flag the page.
	 */
	openReportDialog() {
		this._reportDialogOpen = true;
	}

	/**
	 * Gets the reason selected by the user.
	 *
	 * @return {String} reason
	 */
	_getReason() {
		let reason = this.one('#reason').value;

		if (reason === 'other') {
			let otherReason = this.one('#otherReason').value;

			reason = otherReason || Liferay.Language.get('no-reason-specified');
		}

		return reason;
	}

	/**
	 * Checks the reason selected by the user, and allows
	 * to introduce a specific reasons if necessary.
	 *
	 * @param {Event} event
	 * @protected
	 */
	_onReasonChange(event) {
		let reason = event.delegateTarget.value;

		let otherReasonContainer = this.one('#otherReasonContainer');

		if (reason === 'other') {
			dom.removeClasses(otherReasonContainer, 'hide');
		}
		else {
			dom.addClasses(otherReasonContainer, 'hide');
		}
	}

	/**
	 * Makes an ajax request to submit the data.
	 *
	 * @param {Event} event
	 * @protected
	 */
	_onSubmitForm(event) {
		event.preventDefault();

		let form = this.one('form[name="' + this.ns('flagsForm') +'"]');

		this.data[this.ns('reason')] = this._getReason();
		this.data[this.ns('reporterEmailAddress')] = this.one('#reporterEmailAddress').value;

		let formData = new FormData();

		for (let name in this.data) {
			formData.append(name, this.data[name]);
		}

		fetch(this.uri, {
			body: formData,
			credentials: 'include',
		 	method: 'post'
		})
		.then((xhr) => {
			if (xhr.status === Liferay.STATUS_CODE.OK) {
				this._showConfirmationMessage = true;
			}
		})
		.catch(() => {
			this._showErrorMessage = true;
		});
	}

	/**
	 * Forms the submit.
	 * @internal
	 * @protected
	 */
	_reportButton() {
		let input = this.one('input[type="submit"]');

		input.click();
	}
};

/**
 * State definition.
 * @ignore
 * @static
 * @type {!Object}
 */
Flags.STATE = {
	/**
	 * Company name.
	 *
	 * @instance
	 * @memberof Flags
	 * @type {String}
	 */
	companyName: {
		validator: core.isString
	},

	/**
	 * CSS classes.
	 *
	 * @instance
	 * @memberof Flags
	 * @type {String}
	 */
	cssClass: {
		validator: core.isString
	},

	/**
	 * Portlet's data.
	 * @instance
	 * @memberof Flags
	 * @type {!Object}
	 */
	data: {
		validator: core.isObject
	},

	/**
	 * Whether the form to flag is enabled
	 * or not.
	 *
	 * @instance
	 * @memberof Flags
	 * @type {Boolean}
	 */
	enabled: {
		validator: core.isBoolean
	},

	/**
	 * Whether the user is able to flag the page.
	 * @instance
	 * @memberof Flags
	 * @type {!Boolean}
	 */
	flagsEnabled: {
		validator: core.isBoolean
	},

	/**
	 * Component id.
	 *
	 * @instance
	 * @memberof Flags
	 * @type {String}
	 */
	id: {
		validator: core.isString
	},

	/**
	 * Whether to show message text as a label next
	 * to the flag icon or as a tooltip.
	 *
	 * @instance
	 * @memberof Flags
	 * @type {Boolean}
	 */
	label: {
		validator: core.isBoolean
	},

	/**
	 * Text to display next to the flag icon.
	 *
	 * @instance
	 * @memberof Flags
	 * @type {String}
	 */
	message: {
		validator: core.isString
	},

	/**
	 * Path to Terms of Use.
	 *
	 * @instance
	 * @memberof Flags
	 * @type {String}
	 */
	pathTermsOfUse: {
		validator: core.isString
	},

	/**
	 * Path to images.
	 *
	 * @instance
	 * @memberof Flags
	 * @type {String}
	 */
	pathThemeImages: {
		validator: core.isString
	}

	/**
	 * Portlet's namespace
	 *
	 * @instance
	 * @memberof Flags
	 * @type {String}
	 */
	portletNamespace: {
		validator: core.isString
	},

	/**
	 * List of possible reasons to flag
	 * a content.
	 *
	 * @instance
	 * @memberof Flags
	 * @type {List}
	 */
	reasons: {
		validator: core.isObject //CORE.LIST??
	},

	/**
	 * Email of the user who reports
	 * the flag.
	 *
	 * @instance
	 * @memberof Flags
	 * @type {String}
	 */
	reporterEmailAddress: {
		validator: core.isString
	},

	/**
	 * Wheter the user is signed in or not.
	 *
	 * @instance
	 * @memberof Flags
	 * @type {Boolean}
	 */
	signedIn: {
		validator: core.isBoolean
	},

	/**
	 * Title to show in the Modal.
	 *
	 * @instance
	 * @memberof Flags
	 * @type {String}
	 */
	title: {
		validator: core.isString
	}

	/**
	 * Uri of the page that will be opened
	 * in the dialog.
	 *
	 * @instance
	 * @memberof Flags
	 * @type {String}
	 */
	uri: {
		validator: core.isString
	}
};

// Register component
Soy.register(Flags, templates);

export default Flags;