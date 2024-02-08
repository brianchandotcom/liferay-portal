/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import {SidebarCategory} from '@liferay/object-js-components-web';
import {ILearnResourceContext} from 'frontend-js-components-web';
import {TabProps} from './useObjectValidationForm';
export interface ConditionsProps extends TabProps {
	creationLanguageId: Liferay.Language.Locale;
	customObjectFields: ObjectField[];
	learnResources: ILearnResourceContext;
	objectValidationRuleElements: SidebarCategory[];
}
export declare function Conditions({
	creationLanguageId,
	customObjectFields,
	disabled,
	errors,
	learnResources,
	objectValidationRuleElements,
	selectedPartialValidationField,
	setValues,
	values,
}: ConditionsProps): JSX.Element;
