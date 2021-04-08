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
import {usePrevious} from '@liferay/frontend-js-react-web';
import React, {useEffect, useRef, useState} from 'react';
import createNumberMask from 'text-mask-addons/dist/createNumberMask';
import vanillaTextMask from 'vanilla-text-mask';

import {FieldBase} from '../FieldBase/ReactFieldBase.es';
import withConfirmationField from '../util/withConfirmationField.es';

const ONE_DIGIT_NEGATIVE_NUMBER_LENGTH = 2;

const getGenericValue = (symbols, value = '') => {
	if (typeof value === 'number' || !symbols) {
		return value;
	}

	return value.replace(symbols.decimalSymbol, '$[DECIMAL_SYMBOL]');
};

const getMaskConfig = (dataType, symbols) => {
	let config = {
		allowLeadingZeroes: true,
		allowNegative: true,
		includeThousandsSeparator: false,
		prefix: '',
	};

	if (dataType === 'double') {
		config = {
			...config,
			allowDecimal: true,
			decimalLimit: null,
			decimalSymbol: symbols.decimalSymbol,
		};
	}

	return config;
};

const getValue = (dataType, symbols, value = '') => {
	let decimalSymbol = symbols.decimalSymbol;

	let newValue;

	if (typeof value === 'number') {
		newValue = `${value}`;
		newValue = newValue.replace('.', decimalSymbol);
	}
	else {
		newValue = value;
	}

	newValue = newValue.replace('$[DECIMAL_SYMBOL]', decimalSymbol);

	if (newValue && !newValue.includes('.') && decimalSymbol != ',') {
		decimalSymbol = ',';
	}

	if (dataType === 'integer' && newValue) {
		newValue = newValue.replace(decimalSymbol, '.');

		if (!isNaN(Number(newValue)) && newValue.indexOf('.') !== -1) {
			newValue = String(Math.round(newValue.replace(decimalSymbol, '.')));
		}
	}

	return newValue;
};

const Numeric = ({
	dataType = 'integer',
	defaultLanguageId,
	disabled,
	editingLanguageId,
	localizable,
	localizedValue,
	onChange,
	symbols = {
		decimalSymbol: '.',
		thousandsSeparator: ',',
	},
	value,
	...otherProps
}) => {
	const [currentValue, setCurrentValue] = useState(value);
	const inputRef = useRef(null);

	const [defaultSymbols] = useState(symbols);

	const prevEditingLanguageId = usePrevious(editingLanguageId);

	useEffect(() => {
		if (prevEditingLanguageId !== editingLanguageId && localizable) {
			let newValue =
				localizedValue[editingLanguageId] !== undefined
					? localizedValue[editingLanguageId]
					: getGenericValue(
							defaultSymbols,
							localizedValue[defaultLanguageId]
					  );

			newValue = getValue(dataType, symbols, newValue);

			setCurrentValue(newValue);
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [
		defaultLanguageId,
		editingLanguageId,
		localizable,
		localizedValue,
		prevEditingLanguageId,
		defaultSymbols,
		setCurrentValue,
	]);

	useEffect(() => {
		let maskInstance = null;

		if (inputRef.current) {
			const newValue = getValue(dataType, symbols, value);

			const mask = createNumberMask(getMaskConfig(dataType, symbols));

			maskInstance = vanillaTextMask({
				inputElement: inputRef.current,
				mask,
			});

			if (newValue !== inputRef.current.value) {
				setCurrentValue(newValue);
			}
		}

		return () => {
			if (maskInstance) {
				maskInstance.destroy();
			}
		};
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [dataType, inputRef, setCurrentValue, value]);

	return (
		<ClayInput
			{...otherProps}
			dir={Liferay.Language.direction[editingLanguageId]}
			disabled={disabled}
			lang={editingLanguageId}
			onChange={(event) => {
				const {value: newValue} = event.target;

				if (
					dataType === 'integer' &&
					newValue.substr(-1) === symbols.decimalSymbol
				) {
					return;
				}

				setCurrentValue(newValue);
				onChange(event);
			}}
			onKeyUp={(event) => {
				const {value: newValue} = event.target;

				if (newValue === '-_') {
					return;
				}

				if (
					!newValue ||
					(newValue.startsWith('-') &&
						newValue.length <= ONE_DIGIT_NEGATIVE_NUMBER_LENGTH)
				) {
					setCurrentValue(newValue);
					onChange(event);
				}
			}}
			ref={inputRef}
			type="text"
			value={currentValue}
		/>
	);
};

const Main = ({
	dataType,
	defaultLanguageId,
	editingLanguageId,
	id,
	localizable,
	localizedValue = {},
	name,
	onBlur,
	onChange,
	onFocus,
	placeholder,
	predefinedValue = '',
	readOnly,
	symbols,
	value = '',
	...otherProps
}) => {
	const [edited, setEdited] = useState(false);

	return (
		<FieldBase
			{...otherProps}
			id={id}
			localizedValue={localizedValue}
			name={name}
			readOnly={readOnly}
		>
			<Numeric
				dataType={dataType}
				defaultLanguageId={defaultLanguageId}
				disabled={readOnly}
				editingLanguageId={editingLanguageId}
				id={id}
				localizable={localizable}
				localizedValue={localizedValue}
				name={name}
				onBlur={onBlur}
				onChange={(event) => {
					if (!edited) {
						setEdited(true);
					}

					onChange(event);
				}}
				onFocus={onFocus}
				placeholder={placeholder}
				predefinedValue={predefinedValue}
				symbols={symbols}
				value={edited || value ? value : predefinedValue}
			/>
		</FieldBase>
	);
};

Main.displayName = 'Numeric';

export {Main};
export default withConfirmationField(Main);
