/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FormError, Input} from '@liferay/object-js-components-web';
import {InputLocalized} from 'frontend-js-components-web';
import React from 'react';

import {NotificationTemplateError} from '../EditNotificationTemplate';

interface SenderProps {
	errors: FormError<NotificationTemplate & NotificationTemplateError>;
	selectedLocale: Locale;
	setValues: (values: Partial<NotificationTemplate>) => void;
	values: NotificationTemplate;
}

export function Sender({
	errors,
	selectedLocale,
	setValues,
	values,
}: SenderProps) {
	return (
		<div className="row">
			<div className="col-lg-6">
				<Input
					disabled={values.system}
					error={errors.from}
					label={Liferay.Language.get('email-address')}
					name="fromAddress"
					onChange={({target}) =>
						setValues({
							...values,
							recipients: [
								{
									...values.recipients[0],
									from: target.value,
								},
							],
						})
					}
					required
					value={(values.recipients[0] as EmailRecipients).from}
				/>
			</div>

			<div className="col-lg-6">
				<InputLocalized
					disabled={values.system}
					error={errors.fromName}
					label={Liferay.Language.get('name')}
					name="fromName"
					onChange={(translation) => {
						setValues({
							...values,
							recipients: [
								{
									...values.recipients[0],
									fromName: translation,
								},
							],
						});
					}}
					placeholder=""
					required
					selectedLocale={selectedLocale}
					translations={
						(values.recipients[0] as EmailRecipients).fromName
					}
				/>
			</div>
		</div>
	);
}
