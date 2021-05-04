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

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import {useResource} from '@clayui/data-provider';
import ClayDropDown, {Align, ClayDropDownWithItems} from '@clayui/drop-down';
import ClayForm, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayModal, {useModal} from '@clayui/modal';
import ClayMultiSelect from '@clayui/multi-select';
import ClaySticker from '@clayui/sticker';
import ClayTable from '@clayui/table';
import {fetch, objectToFormData} from 'frontend-js-web';
import React, {useCallback, useRef, useState} from 'react';

const CollaboratorRow = ({
	handleSelect,
	roles,
	spritemap,
	updatedRoles,
	user,
}) => {
	let activeRole = roles[0];
	let className = '';

	if (
		Object.prototype.hasOwnProperty.call(
			updatedRoles,
			user.userId.toString()
		)
	) {
		activeRole = updatedRoles[user.userId.toString()];

		if (updatedRoles[user.userId.toString()].id === -1) {
			className = 'table-delete';
		}
		else if (updatedRoles[user.userId.toString()].id !== user.roleId) {
			className = 'table-active';
		}
	}
	else {
		for (let i = 0; i < roles.length; i++) {
			if (roles[i].id === user.roleId) {
				activeRole = roles[i];

				break;
			}
		}
	}

	const dropdownItems = [];

	for (let i = 0; i < roles.length; i++) {
		dropdownItems.push({
			label: roles[i].label,
			onClick: () => handleSelect(roles[i]),
			symbolLeft: activeRole.id === roles[i].id ? 'check' : '',
		});
	}

	dropdownItems.push({
		type: 'divider',
	});

	dropdownItems.push({
		label: Liferay.Language.get('remove'),
		onClick: () =>
			handleSelect({
				id: -1,
				label: Liferay.Language.get('remove'),
			}),
		symbolLeft: activeRole.id === -1 ? 'check' : '',
	});

	let title = null;

	if (user.isOwner) {
		title = Liferay.Language.get(
			'you-cannot-update-permissions-for-an-owner'
		);
	}
	else if (user.isCurrentUser) {
		title = Liferay.Language.get(
			'you-cannot-update-permissions-for-yourself'
		);
	}

	return (
		<ClayTable.Row className={className} key={user.userId}>
			<ClayTable.Cell>
				<ClaySticker
					className={`sticker-user-icon ${
						user.portraitURL
							? ''
							: 'user-icon-color-' + (user.userId % 10)
					}`}
					size="lg"
				>
					{user.portraitURL ? (
						<div className="sticker-overlay">
							<img
								className="sticker-img"
								src={user.portraitURL}
							/>
						</div>
					) : (
						<ClayIcon symbol="user" />
					)}
				</ClaySticker>
			</ClayTable.Cell>
			<ClayTable.Cell className="table-cell-expand">
				{user.isCurrentUser
					? user.fullName + ' (' + Liferay.Language.get('you') + ')'
					: user.fullName}
			</ClayTable.Cell>
			<ClayTable.Cell className="table-cell-expand">
				{user.emailAddress}
			</ClayTable.Cell>
			<ClayTable.Cell className="table-column-text-end">
				<ClayDropDownWithItems
					alignmentPosition={Align.BottomLeft}
					items={dropdownItems}
					spritemap={spritemap}
					trigger={
						<ClayButton
							borderless
							data-tooltip-align="top"
							disabled={user.isCurrentUser || user.isOwner}
							displayType="secondary"
							small
							title={title}
						>
							{user.isOwner
								? Liferay.Language.get('owner')
								: activeRole.label}

							<span className="inline-item inline-item-after">
								<ClayIcon
									spritemap={spritemap}
									symbol="caret-bottom"
								/>
							</span>
						</ClayButton>
					}
				/>
			</ClayTable.Cell>
		</ClayTable.Row>
	);
};

const SharingAutocomplete = ({onItemClick = () => {}, sourceItems}) => {
	return (
		<ClayDropDown.ItemList>
			{sourceItems
				.sort((a, b) => {
					if (a.emailAddress < b.emailAddress) {
						return -1;
					}

					return 1;
				})
				.map((item) => {
					let title = '';

					if (!item.hasPublicationsAccess) {
						title = Liferay.Language.get(
							'user-does-not-have-permissions-to-access-publications'
						);
					}
					else if (item.isInvited) {
						title = Liferay.Language.get('user-is-already-invited');
					}
					else if (item.selected) {
						title = Liferay.Language.get(
							'user-is-already-selected'
						);
					}

					return (
						<ClayDropDown.Item
							data-tooltip-align="top"
							disabled={title}
							key={item.id}
							onClick={() => onItemClick(item)}
							title={title}
						>
							<div className="autofit-row autofit-row-center">
								<div className="autofit-col mr-3">
									<ClaySticker
										className={`sticker-user-icon ${
											item.portraitURL
												? ''
												: 'user-icon-color-' +
												  (item.id % 10)
										}`}
										size="lg"
									>
										{item.portraitURL ? (
											<div className="sticker-overlay">
												<img
													className="sticker-img"
													src={item.portraitURL}
												/>
											</div>
										) : (
											<ClayIcon symbol="user" />
										)}
									</ClaySticker>
								</div>

								<div className="autofit-col">
									<strong>{item.fullName}</strong>

									<span>{item.emailAddress}</span>
								</div>
							</div>
						</ClayDropDown.Item>
					);
				})}
		</ClayDropDown.ItemList>
	);
};

export default ({
	autocompleteUserURL,
	getCollaboratorsURL,
	inviteUsersURL,
	namespace,
	roles,
	spritemap,
	updateRolesURL,
	verifyEmailAddressURL,
}) => {
	const [emailAddressErrorMessages, setEmailAddressErrorMessages] = useState(
		[]
	);
	const [multiSelectValue, setMultiSelectValue] = useState('');

	const NAVIGATION_EDIT_ROLES = 'NAVIGATION_EDIT_ROLES';
	const NAVIGATION_INVITE_USERS = 'NAVIGATION_INVITE_USERS';

	const [navigation, setNavigation] = useState(null);

	const [selectedItems, setSelectedItems] = useState([]);
	const [showModal, setShowModal] = useState(false);
	const [updatedRoles, setUpdatedRoles] = useState({});

	const updateRole = (role, user) => {
		setNavigation(NAVIGATION_EDIT_ROLES);

		const json = {};

		const keys = Object.keys(updatedRoles);

		for (let i = 0; i < keys.length; i++) {
			json[keys[i]] = updatedRoles[keys[i]];
		}

		json[user.userId.toString()] = role;

		setUpdatedRoles(json);
	};

	let defaultRole = roles[0];

	for (let i = 0; i < roles.length; i++) {
		if (roles[i].default) {
			defaultRole = roles[i];

			break;
		}
	}

	const [selectedRole, setSelectedRole] = useState(defaultRole);

	const emailValidationInProgress = useRef(false);

	const resetForm = () => {
		setEmailAddressErrorMessages([]);
		setMultiSelectValue('');
		setNavigation(null);
		setSelectedItems([]);
		setSelectedRole(defaultRole);
		setUpdatedRoles({});

		emailValidationInProgress.current = false;
	};

	const {observer, onClose} = useModal({
		onClose: () => {
			setShowModal(false);
		},
	});

	const filterDuplicateItems = (items) => {
		return items.filter(
			(item, index) =>
				items.findIndex(
					(newItem) =>
						newItem.value.toLowerCase() === item.value.toLowerCase()
				) === index
		);
	};

	const isEmailAddressValid = (email) => {
		const emailRegex = /.+@.+\..+/i;

		return emailRegex.test(email);
	};

	const showNotification = (message, error) => {
		const parentOpenToast = Liferay.Util.getOpener().Liferay.Util.openToast;

		const openToastParams = {message};

		if (error) {
			openToastParams.title = Liferay.Language.get('error');
			openToastParams.type = 'danger';
		}

		onClose();
		resetForm();

		parentOpenToast(openToastParams);
	};

	const handleSubmit = (event) => {
		event.preventDefault();

		if (navigation === NAVIGATION_EDIT_ROLES) {
			const roles = [];
			const userIds = [];

			const keys = Object.keys(updatedRoles);

			for (let i = 0; i < keys.length; i++) {
				roles.push(updatedRoles[keys[i]].id);
				userIds.push(keys[i]);
			}

			const data = {
				[`${namespace}roles`]: roles.join(','),
				[`${namespace}userIds`]: userIds.join(','),
			};

			const formData = objectToFormData(data);

			fetch(updateRolesURL, {
				body: formData,
				method: 'POST',
			})
				.then((response) => response.json())
				.then(({errorMessage, successMessage}) => {
					if (errorMessage) {
						showNotification(errorMessage, true);

						return;
					}

					showNotification(successMessage);
				})
				.catch((error) => {
					showNotification(error.message, true);
				});

			return;
		}

		const data = {
			[`${namespace}roleId`]: selectedRole.id,
			[`${namespace}userIds`]: selectedItems.map(({id}) => id).join(','),
		};

		const formData = objectToFormData(data);

		fetch(inviteUsersURL, {
			body: formData,
			method: 'POST',
		})
			.then((response) => response.json())
			.then(({errorMessage, successMessage}) => {
				if (errorMessage) {
					showNotification(errorMessage, true);

					return;
				}

				showNotification(successMessage);
			})
			.catch((error) => {
				showNotification(error.message, true);
			});
	};

	const handleChange = useCallback((value) => {
		if (!emailValidationInProgress.current) {
			setMultiSelectValue(value);
		}
	}, []);

	const handleItemsChange = useCallback(
		(items) => {
			setNavigation(NAVIGATION_INVITE_USERS);

			emailValidationInProgress.current = true;

			Promise.all(
				items.map((item) => {
					if (
						item.id ||
						selectedItems.some(({value}) => item.value === value)
					) {
						return Promise.resolve({item});
					}

					if (!isEmailAddressValid(item.value)) {
						return Promise.resolve({
							error: Liferay.Util.sub(
								Liferay.Language.get(
									'x-is-not-a-valid-email-address'
								),
								item.value
							),
							item,
						});
					}

					return fetch(verifyEmailAddressURL, {
						body: objectToFormData({
							[`${namespace}emailAddress`]: item.value,
						}),
						method: 'POST',
					})
						.then((response) => response.json())
						.then(({errorMessage, user, userExists}) => {
							if (errorMessage) {
								return {
									error: errorMessage,
									item,
								};
							}

							if (userExists) {
								return {
									item: {
										emailAddress: user.emailAddress,
										fullName: user.fullName,
										id: user.userId,
										label: item.label,
										value: item.value,
									},
								};
							}

							return {
								error: Liferay.Util.sub(
									Liferay.Language.get(
										'user-x-does-not-exist'
									),
									item.value
								),
								item,
							};
						});
				})
			).then((results) => {
				emailValidationInProgress.current = false;

				const erroredResults = results.filter(({error}) => !!error);

				setEmailAddressErrorMessages(
					erroredResults.map(({error}) => error)
				);

				if (erroredResults.length === 0) {
					setMultiSelectValue('');
				}

				if (erroredResults.length === 1) {
					setMultiSelectValue(erroredResults[0].item.value);
				}

				setSelectedItems(
					filterDuplicateItems(
						results
							.filter(({error}) => !error)
							.map(({item}) => item)
					)
				);
			});
		},
		[namespace, selectedItems, verifyEmailAddressURL]
	);

	const multiSelectFilter = useCallback(() => true, []);

	const {resource: autocompleteResource} = useResource({
		fetchOptions: {
			credentials: 'include',
			headers: new Headers({'x-csrf-token': Liferay.authToken}),
			method: 'GET',
		},
		fetchRetry: {
			attempts: 0,
		},
		link: autocompleteUserURL,
		variables: {
			[`${namespace}keywords`]: multiSelectValue,
		},
	});

	const autocompleteUsers = autocompleteResource;

	const {
		refetch: collaboratorsRefetch,
		resource: collaboratorsResource,
	} = useResource({
		fetchOptions: {
			credentials: 'include',
			headers: new Headers({'x-csrf-token': Liferay.authToken}),
			method: 'GET',
		},
		fetchRetry: {
			attempts: 0,
		},
		link: getCollaboratorsURL,
	});

	const collaborators = collaboratorsResource;

	const renderCollaborators = () => {
		if (
			navigation === NAVIGATION_INVITE_USERS ||
			!collaborators ||
			!collaborators.length
		) {
			return '';
		}

		return (
			<ClayForm.Group>
				<ClayTable hover={false}>
					<ClayTable.Body>
						{collaborators
							.sort((a, b) => {
								if (a.isOwner && !b.isOwner) {
									return -1;
								}
								else if (!a.isOwner && b.isOwner) {
									return 1;
								}
								else if (a.emailAddress < b.emailAddress) {
									return -1;
								}

								return 1;
							})
							.map((user) => (
								<CollaboratorRow
									handleSelect={(role) =>
										updateRole(role, user)
									}
									key={user.userId}
									roles={roles}
									spritemap={spritemap}
									updatedRoles={updatedRoles}
									user={user}
								/>
							))}
					</ClayTable.Body>
				</ClayTable>
			</ClayForm.Group>
		);
	};

	const renderSelect = () => {
		if (navigation === NAVIGATION_EDIT_ROLES) {
			return '';
		}

		const dropdownItems = [];

		for (let i = 0; i < roles.length; i++) {
			dropdownItems.push({
				label: roles[i].label,
				onClick: () => setSelectedRole(roles[i]),
				symbolLeft: selectedRole.id === roles[i].id ? 'check' : '',
			});
		}

		return (
			<ClayForm.Group
				className={emailAddressErrorMessages.length ? 'has-error' : ''}
			>
				<ClayInput.Group>
					<ClayInput.GroupItem>
						<label htmlFor={`${namespace}userEmailAddress`}>
							{Liferay.Language.get('people')}
						</label>
						<ClayInput.Group>
							<ClayInput.GroupItem>
								<ClayMultiSelect
									filter={multiSelectFilter}
									inputName={`${namespace}userEmailAddress`}
									inputValue={multiSelectValue}
									items={selectedItems}
									menuRenderer={SharingAutocomplete}
									onChange={handleChange}
									onItemsChange={handleItemsChange}
									placeholder={Liferay.Language.get(
										'enter-name-or-email-address'
									)}
									sourceItems={
										multiSelectValue && autocompleteUsers
											? autocompleteUsers.map((user) => {
													let isInvited = false;

													if (
														collaborators &&
														collaborators.length
													) {
														for (
															let i = 0;
															i <
															collaborators.length;
															i++
														) {
															if (
																collaborators[i]
																	.userId ===
																user.userId
															) {
																isInvited = true;

																break;
															}
														}
													}

													return {
														emailAddress:
															user.emailAddress,
														fullName: user.fullName,
														hasPublicationsAccess:
															user.hasPublicationsAccess,
														id: user.userId,
														isInvited,
														label: user.fullName,
														portraitURL:
															user.portraitURL,
														selected: !!selectedItems.find(
															(item) =>
																item.value ===
																user.emailAddress
														),
														value:
															user.emailAddress,
													};
											  })
											: []
									}
									spritemap={spritemap}
								/>
							</ClayInput.GroupItem>
							<ClayInput.GroupItem shrink>
								<ClayDropDownWithItems
									alignmentPosition={Align.BottomLeft}
									items={dropdownItems}
									spritemap={spritemap}
									trigger={
										<ClayButton displayType="secondary">
											{selectedRole.label}

											<span className="inline-item inline-item-after">
												<ClayIcon
													spritemap={spritemap}
													symbol="caret-bottom"
												/>
											</span>
										</ClayButton>
									}
								/>
							</ClayInput.GroupItem>
						</ClayInput.Group>

						{emailAddressErrorMessages.length > 0 && (
							<ClayForm.FeedbackGroup>
								{emailAddressErrorMessages.map(
									(emailAddressErrorMessage) => (
										<ClayForm.FeedbackItem
											key={emailAddressErrorMessage}
										>
											{emailAddressErrorMessage}
										</ClayForm.FeedbackItem>
									)
								)}
							</ClayForm.FeedbackGroup>
						)}
					</ClayInput.GroupItem>
				</ClayInput.Group>
			</ClayForm.Group>
		);
	};

	const renderSubmit = () => {
		if (navigation === NAVIGATION_EDIT_ROLES) {
			return (
				<ClayButton displayType="primary" type="submit">
					{Liferay.Language.get('save')}
				</ClayButton>
			);
		}
		else if (selectedItems.length > 0) {
			return (
				<ClayButton displayType="primary" type="submit">
					{Liferay.Language.get('send')}
				</ClayButton>
			);
		}

		return (
			<ClayButton disabled displayType="primary">
				{Liferay.Language.get('send')}
			</ClayButton>
		);
	};

	const renderModal = () => {
		if (!showModal) {
			return '';
		}

		return (
			<ClayModal
				className="publications-invite-users-modal"
				observer={observer}
				size="lg"
				spritemap={spritemap}
			>
				<ClayForm onSubmit={handleSubmit}>
					<ClayModal.Header>
						<div className="autofit-row">
							<div className="autofit-col">
								{navigation ? (
									<ClayButtonWithIcon
										borderless
										displayType="secondary"
										onClick={() => {
											const keys = Object.keys(
												updatedRoles
											);

											if (
												(keys.length === 0 &&
													selectedItems.length ===
														0) ||
												confirm(
													Liferay.Language.get(
														'discard-unsaved-changes'
													)
												)
											) {
												resetForm();
											}
										}}
										small
										symbol="angle-left"
									/>
								) : (
									<ClaySticker
										className="sticker-use-icon user-icon-color-0"
										displayType="secondary"
										shape="circle"
									>
										<ClayIcon symbol="users" />
									</ClaySticker>
								)}
							</div>
							<div className="autofit-col">
								<div className="modal-title">
									{Liferay.Language.get('invite-users')}
								</div>
							</div>
						</div>
					</ClayModal.Header>
					<div className="inline-scroller modal-body publications-invite-users-modal-body">
						{renderSelect()}
						{renderCollaborators()}
					</div>
					<ClayModal.Footer
						last={
							<ClayButton.Group spaced>
								<ClayButton
									displayType="secondary"
									onClick={() => {
										const keys = Object.keys(updatedRoles);

										if (
											(keys.length === 0 &&
												selectedItems.length === 0) ||
											confirm(
												Liferay.Language.get(
													'discard-unsaved-changes'
												)
											)
										) {
											onClose();
											resetForm();
										}
									}}
								>
									{Liferay.Language.get('cancel')}
								</ClayButton>
								{renderSubmit()}
							</ClayButton.Group>
						}
					/>
				</ClayForm>
			</ClayModal>
		);
	};

	return (
		<>
			{renderModal()}

			<ClayButton
				displayType="secondary"
				onClick={() => {
					collaboratorsRefetch();
					setShowModal(true);
				}}
				small
			>
				<span className="inline-item inline-item-before">
					<ClayIcon spritemap={spritemap} symbol="users" />
				</span>

				{Liferay.Language.get('invite-users')}
			</ClayButton>
		</>
	);
};
