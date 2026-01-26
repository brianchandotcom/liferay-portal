/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import {Wizard, WizardStep} from '../../components/Wizard';
import DataSelectionStep from './steps/DataSelectionStep';
import FileSelectionStep from './steps/FileSelectionStep';
import SettingsStep from './steps/SettingsStep';

export function NewImport({backURL}: {backURL: string}) {
	return (
		<Wizard
			backURL={backURL}
			onSubmit={() => {}}
			onSubmitLabel={Liferay.Language.get('import')}
		>
			<WizardStep
				description={Liferay.Language.get(
					'name-your-import-process-and-upload-your-file'
				)}
				title={Liferay.Language.get('setup')}
			>
				<FileSelectionStep />
			</WizardStep>

			<WizardStep
				description={Liferay.Language.get(
					'select-the-data-from-your-file-that-you-would-like-to-import'
				)}
				title={Liferay.Language.get('data-selection')}
			>
				<DataSelectionStep />
			</WizardStep>

			<WizardStep
				description={Liferay.Language.get(
					'set-up-your-import-configuration'
				)}
				title={Liferay.Language.get('settings')}
			>
				<SettingsStep />
			</WizardStep>
		</Wizard>
	);
}
