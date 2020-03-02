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

import './RadioRegister.soy';

import {ClayRadio} from '@clayui/form';
import React, {useMemo} from 'react';

import {FieldBaseProxy} from '../FieldBase/ReactFieldBase.es';
import getConnectedReactComponentAdapter from '../util/ReactComponentAdapter.es';
import {connectStore} from '../util/connectStore.es';
import {setJSONArrayValue} from '../util/setters.es';
import templates from './RadioAdapter.soy';

const Radio = ({
	disabled,
	inline,
	name,
	onBlur,
	onChange,
	onFocus,
	options,
	value,
}) => (
	<div className="ddm-radio" onBlur={onBlur} onFocus={onFocus}>
		{options.map(option => (
			<ClayRadio
				checked={value === option.value}
				disabled={disabled}
				inline={inline}
				key={option.value}
				label={option.label}
				name={name}
				onChange={onChange}
				value={option.value}
			/>
		))}
	</div>
);

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
					onBlur={event =>
						emit('fieldBlurred', event, event.target.value)
					}
					onChange={event => emit('fieldFocused', event)}
					onFocus={event =>
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
	templates
);

export {ReactRadioAdapter};
export default ReactRadioAdapter;
