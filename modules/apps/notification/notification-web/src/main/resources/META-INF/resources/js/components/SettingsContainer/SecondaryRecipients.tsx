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

import {getCheckedChildren, getRoles} from './rolesUtils';

interface SecondaryRecipientsProps {
	baseResourceURL: string;
	learnResources: ILearnResourceContext;
	recipientOptions: LabelValueObject[];
	setValues: (values: Partial<NotificationTemplate>) => void;
	values: NotificationTemplate;
}

export function SecondaryRecipient({
	baseResourceURL,
	learnResources,
	recipientOptions,
	setValues,
	values,
}: SecondaryRecipientsProps) {
	const [bccRolesList, setBCCRolesList] = useState<MultiSelectItem[]>([]);
	const [ccRolesList, setCCRolesList] = useState<MultiSelectItem[]>([]);
	const [secondaryRecipient] = values.recipients as EmailRecipients[];

	const handleMultiSelectItemsChange = (items: MultiSelectItem[]) => {
		const newRecipients: EmailNotificationRecipients[] = [];

		if (items.length) {
			const [itemsGroup] = items as MultiSelectItem[];

			itemsGroup.children.forEach((child) => {
				if (child.checked) {
					newRecipients.push({['roleName']: child.value});
				}
			});
		}

		return newRecipients;
	};

	useEffect(() => {
		const makeFetch = async () => {
			const [roles] = await getRoles(baseResourceURL);

			if (
				Array.isArray(secondaryRecipient.cc) &&
				!!secondaryRecipient.cc.length
			) {
				setCCRolesList([
					{
						...roles,
						children: getCheckedChildren(
							secondaryRecipient.cc,
							roles.children
						),
					},
				]);

				return;
			}

			setCCRolesList([roles]);
		};

		makeFetch();
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [secondaryRecipient.cc]);

	useEffect(() => {
		const makeFetch = async () => {
			const [roles] = await getRoles(baseResourceURL);

			if (
				Array.isArray(secondaryRecipient.bcc) &&
				!!secondaryRecipient.bcc.length
			) {
				setBCCRolesList([
					{
						...roles,
						children: getCheckedChildren(
							secondaryRecipient.bcc,
							roles.children
						),
					},
				]);

				return;
			}

			setBCCRolesList([roles]);
		};

		makeFetch();
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [secondaryRecipient.bcc]);

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
								items={recipientOptions}
								label={Liferay.Language.get('type')}
								onSelectionChange={(value) => {
									setValues({
										...values,
										recipients: [
											{
												...secondaryRecipient,
												cc: [],
												ccType: value as string,
											},
										],
									});
								}}
								selectedKey={secondaryRecipient.ccType}
							/>
						</div>

						<div className="col-lg-6">
							{secondaryRecipient.ccType === 'email' && (
								<Input
									disabled={values.system}
									feedbackMessage={Liferay.Language.get(
										'you-can-use-a-comma-to-enter-multiple-users'
									)}
									label={Liferay.Language.get('recipients')}
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
									placeholder={Liferay.Language.get(
										'type-email-address'
									)}
									value={
										(values
											.recipients[0] as EmailRecipients)
											.cc as string
									}
								/>
							)}

							{secondaryRecipient.ccType === 'role' && (
								<div className="lfr__notification-template-email-notification-settings-multiple-select">
									<MultipleSelect
										disabled={values.system}
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
											const newRecipients = handleMultiSelectItemsChange(
												items
											);
											setValues({
												...values,
												recipients: [
													{
														...values.recipients[0],
														cc: newRecipients,
													},
												],
											});
											setCCRolesList(items);
										}}
									/>

									<LearnResourcesContext.Provider
										value={learnResources}
									>
										<div className="lfr__notification-template-email-notification-settings-multiple-select-help-text">
											<span>
												{Liferay.Language.get(
													'account-roles-are-subject-to-account-restriction'
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
								items={recipientOptions}
								label={Liferay.Language.get('type')}
								onSelectionChange={(value) => {
									setValues({
										...values,
										recipients: [
											{
												...secondaryRecipient,
												bcc: [],
												bccType: value as string,
											},
										],
									});
								}}
								selectedKey={secondaryRecipient.bccType}
							/>
						</div>

						<div className="col-lg-6">
							{secondaryRecipient.bccType === 'email' && (
								<Input
									disabled={values.system}
									feedbackMessage={Liferay.Language.get(
										'you-can-use-a-comma-to-enter-multiple-users'
									)}
									label={Liferay.Language.get('recipients')}
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
									placeholder={Liferay.Language.get(
										'type-email-address'
									)}
									value={
										(values
											.recipients[0] as EmailRecipients)
											.bcc as string
									}
								/>
							)}

							{secondaryRecipient.bccType === 'role' && (
								<div className="lfr__notification-template-email-notification-settings-multiple-select">
									<MultipleSelect
										disabled={values.system}
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
											const newRecipients = handleMultiSelectItemsChange(
												items
											);
											setValues({
												...values,
												recipients: [
													{
														...values.recipients[0],
														bcc: newRecipients,
													},
												],
											});
											setBCCRolesList(items);
										}}
									/>

									<LearnResourcesContext.Provider
										value={learnResources}
									>
										<div className="lfr__notification-template-email-notification-settings-multiple-select-help-text">
											<span>
												{Liferay.Language.get(
													'account-roles-are-subject-to-account-restriction'
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
