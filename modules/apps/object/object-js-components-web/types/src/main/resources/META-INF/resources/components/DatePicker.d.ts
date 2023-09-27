/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
interface DatePickerProps
	extends Omit<React.HTMLAttributes<HTMLInputElement>, 'onChange'> {
	className?: string;
	defaultLanguageId?: Liferay.Language.Locale;
	disabled?: boolean;
	error?: string;
	feedbackMessage?: string;
	hideFeedback?: boolean;
	id?: string;
	label?: string;
	locale?: Liferay.Language.Locale;
	name?: string;
	onChange: (value: string) => void;
	range?: boolean;
	required?: boolean;
	type: 'date' | 'date_time' | 'Date' | 'DateTime';
	value?: string;
}
export declare function DatePicker({
	className,
	defaultLanguageId,
	disabled,
	error,
	feedbackMessage,
	hideFeedback,
	id,
	label,
	locale,
	onChange,
	name,
	range,
	required,
	type,
	value,
}: DatePickerProps): JSX.Element;
export {};
