/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import {ClayCheckbox, ClayInput, ClaySelectWithOption} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import ClaySticker from '@clayui/sticker';
import {useTimeout} from '@liferay/frontend-js-react-web';
import classNames from 'classnames';
import {fetch, getOpener, objectToFormData, sub} from 'frontend-js-web';
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
	collaborators,
	portletNamespace,
}) => {
	const [currentCollaborators, setCurrentCollaborators] = useState(
		collaborators
	);
	const [deleteSharingEntryIds, setDeleteSharingEntryIds] = useState([]);
	const [expirationDateError, setExpirationDateError] = useState(false);
	const [expandedCollaboratorId, setExpandedCollaboratorId] = useState(1234);
	const [loadingResponse, setLoadingResponse] = useState(false);
	const [
		sharingEntryIdsAndExpirationDate,
		setSharingEntryIdsAndExpirationDate,
	] = useState({});
	const [
		sharingEntryIdsAndPermissions,
		setSharingEntryIdsAndPermissions,
	] = useState({});
	const [
		sharingEntryIdsAndShareables,
		setSharingEntryIdsAndShareables,
	] = useState({});
	const [tomorrowDate, setTomorrowDate] = useState();

	const delay = useTimeout();

	const checkExpirationDate = (expirationDate) => {
		const date = new Date(expirationDate);

		return date >= new Date(tomorrowDate);
	};

	const closeDialog = () => {
		getOpener().Liferay.fire('closeModal', {
			id: 'sharingDialog',
		});
	};

	const objectToPairArray = (object) => {
		const entries = Object.entries(object);
		const result = [];

		entries.forEach(([key, value]) => {
			result.push(`${key},${value}`);
		});

		return result;
	};

	const findExpirationDateError = () => {
		const collaborator = currentCollaborators.find(
			(collaborator) =>
				collaborator.sharingEntryExpirationDateError === true
		);

		setExpirationDateError(!!collaborator);
	};

	const getCollaborator = (collaboratorId) => {
		const collaboratorIdNumber = Number(collaboratorId);

		const collaborator = currentCollaborators.find(
			(collaborator) => collaborator.userId === collaboratorIdNumber
		);

		return collaborator;
	};

	const getTooltipDate = (expirationDate) => {
		return sub(
			Liferay.Language.get('until-x'),
			new Date(expirationDate).toLocaleDateString(
				Liferay.ThemeDisplay.getBCP47LanguageId()
			)
		);
	};

	const handleCollaboratorClick = (event) => {
		const eventTarget = event.target;
		const invalidElements = 'select,option,button';

		if (
			invalidElements.indexOf(eventTarget.nodeName.toLowerCase()) === -1
		) {
			const collaboratorContainer = eventTarget.closest(
				'.list-group-item'
			);

			setExpandedCollaboratorId(
				Number(collaboratorContainer.dataset.collaboratorid)
			);
		}
	};

	const handleDeleteCollaboratorButtonClick = (event) => {
		const button = event.currentTarget;

		const collaboratorId = Number(button.dataset.collaboratorId);
		const sharingEntryId = button.dataset.sharingentryId;

		event.stopPropagation();

		setCurrentCollaborators(
			currentCollaborators.filter(
				(collaborator) => collaborator.userId !== collaboratorId
			)
		);

		deleteSharingEntryIds.push(sharingEntryId);

		setDeleteSharingEntryIds(deleteSharingEntryIds);
	};

	const setCollaborator = (updatedCollaborator) => {
		setCurrentCollaborators(
			currentCollaborators.map((collaborator) => {
				if (collaborator.userId === updatedCollaborator.userId) {
					return {
						...collaborator,
						...updatedCollaborator,
					};
				}

				return collaborator;
			})
		);
	};

	const handleExpirationDateCheckboxChange = (event) => {
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
			[collaborator.sharingEntryId]: sharingEntryExpirationDate,
		});
	};

	const handleExpirationDateInputBlur = (event) => {
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
				[sharingEntryId]: sharingEntryExpirationDate,
			});
		}

		delay(() => findExpirationDateError(), 0);
	};

	const showNotification = (message, error) => {
		const parentOpenToast = getOpener().Liferay.Util.openToast;

		const openToastParams = {
			message,
		};

		if (error) {
			openToastParams.title = Liferay.Language.get('error');
			openToastParams.type = 'danger';
		}

		closeDialog();

		parentOpenToast(openToastParams);
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
			),
		});

		fetch(actionUrl, {
			body: objectToFormData(data),
			method: 'POST',
		})
			.then((response) => {
				const jsonResponse = response.json();

				return response.ok
					? jsonResponse
					: jsonResponse.then((json) => {
							const error = new Error(
								json.errorMessage || response.statusText
							);
							throw Object.assign(error, {response});
					  });
			})
			.then((json) => {
				parent.Liferay.fire('sharing:changed', {
					classNameId,
					classPK,
				});

				setLoadingResponse(false);

				showNotification(json.successMessage);
			})
			.catch((error) => {
				showNotification(error.message, true);

				setLoadingResponse(false);
			});
	};

	const handleShareableCheckboxChange = (event) => {
		const checkbox = event.target;

		const collaboratorId = checkbox.dataset.collaboratorId;
		const shareable = checkbox.checked;
		const sharingEntryId = checkbox.dataset.sharingentryId;

		const collaborator = getCollaborator(collaboratorId);

		collaborator.sharingEntryShareable = shareable;

		setCollaborator(collaborator);

		setSharingEntryIdsAndShareables({
			...sharingEntryIdsAndShareables,
			[sharingEntryId]: shareable,
		});
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
		userId,
	}) => {
		return (
			<li
				className={classNames(
					'list-group-item',
					'list-group-item-action',
					'list-group-item-flex',
					{
						active: userId === expandedCollaboratorId,
					}
				)}
				data-collaboratorid={userId}
				id={`collaborator${userId}`}
				onClick={handleCollaboratorClick}
				role="button"
			>
				<ClayLayout.ContentCol>
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
				</ClayLayout.ContentCol>

				<ClayLayout.ContentCol expand>
					<ClayLayout.ContentRow verticalAlign="center">
						<ClayLayout.ContentCol expand>
							<strong>
								<span>{fullName}</span>
							</strong>
						</ClayLayout.ContentCol>

						<ClayLayout.ContentCol>
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
						</ClayLayout.ContentCol>

						<ClayLayout.ContentCol>
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
						</ClayLayout.ContentCol>

						<ClayLayout.ContentCol>
							<ClaySelectWithOption
								name={sharingEntryId}
								onChange={(event) => {
									setSharingEntryIdsAndPermissions({
										...sharingEntryIdsAndPermissions,
										[event.target.name]: event.target.value,
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
						</ClayLayout.ContentCol>

						<ClayLayout.ContentCol>
							<ClayButtonWithIcon
								borderless
								data-collaborator-id={userId}
								data-sharingentry-id={sharingEntryId}
								disabled={loadingResponse}
								displayType="secondary"
								onClick={handleDeleteCollaboratorButtonClick}
								symbol="times-circle"
							/>
						</ClayLayout.ContentCol>
					</ClayLayout.ContentRow>

					<div
						className={classNames({
							hide: userId !== expandedCollaboratorId,
						})}
					>
						<ClayLayout.ContentRow verticalAlign="center">
							<ClayLayout.ContentCol>
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
												'allow-the-item-to-be-shared-with-other-users'
											)}
											onChange={
												handleShareableCheckboxChange
											}
										/>
									</div>
								</div>
							</ClayLayout.ContentCol>
						</ClayLayout.ContentRow>

						<ClayLayout.ContentRow verticalAlign="center">
							<ClayLayout.ContentCol>
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
							</ClayLayout.ContentCol>

							<ClayLayout.ContentCol
								className={classNames('no-padding', {
									'has-error': sharingEntryExpirationDateError,
								})}
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
							</ClayLayout.ContentCol>
						</ClayLayout.ContentRow>
					</div>
				</ClayLayout.ContentCol>
			</li>
		);
	};

	return (
		<>
			<div className="inline-scroller modal-body">
				{currentCollaborators.length ? (
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
							{currentCollaborators.map((collaborator) => {
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
					<ClayLayout.ContentRow
						className="empty-collaborators"
						verticalAlign="center"
					>
						<ClayLayout.ContentCol expand>
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
						</ClayLayout.ContentCol>
					</ClayLayout.ContentRow>
				)}
			</div>
			<div className="modal-footer">
				<div className="modal-item-last">
					<ClayButton.Group spaced>
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
	collaborators: PropTypes.array.isRequired,
	dialogId: PropTypes.string.isRequired,
	portletNamespace: PropTypes.string,
};

export default ManageCollaborators;
