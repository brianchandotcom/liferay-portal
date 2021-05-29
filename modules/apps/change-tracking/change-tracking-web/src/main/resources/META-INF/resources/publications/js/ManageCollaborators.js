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
	let changed = false;
	let className = '';

	if (
		Object.prototype.hasOwnProperty.call(
			updatedRoles,
			user.userId.toString()
		)
	) {
		changed = true;

		activeRole = updatedRoles[user.userId.toString()];

		if (updatedRoles[user.userId.toString()].value === -1) {
			className = 'table-delete';
		}
		else if (
			updatedRoles[user.userId.toString()].value !== user.roleValue
		) {
			className = 'table-active';
		}
	}
	else {
		for (let i = 0; i < roles.length; i++) {
			if (roles[i].value === user.roleValue) {
				activeRole = roles[i];

				break;
			}
		}
	}

	const dropdownItems = [];

	for (let i = 0; i < roles.length; i++) {
		dropdownItems.push({
			className:
				activeRole.value !== roles[i].value &&
				user.roleValue === roles[i].value
					? 'font-italic'
					: '',
			label: roles[i].label,
			onClick: () => handleSelect(roles[i]),
			symbolLeft: activeRole.value === roles[i].value ? 'check' : '',
		});
	}

	dropdownItems.push(
		{
			type: 'divider',
		},
		{
			label: Liferay.Language.get('remove'),
			onClick: () =>
				handleSelect({
					label: Liferay.Language.get('remove'),
					value: -1,
				}),
			symbolLeft: activeRole.value === -1 ? 'check' : '',
		}
	);

	let title = null;

	if (user.isOwner) {
		title = Liferay.Language.get('cannot-update-permissions-for-an-owner');
	}
	else if (user.isCurrentUser) {
		title = Liferay.Language.get(
			'you-cannot-update-permissions-for-yourself'
		);
	}

	let label = activeRole.label;

	if (user.isOwner) {
		label = Liferay.Language.get('owner');
	}
	else if (
		changed &&
		Object.prototype.hasOwnProperty.call(user, 'roleValue')
	) {
		label = label + ' (' + user.roleLabel + ')';
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
							disabled={title}
							displayType="secondary"
							small
							title={title}
						>
							{label}

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
				.filter((item) => !item.isSelected)
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
					else if (item.isOwner) {
						title = Liferay.Language.get(
							'cannot-update-permissions-for-an-owner'
						);
					}
					else if (item.isInvited) {
						title = Liferay.Language.get('user-is-already-invited');
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

const ManageCollaborators = ({
	autocompleteUserURL,
	getCollaboratorsURL,
	inviteUsersURL,
	namespace,
	roles,
	setShowModal,
	showModal,
	spritemap,
	trigger,
	verifyEmailAddressURL,
}) => {
	const [emailAddressErrorMessages, setEmailAddressErrorMessages] = useState(
		[]
	);
	const [multiSelectValue, setMultiSelectValue] = useState('');
	const [selectedItems, setSelectedItems] = useState([]);
	const [updatedRoles, setUpdatedRoles] = useState({});

	let defaultRole = roles[0];

	for (let i = 0; i < roles.length; i++) {
		if (roles[i].default) {
			defaultRole = roles[i];

			break;
		}
	}

	const [selectedRole, setSelectedRole] = useState(defaultRole);

	const handleChange = useCallback((value) => {
		if (!emailValidationInProgress.current) {
			setMultiSelectValue(value);
		}
	}, []);

	const handleItemsChange = useCallback(
		(items) => {
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
						.then(({errorMessage, user}) => {
							if (errorMessage) {
								return {
									error: errorMessage,
									item,
								};
							}
							else if (user) {
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

	const {observer, onClose} = useModal({
		onClose: () => setShowModal(false),
	});

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

	const emailValidationInProgress = useRef(false);

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

	const resetForm = () => {
		setEmailAddressErrorMessages([]);
		setMultiSelectValue('');
		setSelectedItems([]);
		setSelectedRole(defaultRole);
		setUpdatedRoles({});

		emailValidationInProgress.current = false;
	};

	const showNotification = (message, error) => {
		const parentOpenToast = Liferay.Util.getOpener().Liferay.Util.openToast;

		const openToastParams = {message};

		if (error) {
			openToastParams.title = Liferay.Language.get('error');
			openToastParams.type = 'danger';
		}

		collaboratorsRefetch();
		onClose();
		parentOpenToast(openToastParams);
		resetForm();
	};

	const handleSubmit = (event) => {
		event.preventDefault();

		let roleValues = [];
		let userIds = [];

		const keys = Object.keys(updatedRoles);

		for (let i = 0; i < keys.length; i++) {
			roleValues.push(updatedRoles[keys[i]].value);
			userIds.push(keys[i]);
		}

		if (selectedItems.length > 0) {
			roleValues = roleValues.concat(
				Array(selectedItems.length).fill(selectedRole.value)
			);
			userIds = userIds.concat(selectedItems.map(({id}) => id));
		}

		const formData = objectToFormData({
			[`${namespace}roleValues`]: roleValues.join(','),
			[`${namespace}userIds`]: userIds.join(','),
		});

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

	const updateRole = (role, user) => {
		const json = {};

		const keys = Object.keys(updatedRoles);

		for (let i = 0; i < keys.length; i++) {
			if (keys[i] !== user.userId.toString()) {
				json[keys[i]] = updatedRoles[keys[i]];
			}
		}

		let savedRoleValue = null;

		if (collaborators) {
			for (let i = 0; i < collaborators.length; i++) {
				if (collaborators[i].userId === user.userId) {
					savedRoleValue = collaborators[i].roleValue;

					break;
				}
			}
		}

		if (
			savedRoleValue !== role.value ||
			!Object.prototype.hasOwnProperty.call(
				updatedRoles,
				user.userId.toString()
			)
		) {
			json[user.userId.toString()] = role;
		}

		setUpdatedRoles(json);
	};

	const renderCollaborators = () => {
		if (!collaborators || !collaborators.length) {
			return '';
		}

		return (
			<ClayForm.Group>
				<ClayTable hover={false}>
					<ClayTable.Body>
						{collaborators
							.sort((a, b) => {
								if (a.isOwner) {
									return -1;
								}
								else if (b.isOwner) {
									return 1;
								}

								if (a.isCurrentUser) {
									return -1;
								}
								else if (b.isCurrentUser) {
									return 1;
								}

								if (a.emailAddress < b.emailAddress) {
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
		const dropdownItems = [];

		for (let i = 0; i < roles.length; i++) {
			dropdownItems.push({
				label: roles[i].label,
				onClick: () => setSelectedRole(roles[i]),
				symbolLeft:
					selectedRole.value === roles[i].value ? 'check' : '',
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
													return {
														emailAddress:
															user.emailAddress,
														fullName: user.fullName,
														hasPublicationsAccess:
															user.hasPublicationsAccess,
														id: user.userId,
														isInvited:
															collaborators &&
															!!collaborators.find(
																(item) =>
																	item.emailAddress ===
																	user.emailAddress
															),
														isOwner: user.isOwner,
														isSelected: !!selectedItems.find(
															(item) =>
																item.value ===
																user.emailAddress
														),
														label: user.fullName,
														portraitURL:
															user.portraitURL,
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
		return (
			<ClayButton
				disabled={
					selectedItems.length === 0 &&
					Object.keys(updatedRoles).length === 0
				}
				displayType="primary"
				type="submit"
			>
				{Object.keys(updatedRoles).length === 0
					? Liferay.Language.get('send')
					: Liferay.Language.get('save')}
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
								<ClaySticker
									className="sticker-use-icon user-icon-color-0"
									displayType="secondary"
									shape="circle"
								>
									<ClayIcon symbol="users" />
								</ClaySticker>
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

	const renderTrigger = () => {
		if (trigger) {
			return trigger;
		}

		if (!collaborators || collaborators.length === 0) {
			return (
				<ClayButtonWithIcon
					className="rounded-circle"
					data-tooltip-align="top"
					displayType="secondary"
					onClick={() => setShowModal(true)}
					small
					symbol="plus"
					title={Liferay.Language.get('invite-users')}
				/>
			);
		}

		const columns = [];

		columns.push(
			<div className="autofit-col">
				<ClaySticker
					className="sticker-user-icon user-icon-color-0"
					data-tooltip-align="top"
					size="md"
					title={Liferay.Language.get('invite-users')}
				>
					<ClayIcon symbol="plus" />
				</ClaySticker>
			</div>
		);

		const users = collaborators.sort((a, b) => {
			if (a.isOwner) {
				return -1;
			}
			else if (b.isOwner) {
				return 1;
			}

			if (a.isCurrentUser) {
				return -1;
			}
			else if (b.isCurrentUser) {
				return 1;
			}

			if (a.emailAddress < b.emailAddress) {
				return -1;
			}

			return 1;
		});

		for (let i = 0; i < 3 && i < users.length; i++) {
			const user = users[i];

			columns.push(
				<div className="autofit-col">
					<ClaySticker
						className={`sticker-user-icon ${
							user.portraitURL
								? ''
								: 'user-icon-color-' + (user.userId % 10)
						}`}
						data-tooltip-align="top"
						size="md"
						title={user.fullName}
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
				</div>
			);
		}

		if (users.length > 3) {
			columns.push(
				<div className="autofit-col">
					<ClaySticker className="btn-secondary" size="md">
						{'+' + (users.length - 3)}
					</ClaySticker>
				</div>
			);
		}

		return (
			<ClayButton
				displayType="unstyled"
				onClick={() => setShowModal(true)}
			>
				<div className="autofit-row">{columns}</div>
			</ClayButton>
		);
	};

	return (
		<>
			{renderModal()}
			{renderTrigger()}
		</>
	);
};

const ManageCollaboratorsWithStateHook = ({...props}) => {
	const [showModal, setShowModal] = useState(false);

	return (
		<ManageCollaborators
			setShowModal={setShowModal}
			showModal={showModal}
			{...props}
		/>
	);
};

export default ManageCollaboratorsWithStateHook;
export {ManageCollaborators, ManageCollaboratorsWithStateHook};
