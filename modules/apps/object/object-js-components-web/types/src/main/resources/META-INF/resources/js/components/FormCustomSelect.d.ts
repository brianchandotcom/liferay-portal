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

/// <reference types="react" />

import './FormCustomSelect.scss';
export declare function FormCustomSelect<T extends CustomItem = CustomItem>({
	className,
	disabled,
	error,
	feedbackMessage,
	id,
	label,
	onChange,
	options,
	placeholder,
	required,
	value,
}: IProps<T>): JSX.Element;
export interface CustomItem {
	description?: string;
	label: string;
	value?: string;
}
interface IProps<T extends CustomItem = CustomItem> {
	className?: string;
	disabled?: boolean;
	error?: string;
	feedbackMessage?: string;
	id?: string;
	label?: string;
	onChange?: (selected: T) => void;
	options: T[];
	placeholder?: string;
	required?: boolean;
	value?: string | number | string[];
}
export {};
