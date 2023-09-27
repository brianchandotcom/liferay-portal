/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

interface AddObjectValidationProps {
	apiURL: string;
	objectValidationRuleEngines: ObjectValidationType[];
}
export default function AddObjectValidation({
	apiURL,
	objectValidationRuleEngines,
}: AddObjectValidationProps): JSX.Element;
export {};
