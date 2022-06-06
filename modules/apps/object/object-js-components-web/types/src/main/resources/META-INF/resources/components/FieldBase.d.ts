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

import {ReactNode} from 'react';
import './FieldBase.scss';
export declare function FieldBase({
	children,
	className,
	disabled,
	errorMessage,
	helpMessage,
	id,
	label,
	required,
	tooltip,
	warningMessage,
}: IProps): JSX.Element;
interface IProps {
	children: ReactNode;
	className?: string;
	disabled?: boolean;
	errorMessage?: string;
	helpMessage?: string;
	id?: string;
	label?: string;
	required?: boolean;
	tooltip?: string;
	warningMessage?: string;
}
export {};
