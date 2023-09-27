/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import {CustomItem, SelectProps} from './BaseSelect';
import './index.scss';
interface IProps<T extends CustomItem<number | string> = CustomItem>
	extends SelectProps {
	options: T[];
	selectAllOption?: boolean;
	setOptions: (options: T[]) => void;
	setSelectAllChecked?: Function;
}
export declare function MultipleSelect<
	T extends CustomItem<number | string> = CustomItem
>({options, selectAllOption, setOptions, ...restProps}: IProps<T>): JSX.Element;
export {};
