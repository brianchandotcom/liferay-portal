/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Button from '@clayui/button';
import ClayForm, {ClayInput} from '@clayui/form';
import ClayLayout from '@clayui/layout';
import ClayLink from '@clayui/link';
import ClayPanel from '@clayui/panel';
import {FieldBase} from 'frontend-js-components-web';
import React from 'react';

import {useConfigurationForm} from './hooks/useConfigurationForm';

const FORM_ID = 'configurationForm';

export default function ConfigurationForm({
	accountEntryId,
	backURL,
	externalReferenceCode,
}: {
	accountEntryId: number;
	backURL: string;
	externalReferenceCode: string;
}) {
	const {handleSubmit, isSubmitting, loading, setField, values} =
		useConfigurationForm({accountEntryId, externalReferenceCode});

	if (loading) {
		return null;
	}

	return (
		<ClayLayout.ContainerFluid className="configuration-form p-4">
			<ClayForm id={FORM_ID} onSubmit={handleSubmit}>
				<ClayPanel collapsable={false}>
					<ClayPanel.Body>
						<h2 className="mb-4">
							{Liferay.Language.get('account-configuration')}
						</h2>

						<FieldBase
							id="environmentURLs"
							label={Liferay.Language.get('environment-url')}
						>
							<ClayInput
								id="environmentURLs"
								name="environmentURLs"
								onChange={(event) =>
									setField(
										'environmentURLs',
										event.target.value
									)
								}
								type="text"
								value={values.environmentURLs}
							/>
						</FieldBase>

						<FieldBase
							id="recipientEmailAddress"
							label={Liferay.Language.get('notification-email')}
						>
							<ClayInput
								id="recipientEmailAddress"
								name="recipientEmailAddress"
								onChange={(event) =>
									setField(
										'recipientEmailAddress',
										event.target.value
									)
								}
								type="email"
								value={values.recipientEmailAddress}
							/>
						</FieldBase>

						<div className="mt-4">
							<Button
								disabled={isSubmitting}
								displayType="primary"
								type="submit"
							>
								{Liferay.Language.get('save')}
							</Button>

							<ClayLink
								button
								className="ml-2"
								displayType="secondary"
								href={backURL}
							>
								{Liferay.Language.get('cancel')}
							</ClayLink>
						</div>
					</ClayPanel.Body>
				</ClayPanel>
			</ClayForm>
		</ClayLayout.ContainerFluid>
	);
}
