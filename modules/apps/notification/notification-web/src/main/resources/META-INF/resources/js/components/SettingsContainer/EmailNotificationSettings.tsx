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

import {
	FormError,
	Input,
	InputLocalized,
} from '@liferay/object-js-components-web';
import React from 'react';

import {NotificationTemplateError} from '../EditNotificationTemplate';

interface EmailNotificationSettingsProps {
	errors: FormError<NotificationTemplate & NotificationTemplateError>;
	selectedLocale: Locale;
	setValues: (values: Partial<NotificationTemplate>) => void;
	values: NotificationTemplate;
}

export function EmailNotificationSettings({
	errors,
	selectedLocale,
	setValues,
	values,
}: EmailNotificationSettingsProps) {
	return (
		<>
			<InputLocalized
				label={Liferay.Language.get('to')}
				name="to"
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
				placeholder=""
				selectedLocale={selectedLocale}
				translations={(values.recipients[0] as EmailRecipients).to}
			/>

			<div className="row">
				<div className="col-lg-6">
					<Input
						label={Liferay.Language.get('cc')}
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
						value={(values.recipients[0] as EmailRecipients).cc}
					/>
				</div>

				<div className="col-lg-6">
					<Input
						label={Liferay.Language.get('bcc')}
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
						value={(values.recipients[0] as EmailRecipients).bcc}
					/>
				</div>
			</div>

			<div className="row">
				<div className="col-lg-6">
					<Input
						error={errors.from}
						label={Liferay.Language.get('from-address')}
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
						error={errors.fromName}
						label={Liferay.Language.get('from-name')}
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
		</>
	);
}
