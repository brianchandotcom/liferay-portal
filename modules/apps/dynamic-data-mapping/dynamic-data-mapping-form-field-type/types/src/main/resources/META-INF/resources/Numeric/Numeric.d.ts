/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {FocusEventHandler} from 'react';
import {ISymbols} from '../NumericInputMask/NumericInputMask';
import './Numeric.scss';
import type {FieldChangeEventHandler, Locale, LocalizedValue} from '../types';
declare const Numeric: React.FC<IProps>;
export {Numeric};
declare const _default: any;
export default _default;
interface IProps {
	append: string;
	appendType: 'prefix' | 'suffix';
	dataType: NumericDataType;
	decimalPlaces: number;
	defaultLanguageId: Locale;
	errorMessage?: string;
	focused: boolean;
	id: string;
	inputMask?: boolean;
	inputMaskFormat?: string;
	localizedSymbols?: LocalizedValue<ISymbols>;
	localizedValue?: LocalizedValue<string>;
	name: string;
	onBlur: FocusEventHandler<HTMLInputElement>;
	onChange: FieldChangeEventHandler<string>;
	onFocus: FocusEventHandler<HTMLInputElement>;
	placeholder?: string;
	predefinedValue?: string;
	readOnly: boolean;
	required?: boolean;
	settingsContext?: any;
	symbols: ISymbols;
	tip?: string;
	valid?: boolean;
	value?: string;
}
declare type NumericDataType = 'integer' | 'double';
