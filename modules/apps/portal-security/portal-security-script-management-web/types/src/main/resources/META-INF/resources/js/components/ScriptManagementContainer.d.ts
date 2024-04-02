/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import './ScriptManagementContainer.scss';
export declare type GroovyScriptUseItem = {
	companyWebId: string;
	sourceName: string;
	sourceURL: string;
};
interface ScriptManagementContainerProps {
	allowScriptContentBeExecutedOrIncluded: boolean;
	baseResourceURL: string;
	scriptManagementConfigurationDefined: boolean;
}
export default function ScriptManagementContainer({
	allowScriptContentBeExecutedOrIncluded,
	baseResourceURL,
	scriptManagementConfigurationDefined,
}: ScriptManagementContainerProps): JSX.Element;
export {};
