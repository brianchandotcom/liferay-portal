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
	API,
	Card,
	Input,
	InputLocalized,
	ManagementToolbar,
	SingleSelect,
	invalidateRequired,
	openToast,
	useForm,
} from '@liferay/object-js-components-web';
import {fetch} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import {defaultLanguageId} from '../util/constants';

import './EditNotificationTemplate.scss';
import {BasicInfoContainer} from './BasicInfoContainer';
import ContentContainer from './ContentContainer';

const HEADERS = new Headers({
	'Accept': 'application/json',
	'Content-Type': 'application/json',
});

interface Item extends Partial<LabelValueObject> {}

interface IProps {
	backURL: string;
	baseResourceURL: string;
	editorConfig: object;
	externalReferenceCode: string;
	notificationTemplateId: number;
	notificationTemplateType: string;
	portletNamespace: string;
}

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
	backURL,
	baseResourceURL,
	editorConfig,
	externalReferenceCode,
	notificationTemplateId = 0,
	notificationTemplateType,
	portletNamespace,
}: IProps) {
	notificationTemplateId = Number(notificationTemplateId);

	const [isSubmitted, setIsSubmitted] = useState(false);

	const [selectedLocale, setSelectedLocale] = useState<Locale>(
		Liferay.ThemeDisplay.getDefaultLanguageId
	);

	const [templateTitle, setTemplateTitle] = useState<string>('');

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

		if (notificationTemplateType === 'email' || values.type === 'email') {
			if (!values.recipients[0].from) {
				errors.from = Liferay.Language.get('required');
			}

			if (!values.recipients[0].fromName[defaultLanguageId]) {
				errors.fromName = Liferay.Language.get('required');
			}

			if (!values.recipients[0].to[defaultLanguageId]) {
				errors.to = Liferay.Language.get('required');
			}
		}

		return errors;
	};

	const onSubmit = async (notification: NotificationTemplate) => {
		if (isSubmitted) {
			return;
		}

		setIsSubmitted(true);

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
			} as EmailRecipients,
		];
	}
	else {
		recipientInitialValue = [];
	}

	const initialValues: NotificationTemplate = {
		...(Liferay.FeatureFlags['LPS-162133'] && {
			recipientType:
				notificationTemplateType === 'userNotification'
					? 'term'
					: 'email',
		}),
		attachmentObjectFieldIds: [],
		body: {
			[defaultLanguageId]: '',
		},
		description: '',
		editorType: 'richText' as editorTypeOptions,
		externalReferenceCode: '',
		name: '',
		objectDefinitionExternalReferenceCode: '',
		objectDefinitionId: 0,
		recipientType:
			notificationTemplateType === 'userNotification' ? 'term' : 'email',
		recipients: recipientInitialValue,
		subject: {
			[defaultLanguageId]: '',
		},
		type: notificationTemplateType,
	};

	const {errors, setValues, validateSubmit, values} = useForm({
		initialValues,
		onSubmit,
		validate,
	});

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
		const makeFetch = async () => {
			if (notificationTemplateId !== 0) {
				const {
					attachmentObjectFieldIds,
					body,
					description,
					editorType,
					externalReferenceCode,
					name,
					objectDefinitionExternalReferenceCode,
					objectDefinitionId,
					recipientType,
					recipients,
					subject,
					type,
				} = await API.getNotificationTemplateById(
					notificationTemplateId
				);

				setValues({
					...values,
					attachmentObjectFieldIds,
					body,
					description,
					editorType,
					externalReferenceCode,
					name,
					objectDefinitionExternalReferenceCode,
					objectDefinitionId,
					recipientType,
					recipients,
					subject,
					type,
				});

				setTemplateTitle(name);

				if (recipientType === 'term') {
					setToTerms(
						(recipients as UserNotificationRecipients[])
							.map(({term}) => term)
							.join()
					);
				}
			}
			else {
				setTemplateTitle(
					Liferay.Language.get('untitled-notification-template')
				);
			}
		};

		makeFetch();
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [notificationTemplateId]);

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
		<ClayForm>
			<ManagementToolbar
				backURL={backURL}
				badgeClassName={
					values.type === 'email' ? 'label-success' : 'label-info'
				}
				badgeLabel={
					values.type === 'email'
						? Liferay.Language.get('email')
						: Liferay.Language.get('user-notification')
				}
				entityId={notificationTemplateId}
				externalReferenceCode={
					invalidateRequired(values.externalReferenceCode)
						? externalReferenceCode
						: values.externalReferenceCode
				}
				externalReferenceCodeSaveURL={`/o/notification/v1.0/notification-templates/${notificationTemplateId}`}
				hasPublishPermission={true}
				hasUpdatePermission={true}
				helpMessage={Liferay.Language.get(
					'internal-key-to-reference-the-notification-template'
				)}
				label={templateTitle}
				onExternalReferenceCodeChange={(value) => {
					setValues({
						externalReferenceCode: value,
					});
				}}
				onGetEntity={() =>
					API.getNotificationTemplateById(notificationTemplateId)
				}
				onSubmit={validateSubmit}
				portletNamespace={portletNamespace}
				showEntityDetails={notificationTemplateId !== 0}
			/>

			<div className="lfr__notification-template-container">
				<div className="lfr__notification-template-cards">
					<div className="row">
						<div className="col-lg-6 lfr__notification-template-card">
							<BasicInfoContainer
								errors={errors}
								setValues={setValues}
								values={values}
							/>
						</div>

						<div className="col-lg-6 lfr__notification-template-card">
							<Card title={Liferay.Language.get('settings')}>
								<Text as="span" color="secondary">
									{Liferay.Language.get(
										'use-terms-to-populate-fields-dynamically'
									)}
								</Text>

								{values.type === 'userNotification' ? (
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
													getRoles('');
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
											error={errors.to}
											label={Liferay.Language.get('to')}
											name="to"
											onChange={(translation) => {
												setValues({
													...values,
													recipients: [
														{
															...values
																.recipients[0],
															to: translation,
														},
													],
												});
											}}
											placeholder=""
											required
											selectedLocale={selectedLocale}
											translations={
												(values
													.recipients[0] as EmailRecipients)
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

															recipients: [
																{
																	...values
																		.recipients[0],
																	cc:
																		target.value,
																},
															],
														})
													}
													value={
														(values
															.recipients[0] as EmailRecipients)
															.cc
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

															recipients: [
																{
																	...values
																		.recipients[0],
																	bcc:
																		target.value,
																},
															],
														})
													}
													value={
														(values
															.recipients[0] as EmailRecipients)
															.bcc
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
															recipients: [
																{
																	...values
																		.recipients[0],
																	from:
																		target.value,
																},
															],
														})
													}
													required
													value={
														(values
															.recipients[0] as EmailRecipients)
															.from
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

															recipients: [
																{
																	...values
																		.recipients[0],
																	fromName: translation,
																},
															],
														});
													}}
													placeholder=""
													required
													selectedLocale={
														selectedLocale
													}
													translations={
														(values
															.recipients[0] as EmailRecipients)
															.fromName
													}
												/>
											</div>
										</div>
									</>
								)}
							</Card>
						</div>
					</div>

					<ContentContainer
						baseResourceURL={baseResourceURL}
						editorConfig={editorConfig}
						selectedLocale={selectedLocale}
						setSelectedLocale={setSelectedLocale}
						setValues={setValues}
						values={values}
					/>
				</div>
			</div>
		</ClayForm>
	);
}
