/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayPanel from '@clayui/panel';
import {FormError} from '@liferay/object-js-components-web';
import {ILearnResourceContext} from 'frontend-js-components-web';
import React from 'react';

import {NotificationTemplateError} from '../EditNotificationTemplate';
import {PrimaryRecipient} from './PrimaryRecipients';
import {SecondaryRecipient} from './SecondaryRecipients';
import {Sender} from './Sender';

import './EmailNotificationSettings.scss';

interface EmailNotificationSettingsProps {
	baseResourceURL: string;
	errors: FormError<NotificationTemplate & NotificationTemplateError>;
	learnResources: ILearnResourceContext;
	selectedLocale: Locale;
	setValues: (values: Partial<NotificationTemplate>) => void;
	values: NotificationTemplate;
}

const RECIPIENT_OPTIONS = [
	{
		label: Liferay.Language.get('user-email-address'),
		value: 'email',
	},
	{
		label: Liferay.Language.get('roles'),
		value: 'role',
	},
] as LabelValueObject[];

if (Liferay.FeatureFlags['LPD-50091']) {
	RECIPIENT_OPTIONS.push({
		label: Liferay.Language.get('user-groups'),
		value: 'user-group',
	});
}

const SUBSCRIBERS_OPTION = {
	label: Liferay.Language.get('subscribers'),
	value: 'subscribers',
} as LabelValueObject;

export function EmailNotificationSettings({
	baseResourceURL,
	errors,
	learnResources,
	selectedLocale,
	setValues,
	values,
}: EmailNotificationSettingsProps) {
	return (
		<div className="lfr__notification-template-email-notification-settings">
			<ClayPanel
				displayTitle={Liferay.Language.get('sender')}
				displayType="unstyled"
			>
				<ClayPanel.Body>
					<Sender
						errors={errors}
						selectedLocale={selectedLocale}
						setValues={setValues}
						values={values}
					/>
				</ClayPanel.Body>
			</ClayPanel>

			<ClayPanel
				displayTitle={Liferay.Language.get('primary-recipients')}
				displayType="unstyled"
			>
				<ClayPanel.Body>
					<PrimaryRecipient
						baseResourceURL={baseResourceURL}
						errors={errors}
						learnResources={learnResources}
						recipientOptions={
							Liferay.FeatureFlags['LPD-17564']
								? [...RECIPIENT_OPTIONS, SUBSCRIBERS_OPTION]
								: RECIPIENT_OPTIONS
						}
						selectedLocale={selectedLocale}
						setValues={setValues}
						values={values}
					/>
				</ClayPanel.Body>
			</ClayPanel>

			<ClayPanel
				displayTitle={Liferay.Language.get('secondary-recipients')}
				displayType="unstyled"
			>
				<ClayPanel.Body>
					<SecondaryRecipient
						baseResourceURL={baseResourceURL}
						learnResources={learnResources}
						recipientOptions={RECIPIENT_OPTIONS}
						selectedLocale={selectedLocale}
						setValues={setValues}
						values={values}
					/>
				</ClayPanel.Body>
			</ClayPanel>
		</div>
	);
}
