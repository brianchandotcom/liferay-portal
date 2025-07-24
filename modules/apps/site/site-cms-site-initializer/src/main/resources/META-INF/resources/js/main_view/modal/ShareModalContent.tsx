/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import {Option, Picker} from '@clayui/core';
import {useResource} from '@clayui/data-provider';
import ClayDatePicker from '@clayui/date-picker';
import ClayDropDown from '@clayui/drop-down';
import ClayForm, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayModal from '@clayui/modal';
import ClayMultiSelect from '@clayui/multi-select';
import ClayPanel from '@clayui/panel';
import ClaySticker from '@clayui/sticker';
import {openToast} from 'frontend-js-components-web';
import {dateUtils, fetch, sub} from 'frontend-js-web';
import React, {useState} from 'react';

import '../../../css/components/ShareModalContent.scss';
import {UserAccount, UserGroup} from '../../common/types/UserAccount';

export interface collaborator {
	actionIds?: string;
	dateExpired?: string;
	error?: string;
	share?: boolean;
	toBeShared?: boolean;
	type: string;
	user: UserAccount | UserGroup;
}

const TYPES = {
	USER: 'User',
	USER_GROUP: 'UserGroup',
};

const formatDateForView = (date: string): string => {
	const formattedDate = new Date(date.replace('--:--', '00:00'));

	if (isNaN(formattedDate.getTime())) {
		return 'NaN';
	}

	if (formattedDate < new Date()) {
		return 'EXPIRED';
	}

	return formattedDate.toLocaleString(
		Liferay.ThemeDisplay.getBCP47LanguageId(),
		{
			day: 'numeric',
			hour: 'numeric',
			minute: 'numeric',
			month: 'short',
			year: 'numeric',
		}
	);
};

const formatDateToISO = (date: string): string => {
	const formattedDate = new Date(date.replace('--:--', '00:00'));

	return formattedDate.toISOString();
};

function CollaboratorListItem({
	actionIds,
	dateExpired,
	error,
	onChangeUser,
	onRemoveUser,
	share,
	toBeShared,
	type = TYPES.USER,
	user,
}: {
	actionIds?: string;
	dateExpired?: string;
	error?: string;
	onChangeUser: (user: UserAccount | UserGroup, property: object) => void;
	onRemoveUser: (user: UserAccount | UserGroup) => void;
	share?: boolean;
	toBeShared?: boolean;
	type: string;
	user: UserAccount | UserGroup;
}) {
	const _handleDatePickerChange = (value: string) => {
		if (value === '') {
			onChangeUser(user, {dateExpired: '', error: ''});
		}
		else {
			const formattedDate = formatDateForView(value);

			onChangeUser(user, {
				dateExpired: value,
				error:
					formattedDate === 'NaN'
						? Liferay.Language.get(
								'please-select-a-valid-expiration-date'
							)
						: formattedDate === 'EXPIRED'
							? Liferay.Language.get(
									'please-enter-an-expiration-date-that-comes-after-today'
								)
							: '',
			});
		}
	};

	return (
		<li
			className="border-0 c-px-0 c-py-1 list-group-item list-group-item-flex"
			key={`collaborator-${user.id}`}
		>
			<div className="autofit-col">
				<ClaySticker displayType="secondary" shape="circle" size="sm">
					{type === TYPES.USER ? (
						'image' in user && user.image ? (
							<img
								alt={user.name}
								className="sticker-img"
								src={(user as UserAccount).image}
							/>
						) : (
							<ClayIcon symbol="user" />
						)
					) : (
						<ClayIcon symbol="users" />
					)}
				</ClaySticker>
			</div>

			<div className="autofit-col autofit-col-expand">
				<div className="align-items-center d-flex">
					<span className="text-3 text-truncate text-weight-semi-bold">
						{user.name}
					</span>

					{toBeShared && (
						<span className="inline-item inline-item-after label label-inverse-light">
							<span className="label-item label-item-expand">
								{Liferay.Language.get('to-be-shared')}
							</span>
						</span>
					)}
				</div>

				{error ? (
					<div className="text-2 text-danger">{error}</div>
				) : (
					dateExpired && (
						<div className="text-2">
							{sub(Liferay.Language.get('access-expires-x'), [
								formatDateForView(dateExpired),
							])}

							<ClayButtonWithIcon
								aria-label={sub(
									Liferay.Language.get('clear-x'),
									[Liferay.Language.get('expiration-date')]
								)}
								borderless
								className="c-ml-1 inline-item"
								displayType="secondary"
								monospaced
								onClick={() => _handleDatePickerChange('')}
								size="xs"
								symbol="trash"
							/>
						</div>
					)
				)}
			</div>

			<div className="autofit-col">
				<Picker
					aria-label={Liferay.Language.get('edit-permissions')}
					className="border-0 c-py-0 permissions-picker text-2 text-secondary text-weight-semi-bold"
					items={[
						{
							label: Liferay.Language.get('view-and-download'),
							value: 'VIEW',
						},
						{
							label: Liferay.Language.get(
								'view-download-and-comment'
							),
							value: 'ADD_DISCUSSION,VIEW',
						},
						{
							label: Liferay.Language.get(
								'view-download-comment-and-update'
							),
							value: 'UPDATE,ADD_DISCUSSION,VIEW',
						},
					]}
					onSelectionChange={(value: React.Key) =>
						onChangeUser(user, {actionIds: value})
					}
					placeholder=""
					selectedKey={actionIds}
				>
					{(item: {label: string; value: string}) => (
						<Option key={item.value}>{item.label}</Option>
					)}
				</Picker>
			</div>

			<div className="autofit-col">
				<div className="d-flex">
					<ClayDropDown
						trigger={
							<ClayButtonWithIcon
								aria-label={Liferay.Language.get(
									'set-expiration-date'
								)}
								borderless
								className="inline-item inline-item-before"
								displayType="secondary"
								monospaced
								size="xs"
								symbol="date-time"
							/>
						}
					>
						<ClayDropDown.ItemList>
							<ClayDropDown.Section>
								<ClayDatePicker
									firstDayOfWeek={dateUtils.getFirstDayOfWeek()}
									months={[
										`${Liferay.Language.get('january')}`,
										`${Liferay.Language.get('february')}`,
										`${Liferay.Language.get('march')}`,
										`${Liferay.Language.get('april')}`,
										`${Liferay.Language.get('may')}`,
										`${Liferay.Language.get('june')}`,
										`${Liferay.Language.get('july')}`,
										`${Liferay.Language.get('august')}`,
										`${Liferay.Language.get('september')}`,
										`${Liferay.Language.get('october')}`,
										`${Liferay.Language.get('november')}`,
										`${Liferay.Language.get('december')}`,
									]}
									onChange={_handleDatePickerChange}
									placeholder={Liferay.Language.get(
										'yyyy-mm-dd hh:mm'
									)}
									time={true}
									value={dateExpired}
									years={{
										end: new Date().getFullYear(),
										start: 1998,
									}}
								/>
							</ClayDropDown.Section>
						</ClayDropDown.ItemList>
					</ClayDropDown>

					<ClayDropDown
						hasLeftSymbols={true}
						trigger={
							<ClayButtonWithIcon
								aria-label={Liferay.Language.get(
									'more-options'
								)}
								borderless
								displayType="secondary"
								monospaced
								size="xs"
								symbol="ellipsis-v"
							/>
						}
					>
						<ClayDropDown.ItemList>
							<ClayDropDown.Item
								aria-label={Liferay.Language.get(
									'allow-resharing'
								)}
								key={`share-${user.id}`}
								onClick={() =>
									onChangeUser(user, {
										share: !share,
									})
								}
								symbolLeft={share ? 'check-small' : ''}
							>
								{Liferay.Language.get('allow-resharing')}
							</ClayDropDown.Item>

							<ClayDropDown.Item
								aria-label={Liferay.Language.get(
									'remove-access'
								)}
								key={`remove-${user.id}`}
								onClick={() => onRemoveUser(user)}
							>
								{Liferay.Language.get('remove-access')}
							</ClayDropDown.Item>
						</ClayDropDown.ItemList>
					</ClayDropDown>
				</div>
			</div>
		</li>
	);
}

export default function ShareModalContent({
	autocompleteURL = '',
	closeModal,
	collaboratorURL = '',
	creator,
	initialCollaborators = [],
	title = '',
}: {
	autocompleteURL: string;
	closeModal: () => void;
	collaboratorURL: string;
	creator: {
		contentType: string;
		id: string;
		image?: string;
		name: string;
	};
	initialCollaborators: collaborator[];
	title: string;
}) {
	const [autocompleteValue, setAutocompleteValue] = useState('');
	const [autocompleteNetworkStatus, setAutocompleteNetworkStatus] =
		useState(4);
	const [collaborators, setCollaborators] =
		useState<collaborator[]>(initialCollaborators);
	const [loading, setLoading] = useState(false);

	const {resource: users} = useResource({
		fetchOptions: {
			credentials: 'include',
			headers: new Headers({'x-csrf-token': Liferay.authToken}),
			method: 'GET',
		},
		fetchRetry: {
			attempts: 0,
		},
		link: `${window.location.origin}${autocompleteURL}`,
		onNetworkStatusChange: setAutocompleteNetworkStatus,
		variables: {search: autocompleteValue},
	});

	const _handleAddUser = (user: UserAccount | UserGroup, type: string) => {
		setCollaborators((collaborators) => {
			return collaborators.every(
				(collaborator) => collaborator.user.id !== user.id
			) && creator.id !== user.id
				? [
						{
							actionIds: 'VIEW',
							share: false,
							toBeShared: true,
							type,
							user,
						},
						...collaborators,
					]
				: collaborators;
		});

		setAutocompleteValue('');
	};

	const _handleRemoveUser = async (
		user: UserAccount | UserGroup
	): Promise<void> => {
		setCollaborators((collaborator) =>
			collaborator.filter(
				(collaborator) => collaborator.user.id !== user.id
			)
		);
	};

	const _handleChangeUser = (
		user: UserAccount | UserGroup,
		property: object
	) => {
		setCollaborators((collaborator) =>
			collaborator.map((item) => {
				if (item.user.id === user.id) {
					return {
						...item,
						...property,
					};
				}

				return item;
			})
		);
	};

	const _handleSubmit = async (event: React.FormEvent) => {
		event.preventDefault();

		setLoading(true);

		const data = collaborators.map(
			({actionIds, dateExpired, share, type, user}) => ({
				actionIds: actionIds?.split(','),
				...(!!dateExpired && {
					dateExpired: formatDateToISO(dateExpired),
				}),
				id: user.id,
				share,
				type,
			})
		);

		fetch(collaboratorURL, {
			body: JSON.stringify(data),
			headers: {
				'Accept': 'application/json',
				'Accept-Language': Liferay.ThemeDisplay.getBCP47LanguageId(),
				'Content-Type': 'application/json',
			},
			method: 'POST',
		})
			.then((response) => {
				setLoading(false);

				const jsonResponse = response.json();

				return response.ok
					? jsonResponse
					: jsonResponse.then((json) => {
							throw Object.assign(new Error(json.title), {
								response,
							});
						});
			})
			.then(() => {
				openToast({
					message: sub(
						collaborators.some(({toBeShared}) => !!toBeShared)
							? Liferay.Language.get('x-was-shared-successfully')
							: Liferay.Language.get(
									'x-was-updated-successfully'
								),
						title
					),
					type: 'success',
				});

				closeModal();
			})
			.catch((error) => {
				openToast({
					message:
						error.message ||
						Liferay.Language.get('an-unexpected-error-occurred'),
					type: 'danger',
				});
			});
	};

	const _isCollaboratorsUpdated = () =>
		JSON.stringify(collaborators) !== JSON.stringify(initialCollaborators);

	return (
		<div className="share-modal-content">
			<ClayModal.Header>
				{sub(Liferay.Language.get('share-x'), `"${title}"`)}
			</ClayModal.Header>

			<ClayModal.Body scrollable={true}>
				<ClayForm.Group>
					<ClayInput.Group>
						<ClayInput.GroupItem>
							<label htmlFor="collaboratorAutocomplete">
								{Liferay.Language.get(
									'add-people-to-collaborate'
								)}
							</label>

							<ClayMultiSelect
								id="collaboratorAutocomplete"
								items={[]}
								loadingState={autocompleteNetworkStatus}
								onChange={setAutocompleteValue}
								placeholder={Liferay.Language.get(
									'enter-name-email-or-groups'
								)}
								sourceItems={
									autocompleteValue && !!users?.items?.length
										? users.items?.map((item: any) => {
												if (
													item.entryClassName?.includes(
														'UserGroup'
													)
												) {
													return {
														type: TYPES.USER_GROUP,
														user: {
															id: item.embedded.id.toString(),
															name: item.embedded
																.name,
														},
													};
												}

												return {
													type: TYPES.USER,
													user: {
														emailAddress:
															item.embedded
																.emailAddress,
														id: item.embedded.id.toString(),
														image: item.embedded
															.image,
														name: item.embedded
															.name,
													},
												};
											})
										: []
								}
								value={autocompleteValue}
							>
								{({
									type,
									user,
								}: {
									type: string;
									user: UserAccount | UserGroup;
								}) => (
									<ClayMultiSelect.Item
										key={`autocomplete-${type}-${user.id}`}
										onClick={() =>
											_handleAddUser(user, type)
										}
										textValue={user.name}
									>
										<div className="autofit-row autofit-row-center">
											<div className="autofit-col c-mr-1">
												<ClaySticker
													className="sticker-user-icon"
													size="sm"
												>
													{type === TYPES.USER ? (
														'image' in user &&
														user.image ? (
															<div className="sticker-overlay">
																<img
																	className="sticker-img"
																	src={
																		user.image
																	}
																/>
															</div>
														) : (
															<ClayIcon symbol="user" />
														)
													) : (
														<ClayIcon symbol="users" />
													)}
												</ClaySticker>
											</div>

											<div className="autofit-col">
												<span className="text-weight-semibold">
													<span className="c-mr-1">
														{user.name}
													</span>

													{'emailAddress' in user &&
														`(${user.emailAddress})`}
												</span>
											</div>
										</div>
									</ClayMultiSelect.Item>
								)}
							</ClayMultiSelect>
						</ClayInput.GroupItem>
					</ClayInput.Group>
				</ClayForm.Group>

				<ClayForm.Group>
					<ClayPanel
						className="border-0"
						collapsable
						defaultExpanded={true}
						displayTitle={
							<div className="panel-title text-secondary">
								{Liferay.Language.get('who-has-access') +
									` (` +
									sub(
										Liferay.Language.get('x-users'),
										collaborators.length + 1
									) +
									`)`}
							</div>
						}
						displayType="unstyled"
					>
						<ClayPanel.Body>
							<ul className="c-mb-0 list-group">
								{collaborators.map((item) => (
									<CollaboratorListItem
										key={`listItem-${item.type}-${item.user.id}`}
										onChangeUser={_handleChangeUser}
										onRemoveUser={_handleRemoveUser}
										{...item}
									/>
								))}

								{creator.id && (
									<li
										className="border-0 c-px-0 c-py-1 list-group-item list-group-item-flex"
										key={`listItem-creator-${creator.id}`}
									>
										<div className="autofit-col">
											<ClaySticker
												displayType="secondary"
												shape="circle"
												size="sm"
											>
												{creator.contentType ===
												'UserAccount' ? (
													'image' in creator &&
													creator.image ? (
														<img
															alt={creator.name}
															className="sticker-img"
															src={creator.image}
														/>
													) : (
														<ClayIcon symbol="user" />
													)
												) : (
													<ClayIcon symbol="users" />
												)}
											</ClaySticker>
										</div>

										<div className="autofit-col autofit-col-expand">
											<span className="text-3 text-truncate text-weight-semi-bold">
												{creator.name}
											</span>
										</div>

										<div className="autofit-col">
											<span className="text-2 text-secondary text-weight-semi-bold">
												{Liferay.Language.get('owner')}
											</span>
										</div>
									</li>
								)}
							</ul>
						</ClayPanel.Body>
					</ClayPanel>
				</ClayForm.Group>
			</ClayModal.Body>

			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						<ClayButton
							displayType="secondary"
							onClick={closeModal}
							type="button"
						>
							{Liferay.Language.get('cancel')}
						</ClayButton>

						<ClayButton
							disabled={
								loading ||
								!collaborators.some(({error}) => !error) ||
								!_isCollaboratorsUpdated()
							}
							displayType="primary"
							onClick={_handleSubmit}
							type="submit"
						>
							{loading && (
								<span className="inline-item inline-item-before">
									<span
										aria-hidden="true"
										className="loading-animation"
									></span>
								</span>
							)}

							{Liferay.Language.get('save')}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</div>
	);
}
