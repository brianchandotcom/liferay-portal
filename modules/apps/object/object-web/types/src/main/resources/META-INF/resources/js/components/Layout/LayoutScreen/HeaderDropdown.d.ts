/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {MouseEventHandler} from 'react';
interface HeaderDropdownProps {
	addCategorization?: MouseEventHandler;
	deleteElement: MouseEventHandler;
	disabled?: boolean;
}
export declare function HeaderDropdown({
	addCategorization,
	deleteElement,
	disabled,
}: HeaderDropdownProps): JSX.Element;
export {};
