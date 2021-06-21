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
	dataType,
	decimalSymbol,
	inputMask,
	inputMaskFormat,
	value,
}: {
	dataType: NumericDataType;
	decimalSymbol: string;
	inputMask?: boolean;
	inputMaskFormat?: string;
	value: string;
}): IMaskedNumber => {
	let mask;

	if (inputMask) {
		mask = adaptiveMask(value, inputMaskFormat as string);
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
			config.decimalSymbol = decimalSymbol;
		}
		mask = createNumberMask(config);

		if (typeof value === 'string') {
			if (!value) {
				return {masked: '', raw: ''};
			}
			value = value.replace(decimalSymbol, '.');
			if (dataType == 'integer' && value.includes('.')) {
				value = value.replace(decimalSymbol, '');
			}
		}
		value = value.replace('.', decimalSymbol);
	}

	const {conformedValue: masked} = conformToMask(value, mask, {
		guide: false,
		keepCharPositions: false,
		placeholderChar: '\u2000',
	});

	return {masked, raw: inputMask ? masked.replace(/\D/g, '') : masked};
};

const Numeric: React.FC<IProps> = ({
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
	symbols: {decimalSymbol} = {decimalSymbol: '.'},
	value,
	...otherProps
}) => {
	const {editingLanguageId} = useFormState();

	const inputValue = useMemo<IMaskedNumber>(() => {
		const newValue =
			localizedValue?.[editingLanguageId] ??
			localizedValue?.[defaultLanguageId] ??
			value ??
			predefinedValue ??
			'';

		return getMaskedValue({
			dataType,
			decimalSymbol,
			inputMask,
			inputMaskFormat,
			value: newValue,
		});
	}, [
		dataType,
		decimalSymbol,
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
			dataType,
			decimalSymbol,
			inputMask,
			inputMaskFormat,
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
	allowLeadingZeroes: boolean;
	allowNegative: boolean;
	includeThousandsSeparator: boolean;
	prefix: string;
	allowDecimal?: boolean;
	decimalLimit?: number | null;
	decimalSymbol?: string;
}
interface IProps {
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
	symbols: {decimalSymbol: string; thousandSymbol?: string};
	value?: string;
}

type NumericDataType = 'integer' | 'double';
