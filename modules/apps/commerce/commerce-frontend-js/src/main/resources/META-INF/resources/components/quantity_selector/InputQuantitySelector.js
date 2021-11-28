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
import React from 'react';

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
	return (
		<ClayInput
			className={className}
			disabled={disabled}
			max={maxQuantity}
			min={minQuantity}
			name={name}
			onChange={({target}) => {
				onUpdate(Number(target.value));
			}}
			step={multipleQuantity}
			type="number"
			value={String(quantity)}
		/>
	);
}

InputQuantitySelector.defaultProps = {
	maxQuantity: '',
	minQuantity: 1,
	multipleQuantity: '',
};

export default InputQuantitySelector;
