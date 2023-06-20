/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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
	required?: boolean;
	value?: string;
}
interface IProps extends SelectProps {
	children: ReactNode;
	dropdownActive: boolean;
	setDropdownActive: React.Dispatch<React.SetStateAction<boolean>>;
	trigger?: JSX.Element;
}
export declare function BaseSelect({
	children,
	className,
	disabled,
	dropdownActive,
	error,
	feedbackMessage,
	id,
	label,
	placeholder,
	required,
	setDropdownActive,
	trigger,
	value,
	...restProps
}: IProps): JSX.Element;
export {};
