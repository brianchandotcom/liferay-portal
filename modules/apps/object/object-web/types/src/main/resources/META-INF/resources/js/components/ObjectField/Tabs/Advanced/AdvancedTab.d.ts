/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {SidebarCategory} from '@liferay/object-js-components-web';
import {ElementType} from 'react';
import {ObjectFieldErrors} from '../../ObjectFieldFormBase';
interface AdvancedTabProps {
	containerWrapper: ElementType;
	creationLanguageId: Liferay.Language.Locale;
	errors: ObjectFieldErrors;
	isDefaultStorageType: boolean;
	learnResources: ObjectWebLearnResources;
	modelBuilder?: boolean;
	readOnlySidebarElements: SidebarCategory[];
	setValues: (value: Partial<ObjectField>) => void;
	sidebarElements: SidebarCategory[];
	values: Partial<ObjectField>;
}
export declare function AdvancedTab({
	containerWrapper: ContainerWrapper,
	creationLanguageId,
	errors,
	isDefaultStorageType,
	learnResources,
	modelBuilder,
	readOnlySidebarElements,
	setValues,
	sidebarElements,
	values,
}: AdvancedTabProps): JSX.Element;
export {};
