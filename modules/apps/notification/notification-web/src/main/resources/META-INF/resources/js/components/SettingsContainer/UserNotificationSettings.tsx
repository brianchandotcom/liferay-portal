/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	Input,
	MultiSelectItem,
	MultipleSelect,
	SingleSelect,
} from '@liferay/object-js-components-web';
import {fetch} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import {HEADERS} from '../../util/constants';
import {getRoles, getUserNotificationRoles} from './rolesUtil';

interface User {
	alternateName: string;
	givenName: string;
}

interface UserNotificationSettingsProps {
	setValues: (values: Partial<NotificationTemplate>) => void;
	values: NotificationTemplate;
}

const RECIPIENT_OPTIONS = [
	{
		label: Liferay.Language.get('role'),
		value: 'role',
	},
	{
		label: Liferay.Language.get('definition-of-terms'),
		value: 'term',
	},
	{
		label: Liferay.Language.get('user'),
		value: 'user',
	},
	Liferay.FeatureFlags['LPD-50091'] && {
		label: Liferay.Language.get('user-group'),
		value: 'user-group',
	},
] as LabelValueObject[];

export function UserNotificationSettings({
	setValues,
	values,
}: UserNotificationSettingsProps) {
	const [rolesList, setRolesList] = useState<MultiSelectItem[]>([]);
	const [toTerms, setToTerms] = useState<string>('');
	const [userGroupList, setUserGroupList] = useState<MultiSelectItem[]>([]);
	const [userList, setUserList] = useState<MultiSelectItem[]>([]);

	const getUserRoles = async () => {
		const roles = getUserNotificationRoles(
			await getRoles(),
			values.recipients as {['roleName']: string}[]
		);

		setRolesList([roles]);
		setUserGroupList([]);
		setUserList([]);
	};

	const getTerms = async () => {
		const recipientList = values.recipients as UserNotificationRecipients[];

		setToTerms(recipientList.map(({term}) => term).join());
	};

	const getUserAccounts = async () => {
		const apiURL = '/o/headless-admin-user/v1.0/user-accounts';
		const query = `${apiURL}?page=-1&sort=givenName:asc`;

		const response = await fetch(query, {
			headers: HEADERS,
			method: 'GET',
		});

		const {items} = (await response.json()) as {items: User[]};

		const selectedUser = new Set(
			(values.recipients as Partial<UserNotificationRecipients>[]).map(
				(recipient) => recipient.userScreenName
			)
		);

		const users = {
			children: items.map(({alternateName, givenName}) => {
				return {
					checked: selectedUser.has(alternateName),
					label: givenName,
					value: alternateName,
				};
			}),
			label: '',
			value: 'usersList',
		} as MultiSelectItem;

		setRolesList([]);
		setUserGroupList([]);
		setUserList([users]);
	};

	const getUserGroups = async () => {
		const apiURL = '/o/headless-admin-user/v1.0/user-groups';
		const query = `${apiURL}?page=-1&sort=name:asc`;

		const response = await fetch(query, {
			headers: HEADERS,
			method: 'GET',
		});

		const {items} = (await response.json()) as {items: {name: string}[]};

		const selectedUserGroups = new Set(
			(values.recipients as Partial<UserNotificationRecipients>[]).map(
				(recipient) => recipient.userGroupName
			)
		);

		const userGroups = {
			children: items.map(({name}) => ({
				checked: selectedUserGroups.has(name),
				label: name,
				value: name,
			})),
			label: '',
			value: 'userGroupList',
		} as MultiSelectItem;

		setRolesList([]);
		setUserGroupList([userGroups]);
		setUserList([]);
	};

	const handleMultiSelectItemsChange = (items: MultiSelectItem[]) => {
		const keySet = {
			'role': 'roleName',
			'term': 'userScreenName',
			'user': 'userScreenName',
			'user-group': 'userGroupName',
		} as {[key: string]: string};

		const key = keySet[values.recipientType];

		const newRecipients: UserNotificationRecipients[] = [];

		if (items.length) {
			const [itemsGroup] = items as MultiSelectItem[];

			itemsGroup.children.forEach((child) => {
				if (child.checked) {
					newRecipients.push({[key]: child.value});
				}
			});
		}

		setValues({
			...values,
			recipients: newRecipients,
		});
	};

	useEffect(() => {
		const makeFetch = async () => {
			if (values.recipientType === 'role') {
				await getUserRoles();

				return;
			}

			if (values.recipientType === 'term') {
				await getTerms();

				return;
			}

			if (values.recipientType === 'user') {
				await getUserAccounts();

				return;
			}

			if (values.recipientType === 'user-group') {
				await getUserGroups();

				return;
			}
		};

		makeFetch();

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [values.recipientType]);

	useEffect(() => {
		const regex = /,/g;
		const toItems = toTerms
			.replace(regex, ' ')
			.split(' ')
			.filter((item) => {
				if (item !== '') {
					return item;
				}
			});

		if (toItems.length) {
			const toRecipients = toItems.map((item) => {
				return {term: item};
			});

			setValues({
				...values,
				recipients: toRecipients,
			});
		}

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [toTerms]);

	return (
		<>
			<SingleSelect<LabelValueObject>
				disabled={values.system}
				items={RECIPIENT_OPTIONS}
				label={Liferay.Language.get('recipients')}
				onSelectionChange={(value) => {
					setValues({
						...values,
						recipientType: value as string,
						recipients: [],
					});

					if (value === 'role') {
						getUserRoles();
					}
					else if (value === 'user-group') {
						getUserGroups();
					}
				}}
				selectedKey={values.recipientType}
			/>

			{values.recipientType === 'term' && (
				<Input
					component="textarea"
					label={Liferay.Language.get('to[recipient]')}
					onChange={({target}) => {
						setToTerms(target.value);
					}}
					placeholder={Liferay.Util.sub(
						Liferay.Language.get(
							'use-terms-to-configure-recipients-for-this-notification-x'
						),
						'[%OBJECT_AUTHOR_ID%]',
						'.'
					)}
					type="text"
					value={toTerms}
				/>
			)}

			{values.recipientType === 'role' && (
				<MultipleSelect
					disabled={values.system}
					label={Liferay.Language.get('role')}
					options={rolesList}
					placeholder={Liferay.Language.get('enter-a-role')}
					setOptions={(items) => {
						handleMultiSelectItemsChange(items);
						setRolesList(items);
					}}
				/>
			)}

			{values.recipientType === 'user' && (
				<MultipleSelect
					disabled={values.system}
					label={Liferay.Language.get('users')}
					options={userList}
					placeholder={Liferay.Language.get('enter-user-name')}
					setOptions={(items) => {
						handleMultiSelectItemsChange(items);
						setUserList(items);
					}}
				/>
			)}

			{values.recipientType === 'user-group' &&
				Liferay.FeatureFlags['LPD-50091'] && (
					<MultipleSelect
						disabled={values.system}
						label={Liferay.Language.get('user-group')}
						options={userGroupList}
						placeholder={Liferay.Language.get('select-user-group')}
						search
						searchPlaceholder={Liferay.Language.get(
							'search-for-a-user-group'
						)}
						selectAllOption
						setOptions={(items) => {
							handleMultiSelectItemsChange(items);
							setUserGroupList(items);
						}}
					/>
				)}
		</>
	);
}
