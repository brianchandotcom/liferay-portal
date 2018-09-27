import Soy from 'metal-soy';
import {Config} from 'metal-state';
import PortletBase from 'frontend-js-web/liferay/PortletBase.es';

import templates from './Sharing.soy';

class Sharing extends PortletBase {
	constructor(config, ...args) {
		super(config, ...args);

		this._classNameId = config.classNameId;
		this._classPK = config.classPK;
		this._refererPortletNamespace = config.refererPortletNamespace;
		this._sharingDialogId = config.sharingDialogId;
	}

	/**
	 * Close the SharingDialog
	 * @private
	 * @review
	 */
	_closeDialog() {
		const sharingDialog = Liferay.Util.getWindow(this._sharingDialogId);

		if (sharingDialog && sharingDialog.hide) {
			sharingDialog.hide();
		}
	}

	/**
	 * Returns af tokens that should be emails.
	 * Does not validate emails to see if they are well formed.
	 * @param {string} emailAddress a single paramater which is one or
	 * more emails separated by comma, semicolon, or whitespace (space, tab, or newline).
	 * @return {Array<String>} List of lowercase string that should be emails.
	 * @private
	 * @review
	 */
	_getEmailAdress(emailAddress = '') {
		return emailAddress
			.toLowerCase()
			.split(/[\s,;]+/)
			.filter(email => !!email);
	}

	/**
	 * Sync the inputs with the state
	 * @param {!Event} event
	 * @private
	 * @review
	 */
	_handleInputChange(event) {
		const target = event.target;
		const value = target.type === 'checkbox' ? target.checked : target.value;
		const name = target.name;

		this[name] = value;
	}

	/**
	 * Save the share permisions
	 * @param {!Event} event
	 * @private
	 * @review
	 */
	_handleSubmit(event) {
		event.preventDefault();

		if (this.submitting || !this._validateEmail(this.userEmailAddress)) return;

		this.submitting = true;

		this.fetch(
			this.shareActionURL,
			{
				classNameId: this._classNameId,
				classPK: this._classPK,
				shareable: this.shareable,
				sharingEntryPermissionDisplayActionId: this.sharingEntryPermissionDisplayActionId,
				userEmailAddress: this._getEmailAdress(this.userEmailAddress)
			}
		)
			.then(response => {
				this.submitting = false;

				if (response.ok) {
					return response.json();
				}

				return response.json().then(json => {
					const error = new Error(json.errorMessage || response.statusText);
					throw Object.assign(error, { response });
				});
			})
			.then(json => {
				parent.Liferay.Portlet.refresh(`#p_p_id${this._refererPortletNamespace}`);

				this._showNotification(json.successMessage);
			})
			.catch(error => {
				this.submitting = false;

				this._showNotification(error.message, true);
			});
	}

	/**
	 * Event handler executed on userEmailAddress blur
	 * @param {!Event} event
	 * @private
	 */
	_handleValidateEmail(event) {
		const value = event.delegateTarget.value;

		this._validateEmail(value);
	}

	/**
	 * Show notification in the opener and closes dialog
	 * after is rendered
	 * @param {string} message message for notification
	 * @param {boolean} error Flag indicating if is an error or not
	 * @private
	 * @review
	 */
	_showNotification(message, error) {
		const parentOpenToast = Liferay.Util.getOpener().Liferay.Util.openToast;

		const openToastParams = {
			events: {
				'attached': this._closeDialog.bind(this)
			},
			message
		};

		if (error) {
			openToastParams.title = Liferay.Language.get('error');
			openToastParams.type = 'danger';
		}

		parentOpenToast(openToastParams);
	}

	/**
	 * Validates if is email isn't emtpy
	 * @param {string} emails value
	 * @return {Boolean} value isn't emtpy
	 * @private
	 * @review
	 */
	_validateEmail(value) {
		const empty = value && value.trim
			? !value.trim()
			: !value
		;

		this.emailErrorMessage = empty
			? Liferay.Language.get('this-field-is-required')
			: ''
		;

		if (empty) return false;

		const emailRegex = /.+@.+\..+/i;
		const valid = this._getEmailAdress(value).every(
			email => emailRegex.test(email)
		);

		this.emailErrorMessage = valid
			? ''
			: Liferay.Language.get('please-enter-a-valid-email-address')
		;

		return valid;
	}
}

/**
 * State definition.
 * @review
 * @static
 * @type {!Object}
 */
Sharing.STATE = {
	emailErrorMessage: Config.string().value(''),
	shareable: Config.bool().value(true),
	shareActionURL: Config.string().required(),
	sharingEntryPermissionDisplayActionId: Config.string().required(),
	submitting: Config.bool().value(false),
};

Soy.register(Sharing, templates);

export default Sharing;