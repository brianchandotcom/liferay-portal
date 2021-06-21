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
import {useFormState} from 'data-engine-js-components-web';
import React, {useMemo} from 'react';
import createNumberMask from 'text-mask-addons/dist/createNumberMask';
import {conformToMask} from 'vanilla-text-mask';

import {FieldBase} from '../FieldBase/ReactFieldBase.es';
import withConfirmationField from '../util/withConfirmationField.es';

const adaptiveMask = (rawValue, inputMaskFormat) => {
	const generateMask = (mask) => {
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
}) => {
	let mask;

	if (inputMask) {
		mask = adaptiveMask(value, inputMaskFormat);
	}
	else {
		const config = {
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
				return '';
			}
			value = value.replace(decimalSymbol, '.');
			if (dataType == 'integer' && value.includes('.')) {
				value = value.replace(decimalSymbol, '');
			}
		}
		value = String(value).replace('.', decimalSymbol);
	}

	const {conformedValue: masked} = conformToMask(value, mask, {
		guide: false,
		keepCharPositions: false,
		placeholderChar: '\u2000',
	});

	return {masked, raw: inputMask ? masked.replace(/\D/g, '') : masked};
};

const Numeric = ({
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

	const inputValue = useMemo(() => {
		const newValue =
			(localizedValue?.[editingLanguageId] ||
				localizedValue?.[defaultLanguageId] ||
				value) ??
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

	const handleChange = ({target: {value}}) => {
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
					(inputMask ? inputMaskFormat?.replace(/\d/g, '_') : null)
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
