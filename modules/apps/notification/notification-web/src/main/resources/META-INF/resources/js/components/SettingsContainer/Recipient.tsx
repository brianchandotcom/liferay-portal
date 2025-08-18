/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	Input,
	MultiSelectItem,
	SingleSelect,
} from '@liferay/object-js-components-web';
import classNames from 'classnames';
import {
	ILearnResourceContext,
	InputLocalized,
	LearnMessage,
	LearnResourcesContext,
} from 'frontend-js-components-web';
import React, {useEffect, useState} from 'react';

import {RecipientMultipleSelect} from './RecipientMultipleSelect';
import {
	getCheckedChildren,
	uncheckMultiSelectItemChildrens,
} from './multiSelectUtil';
import {getEmailNotificationRoles} from './rolesUtil';
import {getEmailNotificationUserGroups} from './userGroupsUtil';

interface RecipientProps {
	baseResourceURL: string;
	disabled: boolean;
	displayType: 'column' | 'row';
	error?: string;
	id: 'to' | 'cc' | 'bcc';
	label: string;
	learnResources: ILearnResourceContext;
	onChange: (key: string, value: any) => void;
	onTypeChange: (key: string, type: any) => void;
	recipientOptions: LabelValueObject[];
	required?: boolean;
	selectedLocale: Locale;
	userEmailAddressLocalized?: boolean;
	values: NotificationTemplate;
}

export function Recipient({
	baseResourceURL,
	disabled,
	displayType,
	error,
	id,
	label,
	learnResources,
	onChange,
	onTypeChange,
	recipientOptions,
	required,
	selectedLocale,
	userEmailAddressLocalized,
	values,
}: RecipientProps) {
	const [rolesList, setRolesList] = useState<MultiSelectItem[]>([]);
	const [userGroupsList, setUserGroupsList] = useState<MultiSelectItem[]>([]);

	const [recipient] = values.recipients as EmailRecipients[];

	const recipientType = recipient[`${id}Type`];
	const RECIPIENT_TYPE_DETAILS = [
		{
			label: Liferay.Language.get('role'),
			name: 'roleName',
			options: rolesList,
			placeholder: Liferay.Language.get('select-role'),
			searchPlaceholder: Liferay.Language.get('search-for-a-role'),
			setOptions: setRolesList,
			type: 'role',
		},
		{
			featureFlag: 'LPD-50091',
			label: Liferay.Language.get('user-group'),
			name: 'userGroupName',
			options: userGroupsList,
			placeholder: Liferay.Language.get('select-user-group'),
			searchPlaceholder: Liferay.Language.get('search-for-a-user-group'),
			setOptions: setUserGroupsList,
			type: 'user-group',
		},
	];

	useEffect(() => {
		const fetchInitialData = async () => {
			const recipients = Array.isArray(recipient[id])
				? (recipient[id] as EmailNotificationRecipients[])
				: [];

			if (recipientType === 'role') {
				const emailNotificationRoles =
					await getEmailNotificationRoles(baseResourceURL);

				const newRolesList = emailNotificationRoles.map((role) => ({
					...role,
					children: getCheckedChildren(
						recipients,
						role.children,
						'roleName'
					),
				}));

				setRolesList(newRolesList);
			}
			if (recipientType === 'user-group') {
				const emailNotificationUserGroups =
					await getEmailNotificationUserGroups(baseResourceURL);

				const newUserGroupsList = emailNotificationUserGroups.map(
					(userGroup) => ({
						...userGroup,
						children: getCheckedChildren(
							recipients,
							userGroup.children,
							'userGroupName'
						),
					})
				);

				setUserGroupsList(newUserGroupsList);
			}
		};

		fetchInitialData();
	}, [baseResourceURL, id, recipient, recipientType]);

	return (
		<div className={displayType}>
			<div className={classNames({'col-lg-6': displayType === 'row'})}>
				<SingleSelect<LabelValueObject>
					disabled={disabled}
					id={id + 'Type'}
					items={recipientOptions}
					label={Liferay.Language.get('type')}
					onSelectionChange={(value) => {
						if (value !== 'role') {
							const newRoleList =
								uncheckMultiSelectItemChildrens(rolesList);

							setRolesList(newRoleList);
						}

						if (value !== 'user-group') {
							const newUserGroupList =
								uncheckMultiSelectItemChildrens(userGroupsList);

							setUserGroupsList(newUserGroupList);
						}

						onTypeChange(id, value as string);
					}}
					required={required}
					selectedKey={recipientType}
				/>
			</div>

			<div className={classNames({'col-lg-6': displayType === 'row'})}>
				{recipientType === 'email' && userEmailAddressLocalized && (
					<InputLocalized
						disabled={disabled}
						error={error}
						helpMessage={Liferay.Language.get(
							'you-can-use-a-comma-to-enter-multiple-users'
						)}
						id={id}
						label={label}
						name={id}
						onChange={(translation) => onChange(id, translation)}
						placeholder={Liferay.Language.get('type-email-address')}
						required={required}
						selectedLocale={selectedLocale}
						translations={recipient[id] as LocalizedValue<string>}
					/>
				)}

				{recipientType === 'email' && !userEmailAddressLocalized && (
					<Input
						disabled={disabled}
						error={error}
						feedbackMessage={Liferay.Language.get(
							'you-can-use-a-comma-to-enter-multiple-users'
						)}
						id={id}
						label={label}
						name={id}
						onChange={({target}) => onChange(id, target.value)}
						placeholder={Liferay.Language.get('type-email-address')}
						required={required}
						value={recipient[id] as string}
					/>
				)}

				{recipientType === 'subscribers' && (
					<div className="lfr__notification-template-email-notification-settings-primary-recipient-input-not-localized">
						<Input
							disabled
							id="subscribersRecipients"
							label={Liferay.Language.get('recipients')}
							name="recipients"
							required={required}
							value="[%EMAIL_RECIPIENT_ADDRESS%]"
						/>
					</div>
				)}

				{RECIPIENT_TYPE_DETAILS.map(
					({
						featureFlag,
						name,
						options,
						placeholder,
						searchPlaceholder,
						setOptions,
						type,
					}) =>
						recipientType === type &&
						(!featureFlag || Liferay.FeatureFlags[featureFlag]) && (
							<RecipientMultipleSelect
								disabled={disabled}
								error={error}
								id={`${id}${name}`}
								label={label}
								name={name as 'roleName' | 'userGroupName'}
								onRecipientsChange={(recipients) =>
									onChange(id, recipients)
								}
								options={options}
								placeholder={placeholder}
								required={required}
								searchPlaceholder={searchPlaceholder}
								setOptions={setOptions}
							/>
						)
				)}

				{recipientType === 'role' && (
					<LearnResourcesContext.Provider value={learnResources}>
						<div className="lfr__notification-template-email-notification-settings-multiple-select-help-text">
							<span>
								{Liferay.Language.get(
									'account-roles-are-subject-to-account-restrictions'
								)}
							</span>
							&nbsp;
							<LearnMessage
								className="alert-link"
								resource="notification-web"
								resourceKey="general"
							/>
						</div>
					</LearnResourcesContext.Provider>
				)}
			</div>
		</div>
	);
}
