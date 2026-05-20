/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import React, {useState} from 'react';

import {Wizard, WizardStep} from '../../components/Wizard';
import {ImportPreview} from '../../types/exportImportPreview';
import DataSelectionStep from './steps/DataSelectionStep';
import FileSelectionStep from './steps/FileSelectionStep';
import SettingsStep, {SETTINGS_STEP_INITIAL_VALUES} from './steps/SettingsStep';

export function NewImport({
	backURL,
	importPreviewAPIURL,
}: {
	backURL: string;
	importPreviewAPIURL: string;
}) {
	const [importPreview, setImportPreview] = useState<
		ImportPreview | undefined
	>();

	return (
		<Wizard backURL={backURL}>
			<WizardStep
				description={Liferay.Language.get(
					'name-your-import-process-and-upload-your-file'
				)}
				initialValues={{
					fileSelector: undefined,
					name: '',
				}}
				isStepValid={(values) =>
					values.fileSelector instanceof File &&
					!!values.name.trim() &&
					!!importPreview
				}
				title={Liferay.Language.get('setup')}
			>
				<FileSelectionStep
					importPreviewAPIURL={importPreviewAPIURL}
					setImportPreview={setImportPreview}
				/>
			</WizardStep>

			<WizardStep
				description={Liferay.Language.get(
					'select-the-data-from-your-file-that-you-would-like-to-import'
				)}
				initialValues={{
					contentSelection: undefined,
					deletions: false,
					importPermissions: false,
				}}
				isStepValid={(values) => !!values.contentSelection}
				title={Liferay.Language.get('data-selection')}
			>
				<DataSelectionStep importPreview={importPreview} />
			</WizardStep>

			<WizardStep
				actionButton={
					<ClayButton type="submit">
						<span className="inline-item inline-item-before">
							<ClayIcon className="mr-1" symbol="import" />
						</span>

						{Liferay.Language.get('import')}
					</ClayButton>
				}
				description={Liferay.Language.get(
					'set-up-your-import-configuration'
				)}
				initialValues={SETTINGS_STEP_INITIAL_VALUES}
				onSubmit={async () => {
					alert('Import started!');
				}}
				title={Liferay.Language.get('settings')}
			>
				<SettingsStep />
			</WizardStep>
		</Wizard>
	);
}
