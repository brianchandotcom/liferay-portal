/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import './ObjectDefinitionNodeObjectFields.scss';
interface ObjectDefinitionNodeFieldsProps {
	defaultLanguageId: Liferay.Language.Locale;
	objectFields: ObjectFieldNode[];
	showAllObjectFields: boolean;
}
export default function ObjectDefinitionNodeFields({
	objectFields,
	showAllObjectFields,
}: ObjectDefinitionNodeFieldsProps): JSX.Element;
export {};
