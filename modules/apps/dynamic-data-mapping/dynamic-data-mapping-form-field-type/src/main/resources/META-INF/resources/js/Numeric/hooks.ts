/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useMemo} from 'react';

import {ISymbols} from '../NumericInputMask/NumericInputMask';
import {getFormattedValue, getMaskedValue} from './numericUtil';

export type IMaskedNumber = {
	masked: string;
	placeholder?: string;
	raw: string;
};

export type useNumericInputValueProps = {
	dataType: 'integer' | 'double';
	decimalPlaces: number;
	focused: boolean;
	inputMask?: boolean;
	inputMaskFormat?: string;
	placeholder?: string;
	symbols: ISymbols;
	value: string;
};

export function useNumericInputValueMemo({
	dataType,
	decimalPlaces,
	focused,
	inputMask,
	inputMaskFormat,
	placeholder,
	symbols,
	value,
}: useNumericInputValueProps): IMaskedNumber {
	return useMemo<IMaskedNumber>(() => {
		let newValue = value;

		if (dataType === 'double') {
			const symbolsValue = value.match(/[^-\d]/g);

			newValue = symbolsValue
				? value.replace(symbolsValue[0], symbols.decimalSymbol)
				: value;
		}

		const inputValue = inputMask
			? getMaskedValue({
					dataType,
					decimalPlaces,
					focused,
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
						decimalSymbol: symbols.decimalSymbol,
						value: newValue,
					}),
					placeholder,
				};

		return inputValue;
	}, [
		dataType,
		decimalPlaces,
		focused,
		inputMask,
		inputMaskFormat,
		placeholder,
		symbols,
		value,
	]);
}
