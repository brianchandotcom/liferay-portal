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

import './NumericRegister.soy';

import {ClayInput} from '@clayui/form';
import React, {useEffect, useRef} from 'react';
import createNumberMask from 'text-mask-addons/dist/createNumberMask';
import vanillaTextMask from 'vanilla-text-mask';

import {FieldBaseProxy} from '../FieldBase/ReactFieldBase.es';
import {useSyncValue} from '../Text/Text.es';
import getConnectedReactComponentAdapter from '../util/ReactComponentAdapter.es';
import {connectStore} from '../util/connectStore.es';
import templates from './NumericAdapter.soy';

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
	value: initialValue,
	...otherProps
}) => {
	const [value, setValue] = useSyncValue(initialValue);
	const inputRef = useRef(null);

	useEffect(() => {
		let maskInstance = null;

		if (inputRef.current) {
			let {value} = inputRef.current;

			if (dataType === 'integer' && value) {
				value = String(
					Math.round(value.replace(symbols.decimalSymbol, '.'))
				);
			}

			const mask = createNumberMask(getMaskConfig(dataType, symbols));

			maskInstance = vanillaTextMask({
				inputElement: inputRef.current,
				mask,
			});

			if (value !== '') {
				setValue(value);
				onChange({target: {value}});
			}
		}

		return () => {
			if (maskInstance) {
				maskInstance.destroy();
			}
		};
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [dataType, inputRef, setValue]);

	return (
		<ClayInput
			{...otherProps}
			aria-label="numeric"
			disabled={disabled}
			onChange={event => {
				const {value: newValue} = event.target;

				if (newValue.substr(-1) === symbols.decimalSymbol) {
					return;
				}

				setValue(newValue);
				onChange(event);
			}}
			ref={inputRef}
			type="text"
			value={value}
		/>
	);
};

const NumericProxy = connectStore(
	({
		dataType,
		emit,
		id,
		name,
		placeholder,
		predefinedValue = '',
		readOnly,
		symbols,
		value,
		...otherProps
	}) => (
		<FieldBaseProxy {...otherProps} id={id} name={name} readOnly={readOnly}>
			<Numeric
				dataType={dataType}
				disabled={readOnly}
				id={id}
				name={name}
				onBlur={event =>
					emit('fieldBlurred', event, event.target.value)
				}
				onChange={event =>
					emit('fieldEdited', event, event.target.value)
				}
				onFocus={event =>
					emit('fieldFocused', event, event.target.value)
				}
				placeholder={placeholder}
				symbols={symbols}
				value={value ? value : predefinedValue}
			/>
		</FieldBaseProxy>
	)
);

const ReactNumericAdapter = getConnectedReactComponentAdapter(
	NumericProxy,
	templates
);

export {ReactNumericAdapter};
export default ReactNumericAdapter;
