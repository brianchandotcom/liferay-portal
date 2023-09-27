/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {ReactNode} from 'react';
import './index.scss';
export interface CustomItem<T = string> {
	checked?: boolean;
	description?: string;
	disabled?: boolean;
	label: string;
	name?: string;
	popover?: {
		body: string;
		header: string;
	};
	type?: string;
	value?: T;
}
export interface SelectProps {
	className?: string;
	disabled?: boolean;
	error?: string;
	feedbackMessage?: string;
	id?: string;
	label?: string;
	placeholder?: string;
	readonly?: boolean;
	required?: boolean;
	value?: string;
}
interface BaseSelectProps extends SelectProps {
	children: ReactNode;
	contentRight?: ReactNode;
	dropdownActive: boolean;
	setDropdownActive: React.Dispatch<React.SetStateAction<boolean>>;
	trigger?: JSX.Element;
}
export declare function BaseSelect({
	children,
	className,
	contentRight,
	disabled,
	dropdownActive,
	error,
	feedbackMessage,
	id,
	label,
	placeholder,
	readonly,
	required,
	setDropdownActive,
	trigger,
	value,
	...restProps
}: BaseSelectProps): JSX.Element;
export {};
