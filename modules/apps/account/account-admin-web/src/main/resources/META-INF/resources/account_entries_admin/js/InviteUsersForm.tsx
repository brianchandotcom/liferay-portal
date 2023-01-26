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
import ClayForm from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import {sub} from 'frontend-js-web';
import React, {MouseEventHandler, useState} from 'react';

import InviteUserFormGroup from './InviteUsersFormGroup';
import {InputGroup, MultiSelectItem, ValidatableMultiSelectItem} from './types';

interface IProps {
	accountEntryId: number;
	availableAccountRoles: MultiSelectItem[];
	inviteAccountUsersURL: string;
	portletNamespace: string;
	redirectURL: string;
}

function InviteUsersForm({
	accountEntryId,
	availableAccountRoles,
	inviteAccountUsersURL,
	portletNamespace,
	redirectURL,
}: IProps) {
	const [inputGroups, setInputGroups] = useState<InputGroup[]>([
		{
			accountRoles: [],
			emailAddresses: [],
			id: 'inputGroup-0',
		},
	]);

	const formId = `${portletNamespace}inviteUserForm`;

	function closeModal(modalConfig = {}) {
		const openerWindow = Liferay.Util.getOpener();

		openerWindow.Liferay.fire('closeModal', modalConfig);
	}

	function getInputGroup(inputGroupId: string) {
		const inputGroup = inputGroups.find((item) => item.id === inputGroupId);

		if (inputGroup) {
			return inputGroup;
		}

		throw new Error(`No input group found for id ${inputGroupId}`);
	}

	function setAccountRoles(
		inputGroupId: string,
		accountRoles: MultiSelectItem[]
	) {
		const inputGroup = getInputGroup(inputGroupId);

		inputGroup.accountRoles = accountRoles.map((accountRole) => {
			const validatedAccountRole: ValidatableMultiSelectItem = {
				...accountRole,
			};

			if (
				!availableAccountRoles.some(
					(availableAccountRole) =>
						availableAccountRole.label === accountRole.label
				)
			) {
				validatedAccountRole.errorMessage = sub(
					Liferay.Language.get('x-is-not-a-valid-role'),
					accountRole.label
				);
			}

			return validatedAccountRole;
		});

		setInputGroups([...inputGroups]);
	}

	async function setEmailAddresses(
		inputGroupId: string,
		emailAddresses: MultiSelectItem[]
	) {
		const inputGroup = getInputGroup(inputGroupId);

		const promises = emailAddresses.map(
			(emailAddress) =>
				new Promise<ValidatableMultiSelectItem>((resolve) => {
					const validatedEmailAddress: ValidatableMultiSelectItem = {
						...emailAddress,
					};

					Liferay.Util.fetch(
						`/o/com-liferay-account-admin-web/validate-email-address/`,
						{
							body: Liferay.Util.objectToFormData({
								accountEntryId,
								emailAddress: emailAddress.label,
							}),
							method: 'POST',
						}
					)
						.then((response) => response.json())
						.then(({errorMessage}) => {
							if (errorMessage) {
								validatedEmailAddress.errorMessage = errorMessage;
							}

							resolve(validatedEmailAddress);
						});
				})
		);

		inputGroup.emailAddresses = await Promise.all(promises);

		setInputGroups([...inputGroups]);
	}

	const submitForm: MouseEventHandler<HTMLButtonElement> = async (event) => {
		event.preventDefault();

		const form = document.querySelector(`#${formId}`) as HTMLFormElement;

		const error = form?.querySelector('.has-error');

		if (!error && form) {
			const formData = new FormData(form);

			formData.append(
				`${portletNamespace}count`,
				String(inputGroups.length)
			);

			const response = await Liferay.Util.fetch(inviteAccountUsersURL, {
				body: formData,
				method: 'POST',
			});

			const {success} = await response.json();

			if (success) {
				closeModal({
					id: `${portletNamespace}inviteUsersDialog`,
					redirect: redirectURL,
				});
			}
			else {
				Liferay.Util.openToast({
					message: Liferay.Language.get(
						'your-request-failed-to-complete'
					),
					title: Liferay.Language.get('error'),
					type: 'danger',
				});
			}
		}
	};

	return (
		<ClayForm className="lfr-form-content" id={formId}>
			{inputGroups.map((inputGroup, index) => (
				<InviteUserFormGroup
					accountRoles={inputGroup.accountRoles}
					availableAccountRoles={availableAccountRoles}
					emailAddresses={inputGroup.emailAddresses}
					id={inputGroup.id}
					index={index}
					key={inputGroup.id}
					onAccountRoleItemsChange={(items) =>
						setAccountRoles(inputGroup.id, items)
					}
					onEmailAddressItemsChange={(items) =>
						setEmailAddresses(inputGroup.id, items)
					}
					portletNamespace={portletNamespace}
				/>
			))}

			<ClayLayout.SheetFooter>
				<ClayButton
					displayType="secondary"
					onClick={() => {
						setInputGroups([
							...inputGroups,
							{
								accountRoles: [],
								emailAddresses: [],
								id: `inputGroup-${inputGroups.length}`,
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
