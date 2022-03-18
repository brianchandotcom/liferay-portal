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
import classNames from 'classnames';

// @ts-ignore

import {SettingsContext, useFormState} from 'data-engine-js-components-web';
import React, {ChangeEventHandler, FocusEventHandler, useMemo} from 'react';
import {createNumberMask} from 'text-mask-addons';
import {conformToMask} from 'text-mask-core';

import {FieldBase} from '../FieldBase/ReactFieldBase.es';
import {ISymbols} from '../NumericInputMask/NumericInputMask';
import {trimLeftZero} from '../util/numericalOperations';

// @ts-ignore

import withConfirmationField from '../util/withConfirmationField.es';

import './Numeric.scss';

import type {FieldChangeEventHandler, Locale, LocalizedValue} from '../types';

const NON_NUMERIC_REGEX = /[\D]/g;

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
	dataType,
	decimalPlaces,
	includeThousandsSeparator = false,
	inputMaskFormat,
	symbols,
	value,
}: {
	dataType: NumericDataType;
	decimalPlaces: number;
	includeThousandsSeparator?: boolean;
	inputMaskFormat: string;
	symbols: ISymbols;
	value: string;
}): IMaskedNumber => {
	let mask;
	if (dataType === 'double') {
		const config: INumberMaskConfig = {
			allowDecimal: true,
			allowLeadingZeroes: true,
			allowNegative: true,
			decimalLimit: decimalPlaces,
			decimalSymbol: symbols.decimalSymbol,
			includeThousandsSeparator,
			prefix: '',
			thousandsSeparatorSymbol: symbols.thousandsSeparator ?? '',
		};

		mask = createNumberMask(config);
	}
	else {
		mask = adaptiveMask(value, inputMaskFormat);
	}

	const {conformedValue: masked} = conformToMask(value, mask, {
		guide: false,
		keepCharPositions: false,
		placeholderChar: '\u2000',
	});

	const regex = new RegExp(
		dataType === 'double' ? `[^-${symbols.decimalSymbol}\\d]` : '[^\\d]',
		'g'
	);

	return {
		masked,
		placeholder:
			dataType === 'double'
				? `0${symbols.decimalSymbol}${'0'.repeat(decimalPlaces)}`
				: inputMaskFormat.replace(/\d/g, '_'),
		raw: masked.replace(regex, ''),
	};
};

const getFormattedValue = ({
	dataType,
	symbols,
	value,
}: {
	dataType: NumericDataType;
	symbols: ISymbols;
	value: string;
}) => {
	if (!value) {
		return {masked: '', raw: ''};
	}

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
	const mask = createNumberMask(config);

	const {conformedValue: masked}: {conformedValue: string} = conformToMask(
		value,
		mask,
		{
			guide: false,
			keepCharPositions: false,
			placeholderChar: '\u2000',
		}
	);

	return {
		masked: dataType === 'double' ? value : masked,
		raw: masked,
	};
};

const Numeric: React.FC<IProps> = ({
	append,
	appendType,
	dataType = 'integer',
	decimalPlaces,
	defaultLanguageId,
	id,
	inputMask,
	inputMaskFormat,
	localizedSymbols: initialLocalizedSymbols,
	localizedValue,
	name,
	onBlur,
	onChange,
	onFocus,
	placeholder,
	predefinedValue,
	readOnly,
	settingsContext,
	symbols: symbolsProp = {decimalSymbol: '.'},
	value,
	...otherProps
}) => {
	const {editingLanguageId}: {editingLanguageId: Locale} = useFormState();

	const localizedSymbols = settingsContext
		? SettingsContext.getSettingsContextProperty(
				settingsContext,
				'predefinedValue',
				'localizedSymbols'
		  )
		: initialLocalizedSymbols;

	const symbols = useMemo<ISymbols>(() => {
		if (inputMask) {
			return {
				decimalSymbol: symbolsProp.decimalSymbol,
				thousandsSeparator:
					symbolsProp.thousandsSeparator === 'none'
						? null
						: symbolsProp.thousandsSeparator,
			};
		}

		return localizedSymbols?.[editingLanguageId] || symbolsProp;
	}, [editingLanguageId, inputMask, localizedSymbols, symbolsProp]);

	const inputValue = useMemo<IMaskedNumber>(() => {
		let newValue =
			((localizedValue?.[editingLanguageId] ??
				localizedValue?.[defaultLanguageId]) ||
				value) ??
			predefinedValue ??
			'';

		if (dataType === 'double') {
			const symbolsValue = newValue.match(/[^-\d]/g);

			newValue = symbolsValue
				? newValue.replace(symbolsValue[0], symbols.decimalSymbol)
				: newValue;
		}

		return inputMask
			? getMaskedValue({
					dataType,
					decimalPlaces,
					includeThousandsSeparator: Boolean(
						symbols.thousandsSeparator
					),
					inputMaskFormat: String(inputMaskFormat),
					symbols,
					value: newValue,
			  })
			: {
					...getFormattedValue({
						dataType,
						symbols,
						value: newValue,
					}),
					placeholder,
			  };
	}, [
		dataType,
		decimalPlaces,
		defaultLanguageId,
		editingLanguageId,
		inputMask,
		inputMaskFormat,
		localizedValue,
		placeholder,
		predefinedValue,
		symbols,
		value,
	]);

	const handleChange: ChangeEventHandler<HTMLInputElement> = ({
		target: {value},
	}) => {
		value =
			inputMask && dataType === 'integer'
				? value
				: trimLeftZero({
						decimalSymbol: symbols.decimalSymbol,
						thousandsSeparator: symbols.thousandsSeparator,
						value,
				  });

		// allows user to delete characters from the mask

		const inputValueRaw = inputValue.raw.replace(NON_NUMERIC_REGEX, '');
		const rawValue = value.replace(NON_NUMERIC_REGEX, '');

		if (
			inputValue.masked?.length > value.length &&
			(inputValueRaw?.length ?? 0) === rawValue.length
		) {
			value = inputValueRaw.slice(0, -1);
		}

		const {masked, raw} = inputMask
			? getMaskedValue({
					dataType,
					decimalPlaces,
					inputMaskFormat: String(inputMaskFormat),
					symbols,
					value,
			  })
			: getFormattedValue({dataType, symbols, value});

		if (masked !== inputValue.masked) {
			onChange({target: {value: raw}});
		}
	};

	const input = (
		<ClayInput
			className={classNames({
				'ddm-form-field-type__numeric--rtl':
					Liferay.Language.direction[editingLanguageId] === 'rtl',
			})}
			disabled={readOnly}
			id={id}
			name={`${name}${inputMask ? '_masked' : ''}`}
			onBlur={onBlur}
			onChange={handleChange}
			onFocus={onFocus}
			placeholder={inputValue.placeholder}
			type="text"
			value={inputValue.masked}
		/>
	);

	return (
		<FieldBase
			{...otherProps}
			id={id}
			localizedValue={localizedValue}
			name={name}
			readOnly={readOnly}
		>
			{inputMask && append && dataType === 'double' ? (
				<ClayInput.Group>
					{appendType === 'prefix' && (
						<ClayInput.GroupItem prepend shrink>
							<ClayInput.GroupText>{append}</ClayInput.GroupText>
						</ClayInput.GroupItem>
					)}

					<ClayInput.GroupItem prepend>{input}</ClayInput.GroupItem>

					{appendType === 'suffix' && (
						<ClayInput.GroupItem append shrink>
							<ClayInput.GroupText>{append}</ClayInput.GroupText>
						</ClayInput.GroupItem>
					)}
				</ClayInput.Group>
			) : (
				input
			)}

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
	placeholder?: string;
	raw: string;
}

interface INumberMaskConfig {
	allowDecimal?: boolean;
	allowLeadingZeroes: boolean;
	allowNegative: boolean;
	decimalLimit?: number | null;
	decimalSymbol?: string;
	includeThousandsSeparator: boolean;
	prefix?: string;
	suffix?: string;
	thousandsSeparatorSymbol?: string | null;
}

interface IProps {
	append: string;
	appendType: 'prefix' | 'suffix';
	dataType: NumericDataType;
	decimalPlaces: number;
	defaultLanguageId: Locale;
	id: string;
	inputMask?: boolean;
	inputMaskFormat?: string;
	localizedSymbols?: LocalizedValue<ISymbols>;
	localizedValue?: LocalizedValue<string>;
	name: string;
	onBlur: FocusEventHandler<HTMLInputElement>;
	onChange: FieldChangeEventHandler<String>;
	onFocus: FocusEventHandler<HTMLInputElement>;
	placeholder?: string;
	predefinedValue?: string;
	readOnly: boolean;
	settingsContext?: any;
	symbols: ISymbols;
	value?: string;
}

type NumericDataType = 'integer' | 'double';
