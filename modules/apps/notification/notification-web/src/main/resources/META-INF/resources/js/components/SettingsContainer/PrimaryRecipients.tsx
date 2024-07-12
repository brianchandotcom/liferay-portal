/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm, {ClayCheckbox} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {ClayTooltipProvider} from '@clayui/tooltip';
import {
	FormError,
	MultiSelectItem,
	MultipleSelect,
	SingleSelect,
} from '@liferay/object-js-components-web';
import {
	ILearnResourceContext,
	InputLocalized,
	LearnMessage,
	LearnResourcesContext,
} from 'frontend-js-components-web';
import React, {useEffect, useState} from 'react';

import {NotificationTemplateError} from '../EditNotificationTemplate';
import {
	getCheckedChildren,
	handleMultiSelectRoleItemsChange,
	uncheckMultiSelectItemChildrens,
} from './rolesUtil';

interface PrimaryRecipientProps {
	emailNotificationRoles: MultiSelectItem[];
	errors: FormError<NotificationTemplate & NotificationTemplateError>;
	learnResources: ILearnResourceContext;
	recipientOptions: LabelValueObject[];
	selectedLocale: Locale;
	setValues: (values: Partial<NotificationTemplate>) => void;
	values: NotificationTemplate;
}

export function PrimaryRecipient({
	emailNotificationRoles,
	errors,
	learnResources,
	recipientOptions,
	selectedLocale,
	setValues,
	values,
}: PrimaryRecipientProps) {
	const [recipient] = values.recipients as EmailRecipients[];
	const [toRolesList, setToRolesList] = useState<MultiSelectItem[]>([]);

	useEffect(() => {
		if (emailNotificationRoles.length && !toRolesList.length) {
			setToRolesList(emailNotificationRoles);
		}

		if (
			recipient.toType === 'role' &&
			Array.isArray(recipient.to) &&
			!!recipient.to.length &&
			(!!toRolesList.length || !!emailNotificationRoles.length)
		) {
			const baseRoleList = toRolesList.length
				? toRolesList
				: emailNotificationRoles;

			setToRolesList(
				baseRoleList.map((baseRoleElement) => {
					return {
						...baseRoleElement,
						children: getCheckedChildren(
							recipient.to as EmailNotificationRecipients[],
							baseRoleElement.children
						),
					};
				})
			);

			return;
		}

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [emailNotificationRoles, recipient.to]);

	return (
		<>
			<SingleSelect<LabelValueObject>
				disabled={values.system}
				id="primaryRecipientType"
				items={recipientOptions}
				label={Liferay.Language.get('type')}
				onSelectionChange={(value) => {
					if (value === 'email') {
						const newToRoleList =
							uncheckMultiSelectItemChildrens(toRolesList);
						setToRolesList(newToRoleList);
					}
					setValues({
						...values,
						recipients: [
							{
								...recipient,
								to: [],
								toType: value as string,
							},
						],
					});
				}}
				required
				selectedKey={recipient.toType}
			/>

			{recipient.toType === 'email' && (
				<div className="lfr__notification-template-email-notification-settings-primary-recipient-input-localized">
					<InputLocalized
						disabled={values.system}
						error={errors.to}
						helpMessage={Liferay.Language.get(
							'you-can-use-a-comma-to-enter-multiple-users'
						)}
						id="primaryRecipients"
						label={Liferay.Language.get('recipients')}
						name="recipients"
						onChange={(translation) => {
							setValues({
								...values,
								recipients: [
									{
										...recipient,
										to: translation,
									},
								],
							});
						}}
						placeholder={Liferay.Language.get('type-email-address')}
						required
						selectedLocale={selectedLocale}
						translations={recipient.to as LocalizedValue<string>}
					/>
				</div>
			)}

			{recipient.toType === 'role' && (
				<div className="lfr__notification-template-email-notification-settings-multiple-select">
					<MultipleSelect
						disabled={values.system}
						error={errors.to}
						id="primaryRecipientRoles"
						label={Liferay.Language.get('role')}
						options={toRolesList}
						placeholder={Liferay.Language.get('select-role')}
						required
						search
						searchPlaceholder={Liferay.Language.get(
							'search-for-a-role'
						)}
						selectAllOption
						setOptions={(items) => {
							const newRecipients =
								handleMultiSelectRoleItemsChange(items);

							setValues({
								...values,
								recipients: [
									{
										...recipient,
										to: newRecipients,
									},
								],
							});

							setToRolesList(items);
						}}
					/>

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
				</div>
			)}

			<>
				<ClayForm.Group className="ml-1 row">
					<div className="mr-2">
						<ClayCheckbox
							checked={recipient.singleRecipient}
							disabled={values.system}
							label={Liferay.Language.get(
								'send-emails-separately'
							)}
							onChange={({target: {checked}}) => {
								setValues({
									...values,
									recipients: [
										{
											...recipient,
											singleRecipient: checked,
										},
									],
								});
							}}
						/>
					</div>

					<ClayTooltipProvider>
						<span
							title={Liferay.Language.get(
								'each-to-recipient-will-receive-separate-emails'
							)}
						>
							<ClayIcon
								className="lfr__notification-template-email-notification-settings-tooltip-icon"
								symbol="question-circle-full"
							/>
						</span>
					</ClayTooltipProvider>
				</ClayForm.Group>
			</>
		</>
	);
}
