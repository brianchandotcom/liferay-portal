/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

interface Option {
	label: string;
	value: string;
}
interface SelectProps {
	cleanUp?: voidReturn;
	disabled?: boolean;
	onClick: (value: string) => void;
	options: Option[];
	placeholder?: string;
	selectedOption?: string;
}
export declare function Select({
	cleanUp,
	disabled,
	onClick,
	options,
	placeholder,
	selectedOption,
}: SelectProps): JSX.Element;
export {};
