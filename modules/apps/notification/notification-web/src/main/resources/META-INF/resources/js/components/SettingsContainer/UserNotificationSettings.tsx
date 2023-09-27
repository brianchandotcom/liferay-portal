/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm from '@clayui/form';
import ClayMultiSelect from '@clayui/multi-select';
import {Input, SingleSelect} from '@liferay/object-js-components-web';
import {fetch} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

interface Role {
	description: string;
	id: number;
	name: string;
	roleType: string;
}

interface User {
	alternateName: string;
	givenName: string;
}

interface Item extends Partial<LabelValueObject> {}

interface UserNotificationSettingsProps {
	setValues: (values: Partial<NotificationTemplate>) => void;
	values: NotificationTemplate;
}

const HEADERS = new Headers({
	'Accept': 'application/json',
	'Content-Type': 'application/json',
});

const RECIPIENT_OPTIONS = [
	{
		label: Liferay.Language.get('definition-of-terms'),
		value: 'term',
	},
	{
		label: Liferay.Language.get('user'),
		value: 'user',
	},
	{
		label: Liferay.Language.get('role'),
		value: 'role',
	},
];

export function UserNotificationSettings({
	setValues,
	values,
}: UserNotificationSettingsProps) {
	const [multiSelectItems, setMultiSelectItems] = useState<Item[]>([]);
	const [toTerms, setToTerms] = useState<string>('');
	const [rolesList, setRolesList] = useState<Item[]>([]);
	const [userList, setUserList] = useState<Item[]>([]);
	const [searchTerm, setSearchTerm] = useState('');

	const getRoles = async (searchTerm: string) => {
		const query = `/o/headless-admin-user/v1.0/roles?page=1&pageSize=10${
			searchTerm ? `&search=${searchTerm}` : ''
		}`;

		const response = await fetch(query, {
			headers: HEADERS,
			method: 'GET',
		});

		const responseJSON = await response.json();

		const roles = responseJSON.items.map(({name}: Role) => {
			return {
				label: name,
				value: name,
			};
		});

		setRolesList(roles);
	};

	const getUserAccounts = async (searchTerm: string) => {
		const apiURL = '/o/headless-admin-user/v1.0/user-accounts';
		const query = `${apiURL}?page=1&pageSize=10&sort=givenName:asc${
			searchTerm ? `&search=${searchTerm}` : ''
		}`;

		const response = await fetch(query, {
			headers: HEADERS,
			method: 'GET',
		});

		const responseJSON = await response.json();

		const users = responseJSON.items.map(
			({alternateName, givenName}: User) => {
				return {
					label: givenName,
					value: alternateName,
				};
			}
		);

		setUserList(users);
	};

	const handleMultiSelectItemsChange = (items: Item[]) => {
		const key =
			values.recipientType === 'role' ? 'roleName' : 'userScreenName';

		const newRecipients = items.map((item) => ({[key]: item.value}));

		setValues({
			...values,
			recipients: newRecipients,
		});

		setMultiSelectItems(items);
	};

	useEffect(() => {
		const delayDebounceFn = setTimeout(() => {
			values.recipientType === 'role'
				? getRoles(searchTerm)
				: getUserAccounts(searchTerm);
		}, 500);

		return () => clearTimeout(delayDebounceFn);
	}, [searchTerm, values.recipientType]);

	useEffect(() => {
		const recipientList = values.recipients as UserNotificationRecipients[];

		const generateMultiSelectItems = () => {
			return recipientList.map((item) => {
				if (values.recipientType === 'user') {
					return {
						label: item.userScreenName,
						value: item.userScreenName,
					};
				}

				return {
					label: item.roleName,
					value: item.roleName,
				};
			});
		};

		if (
			values.recipientType === 'role' ||
			values.recipientType === 'user'
		) {
			setMultiSelectItems(generateMultiSelectItems());
		}

		if (values.recipientType === 'term') {
			setToTerms(recipientList.map(({term}) => term).join());
		}
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
			<SingleSelect
				label={Liferay.Language.get('recipients')}
				onChange={(item) => {
					setValues({
						...values,
						recipientType: item.value,
						recipients: [],
					});

					if (item.value === 'role') {
						getRoles('');
					}
				}}
				options={RECIPIENT_OPTIONS}
				value={
					RECIPIENT_OPTIONS.find(
						(recipient) => values.recipientType === recipient.value
					)?.label
				}
			/>

			{values.recipientType === 'term' && (
				<>
					<Input
						component="textarea"
						label={Liferay.Language.get('to')}
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
				</>
			)}

			{values.recipientType === 'role' && (
				<>
					<ClayForm.Group>
						<label>{Liferay.Language.get('role')}</label>

						<ClayMultiSelect
							items={multiSelectItems}
							loadingState={4}
							onChange={setSearchTerm}
							onItemsChange={handleMultiSelectItemsChange}
							placeholder={Liferay.Language.get('enter-a-role')}
							sourceItems={rolesList}
							value={searchTerm}
						/>

						<ClayForm.Text>
							{Liferay.Language.get(
								'you-can-use-a-comma-to-enter-multiple-users'
							)}
						</ClayForm.Text>
					</ClayForm.Group>
				</>
			)}

			{values.recipientType === 'user' && (
				<>
					<ClayForm.Group>
						<label>{Liferay.Language.get('users')}</label>

						<ClayMultiSelect
							items={multiSelectItems}
							loadingState={4}
							onChange={setSearchTerm}
							onItemsChange={handleMultiSelectItemsChange}
							placeholder={Liferay.Language.get(
								'enter-user-name'
							)}
							sourceItems={userList}
							value={searchTerm}
						/>

						<ClayForm.Text>
							{Liferay.Language.get(
								'you-can-use-a-comma-to-enter-multiple-users'
							)}
						</ClayForm.Text>
					</ClayForm.Group>
				</>
			)}
		</>
	);
}
