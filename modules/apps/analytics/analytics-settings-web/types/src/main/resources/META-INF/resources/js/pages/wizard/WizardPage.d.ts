/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
export interface IGenericStepProps {
	onCancel: () => void;
	onChangeStep: (step: ESteps) => void;
}
export declare enum ESteps {
	ConnectAC = 0,
	Property = 1,
	People = 2,
	Attributes = 3,
}
declare const WizardPage: React.FC<React.HTMLAttributes<HTMLElement>>;
export default WizardPage;
