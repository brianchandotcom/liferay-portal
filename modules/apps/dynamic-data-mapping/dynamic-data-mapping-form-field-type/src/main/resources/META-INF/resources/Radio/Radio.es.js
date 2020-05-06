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

import {FieldBaseProxy} from '../FieldBase/ReactFieldBase.es';
import {useSyncValue} from '../hooks/useSyncValue.es';
import getConnectedReactComponentAdapter from '../util/ReactComponentAdapter.es';
import {connectStore} from '../util/connectStore.es';
import {setJSONArrayValue} from '../util/setters.es';

const Radio = ({
	disabled,
	inline,
	name,
	onBlur,
	onChange,
	onFocus,
	options,
	value,
}) => {
	const [currentValue, setCurrentValue] = useSyncValue(value);

	return (
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
	);
};

const RadioProxy = connectStore(
	({
		emit,
		options = [
			{
				label: 'Option 1',
			},
			{
				label: 'Option 2',
			},
		],
		predefinedValue,
		value,
		readOnly,
		inline,
		name,
		...otherProps
	}) => {
		const predefinedValueMemo = useMemo(() => {
			const predefinedValueJSONArray =
				setJSONArrayValue(predefinedValue) || [];

			return predefinedValueJSONArray[0];
		}, [predefinedValue]);

		return (
			<FieldBaseProxy {...otherProps} name={name} readOnly={readOnly}>
				<Radio
					disabled={readOnly}
					inline={inline}
					name={name}
					onBlur={(event) =>
						emit('fieldBlurred', event, event.target.value)
					}
					onChange={(event) => emit('fieldFocused', event)}
					onFocus={(event) =>
						emit('fieldEdited', event, event.target.value)
					}
					options={options}
					value={value ? value : predefinedValueMemo}
				/>
			</FieldBaseProxy>
		);
	}
);

const ReactRadioAdapter = getConnectedReactComponentAdapter(
	RadioProxy,
	'radio'
);

export {ReactRadioAdapter};
export default ReactRadioAdapter;
