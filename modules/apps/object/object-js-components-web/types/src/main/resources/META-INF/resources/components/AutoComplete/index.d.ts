/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	onActive?: (item: T) => boolean;
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
	externalReferenceCode: string;
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
	onActive,
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
