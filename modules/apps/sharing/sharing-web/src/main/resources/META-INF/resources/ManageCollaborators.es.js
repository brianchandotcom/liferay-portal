/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import 'clay-alert';

import 'clay-button';

import 'clay-select';

import 'clay-sticker';
import {PortletBase} from 'frontend-js-web';
import Soy from 'metal-soy';
import {Config} from 'metal-state';

import templates from './ManageCollaborators.soy';

/**
 * Handles actions to delete or change permissions of the
 * collaborators for a file entry.
 */
class ManageCollaborators extends PortletBase {
	constructor(config, ...args) {
		super(config, ...args);

		this._classNameId = config.classNameId;
		this._classPK = config.classPK;
	}
	/**
	 * @inheritDoc
	 */
	attached() {
		this._deleteSharingEntryIds = [];
		this._sharingEntryIdsAndPermissions = {};
		this._sharingEntryIdsAndExpirationDate = {};
		this._sharingEntryIdsAndShareables = {};

		let tomorrow = new Date();
		tomorrow = tomorrow.setDate(tomorrow.getDate() + 1);

		this._tomorrowDate = new Date(tomorrow).toISOString().split('T')[0];
	}

	/**
	 * Checks if the date is after today.
	 * @param  {String} expirationDate
	 * @protected
	 *
	 * @return {Bool} returns true if the expiration date
	 * is after today, false in other case.
	 */
	_checkExpirationDate(expirationDate) {
		const date = new Date(expirationDate);
		return date >= new Date(this._tomorrowDate);
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

	_convertToPair(sharingEntryObject) {
		const keys = Object.keys(sharingEntryObject);
		const result = [];

		keys.forEach(key => {
			result.push(key + ',' + sharingEntryObject[key]);
		});

		return result;
	}

	/**
	 * Looks if there is a collaborator with an invalid
	 * expiration date.
	 *
	 * @return {Boolean} If a collaborator has an invalid expiration date
	 */
	_findExpirationDateError() {
		const collaborator = this.collaborators.find(
			collaborator =>
				collaborator.sharingEntryExpirationDateError === true
		);

		this.expirationDateError = collaborator != null;

		return this.expirationDateError;
	}

	/**
	 * Finds a collaborator by his id
	 *
	 * @param  {String} collaboratorId The id of a collaborator
	 * @return {Object} Collaborator
	 */
	_getCollaborator(collaboratorId) {
		const collaboratorIdNumber = Number(collaboratorId);

		const collaborator = this.collaborators.find(
			collaborator => collaborator.userId === collaboratorIdNumber
		);

		return collaborator;
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
		const sharingEntryId = event.target.getAttribute('name');
		const sharingEntryPermissionKey = event.target.value;

		this._sharingEntryIdsAndPermissions[
			sharingEntryId
		] = sharingEntryPermissionKey;
	}

	/**
	 * Gets the selected expiration date.
	 *
	 * @param {Event} event
	 * @protected
	 */
	_handleBlurExpirationDate(event) {
		const collaboratorId = event.target.dataset.collaboratorId;
		const sharingEntryExpirationDate = event.target.value;
		const sharingEntryId = event.target.dataset.sharingentryId;

		const collaborator = this._getCollaborator(collaboratorId);
		const dateError = !this._checkExpirationDate(
			sharingEntryExpirationDate
		);

		collaborator.sharingEntryExpirationDateError = dateError;

		if (!dateError) {
			collaborator.sharingEntryExpirationDateTooltip = this._getTooltipDate(
				sharingEntryExpirationDate
			);

			this._sharingEntryIdsAndExpirationDate[
				sharingEntryId
			] = sharingEntryExpirationDate;
		}

		this.collaborators = this.collaborators;

		setTimeout(() => this._findExpirationDateError(), 0);
	}

	/**
	 * Get shareable permissions
	 *
	 * @param {Event} event
	 * @protected
	 */
	_handleChangeShareable(event) {
		const target = event.delegateTarget;

		const collaboratorId = target.dataset.collaboratorId;
		const shareable = target.checked;
		const sharingEntryId = target.dataset.sharingentryId;

		const collaborator = this._getCollaborator(collaboratorId);

		if (collaborator) {
			collaborator.sharingEntryShareable = shareable;

			this.collaborators = this.collaborators;
		}

		this._sharingEntryIdsAndShareables[sharingEntryId] = shareable;
	}

	/**
	 * Deletes the collaborator.
	 *
	 * @param {Event} event
	 * @protected
	 */
	_handleDeleteCollaborator(event) {
		const target = event.delegateTarget;

		const collaboratorId = Number(target.dataset.collaboratorId);
		const sharingEntryId = target.dataset.sharingentryId;

		event.stopPropagation();

		this.collaborators = this.collaborators.filter(
			collaborator => collaborator.userId != collaboratorId
		);

		this._deleteSharingEntryIds.push(sharingEntryId);
	}

	/**
	 * Enable and disable the expiration date field
	 * @param  {Event} event
	 * @protected
	 */
	_handleEnableDisableExpirationDate(event) {
		const target = event.delegateTarget;

		const collaboratorId = target.dataset.collaboratorId;
		const enabled = target.checked;

		const collaborator = this._getCollaborator(collaboratorId);

		if (collaborator) {
			const sharingEntryExpirationDate = enabled
				? this._tomorrowDate
				: '';
			collaborator.enabledExpirationDate = enabled;

			if (!enabled) {
				collaborator.sharingEntryExpirationDateError = false;
				this._findExpirationDateError();
			}

			collaborator.sharingEntryExpirationDate = sharingEntryExpirationDate;
			collaborator.sharingEntryExpirationDateTooltip = this._getTooltipDate(
				sharingEntryExpirationDate
			);

			this._sharingEntryIdsAndExpirationDate[
				collaborator.sharingEntryId
			] = sharingEntryExpirationDate;

			this.collaborators = this.collaborators;
		}
	}

	/**
	 * Expand configuration for sharing permissions and expiration
	 *
	 * @param {Event} event
	 * @protected
	 */
	_handleExpandCollaborator(event) {
		const invalidElements = 'select,option,button';

		if (
			invalidElements.indexOf(event.target.nodeName.toLowerCase()) == -1
		) {
			this.expandedCollaboratorId =
				event.delegateTarget.dataset.collaboratorid;
		}
	}

	/**
	 * Sends a request to the server to update permissions
	 * or delete collaborators.
	 *
	 * @protected
	 */
	_handleSaveButtonClick() {
		if (this._findExpirationDateError()) {
			return;
		}

		this.fetch(this.actionUrl, {
			deleteSharingEntryIds: this._deleteSharingEntryIds,
			sharingEntryIdActionIdPairs: this._convertToPair(
				this._sharingEntryIdsAndPermissions
			),
			sharingEntryIdExpirationDatePairs: this._convertToPair(
				this._sharingEntryIdsAndExpirationDate
			),
			sharingEntryIdShareablePairs: this._convertToPair(
				this._sharingEntryIdsAndShareables
			)
		})
			.then(response => {
				this.submitting = false;

				const jsonResponse = response.json();

				return response.ok
					? jsonResponse
					: jsonResponse.then(json => {
							const error = new Error(
								json.errorMessage || response.statusText
							);
							throw Object.assign(error, {response});
					  });
			})
			.then(json => {
				parent.Liferay.fire('sharing:changed', {
					classNameId: this._classNameId,
					classPK: this._classPK
				});
				this._loadingResponse = false;
				this._showNotification(json.successMessage);
			})
			.catch(error => {
				this._loadingResponse = false;
				this._showNotification(error.message, true);
			});

		this._loadingResponse = true;
	}

	/**
	 * Get the formatted date that has to be shown
	 * in the tooltip.
	 *
	 * @param  {Date} expirationDate [description]
	 * @return {String}                [description]
	 */
	_getTooltipDate(expirationDate) {
		return Liferay.Util.sub(
			Liferay.Language.get('until-x'),
			new Date(expirationDate).toLocaleDateString(
				Liferay.ThemeDisplay.getBCP47LanguageId()
			)
		);
	}

	/**
	 * Cleans the error.
	 * @protected
	 */
	_removeExpirationDateError() {
		this.expirationDateError = false;
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
				attached: this._closeDialog.bind(this)
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
	collaborators: Config.arrayOf(
		Config.shapeOf({
			fullName: Config.string(),
			sharingEntryExpirationDate: Config.string(),
			sharingEntryExpirationDateTooltip: Config.string(),
			sharingEntryId: Config.string(),
			sharingEntryPermissionDisplaySelectOptions: Config.arrayOf(
				Config.shapeOf({
					label: Config.string(),
					selected: Config.bool(),
					value: Config.string()
				})
			),
			sharingEntryShareable: Config.bool(),
			userId: Config.number()
		})
	).required(),

	/**
	 * Id of the dialog
	 * @type {String}
	 */
	dialogId: Config.string().required,

	/**
	 * Id of the expanded collaborator
	 * @memberof ManageCollaborators
	 * @type {String}
	 */
	expandedCollaboratorId: Config.string(),

	/**
	 * Path to images.
	 * @instance
	 * @memberof ManageCollaborators
	 * @type {String}
	 */
	spritemap: Config.string().required()
};

// Register component

Soy.register(ManageCollaborators, templates);

export default ManageCollaborators;
