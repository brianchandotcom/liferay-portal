/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayPanel from '@clayui/panel';
import {
	Input,
	MultiSelectItem,
	MultipleSelect,
	SingleSelect,
} from '@liferay/object-js-components-web';
import {
	ILearnResourceContext,
	LearnMessage,
	LearnResourcesContext,
} from 'frontend-js-components-web';
import React, {useEffect, useState} from 'react';

import {
	getCheckedChildren,
	handleMultiSelectRoleItemsChange,
	uncheckMultiSelectItemChildrens,
} from './rolesUtil';

interface SecondaryRecipientsProps {
	emailNotificationRoles: MultiSelectItem[];
	learnResources: ILearnResourceContext;
	recipientOptions: LabelValueObject[];
	setValues: (values: Partial<NotificationTemplate>) => void;
	values: NotificationTemplate;
}

export function resetRecipientTypeValue(newRecipientTypeValue: string) {
	if (newRecipientTypeValue === 'email') {
		return '';
	}

	return [];
}

export function SecondaryRecipient({
	emailNotificationRoles,
	learnResources,
	recipientOptions,
	setValues,
	values,
}: SecondaryRecipientsProps) {
	const [bccRolesList, setBCCRolesList] = useState<MultiSelectItem[]>([]);
	const [ccRolesList, setCCRolesList] = useState<MultiSelectItem[]>([]);
	const [recipient] = values.recipients as EmailRecipients[];

	const handleRecipientRoleChange = (
		items: MultiSelectItem[],
		recipientKey: 'cc' | 'bcc',
		setRoleList: (value: MultiSelectItem[]) => void
	) => {
		const newRecipients = handleMultiSelectRoleItemsChange(items);

		setValues({
			...values,
			recipients: [
				{
					...(values.recipients[0] as EmailRecipients),
					[recipientKey]: newRecipients,
				},
			],
		});

		setRoleList(items);
	};

	const handleRecipientTypeChange = (
		newRecipientTypeValue: string,
		recipientKey: 'cc' | 'bcc',
		roleList: MultiSelectItem[],
		recipientTypeKey: 'ccType' | 'bccType',
		setRoleList: (value: MultiSelectItem[]) => void
	) => {
		if (newRecipientTypeValue === 'email') {
			const newRoleList = uncheckMultiSelectItemChildrens(roleList);
			setRoleList(newRoleList);
		}
		setValues({
			...values,
			recipients: [
				{
					...recipient,
					[recipientKey]: resetRecipientTypeValue(
						newRecipientTypeValue
					),
					[recipientTypeKey]: newRecipientTypeValue as string,
				},
			],
		});
	};

	useEffect(() => {
		if (emailNotificationRoles.length && !ccRolesList.length) {
			setCCRolesList(emailNotificationRoles);
		}

		if (
			recipient.ccType === 'role' &&
			Array.isArray(recipient.cc) &&
			!!recipient.cc.length &&
			(!!ccRolesList.length || !!emailNotificationRoles.length)
		) {
			const baseRoleList = ccRolesList.length
				? ccRolesList
				: emailNotificationRoles;

			setCCRolesList(
				baseRoleList.map((baseRoleElement) => {
					return {
						...baseRoleElement,
						children: getCheckedChildren(
							recipient.cc as EmailNotificationRecipients[],
							baseRoleElement.children
						),
					};
				})
			);

			return;
		}

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [emailNotificationRoles, recipient.cc]);

	useEffect(() => {
		if (emailNotificationRoles.length && !bccRolesList.length) {
			setBCCRolesList(emailNotificationRoles);
		}

		if (
			recipient.bccType === 'role' &&
			Array.isArray(recipient.bcc) &&
			!!recipient.bcc.length &&
			(!!bccRolesList.length || !!emailNotificationRoles.length)
		) {
			const baseRoleList = bccRolesList.length
				? bccRolesList
				: emailNotificationRoles;

			setBCCRolesList(
				baseRoleList.map((baseRoleElement) => {
					return {
						...baseRoleElement,
						children: getCheckedChildren(
							recipient.bcc as EmailNotificationRecipients[],
							baseRoleElement.children
						),
					};
				})
			);

			return;
		}

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [emailNotificationRoles, recipient.bcc]);

	return (
		<>
			<ClayPanel
				displayTitle={Liferay.Language.get('cc')}
				displayType="unstyled"
			>
				<ClayPanel.Body>
					<div className="row">
						<div className="col-lg-6">
							<SingleSelect<LabelValueObject>
								disabled={values.system}
								id="secondaryRecipientTypeCC"
								items={recipientOptions}
								label={Liferay.Language.get('type')}
								onSelectionChange={(value) => {
									handleRecipientTypeChange(
										value as string,
										'cc',
										ccRolesList,
										'ccType',
										setCCRolesList
									);
								}}
								selectedKey={recipient.ccType}
							/>
						</div>

						<div className="col-lg-6">
							{recipient.ccType === 'email' && (
								<Input
									disabled={values.system}
									feedbackMessage={Liferay.Language.get(
										'you-can-use-a-comma-to-enter-multiple-users'
									)}
									id="secondaryRecipientsCC"
									label={Liferay.Language.get('recipients')}
									name="secondaryRecipientsCC"
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
									placeholder={Liferay.Language.get(
										'type-email-address'
									)}
									value={
										(
											values
												.recipients[0] as EmailRecipients
										).cc as string
									}
								/>
							)}

							{recipient.ccType === 'role' && (
								<div className="lfr__notification-template-email-notification-settings-multiple-select">
									<MultipleSelect
										disabled={values.system}
										id="secondaryRecipientRolesCC"
										label={Liferay.Language.get('role')}
										options={ccRolesList}
										placeholder={Liferay.Language.get(
											'select-role'
										)}
										search
										searchPlaceholder={Liferay.Language.get(
											'search-for-a-role'
										)}
										selectAllOption
										setOptions={(items) => {
											handleRecipientRoleChange(
												items,
												'cc',
												setCCRolesList
											);
										}}
									/>

									<LearnResourcesContext.Provider
										value={learnResources}
									>
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
								</div>
							)}
						</div>
					</div>
				</ClayPanel.Body>
			</ClayPanel>

			<ClayPanel
				displayTitle={Liferay.Language.get('bcc')}
				displayType="unstyled"
			>
				<ClayPanel.Body>
					<div className="row">
						<div className="col-lg-6">
							<SingleSelect<LabelValueObject>
								disabled={values.system}
								id="secondaryRecipientTypeBCC"
								items={recipientOptions}
								label={Liferay.Language.get('type')}
								onSelectionChange={(value) => {
									handleRecipientTypeChange(
										value as string,
										'bcc',
										bccRolesList,
										'bccType',
										setBCCRolesList
									);
								}}
								selectedKey={recipient.bccType}
							/>
						</div>

						<div className="col-lg-6">
							{recipient.bccType === 'email' && (
								<Input
									disabled={values.system}
									feedbackMessage={Liferay.Language.get(
										'you-can-use-a-comma-to-enter-multiple-users'
									)}
									id="secondaryRecipientsBCC"
									label={Liferay.Language.get('recipients')}
									name="secondaryRecipientsBCC"
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
									placeholder={Liferay.Language.get(
										'type-email-address'
									)}
									value={
										(
											values
												.recipients[0] as EmailRecipients
										).bcc as string
									}
								/>
							)}

							{recipient.bccType === 'role' && (
								<div className="lfr__notification-template-email-notification-settings-multiple-select">
									<MultipleSelect
										disabled={values.system}
										id="secondaryRecipientRolesBCC"
										label={Liferay.Language.get('role')}
										options={bccRolesList}
										placeholder={Liferay.Language.get(
											'select-role'
										)}
										search
										searchPlaceholder={Liferay.Language.get(
											'search-for-a-role'
										)}
										selectAllOption
										setOptions={(items) => {
											handleRecipientRoleChange(
												items,
												'bcc',
												setBCCRolesList
											);
										}}
									/>

									<LearnResourcesContext.Provider
										value={learnResources}
									>
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
								</div>
							)}
						</div>
					</div>
				</ClayPanel.Body>
			</ClayPanel>
		</>
	);
}
