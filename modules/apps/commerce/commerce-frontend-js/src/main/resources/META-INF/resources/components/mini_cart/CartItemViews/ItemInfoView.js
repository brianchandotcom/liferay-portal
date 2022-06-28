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

function ItemInfoViewOptions({options}) {
	return (
		<div className="item-info-extra mt-3">
			<h6 className="options">{options}</h6>
		</div>
	);
}

function ItemInfoViewBundle({childItems}) {
	return (
		<div className="child-items">
			{childItems.map((item, index) => {
				const {name, quantity} = item;

				return (
					<div className="child-item" key={index}>
						<span>
							{quantity} &times; {name}
						</span>
					</div>
				);
			})}
		</div>
	);
}

function ItemInfoViewBase({name, sku}) {
	return (
		<div className="item-info-base">
			<h5 className="item-name">{name}</h5>

			<p className="item-sku">{sku}</p>
		</div>
	);
}

function ItemInfoView({childItems = [], name, options = '', sku}) {
	const isBundle = !!childItems.length;
	const hasOptions = !!options;

	return (
		<>
			<ItemInfoViewBase name={name} sku={sku} />

			{isBundle && <ItemInfoViewBundle childItems={childItems} />}

			{hasOptions && <ItemInfoViewOptions options={options} />}
		</>
	);
}

ItemInfoView.propTypes = {
	childItems: PropTypes.array,
	name: PropTypes.string.isRequired,
	options: PropTypes.string,
	sku: PropTypes.string.isRequired,
};

export default ItemInfoView;
