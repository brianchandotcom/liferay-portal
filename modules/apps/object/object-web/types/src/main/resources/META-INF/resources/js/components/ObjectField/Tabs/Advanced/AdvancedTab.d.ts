/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import {SidebarCategory} from '@liferay/object-js-components-web';
import {ObjectFieldErrors} from '../../ObjectFieldFormBase';
interface AdvancedTabProps {
	creationLanguageId: Liferay.Language.Locale;
	errors: ObjectFieldErrors;
	isDefaultStorageType: boolean;
	learnResources: object;
	readOnlySidebarElements: SidebarCategory[];
	setValues: (value: Partial<ObjectField>) => void;
	sidebarElements: SidebarCategory[];
	values: Partial<ObjectField>;
}
export declare function AdvancedTab({
	creationLanguageId,
	errors,
	isDefaultStorageType,
	learnResources,
	readOnlySidebarElements,
	setValues,
	sidebarElements,
	values,
}: AdvancedTabProps): JSX.Element;
export {};
