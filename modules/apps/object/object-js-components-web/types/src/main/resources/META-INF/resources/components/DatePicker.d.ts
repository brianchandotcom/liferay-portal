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
