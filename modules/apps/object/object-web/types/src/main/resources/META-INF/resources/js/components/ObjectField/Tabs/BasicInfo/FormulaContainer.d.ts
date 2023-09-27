/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import {ObjectFieldErrors} from '../../ObjectFieldFormBase';
interface FormulaContainerProps {
	errors: ObjectFieldErrors;
	objectFieldSettings: ObjectFieldSetting[];
	setValues: (values: Partial<ObjectField>) => void;
}
export declare function FormulaContainer({
	errors,
	objectFieldSettings,
	setValues,
}: FormulaContainerProps): JSX.Element;
export {};
