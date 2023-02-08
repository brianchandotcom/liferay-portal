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

import React from 'react';
import './index.scss';
declare type Item = {
	label: string;
	options: LabelValueObject[];
	type: string;
};
interface SelectWithOptionProps
	extends React.SelectHTMLAttributes<HTMLSelectElement> {
	ariaLabel?: string;
	disabled?: boolean;
	error?: string;
	feedbackMessage?: string;
	items: Item[];
	label?: string;
	onSelectChange: (label: string, value: string) => void;
	required?: boolean;
	tooltip?: string;
}
export declare function SelectWithOption({
	ariaLabel,
	className,
	disabled,
	error,
	feedbackMessage,
	id,
	items,
	label,
	onSelectChange,
	placeholder,
	required,
	tooltip,
	value,
}: SelectWithOptionProps): JSX.Element;
export {};
