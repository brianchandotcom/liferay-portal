import 'clay-sticker';
import 'clay-select';
import dom from 'metal-dom';
import Soy from 'metal-soy';
import {Config} from 'metal-state';
import templates from './ManageCollaborators.soy';
import PortletBase from 'frontend-js-web/liferay/PortletBase.es';


class ManageCollaborators extends PortletBase {
	attached() {
		this._sharingEntryIdsToDelete = [];
		this._sharingEntryIdsAndPermissions = new Map();
	}

	_closeDialog() {
		const collaboratorsDialog = Liferay.Util.getWindow(this._dialogId);

		if (collaboratorsDialog && collaboratorsDialog.hide) {
			collaboratorsDialog.hide();
		}
	}

	_handleCancelButtonClick() {
		this._closeDialog();
	}

	_handleChangePermission(event) {
		let sharingEntryId = event.target.getAttribute("name");
		let sharingEntryPermissionKey = event.target.value;

		this._sharingEntryIdsAndPermissions.set(sharingEntryId, sharingEntryPermissionKey);
	}

	_handleDeleteCollaborator(event) {
		let collaboratorId = event.delegateTarget.dataset.collaboratorId;
		let sharingEntryId = event.delegateTarget.dataset.sharingentryId;

		let collaboratorElement = dom.toElement('#collaborator' + collaboratorId);

		if (collaboratorElement) {
			collaboratorElement.remove();
			this._sharingEntryIdsToDelete.push(sharingEntryId);
		}
	}

	_handleSaveButtonClick() {
		let permissions = Array.from(this._sharingEntryIdsAndPermissions, (id, key) => id + "," + key );

		this.fetch(
			this.actionUrl,
			{
				sharingEntryIdsToDelete: this._sharingEntryIdsToDelete,
				sharingEntryIdSharingEntryPermissionDisplayActionIdPairs: permissions
			}
		)
		.then(
			(xhr) => {
				this._closeDialog();

				//TODO success message

				//TODO refresh portlet
			}
		)
		.catch(
			(err) => {debugger} //TODO error message
		)
	}
}

ManageCollaborators.STATE = {
	/**
	 * Uri to send the manage collaborators fetch request.
	 * @instance
	 * @memberof ManageCollaborators
	 * @type {String}
	 */
	actionUrl: Config.string().required()

	/**
	 * List of collaborators
	 * @type {Array.<Object>}
	 */
	collaborators: Config.array().required(),

	/**
	 * [dialogId description]
	 * @type {[type]}
	 */
	dialogId: Config.string().required,

	/**
	 * Path to images.
	 * @instance
	 * @memberof ManageCollaborators
	 * @type {String}
	 */
	spritemap: Config.string().required(),
}

// Register component

Soy.register(ManageCollaborators, templates);

export default ManageCollaborators;
