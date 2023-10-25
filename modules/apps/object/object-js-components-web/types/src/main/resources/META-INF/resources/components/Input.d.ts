/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {InputHTMLAttributes, Ref} from 'react';
interface InputProps
	extends InputHTMLAttributes<HTMLInputElement | HTMLTextAreaElement> {
	component?: 'input' | 'textarea' | React.ForwardRefExoticComponent<any>;
	disabled?: boolean;
	error?: string;
	feedbackMessage?: string;
	id?: string;
	label?: string;
	name?: string;
	placeholder?: string;
	ref?: Ref<HTMLInputElement>;
	required?: boolean;
	tooltip?: string;
	type?: 'number' | 'textarea' | 'text' | 'date';
	value?: string | number | string[];
}
export declare function Input({
	className,
	component,
	disabled,
	error,
	feedbackMessage,
	id,
	label,
	min,
	name,
	onBlur,
	onChange,
	onInput,
	readOnly,
	ref,
	required,
	tooltip,
	type,
	value,
	...otherProps
}: InputProps): JSX.Element;
export {};
