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
	componentId,
	disabled,
	large,
	name,
	onUpdate,
	quantity,
	...quantitySettings
}) {
	const commonProps = {
		className: classnames({
			'form-control-lg': large,
			'quantity-selector': true,
		}),
		'data-component-id': componentId,
		disabled,
		name,
		onUpdate,
		quantity,
	};

	return (
		<>
			{allowedQuantities?.length > 0 ? (
				<ListQuantitySelector
					{...commonProps}
					allowedQuantities={allowedQuantities}
				/>
			) : (
				<InputQuantitySelector {...commonProps} {...quantitySettings} />
			)}
		</>
	);
}

QuantitySelector.defaultProps = {
	allowedQuantities: [],
	disabled: false,
	large: false,
	maxQuantity: 99,
	minQuantity: 1,
	multipleQuantity: 1,
	onUpdate: () => {},
	quantity: 1,
};

QuantitySelector.propTypes = {
	allowedQuantities: PropTypes.arrayOf(PropTypes.number),
	componentId: PropTypes.string,
	disabled: PropTypes.bool,
	large: PropTypes.bool,
	maxQuantity: PropTypes.number,
	minQuantity: PropTypes.number,
	multipleQuantity: PropTypes.number,
	name: PropTypes.string,
	onUpdate: PropTypes.func,
	quantity: PropTypes.number,
};

export default QuantitySelector;
