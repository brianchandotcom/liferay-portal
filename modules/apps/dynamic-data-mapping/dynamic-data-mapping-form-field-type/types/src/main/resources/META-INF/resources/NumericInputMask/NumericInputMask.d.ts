/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {FocusEventHandler} from 'react';
import type {FieldChangeEventHandler, Locale, LocalizedValue} from '../types';
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
	ffDecimalPlacesSettingsEnabled: boolean;
	onBlur: FocusEventHandler<HTMLInputElement>;
	onChange: FieldChangeEventHandler<unknown>;
	onFocus: FocusEventHandler<HTMLInputElement>;
	readOnly: boolean;
	thousandsSeparator?: ThousandsSeparator[];
	thousandsSeparators: ISelectProps<ThousandsSeparator>[];
	value: INumericInputMaskValue;
	visible: boolean;
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
