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
import ClayLabel from '@clayui/label';
import ClayManagementToolbar from '@clayui/management-toolbar';
import ClayMultiSelect from '@clayui/multi-select';
import {
	API,
	Card,
	Input,
	InputLocalized,
	RichTextLocalized,
	SingleSelect,
	openToast,
	useForm,
} from '@liferay/object-js-components-web';
import {fetch} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import {Attachments} from './Attachments';
import {DefinitionOfTerms} from './DefinitionOfTerms';

import './EditNotificationTemplate.scss';

const HEADERS = new Headers({
	'Accept': 'application/json',
	'Content-Type': 'application/json',
});

const defaultLanguageId = Liferay.ThemeDisplay.getDefaultLanguageId();
interface Item {
	label?: string;
	value?: string;
}

interface IProps {
	baseResourceURL: string;
	editorConfig: object;
	notificationTemplateId: number;
	notificationTemplateType: string;
}
interface Role {
	accountId: number;
	description: string;
	displayName: string;
	id: number;
	name: string;
	roleId: number;
}

interface User {
	alternateName: string;
	givenName: string;
}

export type TNotificationTemplate = {
	attachmentObjectFieldIds: string[] | number[];
	bcc: string;
	body: LocalizedValue<string>;
	cc: string;
	description: string;
	from: string;
	fromName: LocalizedValue<string>;
	name: string;
	objectDefinitionId: number | null;
	recipientType: string;
	recipients:
		| Partial<TEmailRecipients>[]
		| Partial<TUserNotificationRecipients>[]
		| [];
	subject: LocalizedValue<string>;
	to: LocalizedValue<string>;
	type: string;
};

type TUserNotificationRecipients = {
	[key in 'term' | 'userScreenName' | 'roleName']?: string;
};

type TEmailRecipients = {
	bcc: string;
	cc: string;
	from: string;
	fromName: LocalizedValue<string>;
	to: LocalizedValue<string>;
};

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

export default function EditNotificationTemplate({
	baseResourceURL,
	editorConfig,
	notificationTemplateId = 0,
	notificationTemplateType,
}: IProps) {
	notificationTemplateId = Number(notificationTemplateId);

	const [selectedLocale, setSelectedLocale] = useState(
		Liferay.ThemeDisplay.getDefaultLanguageId
	);

	const [templateTitle, setTemplateTitle] = useState<string>();

	const [rolesList, setRolesList] = useState<Role[]>([]);

	const [userList, setUserList] = useState<User[]>([]);

	const [multiSelectItems, setMultiSelectItems] = useState<Item[]>([]);

	const [searchTerm, setSearchTerm] = useState('');

	const [toTerms, setToTerms] = useState<string>('');

	const validate = (values: any) => {
		const errors: {
			bcc?: string;
			body?: string;
			cc?: string;
			description?: string;
			from?: string;
			fromName?: string;
			name?: string;
			subject?: string;
			to?: string;
			type?: string;
		} = {};

		if (!values.name) {
			errors.name = Liferay.Language.get('required');
		}

		if (
			(notificationTemplateType === 'email' &&
				!values.recipients[0].from) ||
			(!Liferay.FeatureFlags['LPS-162133'] && !values.from)
		) {
			errors.from = Liferay.Language.get('required');
		}

		if (
			(notificationTemplateType === 'email' &&
				!values.recipients[0].fromName[defaultLanguageId]) ||
			(!Liferay.FeatureFlags['LPS-162133'] &&
				!values.fromName[defaultLanguageId])
		) {
			errors.fromName = Liferay.Language.get('required');
		}

		return errors;
	};

	const onSubmit = async (notification: TNotificationTemplate) => {
		const response = await fetch(
			notificationTemplateId !== 0
				? `/o/notification/v1.0/notification-templates/${notificationTemplateId}`
				: '/o/notification/v1.0/notification-templates',
			{
				body: JSON.stringify(notification),
				headers: HEADERS,
				method: notificationTemplateId !== 0 ? 'PUT' : 'POST',
			}
		);

		if (response.ok) {
			openToast({
				message: Liferay.Language.get(
					'notification-template-was-saved-successfully'
				),
				type: 'success',
			});

			window.location.assign(document.referrer);
		}
		else if (response.status === 404) {
			openToast({
				message: Liferay.Language.get('an-error-occurred'),
				type: 'danger',
			});
		}
		else {
			openToast({
				message: Liferay.Language.get('an-error-occurred'),
				type: 'danger',
			});
		}
	};

	let recipientInitialValue: any;

	if (
		notificationTemplateType === '' ||
		notificationTemplateType === 'email'
	) {
		recipientInitialValue = [
			{
				bcc: '',
				cc: '',
				from: '',
				fromName: {
					[defaultLanguageId]: '',
				},
				to: {
					[defaultLanguageId]: '',
				},
			} as TEmailRecipients,
		];
	}
	else {
		recipientInitialValue = [];
	}

	const initialValues = {
		...(Liferay.FeatureFlags['LPS-162133'] && {
			recipientType:
				notificationTemplateType === 'userNotification'
					? 'term'
					: 'email',
		}),
		...(Liferay.FeatureFlags['LPS-162133'] && {
			type: notificationTemplateType,
		}),
		body: {
			[defaultLanguageId]: '',
		},
		description: '',
		name: '',
		objectDefinitionId: 0,
		recipients: recipientInitialValue,

		...(!Liferay.FeatureFlags['LPS-162133'] && {
			bcc: '',
			cc: '',
			from: '',
			fromName: {
				[defaultLanguageId]: '',
			},
			to: {
				[defaultLanguageId]: '',
			},
		}),
		subject: {
			[defaultLanguageId]: '',
		},
		type: notificationTemplateType,
	};

	const {errors, handleSubmit, setValues, values} = useForm({
		initialValues,
		onSubmit,
		validate,
	});

	const getAccountRoles = async (searchTerm: string) => {
		const apiURL = '/o/headless-admin-user/v1.0/accounts/0/account-roles';
		const query = `${apiURL}?page=1&pageSize=10&sort=name:asc${
			searchTerm ? `&filter=contains(name, '${searchTerm}')` : ''
		}`;
		const response = await fetch(query, {
			headers: HEADERS,
			method: 'GET',
		});

		const responseJSON = await response.json();

		const roles = responseJSON.items.map(({displayName, name}: Role) => {
			return {
				label: displayName,
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
		const newRecipients = [] as TUserNotificationRecipients[];
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
		if (notificationTemplateId !== 0) {
			API.getNotificationTemplate(notificationTemplateId).then(
				({
					attachmentObjectFieldIds,
					body,
					description,
					name,
					objectDefinitionId,
					recipientType,
					recipients,
					subject,
					type,
				}) => {
					setValues({
						...values,
						attachmentObjectFieldIds,
						body,
						description,
						name,
						objectDefinitionId,
						recipientType,
						recipients,
						subject,
						type,
					});

					setTemplateTitle(name);

					if (recipientType === 'term') {
						setToTerms(
							(recipients as TUserNotificationRecipients[])
								.map(({term}) => term)
								.join()
						);
					}
				}
			);
		}
		else {
			setTemplateTitle(
				Liferay.Language.get('untitled-notification-template')
			);
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [notificationTemplateId]);

	useEffect(() => {
		const delayDebounceFn = setTimeout(() => {
			values.recipientType === 'role'
				? getAccountRoles(searchTerm)
				: getUserAccounts(searchTerm);
		}, 500);

		return () => clearTimeout(delayDebounceFn);
	}, [searchTerm, values.recipientType]);

	useEffect(() => {
		if (
			values.recipientType === 'role' ||
			values.recipientType === 'user'
		) {
			const recipientList = values.recipients as TUserNotificationRecipients[];
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
		<ClayForm onSubmit={handleSubmit}>
			<ClayManagementToolbar className="lfr__notification-template-management-toolbar">
				<ClayManagementToolbar.ItemList>
					<h2>{templateTitle}</h2>

					{Liferay.FeatureFlags['LPS-162133'] && (
						<div className="lfr__notification-template-label">
							{values.type === 'email' ? (
								<ClayLabel displayType="success">
									{Liferay.Language.get('email')}
								</ClayLabel>
							) : (
								<ClayLabel displayType="info">
									{Liferay.Language.get('user-notification')}
								</ClayLabel>
							)}
						</div>
					)}
				</ClayManagementToolbar.ItemList>

				<ClayManagementToolbar.ItemList>
					<ClayButton
						displayType="secondary"
						onClick={() => window.history.back()}
					>
						{Liferay.Language.get('cancel')}
					</ClayButton>

					<ClayButton className="inline-item-after" type="submit">
						{Liferay.Language.get('save')}
					</ClayButton>
				</ClayManagementToolbar.ItemList>
			</ClayManagementToolbar>

			<div className="lfr__notification-template-container">
				<div className="lfr__notification-template-cards">
					<div className="row">
						<div className="col-lg-6 lfr__notification-template-card">
							<Card title={Liferay.Language.get('basic-info')}>
								<Input
									error={errors.name}
									label={Liferay.Language.get('name')}
									name="name"
									onChange={({target}) =>
										setValues({
											...values,
											name: target.value,
										})
									}
									required
									value={values.name}
								/>

								<Input
									component="textarea"
									label={Liferay.Language.get('description')}
									name="description"
									onChange={({target}) =>
										setValues({
											...values,
											description: target.value,
										})
									}
									type="text"
									value={values.description}
								/>

								{!Liferay.FeatureFlags['LPS-162133'] && (
									<SingleSelect
										disabled
										label={Liferay.Language.get('type')}
										options={[]}
										value={Liferay.Language.get('email')}
									/>
								)}
							</Card>
						</div>

						<div className="col-lg-6 lfr__notification-template-card">
							<Card title={Liferay.Language.get('settings')}>
								{Liferay.FeatureFlags['LPS-162133'] &&
								values.type === 'userNotification' ? (
									<>
										<SingleSelect
											label={Liferay.Language.get(
												'recipients'
											)}
											onChange={(item) => {
												setValues({
													...values,
													recipientType: item.value,
													recipients: [],
												});

												if (item.value === 'role') {
													getAccountRoles('');
												}
											}}
											options={RECIPIENT_OPTIONS}
											value={
												RECIPIENT_OPTIONS.find(
													(recipient) =>
														values.recipientType ===
														recipient.value
												)?.label
											}
										/>

										{values.recipientType === 'term' && (
											<>
												<Input
													component="textarea"
													label={Liferay.Language.get(
														'to'
													)}
													onChange={({target}) => {
														setToTerms(
															target.value
														);
													}}
													placeholder={Liferay.Util.sub(
														Liferay.Language.get(
															'use-terms-to-configure-recipients-for-this-notifications-x'
														),
														'[%ENTRY_CREATOR%], [%BUYER_NAME%]',
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
													<label>
														{Liferay.Language.get(
															'role'
														)}
													</label>

													<ClayMultiSelect
														items={multiSelectItems}
														onChange={setSearchTerm}
														onItemsChange={
															handleMultiSelectItemsChange
														}
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
													<label>
														{Liferay.Language.get(
															'users'
														)}
													</label>

													<ClayMultiSelect
														items={multiSelectItems}
														onChange={setSearchTerm}
														onItemsChange={
															handleMultiSelectItemsChange
														}
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
													...(Liferay.FeatureFlags[
														'LPS-162133'
													]
														? {
																recipients: [
																	{
																		...values
																			.recipients[0],
																		to: translation,
																	},
																],
														  }
														: {to: translation}),
												});
											}}
											placeholder=""
											selectedLocale={selectedLocale}
											translations={
												(values
													.recipients[0] as TEmailRecipients)
													.to
											}
										/>

										<div className="row">
											<div className="col-lg-6">
												<Input
													label={Liferay.Language.get(
														'cc'
													)}
													name="cc"
													onChange={({target}) =>
														setValues({
															...values,
															...(Liferay
																.FeatureFlags[
																'LPS-162133'
															]
																? {
																		recipients: [
																			{
																				...values
																					.recipients[0],
																				cc:
																					target.value,
																			},
																		],
																  }
																: {
																		cc:
																			target.value,
																  }),
														})
													}
													value={
														Liferay.FeatureFlags[
															'LPS-162133'
														]
															? (values
																	.recipients[0] as TEmailRecipients)
																	.cc
															: values.cc
													}
												/>
											</div>

											<div className="col-lg-6">
												<Input
													label={Liferay.Language.get(
														'bcc'
													)}
													name="bcc"
													onChange={({target}) =>
														setValues({
															...values,
															...(Liferay
																.FeatureFlags[
																'LPS-162133'
															]
																? {
																		recipients: [
																			{
																				...values
																					.recipients[0],
																				bcc:
																					target.value,
																			},
																		],
																  }
																: {
																		bcc:
																			target.value,
																  }),
														})
													}
													value={
														Liferay.FeatureFlags[
															'LPS-162133'
														]
															? (values
																	.recipients[0] as TEmailRecipients)
																	.bcc
															: values.bcc
													}
												/>
											</div>
										</div>

										<div className="row">
											<div className="col-lg-6">
												<Input
													error={errors.from}
													label={Liferay.Language.get(
														'from-address'
													)}
													name="fromAddress"
													onChange={({target}) =>
														setValues({
															...values,
															...(Liferay
																.FeatureFlags[
																'LPS-162133'
															]
																? {
																		recipients: [
																			{
																				...values
																					.recipients[0],
																				from:
																					target.value,
																			},
																		],
																  }
																: {
																		from:
																			target.value,
																  }),
														})
													}
													required
													value={
														Liferay.FeatureFlags[
															'LPS-162133'
														]
															? (values
																	.recipients[0] as TEmailRecipients)
																	.from
															: values.from
													}
												/>
											</div>

											<div className="col-lg-6">
												<InputLocalized
													error={errors.fromName}
													label={Liferay.Language.get(
														'from-name'
													)}
													name="fromName"
													onChange={(translation) => {
														setValues({
															...values,
															...(Liferay
																.FeatureFlags[
																'LPS-162133'
															]
																? {
																		recipients: [
																			{
																				...values
																					.recipients[0],
																				fromName: translation,
																			},
																		],
																  }
																: {
																		fromName: translation,
																  }),
														});
													}}
													placeholder=""
													required
													selectedLocale={
														selectedLocale
													}
													translations={
														Liferay.FeatureFlags[
															'LPS-162133'
														]
															? (values
																	.recipients[0] as TEmailRecipients)
																	.fromName
															: values.fromName!
													}
												/>
											</div>
										</div>
									</>
								)}
							</Card>
						</div>
					</div>

					<Card title={Liferay.Language.get('content')}>
						<InputLocalized
							{...(values.type === 'userNotification' && {
								component: 'textarea',
							})}
							label={Liferay.Language.get('subject')}
							name="subject"
							onChange={(translation) => {
								setValues({
									...values,
									subject: translation,
								});
							}}
							placeholder=""
							selectedLocale={selectedLocale}
							translations={values.subject}
						/>

						{Liferay.FeatureFlags['LPS-162133'] &&
							values.type === 'email' && (
								<RichTextLocalized
									editorConfig={editorConfig}
									label={Liferay.Language.get('body')}
									name="body"
									onSelectedLocaleChange={({label}) =>
										setSelectedLocale(label)
									}
									onTranslationsChange={(translation) => {
										setValues({
											...values,
											body: translation,
										});
									}}
									selectedLocale={selectedLocale}
									translations={values.body}
								/>
							)}

						<DefinitionOfTerms baseResourceURL={baseResourceURL} />

						{Liferay.FeatureFlags['LPS-162133'] &&
							values.type === 'email' && (
								<Attachments
									setValues={setValues}
									values={values}
								/>
							)}
					</Card>
				</div>
			</div>
		</ClayForm>
	);
}
