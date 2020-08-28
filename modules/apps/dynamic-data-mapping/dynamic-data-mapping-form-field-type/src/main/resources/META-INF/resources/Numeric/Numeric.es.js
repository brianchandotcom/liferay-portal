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
import React, {useEffect, useRef, useState} from 'react';
import createNumberMask from 'text-mask-addons/dist/createNumberMask';
import vanillaTextMask from 'vanilla-text-mask';

import {FieldBase} from '../FieldBase/ReactFieldBase.es';

const getMaskConfig = (dataType, symbols) => {
	let config = {
		allowLeadingZeroes: true,
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

const Numeric = ({
	dataType = 'integer',
	disabled,
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

	useEffect(() => {
		let maskInstance = null;

		if (inputRef.current && value) {
			let newValue = value;

			if (dataType === 'integer') {
				newValue = String(
					Math.round(newValue.replace(symbols.decimalSymbol, '.'))
				);
			}

			const mask = createNumberMask(getMaskConfig(dataType, symbols));

			maskInstance = vanillaTextMask({
				inputElement: inputRef.current,
				mask,
			});

			if (newValue !== '') {
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
			disabled={disabled}
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
			ref={inputRef}
			type="text"
			value={currentValue}
		/>
	);
};

const Main = ({
	dataType,
	id,
	name,
	onBlur,
	onChange,
	onFocus,
	placeholder,
	predefinedValue = '',
	readOnly,
	symbols,
	value,
	...otherProps
}) => (
	<FieldBase {...otherProps} id={id} name={name} readOnly={readOnly}>
		<Numeric
			dataType={dataType}
			disabled={readOnly}
			id={id ? id : name}
			name={name}
			onBlur={onBlur}
			onChange={onChange}
			onFocus={onFocus}
			placeholder={placeholder}
			symbols={symbols}
			value={value ? value : predefinedValue}
		/>
	</FieldBase>
);

Main.displayName = 'Numeric';

export {Main};
export default Main;
