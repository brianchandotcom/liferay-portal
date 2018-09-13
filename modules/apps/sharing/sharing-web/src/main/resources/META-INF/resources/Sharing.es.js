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
			.split(/[\s,;]+/);
	}

	/**
	 * Event handler executed on userEmailAddress blur
	 * @param {!Event} event
	 * @private
	 */
	_handleValidateEmail(event) {
		const value = event.delegateTarget.value;

		this._validateRequiredEmail(value);
	}

	/**
	 * Validates if is email isn't emtpy
	 * @param {string} emails value
	 * @return {Boolean} value isn't emtpy
	 * @private
	 * @review
	 */
	_validateRequiredEmail(value) {
		const valid = value && value.trim
			? !!value.trim()
			: !!value
		;

		this.emailErrorMessage = valid
			? ''
			: Liferay.Language.get('this-field-is-required')
		;

		return valid;
	}

	/**
	 * Save the share permisions
	 * @param {!Event} event
	 * @private
	 * @review
	 */
	_handleSubmit(event) {
		event.preventDefault();

		if (this.submitting || !this._validateRequiredEmail(this.userEmailAddress)) return;

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
					parent.Liferay.Portlet.refresh(`#p_p_id${this._refererPortletNamespace}`);
					this._closeDialog();

					return response;
				}

				return response.json();
			})
			.then(response => {
				if (response.error) {
					throw response.error;
				}
			})
			.catch(err => {
				this.submitting = false;
				this.errorMessage = err.message;
			});
	}

	/**
	 * Sync the inputs with the state
	 * @param {!Event} event
	 * @private
	 * @review
	 */
	_handleInputChange(event) {
		const target = event.target;

		const name = target.name;
		const value = target.type === 'checkbox' ? target.checked : target.value;

		this[name] = value;
	}
}

/**
 * State definition.
 * @review
 * @static
 * @type {!Object}
 */
Sharing.STATE = {
	shareable: Config.bool().value(true),
	shareActionURL: Config.string().required(),
	sharingEntryPermissionDisplayActionId: Config.string().required()
	errorMessage: Config.string().value(''),
	emailErrorMessage: Config.string().value(''),
	submitting: Config.bool().value(false),
};

Soy.register(Sharing, templates);

export default Sharing;