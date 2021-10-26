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

import React, {FocusEventHandler} from 'react';
declare type DecimalSymbol = ',' | '.';
declare type ThousandsSeparator = DecimalSymbol | ' ' | "'" | 'none';
interface INumericInputMaskValue {
	append?: string;
	appendType?: 'prefix' | 'suffix';
	decimalSymbol?: DecimalSymbol[];
	symbols: LocalizedValue<ISymbols>;
	thousandsSeparator?: ThousandsSeparator[];
}
interface IProps {
	append?: string;
	appendType?: 'prefix' | 'suffix';
	decimalPlaces: number;
	decimalSymbol: DecimalSymbol[];
	decimalSymbols: ISelectProps<DecimalSymbol>[];
	defaultLanguageId: Locale;
	editingLanguageId: Locale;
	readOnly: boolean;
	thousandsSeparator?: ThousandsSeparator[];
	thousandsSeparators: ISelectProps<ThousandsSeparator>[];
	value: INumericInputMaskValue;
	visible: boolean;
	onBlur: FocusEventHandler<HTMLInputElement>;
	onChange: FieldChangeEventHandler;
	onFocus: FocusEventHandler<HTMLInputElement>;
}
export interface ISymbols {
	decimalSymbol: DecimalSymbol;
	thousandsSeparator?: ThousandsSeparator | null;
}
interface ISelectProps<T> {
	disabled: boolean;
	label: LocalizedValue<string>;
	reference: string;
	value: T;
}
declare const NumericInputMask: React.FC<IProps>;
export default NumericInputMask;
