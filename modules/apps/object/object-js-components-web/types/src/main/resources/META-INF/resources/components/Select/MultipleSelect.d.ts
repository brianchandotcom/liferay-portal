/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FocusEvent} from 'react';
interface MultipleSelectProps {
	className?: string;
	disabled?: boolean;
	error?: string;
	feedbackMessage?: string;
	id?: string;
	label?: string;
	onBlur?: (event: FocusEvent<HTMLInputElement>) => void;
	options: MultiSelectItem[];
	placeholder?: string;
	required?: boolean;
	selectAllOption?: boolean;
	setOptions: (options: MultiSelectItem[]) => void;
}
export interface MultiSelectItem extends LabelValueObject {
	checked?: boolean;
}
export declare function MultipleSelect({
	className,
	disabled,
	error,
	feedbackMessage,
	id,
	label,
	onBlur,
	options,
	placeholder,
	required,
	selectAllOption,
	setOptions,
}: MultipleSelectProps): JSX.Element;
export {};
