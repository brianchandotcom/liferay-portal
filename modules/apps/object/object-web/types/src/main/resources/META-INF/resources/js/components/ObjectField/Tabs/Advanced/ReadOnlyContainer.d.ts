/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import {SidebarCategory} from '@liferay/object-js-components-web';
interface ReadOnlyContainerProps {
	disabled?: boolean;
	readOnlySidebarElements: SidebarCategory[];
	requiredField: boolean;
	setValues: (value: Partial<ObjectField>) => void;
	values: Partial<ObjectField>;
}
export declare function ReadOnlyContainer({
	disabled,
	readOnlySidebarElements,
	requiredField,
	setValues,
	values,
}: ReadOnlyContainerProps): JSX.Element;
export {};
