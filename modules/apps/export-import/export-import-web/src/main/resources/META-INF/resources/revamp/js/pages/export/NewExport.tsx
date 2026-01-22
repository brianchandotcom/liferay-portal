/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import {Wizard, WizardStep} from '../../components/Wizard';
import DataSelectionStep from './steps/DataSelectionStep';
import SettingsStep from './steps/SettingsStep';
import SetupStep from './steps/SetupStep';

export function NewExport({backURL}: {backURL: string}) {
	return (
		<Wizard backURL={backURL}>
			<WizardStep
				description={Liferay.Language.get(
					'name-your-export-and-make-an-initial-data-selection-to-narrow-down-in-the-next-step'
				)}
				title={Liferay.Language.get('setup')}
			>
				<SetupStep />
			</WizardStep>

			<WizardStep
				description={Liferay.Language.get(
					'select-and-filter-the-data-you-want-to-include-in-your-export'
				)}
				title={Liferay.Language.get('data-selection')}
			>
				<DataSelectionStep />
			</WizardStep>

			<WizardStep
				description={Liferay.Language.get(
					'configure-your-export-settings'
				)}
				title={Liferay.Language.get('settings')}
			>
				<SettingsStep />
			</WizardStep>
		</Wizard>
	);
}
