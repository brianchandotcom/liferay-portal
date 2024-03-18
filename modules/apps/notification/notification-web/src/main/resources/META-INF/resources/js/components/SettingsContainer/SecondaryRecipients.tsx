/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayPanel from '@clayui/panel';
import {Input, SingleSelect} from '@liferay/object-js-components-web';
import React from 'react';

interface SecondaryRecipientsProps {
	recipientOptions: LabelValueObject[];
	setValues: (values: Partial<NotificationTemplate>) => void;
	values: NotificationTemplate;
}

export function SecondaryRecipient({
	recipientOptions,
	setValues,
	values,
}: SecondaryRecipientsProps) {
	return (
		<>
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
								selectedKey={values.recipientType}
							/>
						</div>

						<div className="col-lg-6">
							{values.recipientType === 'email' && (
								<Input
									disabled={values.system}
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
										'type-email-adress'
									)}
									value={
										(values
											.recipients[0] as EmailRecipients)
											.bcc
									}
								/>
							)}
						</div>
					</div>
				</ClayPanel.Body>
			</ClayPanel>

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
								selectedKey={values.recipientType}
							/>
						</div>

						<div className="col-lg-6">
							{values.recipientType === 'email' && (
								<Input
									disabled={values.system}
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
										'type-email-adress'
									)}
									value={
										(values
											.recipients[0] as EmailRecipients)
											.cc
									}
								/>
							)}
						</div>
					</div>
				</ClayPanel.Body>
			</ClayPanel>
		</>
	);
}
