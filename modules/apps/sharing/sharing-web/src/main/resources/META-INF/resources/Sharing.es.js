import 'clay-multi-select';
import 'clay-sticker';
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
		this._userEmailAddresses = [];
	}

	/**
	 * @inheritDoc
	 */
	attached(...args) {
		super.attached(...args);

		this.refs.multiSelect.dataSource = query => this.fetch(
			this.sharingUserAutocompleteURL,
			{
				query
			}
		).then(
			res => res.json()
		).then(
			users => users.map(
				({emailAddress, fullName, portraitURL}) => (
					{
						emailAddress,
						fullName,
						label: `${fullName} (${emailAddress})`,
						portraitURL,
						value: emailAddress
					}
				)
			)
		);
	}

	/**
	 * Disables filtering of results
	 * @private
	 * @review
	 */
	_handleDataChange(e) {
		e.preventDefault();

		if (e.data) {
			e.target.filteredItems = e.data.map((element, index) => ({
				data: element,
				index,
				matches: [],
				score: 0,
				value: element
			}));
		}
		else {
			e.target.filteredItems = [];
		}
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

	/**
	 * Save the share permisions
	 * @param {!Event} event
	 * @private
	 * @review
	 */
	_handleSubmit(event) {
		event.preventDefault();

		if (!this.submitting && this._validateEmails()) {
			this.submitting = true;

			this.fetch(
				this.shareActionURL,
				{
					classNameId: this._classNameId,
					classPK: this._classPK,
					shareable: this.shareable,
					sharingEntryPermissionDisplayActionId: this.sharingEntryPermissionDisplayActionId,
					userEmailAddress: this._userEmailAddresses.map(({value}) => value).join(',')
				}
			)
				.then(
					response => {
						this.submitting = false;

						const jsonResponse = response.json();

						return response.ok ?
							jsonResponse :
							jsonResponse.then(
								json => {
									const error = new Error(json.errorMessage || response.statusText);
									throw Object.assign(error, {response});
								}
							)
						;
					}
				)
				.then(
					json => {
						parent.Liferay.Portlet.refresh(`#p_p_id${this._refererPortletNamespace}`);

						this._showNotification(json.successMessage);
					}
				)
				.catch(
					error => {
						this.submitting = false;

						this._showNotification(error.message, true);
					}
				);
		}
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
	 * Validates if there are email addresses and all are valid
	 * @return {Boolean} value isn't emtpy
	 * @private
	 * @review
	 */
	_validateEmails() {
		const empty = this._userEmailAddresses.length === 0;

		this.emailErrorMessage = empty ?
			Liferay.Language.get('this-field-is-required') :
			''
		;

		let valid = false;

		if (!empty) {
			const emailRegex = /.+@.+\..+/i;

			valid = this._userEmailAddresses.every(
				({value}) => emailRegex.test(value)
			);

			this.emailErrorMessage = valid ?
				'' :
				Liferay.Language.get('please-enter-a-valid-email-address')
			;
		}

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
	sharingUserAutocompleteURL: Config.string().required(),
	spritemap: Config.string().required(),
	submitting: Config.bool().value(false)
};

Soy.register(Sharing, templates);

export default Sharing;