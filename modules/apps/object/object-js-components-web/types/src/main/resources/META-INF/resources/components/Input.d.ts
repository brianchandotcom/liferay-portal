/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
export declare const Input: React.ForwardRefExoticComponent<
	IProps & React.RefAttributes<HTMLInputElement>
>;
interface IProps
	extends React.InputHTMLAttributes<HTMLInputElement | HTMLTextAreaElement> {
	component?: 'input' | 'textarea' | React.ForwardRefExoticComponent<any>;
	disabled?: boolean;
	error?: string;
	feedbackMessage?: string;
	id?: string;
	label?: string;
	name?: string;
	placeholder?: string;
	required?: boolean;
	type?: 'number' | 'textarea' | 'text' | 'date';
	value?: string | number | string[];
}
export {};
