/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import {SidebarCategory} from './CodeEditor/index';
export declare function ExpressionBuilder({
	buttonDisabled,
	className,
	component,
	disabled,
	error,
	feedbackMessage,
	hideFeedback,
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
	sidebarElements: SidebarCategory[];
}
interface IProps extends React.InputHTMLAttributes<HTMLInputElement> {
	buttonDisabled?: boolean;
	component?: 'input' | 'textarea' | React.ForwardRefExoticComponent<any>;
	disabled?: boolean;
	error?: string;
	feedbackMessage?: string;
	hideFeedback?: boolean;
	id?: string;
	label?: string;
	name?: string;
	onOpenModal: () => void;
	required?: boolean;
	type?: 'number' | 'text';
	value?: string | number | string[];
}
export {};
