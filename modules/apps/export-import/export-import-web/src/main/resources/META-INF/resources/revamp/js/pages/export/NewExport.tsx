/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import {
	ESteps,
	IGenericStepProps,
	IStepProps,
	Wizard,
} from '../../components/Wizard';
import DataSelectionStep from './steps/DataSelectionStep';
import SettingsStep from './steps/SettingsStep';
import SetupStep from './steps/SetupStep';

const STEPS: IStepProps<IGenericStepProps, ESteps>[] = [
	{
		Component: SetupStep,
		available: true,
		key: ESteps.Setup,
		title: Liferay.Language.get('setup'),
	},
	{
		Component: DataSelectionStep,
		available: false,
		key: ESteps.DataSelection,
		title: Liferay.Language.get('data-selection'),
	},
	{
		Component: SettingsStep,
		available: false,
		key: ESteps.Settings,
		title: Liferay.Language.get('settings'),
	},
];

export function NewExport({backURL}: {backURL: string}) {
	return <Wizard backURL={backURL} externalSteps={STEPS} />;
}
