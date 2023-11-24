/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {MutableRefObject} from 'react';
interface RuleSelectProps<T> {
	'aria-label'?: string;
	'items': ReadonlyArray<{
		label: string;
		value: T;
	}>;
	'onSelectionChange': (selection: T) => void;
	'selectedKey'?: string;
	'triggerRef'?: MutableRefObject<HTMLButtonElement | undefined>;
}
export default function RuleSelect<T extends string>({
	items,
	onSelectionChange,
	selectedKey,
	triggerRef,
	...otherProps
}: RuleSelectProps<T>): JSX.Element;
export {};
