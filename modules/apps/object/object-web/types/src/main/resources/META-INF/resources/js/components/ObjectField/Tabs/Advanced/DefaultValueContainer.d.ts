/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import {SidebarCategory} from '@liferay/object-js-components-web';
import {ObjectFieldErrors} from '../../ObjectFieldFormBase';
interface DefaultValueContainerProps {
	creationLanguageId: Liferay.Language.Locale;
	disabled?: boolean;
	errors: ObjectFieldErrors;
	learnResources: object;
	objectFieldBusinessType: ObjectFieldBusinessType;
	objectFieldSettings: ObjectFieldSetting[];
	setValues: (value: Partial<ObjectField>) => void;
	sidebarElements: SidebarCategory[];
	values: Partial<ObjectField>;
}
export interface InputAsValueFieldComponentProps {
	creationLanguageId: Liferay.Language.Locale;
	defaultValue?: ObjectFieldSettingValue;
	error?: string;
	label: string;
	placeholder?: string;
	required?: boolean;
	setValues: (values: Partial<ObjectField>) => void;
	values: Partial<ObjectField>;
}
export declare function DefaultValueContainer({
	creationLanguageId,
	errors,
	learnResources,
	setValues,
	sidebarElements,
	values,
}: DefaultValueContainerProps): JSX.Element;
export {};
