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
import React, {useMemo} from 'react';

import {
	getProductMaxQuantity,
	getProductMinQuantity,
} from '../../utilities/quantities';

function InputQuantitySelector({
	className,
	disabled,
	maxQuantity,
	minQuantity,
	multipleQuantity,
	name,
	onUpdate,
	quantity,
}) {
	const inputMin = useMemo(
		() => getProductMinQuantity(minQuantity, multipleQuantity),
		[multipleQuantity, minQuantity]
	);

	const inputMax = useMemo(
		() => getProductMaxQuantity(maxQuantity, multipleQuantity),
		[maxQuantity, multipleQuantity]
	);

	const getValidInputNumber = (value) => {
		if (!value || value < inputMin) {
			return inputMin;
		}

		if (inputMax && value > inputMax) {
			return inputMax;
		}

		if (multipleQuantity > 1) {
			const diff = value % multipleQuantity;

			return diff ? value - diff + multipleQuantity : value;
		}

		return value;
	};

	return (
		<ClayInput
			className={className}
			disabled={disabled}
			max={inputMax || ''}
			min={inputMin}
			name={name}
			onChange={({target}) => {
				onUpdate(getValidInputNumber(Number(target.value)));
			}}
			step={multipleQuantity > 1 ? multipleQuantity : ''}
			type="number"
			value={String(quantity || '')}
		/>
	);
}

InputQuantitySelector.defaultProps = {
	maxQuantity: '',
	minQuantity: 1,
	multipleQuantity: 1,
};

export default InputQuantitySelector;
