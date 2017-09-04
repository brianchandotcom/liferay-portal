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
	created() {
		this.eventHandler_ = new EventHandler();
	}

	/**
	 * @inheritDoc
	 */
	attached() {
		this.reportDialogOpen = false;
		this.rootNode = this.one('.taglib-flags');
		this.showConfirmationMessage_ = false;
		this.showErrorMessage_ = false;
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
		this.reportDialogOpen = false;
		this.showConfirmationMessage_ = false;
		this.showErrorMessage_ = false;
	}

	/**
	 * Opens a dialog where the user can flag the page.
	 */
	openReportDialog() {
		this.reportDialogOpen = true;
	}

	/**
	 * Gets the reason selected by the user.
	 *
	 * @return {String} reason
	 */
	getReason_() {
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
	onReasonChange_(event) {
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
	onSubmitForm_(event) {
		event.preventDefault();

		let form = this.one('form[name="' + this.ns('flagsForm') +'"]');

		this.data[this.ns('reason')] = this.getReason_();
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
				this.showConfirmationMessage_ = true;
			}
		})
		.catch(() => {
			this.showErrorMessage_ = true;
		});
	}

	/**
	 * Forms the submit.
	 * @protected
	 */
	reportButton_() {
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
	 * Portlet's data.
	 * @instance
	 * @memberof Flags
	 * @type {!Object}
	 */
	data: {
		validator: core.isObject
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
	 * Uri of the page that will be opened
	 * in the dialog.
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