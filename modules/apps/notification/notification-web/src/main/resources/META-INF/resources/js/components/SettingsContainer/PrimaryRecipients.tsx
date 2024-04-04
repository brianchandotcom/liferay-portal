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
import {InputLocalized} from 'frontend-js-components-web';
import React, {useEffect, useState} from 'react';

import {NotificationTemplateError} from '../EditNotificationTemplate';
import {getCheckedChildren, getRoles} from './rolesUtils';

interface PrimaryRecipientProps {
	baseResourceURL: string;
	errors: FormError<NotificationTemplate & NotificationTemplateError>;
	recipientOptions: LabelValueObject[];
	selectedLocale: Locale;
	setValues: (values: Partial<NotificationTemplate>) => void;
	values: NotificationTemplate;
}

export function PrimaryRecipient({
	baseResourceURL,
	errors,
	recipientOptions,
	selectedLocale,
	setValues,
	values,
}: PrimaryRecipientProps) {
	const [primaryRecipient] = values.recipients as EmailRecipients[];
	const [toRolesList, setToRolesList] = useState<MultiSelectItem[]>([]);

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

		setValues({
			...values,
			recipients: [
				{
					...values.recipients[0],
					to: newRecipients,
				},
			],
		});
	};

	useEffect(() => {
		const makeFetch = async () => {
			const [roles] = await getRoles(baseResourceURL);
			if (
				Array.isArray(primaryRecipient.to) &&
				!!primaryRecipient.to.length
			) {
				setToRolesList([
					{
						...roles,
						children: getCheckedChildren(
							primaryRecipient.to,
							roles.children
						),
					},
				]);

				return;
			}

			setToRolesList([roles]);
		};

		makeFetch();
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [primaryRecipient.to]);

	return (
		<>
			<SingleSelect<LabelValueObject>
				disabled={values.system}
				items={recipientOptions}
				label={Liferay.Language.get('type')}
				onSelectionChange={(value) => {
					setValues({
						...values,
						recipients: [
							{
								...primaryRecipient,
								to: [],
								toType: value as string,
							},
						],
					});
				}}
				required
				selectedKey={primaryRecipient.toType}
			/>

			{primaryRecipient.toType === 'email' && (
				<>
					<InputLocalized
						disabled={values.system}
						error={errors.to}
						label={Liferay.Language.get('recipients')}
						name="recipients"
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
						placeholder={Liferay.Language.get('type-email-adress')}
						required
						selectedLocale={selectedLocale}
						translations={
							(values.recipients[0] as EmailRecipients)
								.to as LocalizedValue<string>
						}
					/>
				</>
			)}

			{primaryRecipient.toType === 'role' && (
				<MultipleSelect
					disabled={values.system}
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
						handleMultiSelectItemsChange(items);
						setToRolesList(items);
					}}
				/>
			)}

			<ClayForm.Group className="ml-1 row">
				<div className="mr-2">
					<ClayCheckbox
						checked={
							(values.recipients[0] as EmailRecipients)
								.singleRecipient
						}
						disabled={values.system}
						label={Liferay.Language.get('send-emails-separately')}
						onChange={({target: {checked}}) => {
							setValues({
								...values,
								recipients: [
									{
										...values.recipients[0],
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
							'each-to-primaryRecipient-will-receive-separate-emails'
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
	);
}
