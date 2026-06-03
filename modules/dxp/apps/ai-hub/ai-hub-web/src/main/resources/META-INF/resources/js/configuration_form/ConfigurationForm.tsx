/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Button from '@clayui/button';
import ClayForm from '@clayui/form';
import ClayLayout from '@clayui/layout';
import ClayLink from '@clayui/link';
import ClayPanel from '@clayui/panel';
import React from 'react';

import AccountConfigurationPanel from './AccountConfigurationPanel';
import CredentialsPanel from './CredentialsPanel';
import {useConfigurationForm} from './hooks/useConfigurationForm';

const FORM_ID = 'configurationForm';

export default function ConfigurationForm({
	accountEntryId,
	backURL,
	clientId,
	externalReferenceCode,
}: {
	accountEntryId: number;
	backURL: string;
	clientId?: string;
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
						<AccountConfigurationPanel
							setField={setField}
							values={values}
						/>

						{clientId && (
							<>
								<hr className="my-4" />

								<CredentialsPanel clientId={clientId} />
							</>
						)}

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
