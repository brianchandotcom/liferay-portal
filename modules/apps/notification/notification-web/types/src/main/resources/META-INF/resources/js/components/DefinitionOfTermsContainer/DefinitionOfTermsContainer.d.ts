/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

interface DefinitionOfTermsContainerProps {
	baseResourceURL: string;
	objectDefinitions: ObjectDefinition[];
}
export interface Item {
	termLabel: string;
	termName: string;
}
export default function DefinitionOfTermsContainer({
	baseResourceURL,
	objectDefinitions,
}: DefinitionOfTermsContainerProps): JSX.Element;
export {};
