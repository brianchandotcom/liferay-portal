/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Text} from '@clayui/core';
import {Card, FormError} from '@liferay/object-js-components-web';
import React from 'react';

import {NotificationTemplateError} from '../EditNotificationTemplate';
import {EmailNotificationSettings} from './EmailNotificationSettings';
import {UserNotificationSettings} from './UserNotificationSettings';

interface SettingsContainerProps {
	errors: FormError<NotificationTemplate & NotificationTemplateError>;
	selectedLocale: Locale;
	setValues: (values: Partial<NotificationTemplate>) => void;
	values: NotificationTemplate;
}

export function SettingsContainer({
	errors,
	selectedLocale,
	setValues,
	values,
}: SettingsContainerProps) {
	return (
		<Card title={Liferay.Language.get('settings')}>
			<Text as="span" color="secondary">
				{Liferay.Language.get(
					'use-terms-to-populate-fields-dynamically'
				)}
			</Text>

			{values.type === 'userNotification' ? (
				<UserNotificationSettings
					setValues={setValues}
					values={values}
				/>
			) : (
				<EmailNotificationSettings
					errors={errors}
					selectedLocale={selectedLocale}
					setValues={setValues}
					values={values}
				/>
			)}
		</Card>
	);
}
