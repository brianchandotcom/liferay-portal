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

import PropTypes from 'prop-types';
import React from 'react';

import AddToCartButton from './AddToCartButton';
import WrapperWithQuantity from './WrapperWithQuantity';

function AddToCart(props) {
	return props?.settings?.withQuantity ? (
		<WrapperWithQuantity AddToCartButton={AddToCartButton} {...props} />
	) : (
		<AddToCartButton {...props} />
	);
}

AddToCart.defaultProps = {
	settings: {
		withQuantity: false,
	},
};

AddToCart.propTypes = {
	settings: PropTypes.shape({
		withQuantity: PropTypes.oneOfType([
			PropTypes.bool,
			PropTypes.shape({
				allowedQuantities: PropTypes.arrayOf(PropTypes.number),
				disabled: PropTypes.bool,
				large: PropTypes.bool,
				maxQuantity: PropTypes.number,
				minQuantity: PropTypes.number,
				multipleQuantity: PropTypes.number,
				name: PropTypes.string,
				onUpdate: PropTypes.func,
				quantity: PropTypes.number,
			}),
		]),
	}),
};

export default AddToCart;
