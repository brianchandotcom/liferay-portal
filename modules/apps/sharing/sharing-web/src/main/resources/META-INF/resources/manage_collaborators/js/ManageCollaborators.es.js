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

import ClayAlert from '@clayui/alert';
import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import {ClayCheckbox, ClayInput, ClaySelectWithOption} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import ClaySticker from '@clayui/sticker';
import classNames from 'classnames';
import {useTimeout} from 'frontend-js-react-web';
import {fetch, objectToFormData} from 'frontend-js-web';
import dom from 'metal-dom';
import PropTypes from 'prop-types';
import React, {useEffect, useState} from 'react';

/**
 * Handles actions to delete or change permissions of the
 * collaborators for a file entry.
 */

const ManageCollaborators = ({
	actionUrl,
	classNameId,
	classPK,
	dialogId,
	initialCollaborators,
	portletNamespace
}) => {
	const [collaborators, setCollaborators] = useState(initialCollaborators);
	const [deleteSharingEntryIds, setDeleteSharingEntryIds] = useState([]);
	const [expirationDateError, setExpirationDateError] = useState(false);
	const [expandedCollaboratorId, setExpandedCollaboratorId] = useState(1234);
	const [loadingResponse, setLoadingResponse] = useState(false);
	const [
		sharingEntryIdsAndExpirationDate,
		setSharingEntryIdsAndExpirationDate
	] = useState({});
	const [
		sharingEntryIdsAndPermissions,
		setSharingEntryIdsAndPermissions
	] = useState({});
	const [
		sharingEntryIdsAndShareables,
		setSharingEntryIdsAndShareables
	] = useState({});
	const [tomorrowDate, setTomorrowDate] = useState();

	const delay = useTimeout();

	const checkExpirationDate = expirationDate => {
		const date = new Date(expirationDate);

		return date >= new Date(tomorrowDate);
	};

	const closeDialog = () => {
		const collaboratorsDialog = Liferay.Util.getWindow(dialogId);

		if (collaboratorsDialog && collaboratorsDialog.hide) {
			collaboratorsDialog.hide();
		}
	};

	const objectToPairArray = object => {
		const entries = Object.entries(object);
		const result = [];

		entries.forEach(([key, value]) => {
			result.push(`${key},${value}`);
		});

		return result;
	};

	const findExpirationDateError = () => {
		const collaborator = collaborators.find(
			collaborator =>
				collaborator.sharingEntryExpirationDateError === true
		);

		setExpirationDateError(!!collaborator);
	};

	const getCollaborator = collaboratorId => {
		const collaboratorIdNumber = Number(collaboratorId);

		const collaborator = collaborators.find(
			collaborator => collaborator.userId === collaboratorIdNumber
		);

		return collaborator;
	};

	const getTooltipDate = expirationDate => {
		return Liferay.Util.sub(
			Liferay.Language.get('until-x'),
			new Date(expirationDate).toLocaleDateString(
				Liferay.ThemeDisplay.getBCP47LanguageId()
			)
		);
	};

	const handleCollaboratorClick = event => {
		const eventTarget = event.target;
		const invalidElements = 'select,option,button';

		if (
			invalidElements.indexOf(eventTarget.nodeName.toLowerCase()) === -1
		) {
			const collaboratorContainer = dom.closest(
				eventTarget,
				'.list-group-item'
			);

			setExpandedCollaboratorId(
				Number(collaboratorContainer.dataset.collaboratorid)
			);
		}
	};

	const handleDeleteCollaboratorButtonClick = event => {
		const button = event.currentTarget;

		const collaboratorId = Number(button.dataset.collaboratorId);
		const sharingEntryId = button.dataset.sharingentryId;

		event.stopPropagation();

		setCollaborators(
			collaborators.filter(
				collaborator => collaborator.userId != collaboratorId
			)
		);

		deleteSharingEntryIds.push(sharingEntryId);

		setDeleteSharingEntryIds(deleteSharingEntryIds);
	};

	const handleExpirationDateCheckboxChange = event => {
		const checkbox = event.target;

		const collaboratorId = checkbox.dataset.collaboratorId;
		const enabled = checkbox.checked;

		const collaborator = getCollaborator(collaboratorId);

		collaborator.enabledExpirationDate = enabled;

		const sharingEntryExpirationDate = enabled ? tomorrowDate : '';

		if (!enabled) {
			collaborator.sharingEntryExpirationDateError = false;

			findExpirationDateError();
		}

		collaborator.sharingEntryExpirationDate = sharingEntryExpirationDate;
		collaborator.sharingEntryExpirationDateTooltip = getTooltipDate(
			sharingEntryExpirationDate
		);

		setCollaborator(collaborator);

		setSharingEntryIdsAndExpirationDate({
			...sharingEntryIdsAndExpirationDate,
			[collaborator.sharingEntryId]: sharingEntryExpirationDate
		});
	};

	const handleExpirationDateInputBlur = event => {
		const input = event.target;

		const collaboratorId = input.dataset.collaboratorId;
		const sharingEntryExpirationDate = input.value;
		const sharingEntryId = input.dataset.sharingentryId;

		const collaborator = getCollaborator(collaboratorId);

		const dateError = !checkExpirationDate(sharingEntryExpirationDate);

		collaborator.sharingEntryExpirationDateError = dateError;

		if (!dateError) {
			collaborator.sharingEntryExpirationDate = sharingEntryExpirationDate;
			collaborator.sharingEntryExpirationDateTooltip = getTooltipDate(
				sharingEntryExpirationDate
			);

			setCollaborator(collaborator);

			setSharingEntryIdsAndExpirationDate({
				...sharingEntryIdsAndExpirationDate,
				[sharingEntryId]: sharingEntryExpirationDate
			});
		}

		delay(() => findExpirationDateError(), 0);
	};

	const handleSaveButtonClick = () => {
		if (findExpirationDateError()) {
			return;
		}

		setLoadingResponse(true);

		const data = Liferay.Util.ns(portletNamespace, {
			deleteSharingEntryIds,
			sharingEntryIdActionIdPairs: objectToPairArray(
				sharingEntryIdsAndPermissions
			),
			sharingEntryIdExpirationDatePairs: objectToPairArray(
				sharingEntryIdsAndExpirationDate
			),
			sharingEntryIdShareablePairs: objectToPairArray(
				sharingEntryIdsAndShareables
			)
		});

		fetch(actionUrl, {
			body: objectToFormData(data),
			method: 'POST'
		})
			.then(response => {
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
					classNameId,
					classPK
				});

				showNotification(json.successMessage);

				setLoadingResponse(false);
			})
			.catch(error => {
				showNotification(error.message, true);

				setLoadingResponse(false);
			});
	};

	const handleShareableCheckboxChange = event => {
		const checkbox = event.target;

		const collaboratorId = checkbox.dataset.collaboratorId;
		const shareable = checkbox.checked;
		const sharingEntryId = checkbox.dataset.sharingentryId;

		const collaborator = getCollaborator(collaboratorId);

		collaborator.sharingEntryShareable = shareable;

		setCollaborator(collaborator);

		setSharingEntryIdsAndShareables({
			...sharingEntryIdsAndShareables,
			[sharingEntryId]: shareable
		});
	};

	const setCollaborator = updatedCollaborator => {
		setCollaborators(
			collaborators.map(collaborator => {
				if (collaborator.userId === updatedCollaborator.userId) {
					return {
						...collaborator,
						...updatedCollaborator
					};
				}

				return collaborator;
			})
		);
	};

	const showNotification = (message, error) => {
		const parentOpenToast = Liferay.Util.getOpener().Liferay.Util.openToast;

		const openToastParams = {
			message
		};

		if (error) {
			openToastParams.title = Liferay.Language.get('error');
			openToastParams.type = 'danger';
		}

		closeDialog();

		parentOpenToast(openToastParams);
	};

	useEffect(() => {
		let tomorrow = new Date();

		tomorrow = tomorrow.setDate(tomorrow.getDate() + 1);

		setTomorrowDate(new Date(tomorrow).toISOString().split('T')[0]);
	}, []);

	const Collaborator = ({
		fullName,
		portraitURL,
		sharingEntryExpirationDate,
		sharingEntryExpirationDateError,
		sharingEntryExpirationDateTooltip,
		sharingEntryId,
		sharingEntryPermissionActionId,
		sharingEntryPermissionDisplaySelectOptions,
		sharingEntryShareable,
		userId
	}) => {
		return (
			<li
				className={classNames(
					'list-group-item',
					'list-group-item-action',
					'list-group-item-flex',
					{
						active: userId === expandedCollaboratorId
					}
				)}
				data-collaboratorid={userId}
				id={`collaborator${userId}`}
				onClick={handleCollaboratorClick}
				role="button"
			>
				<div className="autofit-col">
					<ClaySticker
						className={
							!portraitURL && `user-icon-color-${userId % 10}`
						}
						shape="circle"
						size="lg"
					>
						{portraitURL ? (
							<img className="sticker-img" src={portraitURL} />
						) : (
							<ClayIcon symbol="user" />
						)}
					</ClaySticker>
				</div>
				<div className="autofit-col autofit-col-expand">
					<div className="autofit-row autofit-row-center">
						<div className="autofit-col autofit-col-expand">
							<strong>
								<span>{fullName}</span>
							</strong>
						</div>
						<div className="autofit-col">
							{sharingEntryExpirationDate ? (
								<ClayIcon
									data-title={
										sharingEntryExpirationDateTooltip
									}
									symbol="time"
								/>
							) : (
								<span className="lexicon-icon"></span>
							)}
						</div>
						<div className="autofit-col">
							{sharingEntryShareable ? (
								<ClayIcon
									data-title={Liferay.Language.get(
										'user-can-share'
									)}
									symbol="users"
								/>
							) : (
								<span className="lexicon-icon"></span>
							)}
						</div>
						<div className="autofit-col">
							<ClaySelectWithOption
								name={sharingEntryId}
								onChange={event => {
									setSharingEntryIdsAndPermissions({
										...sharingEntryIdsAndPermissions,
										[event.target.name]: event.target.value
									});
								}}
								options={
									sharingEntryPermissionDisplaySelectOptions
								}
								value={
									sharingEntryIdsAndPermissions[
										sharingEntryId
									] || sharingEntryPermissionActionId
								}
							/>
						</div>
						<div className="autofit-col">
							<ClayButtonWithIcon
								borderless
								data-collaborator-id={userId}
								data-sharingentry-id={sharingEntryId}
								disabled={loadingResponse}
								displayType="secondary"
								onClick={handleDeleteCollaboratorButtonClick}
								symbol="times-circle"
							/>
						</div>
					</div>
					<div
						className={classNames({
							hide: userId !== expandedCollaboratorId
						})}
					>
						<div className="autofit-row autofit-row-center">
							<div className="autofit-col">
								<div className="form-group">
									<div className="custom-checkbox custom-control">
										<ClayCheckbox
											checked={sharingEntryShareable}
											className="custom-control-input"
											data-collaborator-id={userId}
											data-sharingentry-id={
												sharingEntryId
											}
											label={Liferay.Language.get(
												'allow-the-document-to-be-shared-with-other-users'
											)}
											onChange={
												handleShareableCheckboxChange
											}
										/>
									</div>
								</div>
							</div>
						</div>
						<div className="autofit-row autofit-row-center">
							<div className="autofit-col">
								<div className="form-group">
									<div className="custom-checkbox custom-control">
										<ClayCheckbox
											className="custom-control-input"
											data-collaborator-id={userId}
											defaultChecked={
												sharingEntryExpirationDate
											}
											label={Liferay.Language.get(
												'set-expiration-date'
											)}
											onChange={
												handleExpirationDateCheckboxChange
											}
										/>
									</div>
								</div>
							</div>
							<div
								className={classNames(
									'autofit-col',
									'no-padding',
									{
										'has-error': sharingEntryExpirationDateError
									}
								)}
							>
								<ClayInput
									className="form-control"
									data-collaborator-id={userId}
									data-sharingentry-id={sharingEntryId}
									defaultValue={sharingEntryExpirationDate}
									disabled={!sharingEntryExpirationDate}
									min={tomorrowDate}
									onBlur={handleExpirationDateInputBlur}
									type="date"
								/>
							</div>
						</div>
					</div>
				</div>
			</li>
		);
	};

	return (
		<>
			<div>
				{collaborators.length ? (
					<>
						{expirationDateError && (
							<ClayAlert
								displayType="danger"
								onClose={() => {
									setExpirationDateError(false);
								}}
								title={`${Liferay.Language.get('error')}:`}
								variant="stripe"
							>
								{Liferay.Language.get(
									'please-enter-an-expiration-date-that-comes-after-today'
								)}
							</ClayAlert>
						)}
						<ul className="list-group">
							{collaborators.map(collaborator => {
								return (
									<Collaborator
										{...collaborator}
										key={collaborator.userId}
									/>
								);
							})}
						</ul>
					</>
				) : (
					<div className="autofit-row autofit-row-center empty-collaborators">
						<div className="autofit-col autofit-col-expand">
							<div className="message-content">
								<h3>
									{Liferay.Language.get('no-collaborators')}
								</h3>
								<p>
									{Liferay.Language.get(
										'to-add-collaborators-share-the-file-again'
									)}
								</p>
							</div>
						</div>
					</div>
				)}
			</div>
			<div className="modal-footer">
				<div className="modal-item-last">
					<ClayButton.Group>
						<ClayButton
							disabled={loadingResponse}
							displayType="secondary"
							onClick={closeDialog}
						>
							{Liferay.Language.get('cancel')}
						</ClayButton>
						<ClayButton
							disabled={loadingResponse || expirationDateError}
							displayType="primary"
							onClick={handleSaveButtonClick}
						>
							{loadingResponse && <ClayLoadingIndicator />}
							{Liferay.Language.get('save')}
						</ClayButton>
					</ClayButton.Group>
				</div>
			</div>
		</>
	);
};

ManageCollaborators.propTypes = {
	actionUrl: PropTypes.string.isRequired,
	classNameId: PropTypes.string,
	classPK: PropTypes.string,
	dialogId: PropTypes.string.isRequired,
	initialCollaborators: PropTypes.array.isRequired,
	portletNamespace: PropTypes.string
};

export default function(props) {
	return <ManageCollaborators {...props} />;
}
