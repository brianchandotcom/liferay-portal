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
import {Card, FormError} from '@liferay/object-js-components-web';
import React from 'react';

import {NotificationTemplateError} from './EditNotificationTemplate';
import {EmailNotificationSettings} from './EmailNotificationSettings';
import {UserNotificationSettings} from './UserNotificationSettings';

import './EditNotificationTemplate.scss';

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

			{Liferay.FeatureFlags['LPS-162133'] &&
			values.type === 'userNotification' ? (
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
