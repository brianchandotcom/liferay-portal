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
import {ISymbols} from '../NumericInputMask/NumericInputMask';
import './Numeric.scss';
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
	id: string;
	inputMask?: boolean;
	inputMaskFormat?: string;
	localizedValue?: LocalizedValue<string>;
	name: string;
	onBlur: FocusEventHandler<HTMLInputElement>;
	onChange: FieldChangeEventHandler<String>;
	onFocus: FocusEventHandler<HTMLInputElement>;
	placeholder?: string;
	predefinedValue?: string;
	readOnly: boolean;
	symbols: ISymbols;
	value?: string;
}
declare type NumericDataType = 'integer' | 'double';
