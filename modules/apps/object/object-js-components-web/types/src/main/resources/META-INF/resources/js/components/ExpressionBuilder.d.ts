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
import {SideBarCategory} from './CodeEditor/index';
import './ExpressionBuilder.scss';
export declare function ExpressionBuilder({
	className,
	component,
	disabled,
	error,
	feedbackMessage,
	id,
	label,
	name,
	onChange,
	onInput,
	onOpenModal,
	required,
	type,
	value,
	...otherProps
}: IProps): JSX.Element;
export declare function ExpressionBuilderModal({
	sidebarElements,
}: IModalProps): JSX.Element | null;
interface IModalProps {
	sidebarElements: SideBarCategory[];
}
interface IProps extends React.InputHTMLAttributes<HTMLInputElement> {
	component?: 'input' | 'textarea' | React.ForwardRefExoticComponent<any>;
	disabled?: boolean;
	error?: string;
	feedbackMessage?: string;
	id?: string;
	label?: string;
	name?: string;
	onOpenModal: () => void;
	required?: boolean;
	type?: 'number' | 'text';
	value?: string | number | string[];
}
export {};
