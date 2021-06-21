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

import {ClayInput} from '@clayui/form';

// @ts-ignore

import {useFormState} from 'data-engine-js-components-web';
import React, {ChangeEventHandler, FocusEventHandler, useMemo} from 'react';
import createNumberMask from 'text-mask-addons/dist/createNumberMask';

// @ts-ignore

import {conformToMask} from 'vanilla-text-mask';

// @ts-ignore

import {FieldBase} from '../FieldBase/ReactFieldBase.es';
import {ISymbols} from '../NumericInputMask/NumericInputMask';

// @ts-ignore

import withConfirmationField from '../util/withConfirmationField.es';

const adaptiveMask = (rawValue: string, inputMaskFormat: string) => {
	const generateMask = (mask: string): string => {
		if (!mask.includes('0')) {
			return mask;
		}

		const inputNumbers = rawValue.match(/\d/g)?.length ?? 0;
		const mandatorySize = mask.match(/9/g)?.length ?? 0;
		if (inputNumbers <= mandatorySize) {
			return mask.replace(/0/g, '');
		}

		return generateMask(mask.replace('0', '9'));
	};

	return [...generateMask(inputMaskFormat)].map((char) =>
		char === '9' ? /\d/ : char
	);
};

const getMaskedValue = ({
	append,
	appendType,
	dataType,
	inputMask,
	inputMaskFormat,
	symbols,
	value,
}: {
	append: string;
	appendType?: 'prefix' | 'suffix';
	dataType: NumericDataType;
	inputMask?: boolean;
	inputMaskFormat?: string;
	symbols: ISymbols;
	value: string;
}): IMaskedNumber => {
	let mask;

	if (inputMask) {
		if (dataType === 'double') {
			const config: INumberMaskConfig = {
				allowDecimal: true,
				allowLeadingZeroes: true,
				allowNegative: true,
				decimalSymbol: symbols.decimalSymbol,
				includeThousandsSeparator: Boolean(symbols.thousandsSeparator),
				prefix: appendType === 'prefix' ? append : '',
				suffix: appendType === 'suffix' ? append : '',
				thousandsSeparatorSymbol: symbols.thousandsSeparator,
			};

			mask = createNumberMask(config);
		}
		else {
			mask = adaptiveMask(value, inputMaskFormat as string);
		}
	}
	else {
		const config: INumberMaskConfig = {
			allowLeadingZeroes: true,
			allowNegative: true,
			includeThousandsSeparator: false,
			prefix: '',
		};

		if (dataType === 'double') {
			config.allowDecimal = true;
			config.decimalLimit = null;
			config.decimalSymbol = symbols.decimalSymbol;
		}
		mask = createNumberMask(config);

		if (typeof value === 'string') {
			if (!value) {
				return {masked: '', raw: ''};
			}
			value = value.replace(symbols.decimalSymbol, '.');
			if (dataType == 'integer' && value.includes('.')) {
				value = value.replace(symbols.decimalSymbol, '');
			}
		}
		value = value.replace('.', symbols.decimalSymbol);
	}

	const {conformedValue: masked} = conformToMask(value, mask, {
		guide: false,
		keepCharPositions: false,
		placeholderChar: '\u2000',
	});
	const regex = new RegExp(`[^${symbols.decimalSymbol}|\\d]`, 'g');

	return {masked, raw: inputMask ? masked.replace(regex, '') : masked};
};

const Numeric: React.FC<IProps> = ({
	append,
	appendType,
	dataType = 'integer',
	defaultLanguageId,
	id,
	inputMask,
	inputMaskFormat,
	localizedValue,
	name,
	onBlur,
	onChange,
	onFocus,
	placeholder,
	predefinedValue,
	readOnly,
	symbols: symbolsProp = {decimalSymbol: '.'},
	value,
	...otherProps
}) => {
	const {editingLanguageId} = useFormState();
	const symbols = useMemo<ISymbols>(() => {
		return inputMask
			? {
					decimalSymbol: symbolsProp.decimalSymbol,
					thousandsSeparator:
						symbolsProp.thousandsSeparator == 'none'
							? null
							: symbolsProp.thousandsSeparator,
			  }
			: symbolsProp;
	}, [inputMask, symbolsProp]);

	const inputValue = useMemo<IMaskedNumber>(() => {
		const newValue =
			((localizedValue?.[editingLanguageId] ??
				localizedValue?.[defaultLanguageId]) ||
				value) ??
			predefinedValue ??
			'';

		return getMaskedValue({
			append,
			appendType,
			dataType,
			inputMask,
			inputMaskFormat,
			symbols,
			value: newValue,
		});
	}, [
		append,
		appendType,
		dataType,
		symbols,
		defaultLanguageId,
		editingLanguageId,
		inputMask,
		inputMaskFormat,
		localizedValue,
		predefinedValue,
		value,
	]);

	const handleChange: ChangeEventHandler<HTMLInputElement> = ({
		target: {value},
	}) => {
		const rawValue = value.replace(/\D/g, '');
		if (
			(inputValue.masked?.length ?? 0) - value.length === 1 &&
			(inputValue.raw?.length ?? 0) === rawValue.length
		) {
			value = inputValue.raw.slice(0, -1);
		}

		const newValue = getMaskedValue({
			append,
			appendType,
			dataType,
			inputMask,
			inputMaskFormat,
			symbols,
			value,
		});
		if (newValue.masked !== inputValue.masked) {
			onChange({target: {value: newValue.raw}});
		}
	};

	return (
		<FieldBase
			{...otherProps}
			id={id}
			localizedValue={localizedValue}
			name={name}
			readOnly={readOnly}
		>
			<ClayInput

				// @ts-ignore

				dir={Liferay.Language.direction[editingLanguageId]}
				disabled={readOnly}
				id={id}
				lang={editingLanguageId}
				name={`${name}${inputMask ? '_masked' : ''}`}
				onBlur={onBlur}
				onChange={handleChange}
				onFocus={onFocus}
				placeholder={
					placeholder ||
					(inputMask
						? inputMaskFormat?.replace(/\d/g, '_')
						: undefined)
				}
				type="text"
				value={inputValue.masked}
			/>
			{inputMask && (
				<input name={name} type="hidden" value={inputValue.raw} />
			)}
		</FieldBase>
	);
};

export {Numeric};
export default withConfirmationField(Numeric);

interface IMaskedNumber {
	masked: string;
	raw: string;
}

interface INumberMaskConfig {
	allowDecimal?: boolean;
	allowLeadingZeroes: boolean;
	allowNegative: boolean;
	decimalLimit?: number | null;
	decimalSymbol?: string;
	includeThousandsSeparator: boolean;
	prefix: string;
	suffix?: string;
	thousandsSeparatorSymbol?: string | null;
}
interface IProps {
	append: string;
	appendType: 'prefix' | 'suffix';
	dataType: NumericDataType;
	defaultLanguageId: string;
	id: string;
	inputMask?: boolean;
	inputMaskFormat?: string;
	localizedValue?: {[key: string]: string};
	name: string;
	onBlur: FocusEventHandler<HTMLInputElement>;
	onChange: (event: {target: {value: string}}) => void;
	onFocus: FocusEventHandler<HTMLInputElement>;
	placeholder?: string;
	predefinedValue?: string;
	readOnly: boolean;
	symbols: ISymbols;
	thousandsSeparator?: [',' | '.' | ' ' | "'" | 'none'];
	value?: string;
}

type NumericDataType = 'integer' | 'double';
