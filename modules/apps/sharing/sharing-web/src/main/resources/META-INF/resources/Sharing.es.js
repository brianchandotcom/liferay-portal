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
		this._sharingDialogId = config.sharingDialogId || 'sharingDialogId';
	}

	/**
	 * Close the SharingDialog
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
	 * @review
	 */
	_getEmailAdress(emailAddress = '') {
		return emailAddress
			.toLowerCase()
			.split(/[\s,;]+/);
	}

	/**
	 * Save the share permisions
	 * @param {!Event} event
	 * @private
	 * @review
	 */
	_handleSubmit(event) {
		event.preventDefault();

		this.fetch(
			this.shareActionURL,
			{
				classNameId: this._classNameId,
				classPK: this._classPK,
				shareable: this.shareable,
				sharingEntryPermissionDisplayActionId: this.sharingEntryPermissionDisplayActionId,
				userEmailAddress: this._getEmailAdress(this.userEmailAddress)
			}
		).then(
			response => {
				parent.Liferay.Portlet.refresh(`#p_p_id${this._refererPortletNamespace}`);
				this._closeDialog();
			}
		);
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
};

Soy.register(Sharing, templates);

export default Sharing;