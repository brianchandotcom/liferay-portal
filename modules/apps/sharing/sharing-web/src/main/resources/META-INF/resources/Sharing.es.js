import Soy from 'metal-soy';
import { Config } from 'metal-state';

import PortletBase from 'frontend-js-web/liferay/PortletBase.es';

import templates from './Sharing.soy';

class Sharing extends PortletBase {
	constructor(config, ...args) {
		super(config, ...args);

		this.classNameId = config.classNameId;
		this.classPK = config.classPK;
		this.refererPortletNamespace = config.refererPortletNamespace;
		this.shareDialogId = config.shareDialogId || 'sharingDialog';
	}

	/**
	 * Close the SharingDialog
	 * @review
	 */
	_closeDialog() {
		const shareDialog = Liferay.Util.getWindow(this.shareDialogId);

		if (shareDialog && shareDialog.hide) {
			shareDialog.hide();
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
				classNameId: this.classNameId,
				classPK: this.classPK,
				shareEnabled: this.shareEnabled,
				sharePermissionKey: this.sharePermissionKey,
				userEmailAddress: this._getEmailAdress(this.userEmailAddress)
			}
		).then(
			response => {
				Liferay.Portlet.refresh(`#p_p_id${this.refererPortletNamespace}`);
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
		const value = target.type === 'checkbox' ? target.checked : target.value;
		const name = target.name;

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
	shareActionURL: Config.string().required(),
	shareEnabled: Config.bool().required(),
	sharePermissionKey: Config.string().required()
};

Soy.register(Sharing, templates);

export default Sharing;