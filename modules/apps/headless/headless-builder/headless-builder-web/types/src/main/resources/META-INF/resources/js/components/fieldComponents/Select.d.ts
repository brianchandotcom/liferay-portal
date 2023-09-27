/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

interface SelectProps {
	cleanUp?: voidReturn;
	disabled?: boolean;
	dropDownSearchAriaLabel?: string;
	invalid?: boolean;
	onClick: (value: string) => void;
	options: SelectOption[];
	placeholder?: string;
	required?: boolean;
	searchable?: boolean;
	selectedOption?: SelectOption;
	triggerAriaLabel?: string;
}
export declare function Select({
	cleanUp,
	disabled,
	dropDownSearchAriaLabel,
	invalid,
	onClick,
	options,
	placeholder,
	required,
	searchable,
	selectedOption,
	triggerAriaLabel,
}: SelectProps): JSX.Element;
export {};
