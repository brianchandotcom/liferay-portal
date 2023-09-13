/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import './ObjectDefinitionNodeFooter.scss';
interface ObjectDefinitionNodeFooterProps {
	isLinkedObjectDefinition: boolean;
	setShowAllObjectFields: (value: boolean) => void;
	showAllObjectFields: boolean;
}
export default function ObjectDefinitionNodeFooter({
	isLinkedObjectDefinition,
	setShowAllObjectFields,
	showAllObjectFields,
}: ObjectDefinitionNodeFooterProps): JSX.Element;
export {};
