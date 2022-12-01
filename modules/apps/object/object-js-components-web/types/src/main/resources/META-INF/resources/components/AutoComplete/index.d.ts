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
interface AutoCompleteProps<T> extends React.HTMLAttributes<HTMLElement> {
	children: (item: T) => React.ReactNode;
	contentRight?: React.ReactNode;
	disabled?: boolean;
	emptyStateMessage: string;
	error?: string;
	feedbackMessage?: string;
	hasEmptyItem?: boolean;
	items: T[];
	label: string;
	onChangeQuery: (value: string) => void;
	onSelectEmptyStateItem?: (emptyStateItem: EmptyStateItem) => void;
	onSelectItem: (item: T) => void;
	placeholder?: string;
	query: string;
	required?: boolean;
	tooltip?: string;
	value?: string;
}
declare type EmptyStateItem = {
	id: string;
	label: string;
};
export default function AutoComplete<T>({
	children,
	className,
	contentRight,
	disabled,
	emptyStateMessage,
	error,
	feedbackMessage,
	hasEmptyItem,
	id,
	items,
	label,
	onChangeQuery,
	onSelectEmptyStateItem,
	onSelectItem,
	placeholder,
	query,
	required,
	tooltip,
	value,
}: AutoCompleteProps<T>): JSX.Element;
export {};
