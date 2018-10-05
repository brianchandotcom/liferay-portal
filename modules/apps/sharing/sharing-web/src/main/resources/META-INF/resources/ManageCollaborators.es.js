import 'clay-sticker';
import 'clay-select';
import dom from 'metal-dom';
import Soy from 'metal-soy';
import {Config} from 'metal-state';
import templates from './ManageCollaborators.soy';
import PortletBase from 'frontend-js-web/liferay/PortletBase.es';

/**
 * Handles actions to delete or change permissions of the
 * collaborators for a file entry.
 */
class ManageCollaborators extends PortletBase {

	/**
	 * @inheritDoc
	 */
	attached() {
		this._deleteSharingEntryIds = [];
		this._sharingEntryIdsAndPermissions = new Map();
	}

	/**
	 * Closes the dialog.
	 * @protected
	 */
	_closeDialog() {
		const collaboratorsDialog = Liferay.Util.getWindow(this._dialogId);

		if (collaboratorsDialog && collaboratorsDialog.hide) {
			collaboratorsDialog.hide();
		}
	}

	/**
	 * Closes the dialog.
	 * @protected
	 */
	_handleCancelButtonClick() {
		this._closeDialog();
	}

	/**
	 * Gets the new permission key for the selected
	 * collaborator.
	 *
	 * @param {Event} event
	 * @protected
	 */
	_handleChangePermission(event) {
		let sharingEntryId = event.target.getAttribute('name');
		let sharingEntryPermissionKey = event.target.value;

		this._sharingEntryIdsAndPermissions.set(sharingEntryId, sharingEntryPermissionKey);
	}

	/**
	 * Deletes the collaborator.
	 *
	 * @param {Event} event
	 * @protected
	 */
	_handleDeleteCollaborator(event) {
		let collaboratorId = event.delegateTarget.dataset.collaboratorId;
		let sharingEntryId = event.delegateTarget.dataset.sharingentryId;

		let collaboratorElement = dom.toElement('#collaborator' + collaboratorId);

		if (collaboratorElement) {
			collaboratorElement.remove();
			this._deleteSharingEntryIds.push(sharingEntryId);
		}
	}

	/**
	 * Sends a request to the server to update permissions
	 * or delete collaborators.
	 *
	 * @protected
	 */
	_handleSaveButtonClick() {
		let permissions = Array.from(this._sharingEntryIdsAndPermissions, (id, key) => id + ',' + key);

		this.fetch(
			this.actionUrl,
			{
				deleteSharingEntryIds: this._deleteSharingEntryIds,
				sharingEntryIdActionIdPairs: permissions
			}
		)
			.then(
				() => {
					this._loadingResponse = false;
					this._showNotification(this.strings.successMessage, false);
				}
			)
			.catch(
				() => {
					this._loadingResponse = false;
					this._showNotification(this.strings.errorMessage, true);
				}
			);

		this._loadingResponse = true;
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
}

/**
 * State definition.
 * @ignore
 * @static
 * @type {!Object}
 */
ManageCollaborators.STATE = {

	/**
	 * Uri to send the manage collaborators fetch request.
	 * @instance
	 * @memberof ManageCollaborators
	 * @type {String}
	 */
	actionUrl: Config.string().required(),

	/**
	 * List of collaborators
	 * @type {Array.<Object>}
	 */
	collaborators: Config.array().required(),

	/**
	 * Id of the dialog
	 * @type {String}
	 */
	dialogId: Config.string().required,

	/**
	 * Path to images.
	 * @instance
	 * @memberof ManageCollaborators
	 * @type {String}
	 */
	spritemap: Config.string().required(),

	/**
	 * Dialog's messages
	 * @instance
	 * @memberof ManageCollaborators
	 * @type {!string}
	 */
	strings: Config.object().value(
		{
			errorMessage: Liferay.Language.get('an-unexpected-error-occurred-while-updating-permissions'),
			successMessage: Liferay.Language.get('permissions-changed')
		}
	)
};

// Register component

Soy.register(ManageCollaborators, templates);

export default ManageCollaborators;