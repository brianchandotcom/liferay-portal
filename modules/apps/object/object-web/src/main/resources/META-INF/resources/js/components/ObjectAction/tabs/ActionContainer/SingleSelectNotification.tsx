/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Option, Text} from '@clayui/core';
import ClayLabel from '@clayui/label';
import {SingleSelect} from '@liferay/object-js-components-web';
import React from 'react';

import {ActionError} from '../../ObjectActionContainer';
import {NotificationTemplateAction} from './ThenContainer';
import {updateUsePreferredLanguageForGuestsParameter} from './updateUsePreferredLanguageForGuestsParameter';

import './SingleSelectNotification.scss';

interface SingleSelectAddObejctEntryProps {
	errors: ActionError;
	notificationTemplates: NotificationTemplateAction[];
	setSelectedNotificationTemplate: (
		value: Partial<NotificationTemplateAction>
	) => void;
	setValues: (values: Partial<ObjectAction>) => void;
	values: Partial<ObjectAction>;
}

export function SingleSelectNotification({
	errors,
	notificationTemplates,
	setSelectedNotificationTemplate,
	setValues,
	values,
}: SingleSelectAddObejctEntryProps) {
	return (
		<SingleSelect<NotificationTemplateAction>
			className="lfr-object__action-builder-notification-then"
			disabled={values.system}
			error={errors.objectActionExecutorKey}
			items={notificationTemplates}
			onSelectionChange={(value) => {
				const selectedNotificationTemplate = notificationTemplates.find(
					(notificationTemplate) => {
						return notificationTemplate.value === value;
					}
				);

				if (selectedNotificationTemplate) {
					setSelectedNotificationTemplate(
						selectedNotificationTemplate
					);

					const parameters =
						updateUsePreferredLanguageForGuestsParameter(
							values,
							selectedNotificationTemplate.type
						);

					setValues({
						...values,
						parameters: {
							...parameters,
							notificationTemplateExternalReferenceCode:
								selectedNotificationTemplate.value,
						},
					});
				}
			}}
			required
			selectedKey={
				values.parameters?.notificationTemplateExternalReferenceCode
			}
		>
			{(item) => (
				<Option key={item.value} textValue={item.label}>
					<div className="lfr-object__action-builder-notification-option">
						<Text size={3} weight="semi-bold">
							{item.label}
						</Text>

						<ClayLabel
							className="lfr-object__action-builder-notification-option-label"
							displayType={
								item.type === 'email' ? 'success' : 'info'
							}
						>
							{item.type === 'email'
								? Liferay.Language.get('email')
								: Liferay.Language.get('user-notification')}
						</ClayLabel>
					</div>
				</Option>
			)}
		</SingleSelect>
	);
}
