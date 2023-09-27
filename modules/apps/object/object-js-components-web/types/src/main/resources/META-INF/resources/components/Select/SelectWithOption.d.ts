/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
