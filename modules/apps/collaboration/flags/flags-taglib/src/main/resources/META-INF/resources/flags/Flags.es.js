import { Config } from 'metal-state';
import dom from 'metal-dom';
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
	 * Closes the dialog to flag the page.
	 */
	_closeReportDialog() {
		this._reportDialogOpen = false;
		this._showConfirmationMessage = false;
		this._showErrorMessage = false;
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
	_handleOnReasonChange(event) {
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
	_handleOnSubmitForm(event) {
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
	_handleReportButtonClick() {
		let input = this.one('input[type="submit"]');

		input.click();
	}

	/**
	 * Opens a dialog where the user can flag the page.
	 */
	_openReportDialog() {
		this._reportDialogOpen = true;
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
	companyName: Config.string().required(),

	/**
	 * CSS classes.
	 *
	 * @instance
	 * @memberof Flags
	 * @type {String}
	 */
	cssClass: Config.string().required(),

	/**
	 * Portlet's data.
	 * @instance
	 * @memberof Flags
	 * @type {!Object}
	 */
	data: Config.object().required(),

	/**
	 * Whether the form to flag is enabled
	 * or not.
	 *
	 * @instance
	 * @memberof Flags
	 * @type {Boolean}
	 */
	enabled: Config.bool().required(),

	/**
	 * Whether the user is able to flag the page.
	 * @instance
	 * @memberof Flags
	 * @type {!Boolean}
	 */
	flagsEnabled: Config.bool().required(),

	/**
	 * Component id.
	 *
	 * @instance
	 * @memberof Flags
	 * @type {String}
	 */
	id: Config.string().required(),

	/**
	 * Whether to show message text as a label next
	 * to the flag icon or as a tooltip.
	 *
	 * @instance
	 * @memberof Flags
	 * @type {Boolean}
	 */
	label: Config.bool().required(),

	/**
	 * Text to display next to the flag icon.
	 *
	 * @instance
	 * @memberof Flags
	 * @type {String}
	 */
	message: Config.string().required(),

	/**
	 * Path to Terms of Use.
	 *
	 * @instance
	 * @memberof Flags
	 * @type {String}
	 */
	pathTermsOfUse: Config.string().required(),

	/**
	 * Path to images.
	 *
	 * @instance
	 * @memberof Flags
	 * @type {String}
	 */
	pathThemeImages: Config.string().required(),

	/**
	 * Portlet's namespace
	 *
	 * @instance
	 * @memberof Flags
	 * @type {String}
	 */
	portletNamespace: Config.string().required(),

	/**
	 * List of possible reasons to flag
	 * a content.
	 *
	 * @instance
	 * @memberof Flags
	 * @type {List}
	 */
	reasons: Config.array().required(),

	/**
	 * Email of the user who reports
	 * the flag.
	 *
	 * @instance
	 * @memberof Flags
	 * @type {String}
	 */
	reporterEmailAddress: Config.string(),

	/**
	 * Wheter the user is signed in or not.
	 *
	 * @instance
	 * @memberof Flags
	 * @type {Boolean}
	 */
	signedIn: Config.bool().required(),

	/**
	 * Title to show in the Modal.
	 *
	 * @instance
	 * @memberof Flags
	 * @type {String}
	 */
	title: Config.string().required(),

	/**
	 * Uri of the page that will be opened
	 * in the dialog.
	 *
	 * @instance
	 * @memberof Flags
	 * @type {String}
	 */
	uri: Config.string().required()
};

// Register component
Soy.register(Flags, templates);

export default Flags;