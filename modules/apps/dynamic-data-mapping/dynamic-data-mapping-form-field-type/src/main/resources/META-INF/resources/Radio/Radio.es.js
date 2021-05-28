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

import {ClayRadio} from '@clayui/form';
import React, {useMemo} from 'react';

import {FieldBase} from '../FieldBase/ReactFieldBase.es';
import {useSyncValue} from '../hooks/useSyncValue.es';
import {setJSONArrayValue} from '../util/setters.es';

const Radio = ({
	editingLanguageId,
	inline,
	name,
	onBlur,
	onChange,
	onFocus,
	options = [
		{
			label: 'Option 1',
			value: 'option1',
		},
		{
			label: 'Option 2',
			value: 'option2',
		},
	],
	predefinedValue,
	readOnly: disabled,
	value: initialValue,
	...otherProps
}) => {
	const predefinedValueMemo = useMemo(() => {
		if (typeof predefinedValue === 'string') {
			return predefinedValue;
		}

		const predefinedValueJSONArray =
			setJSONArrayValue(predefinedValue) || [];

		return predefinedValueJSONArray[0];
	}, [predefinedValue]);

	const [currentValue, setCurrentValue] = useSyncValue(
		initialValue ? initialValue : predefinedValueMemo,
		true,
		editingLanguageId
	);

	return (
		<FieldBase {...otherProps} name={name} readOnly={disabled}>
			<div className="ddm-radio" onBlur={onBlur} onFocus={onFocus}>
				{options.map((option) => (
					<ClayRadio
						checked={currentValue === option.value}
						disabled={disabled}
						inline={inline}
						key={option.value}
						label={option.label}
						name={name}
						onChange={(event) => {
							setCurrentValue(option.value);

							onChange(event);
						}}
						value={option.value}
					/>
				))}
			</div>
		</FieldBase>
	);
};

export default Radio;
