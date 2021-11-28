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

import classnames from 'classnames';
import PropTypes from 'prop-types';
import React from 'react';

import InputQuantitySelector from './InputQuantitySelector';
import ListQuantitySelector from './ListQuantitySelector';

function QuantitySelector({
	allowedQuantities,
	disabled,
	maxQuantity,
	minQuantity,
	multipleQuantity,
	name,
	onUpdate,
	quantity,
	size,
}) {
	const Selector =
		allowedQuantities?.length > 0
			? ListQuantitySelector
			: InputQuantitySelector;

	return (
		<Selector
			allowedQuantities={allowedQuantities}
			className={classnames({
				[`form-control-${size}`]: size,
				'quantity-selector': true,
			})}
			disabled={disabled}
			maxQuantity={maxQuantity}
			minQuantity={minQuantity}
			multipleQuantity={multipleQuantity}
			name={name}
			onUpdate={onUpdate}
			quantity={quantity}
		/>
	);
}

QuantitySelector.defaultProps = {
	disabled: false,
	minQuantity: 1,
	multipleQuantity: 1,
	quantity: 1,
};

QuantitySelector.propTypes = {
	disabled: PropTypes.bool,
	maxQuantity: PropTypes.number,
	minQuantity: PropTypes.number,
	multipleQuantity: PropTypes.number,
	name: PropTypes.string,
	onUpdate: PropTypes.func.isRequired,
	quantity: PropTypes.number,
	size: PropTypes.oneOf(['sm', 'md', 'lg']),
};

export default QuantitySelector;
