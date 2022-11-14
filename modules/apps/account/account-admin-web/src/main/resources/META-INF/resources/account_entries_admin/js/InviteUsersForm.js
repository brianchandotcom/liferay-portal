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

import ClayButton from '@clayui/button';
import ClayForm, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import ClayMultiSelect from '@clayui/multi-select';
import {
	fetch,
	getOpener,
	objectToFormData,
	openToast,
	sub,
} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

function InviteUsersForm({
	accountEntryId,
	availableAccountRoles,
	inviteAccountUsersURL,
	portletNamespace,
	redirectURL,
}) {
	const [inputGroups, setInputGroups] = useState([
		{selectedAccountRoles: [], selectedEmailAddresses: []},
	]);

	const closeModal = () => {
		const openerWindow = getOpener();

		openerWindow.Liferay.fire('closeModal');
	};

	const InviteUserFormGroup = ({
		index,
		selectedAccountRoles,
		selectedEmailAddresses,
	}) => {
		const [accountRoles, setAccountRoles] = useState(selectedAccountRoles);
		const [emailAddresses, setEmailAddresses] = useState(
			selectedEmailAddresses
		);
		const [invalidAccountRoles, setInvalidAccountRoles] = useState([]);
		const [invalidEmailAddresses, setInvalidEmailAddresses] = useState([]);
		const [showRequiredMessage, setShowRequiredMessage] = useState(false);

		const checkInputValue = () => {
			if (!emailAddresses.length) {
				setShowRequiredMessage(true);
			}
			else {
				setShowRequiredMessage(false);
			}
		};

		const handleInputGroupValueChange = (name, value) => {
			const inputGroupsArray = [...inputGroups];

			inputGroupsArray[index][name] = value;

			setInputGroups(inputGroupsArray);
		};

		const validateAccountRoles = (items) => {
			handleInputGroupValueChange('selectedAccountRoles', items);
			setAccountRoles(items);

			const invalidItems = [];

			items.map(({label}) => {
				if (
					!availableAccountRoles.some(
						(accountRole) => accountRole.label === label
					)
				) {
					invalidItems.push(label);
				}
			});

			setInvalidAccountRoles(invalidItems);
		};

		const validateEmailAddresses = (items) => {
			handleInputGroupValueChange('selectedEmailAddresses', items);
			setEmailAddresses(items);

			if (items.length) {
				setShowRequiredMessage(false);
			}
		};

		useEffect(() => {
			Promise.allSettled(
				emailAddresses.map(
					({label}) =>
						new Promise((resolve, reject) => {
							fetch(`/o/account-admin/validate-email-address/`, {
								body: objectToFormData({
									accountEntryId,
									emailAddress: label,
								}),
								method: 'POST',
							})
								.then((response) => response.json())
								.then(({errorMessage, isValid}) => {
									if (isValid) {
										resolve();
									}
									else {
										reject({
											emailAddress: label,
											error: errorMessage,
										});
									}
								});
						})
				)
			).then((results) => {
				setInvalidEmailAddresses(
					results
						.filter((result) => result.status === 'rejected')
						.map((result) => result.reason)
				);
			});
		}, [emailAddresses]);

		return (
			<ClayLayout.Sheet size="lg">
				<ClayForm.Group
					className={
						!!invalidEmailAddresses.length || showRequiredMessage
							? 'has-error'
							: ''
					}
				>
					<label
						htmlFor={`${portletNamespace}emailAddressesMultiSelect${index}`}
					>
						{Liferay.Language.get('emails')}

						<ClayIcon
							className="ml-1 reference-mark"
							symbol="asterisk"
						/>
					</label>

					<ClayInput.Group>
						<ClayInput.GroupItem>
							<ClayMultiSelect
								autoFocus={true}
								id={`${portletNamespace}emailAddressesMultiSelect${index}`}
								inputName={`${portletNamespace}emailAddresses${index}`}
								items={emailAddresses}
								onBlur={checkInputValue}
								onItemsChange={validateEmailAddresses}
							/>

							{showRequiredMessage && (
								<ClayForm.FeedbackGroup>
									<ClayForm.FeedbackItem>
										{Liferay.Language.get(
											'this-field-is-required'
										)}
									</ClayForm.FeedbackItem>
								</ClayForm.FeedbackGroup>
							)}

							{!!invalidEmailAddresses.length && (
								<ClayForm.FeedbackGroup>
									{invalidEmailAddresses.map(
										(invalidEmailAddress) => (
											<ClayForm.FeedbackItem
												key={
													invalidEmailAddress.emailAddress
												}
											>
												{invalidEmailAddress.error}
											</ClayForm.FeedbackItem>
										)
									)}
								</ClayForm.FeedbackGroup>
							)}
						</ClayInput.GroupItem>
					</ClayInput.Group>
				</ClayForm.Group>

				<ClayForm.Group
					className={invalidAccountRoles.length ? 'has-error' : ''}
				>
					<label
						htmlFor={`${portletNamespace}accountRolesMultiSelect${index}`}
					>
						{Liferay.Language.get('roles')}
					</label>

					<ClayInput.Group>
						<ClayInput.GroupItem>
							<ClayMultiSelect
								id={`${portletNamespace}accountRolesMultiSelect${index}`}
								inputName={`${portletNamespace}accountRoleIds${index}`}
								items={accountRoles}
								onItemsChange={validateAccountRoles}
								sourceItems={availableAccountRoles}
							/>

							<ClayForm.FeedbackGroup>
								<ClayForm.Text>
									{Liferay.Language.get(
										'roles-will-be-applied-to-all-users-above'
									)}
								</ClayForm.Text>
							</ClayForm.FeedbackGroup>

							{!!invalidAccountRoles.length && (
								<ClayForm.FeedbackGroup>
									{invalidAccountRoles.map(
										(invalidAccountRole) => (
											<ClayForm.FeedbackItem
												key={invalidAccountRole}
											>
												{sub(
													Liferay.Language.get(
														'x-is-not-a-valid-role'
													),
													invalidAccountRole
												)}
											</ClayForm.FeedbackItem>
										)
									)}
								</ClayForm.FeedbackGroup>
							)}
						</ClayInput.GroupItem>
					</ClayInput.Group>
				</ClayForm.Group>
			</ClayLayout.Sheet>
		);
	};

	const submitForm = (event) => {
		event.preventDefault();

		const form = document.querySelector(
			`#${portletNamespace}inviteUserForm`
		);

		const error = form?.querySelector('.has-error');

		if (!error) {
			const formData = new FormData(form);

			formData.append(`${portletNamespace}count`, inputGroups.length);

			fetch(inviteAccountUsersURL, {
				body: formData,
				method: 'POST',
			})
				.then((response) => response.json())
				.then(({success}) => {
					if (success) {
						getOpener().Liferay.fire('closeModal', {
							id: `${portletNamespace}inviteUsersDialog`,
							redirect: redirectURL,
						});
					}
					else {
						throw new Error();
					}
				})
				.catch(() => {
					openToast({
						message: Liferay.Language.get(
							'your-request-failed-to-complete'
						),
						title: Liferay.Language.get('error'),
						type: 'danger',
					});
				});
		}
	};

	return (
		<ClayForm
			className="lfr-form-content"
			id={`${portletNamespace}inviteUserForm`}
		>
			{inputGroups.map((inputGroup, index) => (
				<InviteUserFormGroup
					index={index}
					key={index}
					selectedAccountRoles={inputGroup.selectedAccountRoles}
					selectedEmailAddresses={inputGroup.selectedEmailAddresses}
				/>
			))}

			<ClayLayout.SheetFooter>
				<ClayButton
					displayType="secondary"
					onClick={() => {
						setInputGroups([
							...inputGroups,
							{
								selectedAccountRoles: [],
								selectedEmailAddresses: [],
							},
						]);
					}}
				>
					<span className="inline-item inline-item-before">
						<ClayIcon symbol="plus" />
					</span>

					{Liferay.Language.get('add-entry')}
				</ClayButton>
			</ClayLayout.SheetFooter>

			<ClayLayout.SheetFooter className="dialog-footer">
				<ClayButton displayType="primary" onClick={submitForm}>
					{Liferay.Language.get('invite')}
				</ClayButton>

				<ClayButton displayType="secondary" onClick={closeModal}>
					{Liferay.Language.get('cancel')}
				</ClayButton>
			</ClayLayout.SheetFooter>
		</ClayForm>
	);
}

export default InviteUsersForm;
