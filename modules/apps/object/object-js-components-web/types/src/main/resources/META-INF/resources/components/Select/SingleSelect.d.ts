/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {Key, ReactElement} from 'react';
import './index.scss';
declare type SingleSelectOption = {
	label?: string;
	value?: string | number;
};
interface SingleSelectProps<T extends SingleSelectOption> {
	as?:
		| 'button'
		| React.ForwardRefExoticComponent<any>
		| ((props: React.HTMLAttributes<HTMLElement>) => JSX.Element);
	children?: (item: T) => ReactElement;
	className?: string;
	defaultSelectedKey?: Key;
	disabled?: boolean;
	error?: string;
	feedbackMessage?: string;
	id?: string;
	items: T[];
	label?: string;
	onSelectionChange?: (itemValue: React.Key) => void;
	placeholder?: string;
	required?: boolean;
	selectedKey?: string | number;
	tooltip?: string;
}
export declare function SingleSelect<T extends SingleSelectOption>({
	as,
	children,
	className,
	defaultSelectedKey,
	disabled,
	error,
	feedbackMessage,
	id,
	items,
	label,
	onSelectionChange,
	placeholder,
	required,
	selectedKey,
	tooltip,
}: SingleSelectProps<T>): JSX.Element;
export {};
