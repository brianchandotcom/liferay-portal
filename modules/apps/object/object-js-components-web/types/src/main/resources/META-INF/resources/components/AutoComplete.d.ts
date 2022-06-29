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
import './AutoComplete.scss';
interface IAutoCompleteProps extends React.HTMLAttributes<HTMLElement> {
	children: (item: any) => React.ReactNode;
	contentRight?: React.ReactNode;
	emptyStateMessage: string;
	error?: string;
	feedbackMessage?: string;
	hasEmptyItem?: boolean;
	items: any[];
	label: string;
	onChangeQuery: (value: string) => void;
	onSelectItem: (item: any) => void;
	placeholder?: string;
	query: string;
	required?: boolean;
	value?: string;
}
export declare function AutoComplete({
	children,
	className,
	contentRight,
	emptyStateMessage,
	error,
	feedbackMessage,
	hasEmptyItem,
	id,
	items: initialItems,
	label,
	onChangeQuery,
	onSelectItem,
	placeholder,
	query,
	required,
	value,
}: IAutoCompleteProps): JSX.Element;
export {};
