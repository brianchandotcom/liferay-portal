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
import ClaySticker from '@clayui/sticker';
import {openToast} from 'frontend-js-components-web';
import {dateUtils, fetch, sub} from 'frontend-js-web';
import React, {useState} from 'react';

import {UserAccount, UserGroup} from '../../common/types/UserAccount';

export interface collaborator {
	allowResharing?: boolean;
	expirationDate?: string;
	isOwner?: boolean;
	permission?: string;
	toBeShared?: boolean;
	type: string;
	user: UserAccount | UserGroup;
}

const formatDateToISO = (date: string): string => {
	const formattedDate = new Date(date);

	return formattedDate.toISOString();
};

function ListItem({
	allowResharing,
	expirationDate,
	isOwner,
	onChangeUser,
	onRemoveUser,
	permission,
	toBeShared,
	type = 'User',
	user,
}: {
	allowResharing?: boolean;
	expirationDate?: string;
	isOwner?: boolean;
	onChangeUser: (user: UserAccount | UserGroup, property: object) => void;
	onRemoveUser: (user: UserAccount | UserGroup) => void;
	permission?: string;
	toBeShared?: boolean;
	type: string;
	user: UserAccount | UserGroup;
}) {
	return (
		<li
			className="align-items-center d-flex justify-content-between"
			key={user.id}
		>
			<div className="align-items-center d-flex">
				<ClaySticker displayType="primary" shape="circle" size="sm">
					{type === 'User' ? (
						<img
							alt={user.name}
							className="sticker-img"
							src={
								(user as UserAccount).image ||
								'/image/user_portrait'
							}
						/>
					) : (
						<ClayIcon
							className="text-secondary"
							fontSize="24px"
							symbol="users"
						/>
					)}
				</ClaySticker>

				<div className="c-ml-2 list-group-title text-truncate">
					{user.name}
				</div>

				{toBeShared && (
					<div className="c-ml-2 label label-inverse-light">
						<span className="label-item label-item-expand">
							{Liferay.Language.get('to-be-shared')}
						</span>
					</div>
				)}
			</div>

			<div className="align-items-center d-flex">
				{isOwner ? (
					<span className="text-2 text-secondary text-weight-semi-bold">
						{Liferay.Language.get('owner')}
					</span>
				) : (
					<>
						<Picker
							aria-labelledby={Liferay.Language.get(
								'edit-permissions'
							)}
							className="border-0 text-2 text-secondary text-weight-semi-bold"
							items={[
								{
									label: Liferay.Language.get('view'),
									value: 'VIEW',
								},
								{
									label: Liferay.Language.get(
										'view-and-download'
									),
									value: 'VIEW-AND-DOWNLOAD',
								},
							]}
							onSelectionChange={(value: React.Key) =>
								onChangeUser(user, {permission: value})
							}
							placeholder=""
							selectedKey={permission}
						>
							{(item: {label: string; value: string}) => (
								<Option key={item.value}>{item.label}</Option>
							)}
						</Picker>

						<ClayDropDown
							trigger={
								<ClayButtonWithIcon
									aria-label={Liferay.Language.get(
										'set-expiration-date'
									)}
									borderless
									className="c-m-1"
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
										onChange={(value: string) =>
											onChangeUser(user, {
												expirationDate: value,
											})
										}
										placeholder={Liferay.Language.get(
											'yyyy-mm-dd'
										)}
										value={expirationDate}
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
									className="c-m-1"
									displayType="secondary"
									monospaced
									size="xs"
									symbol="ellipsis-v"
								/>
							}
						>
							<ClayDropDown.ItemList>
								<ClayDropDown.Item
									key={user.id}
									onClick={() =>
										onChangeUser(user, {
											allowResharing: !allowResharing,
										})
									}
									symbolLeft={
										allowResharing ? 'check-small' : ''
									}
								>
									{Liferay.Language.get('allow-resharing')}
								</ClayDropDown.Item>

								<ClayDropDown.Item
									key={user.id}
									onClick={() => onRemoveUser(user)}
								>
									{Liferay.Language.get('remove-access')}
								</ClayDropDown.Item>
							</ClayDropDown.ItemList>
						</ClayDropDown>
					</>
				)}
			</div>
		</li>
	);
}

export default function ShareModalContent({
	autocompleteUserURL = '/o/search/v1.0/search?emptySearch=true&entryClassNames=com.liferay.portal.kernel.model.User%2Ccom.liferay.portal.kernel.model.UserGroup&nestedFields=embedded', // added for testing
	closeModal,
	shareActionURL = '',
	initialCollaborators = [],
	title = '',
}: {
	autocompleteUserURL: string;
	closeModal: () => void;
	initialCollaborators?: collaborator[];
	shareActionURL: string;
	title: string;
}) {
	const [collaborators, setCollaborators] =
		useState<collaborator[]>(initialCollaborators);
	const [multiSelectValue, setMultiSelectValue] = useState('');
	const [networkStatus, setNetworkStatus] = useState(4);

	const {resource: users} = useResource({
		fetchOptions: {
			credentials: 'include',
			headers: new Headers({'x-csrf-token': Liferay.authToken}),
			method: 'GET',
		},
		fetchRetry: {
			attempts: 0,
		},
		link: `${window.location.origin}${autocompleteUserURL}`,
		onNetworkStatusChange: setNetworkStatus,
		variables: {search: multiSelectValue},
	});

	const _handleAddUser = (user: UserAccount | UserGroup, type: string) => {
		if (
			!collaborators.some(
				({user: collabUser}) => collabUser.id === user.id
			)
		) {
			setCollaborators([
				...collaborators,
				{
					isOwner: false,
					permission: 'VIEW-AND-DOWNLOAD',
					toBeShared: true,
					type,
					user,
				},
			]);
		}
		setMultiSelectValue('');
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

		const data = collaborators.map(
			({allowResharing, expirationDate, permission, user}) => ({
				actionIds:
					permission === 'VIEW-AND-DOWNLOAD'
						? ['VIEW', 'DOWNLOAD']
						: ['VIEW'],
				dateExpired: formatDateToISO(expirationDate || ''),
				id: user.id,
				share: allowResharing,
				type: 'User',
			})
		);

		fetch(shareActionURL, {
			body: JSON.stringify(data),
			headers: {
				'Accept': 'application/json',
				'Accept-Language': Liferay.ThemeDisplay.getBCP47LanguageId(),
				'Content-Type': 'application/json',
			},
			method: 'POST',
		})
			.then((response) => {
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
						Liferay.Language.get('x-was-shared-successfully'),
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

	return (
		<form onSubmit={_handleSubmit}>
			<ClayModal.Header>
				{sub(Liferay.Language.get('share-x'), `"${title}"`)}
			</ClayModal.Header>

			<ClayModal.Body scrollable={true}>
				<ClayForm.Group>
					<ClayInput.Group>
						<ClayInput.GroupItem>
							<label htmlFor="userEmailAddress">
								{Liferay.Language.get(
									'add-people-to-collaborate'
								)}
							</label>

							<ClayMultiSelect
								id="userEmailAddress"
								items={[]}
								loadingState={networkStatus}
								locator={{
									id: 'id',
									label: 'name',
									value: 'name',
								}}
								onChange={setMultiSelectValue}
								placeholder={Liferay.Language.get(
									'enter-name-email-or-groups'
								)}
								sourceItems={
									multiSelectValue && !!users?.items?.length
										? users.items?.map((item: any) => {
												return {
													type: item.entryClassName?.includes(
														'UserGroup'
													)
														? 'UserGroup'
														: 'User',
													user: {
														emailAddress:
															item.emailAddress, // not available
														id: item.title, // not available so use title
														image: item.image, // not available
														name: item.title,
													},
												};
											})
										: []
								}
								value={multiSelectValue}
							>
								{({
									type,
									user,
								}: {
									type: string;
									user: UserAccount | UserGroup;
								}) => (
									<ClayMultiSelect.Item
										key={user.id}
										onClick={() =>
											_handleAddUser(user, type)
										}
										textValue={user.name}
									>
										<div className="autofit-row autofit-row-center">
											<div className="autofit-col mr-3">
												<ClaySticker
													className="sticker-user-icon"
													size="lg"
												>
													{'image' in user ? (
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
												<strong>{user.name}</strong>

												{'emailAddress' in user && (
													<span>
														{user.emailAddress}
													</span>
												)}
											</div>
										</div>
									</ClayMultiSelect.Item>
								)}
							</ClayMultiSelect>
						</ClayInput.GroupItem>
					</ClayInput.Group>
				</ClayForm.Group>

				<ClayForm.Group>
					<div className="c-mb-2 panel panel-unstyled">
						<div className="panel-header">
							<span className="panel-title text-secondary">
								{Liferay.Language.get('who-has-access') +
									` (` +
									sub(
										Liferay.Language.get('x-users'),
										collaborators.length
									) +
									`)`}
							</span>
						</div>
					</div>

					{collaborators.map((item) => (
						<ListItem
							key={item.user.id}
							onChangeUser={_handleChangeUser}
							onRemoveUser={_handleRemoveUser}
							{...item}
						/>
					))}
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
							disabled={false} // TO-DO: Determine factors for when to disable
							displayType="primary"
							type="submit"
						>
							{Liferay.Language.get('save')}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</form>
	);
}
