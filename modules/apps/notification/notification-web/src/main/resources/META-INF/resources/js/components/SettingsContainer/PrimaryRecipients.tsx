/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm, {ClayCheckbox} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {ClayTooltipProvider} from '@clayui/tooltip';
import {FormError, SingleSelect} from '@liferay/object-js-components-web';
import {InputLocalized} from 'frontend-js-components-web';
import React from 'react';

import {NotificationTemplateError} from '../EditNotificationTemplate';

interface PrimaryRecipientProps {
	errors: FormError<NotificationTemplate & NotificationTemplateError>;
	recipientOptions: LabelValueObject[];
	selectedLocale: Locale;
	setValues: (values: Partial<NotificationTemplate>) => void;
	values: NotificationTemplate;
}

export function PrimaryRecipient({
	errors,
	recipientOptions,
	selectedLocale,
	setValues,
	values,
}: PrimaryRecipientProps) {
	return (
		<>
			<SingleSelect<LabelValueObject>
				disabled={values.system}
				items={recipientOptions}
				label={Liferay.Language.get('type')}
				required
				selectedKey={values.recipientType}
			/>

			{values.recipientType === 'email' && (
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
							(values.recipients[0] as EmailRecipients).to
						}
					/>
				</>
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
	);
}
