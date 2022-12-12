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

import {Text} from '@clayui/core';
import ClayForm from '@clayui/form';
import ClayMultiSelect from '@clayui/multi-select';
import {
	Card,
	FormError,
	Input,
	InputLocalized,
	SingleSelect,
} from '@liferay/object-js-components-web';
import {fetch} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import {NotificationTemplateError} from './EditNotificationTemplate';

import './EditNotificationTemplate.scss';

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

interface SettingsContainerProps {
	errors: FormError<NotificationTemplate & NotificationTemplateError>;
	selectedLocale: Locale;
	setValues: (values: Partial<NotificationTemplate>) => void;
	values: NotificationTemplate;
}

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

const HEADERS = new Headers({
	'Accept': 'application/json',
	'Content-Type': 'application/json',
});

export function SettingsContainer({
	errors,
	selectedLocale,
	setValues,
	values,
}: SettingsContainerProps) {
	const [multiSelectItems, setMultiSelectItems] = useState<Item[]>([]);
	const [toTerms, setToTerms] = useState<string>('');
	const [rolesList, setRolesList] = useState<Role[]>([]);
	const [userList, setUserList] = useState<User[]>([]);
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
		const newRecipients = [] as UserNotificationRecipients[];
		items.forEach((item) => {
			newRecipients.push({[key]: item.value});
		});
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
		if (
			values.recipientType === 'role' ||
			values.recipientType === 'user'
		) {
			const recipientList = values.recipients as UserNotificationRecipients[];
			let multiSelectItems = [];

			if (values.recipientType === 'user') {
				multiSelectItems = recipientList.map((item) => {
					return {
						label: item.userScreenName,
						value: item.userScreenName,
					};
				});
			}
			else {
				multiSelectItems = recipientList.map((item) => {
					return {
						label: item.roleName,
						value: item.roleName,
					};
				});
			}

			setMultiSelectItems(multiSelectItems);
		}

		if (values.recipientType === 'term') {
			setToTerms(
				(values.recipients as UserNotificationRecipients[])
					.map(({term}) => term)
					.join()
			);
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
		<Card title={Liferay.Language.get('settings')}>
			<Text as="span" color="secondary">
				{Liferay.Language.get(
					'use-terms-to-populate-fields-dynamically'
				)}
			</Text>

			{Liferay.FeatureFlags['LPS-162133'] &&
			values.type === 'userNotification' ? (
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
								(recipient) =>
									values.recipientType === recipient.value
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
									'[%ENTRY_CREATOR%]',
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
									onChange={setSearchTerm}
									onItemsChange={handleMultiSelectItemsChange}
									placeholder={Liferay.Language.get(
										'enter-a-role'
									)}
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
			) : (
				<>
					<InputLocalized
						label={Liferay.Language.get('to')}
						name="to"
						onChange={(translation) => {
							setValues({
								...values,
								recipients: [
									{
										...values.recipients[0],
										to: translation,
									},
								],
							});
						}}
						placeholder=""
						selectedLocale={selectedLocale}
						translations={
							(values.recipients[0] as EmailRecipients).to
						}
					/>

					<div className="row">
						<div className="col-lg-6">
							<Input
								label={Liferay.Language.get('cc')}
								name="cc"
								onChange={({target}) =>
									setValues({
										...values,

										recipients: [
											{
												...values.recipients[0],
												cc: target.value,
											},
										],
									})
								}
								value={
									(values.recipients[0] as EmailRecipients).cc
								}
							/>
						</div>

						<div className="col-lg-6">
							<Input
								label={Liferay.Language.get('bcc')}
								name="bcc"
								onChange={({target}) =>
									setValues({
										...values,

										recipients: [
											{
												...values.recipients[0],
												bcc: target.value,
											},
										],
									})
								}
								value={
									(values.recipients[0] as EmailRecipients)
										.bcc
								}
							/>
						</div>
					</div>

					<div className="row">
						<div className="col-lg-6">
							<Input
								error={errors.from}
								label={Liferay.Language.get('from-address')}
								name="fromAddress"
								onChange={({target}) =>
									setValues({
										...values,
										recipients: [
											{
												...values.recipients[0],
												from: target.value,
											},
										],
									})
								}
								required
								value={
									(values.recipients[0] as EmailRecipients)
										.from
								}
							/>
						</div>

						<div className="col-lg-6">
							<InputLocalized
								error={errors.fromName}
								label={Liferay.Language.get('from-name')}
								name="fromName"
								onChange={(translation) => {
									setValues({
										...values,

										recipients: [
											{
												...values.recipients[0],
												fromName: translation,
											},
										],
									});
								}}
								placeholder=""
								required
								selectedLocale={selectedLocale}
								translations={
									(values.recipients[0] as EmailRecipients)
										.fromName
								}
							/>
						</div>
					</div>
				</>
			)}
		</Card>
	);
}
