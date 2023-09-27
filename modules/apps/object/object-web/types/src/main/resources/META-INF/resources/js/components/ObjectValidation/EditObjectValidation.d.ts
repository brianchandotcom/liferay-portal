/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import {SidebarCategory} from '@liferay/object-js-components-web';
interface EditObjectValidationProps {
	creationLanguageId: Liferay.Language.Locale;
	learnResources: object;
	objectDefinitionId: number;
	objectValidationRuleElements: SidebarCategory[];
	objectValidationRuleId: number;
	readOnly: boolean;
}
export interface PartialValidationFields {
	id: number;
	label: string;
	name: string;
	value: string;
}
export default function EditObjectValidation({
	creationLanguageId,
	learnResources,
	objectDefinitionId,
	objectValidationRuleElements,
	objectValidationRuleId,
	readOnly,
}: EditObjectValidationProps): JSX.Element;
export {};
