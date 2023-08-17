/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ReactNode} from 'react';
import {CustomItem, SelectProps} from './BaseSelect';
import './index.scss';
interface SingleSelectProps<T> extends SelectProps {
	children?: ReactNode;
	contentRight?: ReactNode;
	onChange?: (selected: T) => void;
	options: T[];
}
export declare function SingleSelect<
	T extends CustomItem<number | string> = CustomItem
>({
	contentRight,
	children,
	onChange,
	options,
	...otherProps
}: SingleSelectProps<T>): JSX.Element;
export {};
