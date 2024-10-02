/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {MiniCartUtils} from 'commerce-frontend-js';
import React from 'react';

const ProductOptionsDataRenderer = ({itemData: {options}}) => {
	const productOptions = JSON.parse(options);

	return (
		<div className="child-items">
			{productOptions.map((option, index) => {
				const {skuId, skuOptionName, skuOptionValueNames, value} =
					option;

				const childItem = [].find(
					(childItem) => childItem.skuId === parseInt(skuId, 10)
				);

				const {name, quantity, skuUnitOfMeasure} = childItem || {};

				return name ? (
					<div className="item-info-extra pt-2" key={index}>
						<div className="h6 item-name">{skuOptionName}</div>

						<p className="item-sku">
							<span>
								<span>
									{MiniCartUtils.parseValue(
										skuOptionValueNames
									) || MiniCartUtils.parseValue(value)}
								</span>

								<span className="pl-2">
									{`(${quantity} \u00D7 ${name} ${
										skuUnitOfMeasure?.key || ''
									})`}
								</span>
							</span>
						</p>
					</div>
				) : (
					<div className="item-info-extra pt-2" key={index}>
						<div className="h6 item-name">{skuOptionName}</div>

						<p className="item-sku">
							{MiniCartUtils.parseValue(skuOptionValueNames) ||
								MiniCartUtils.parseValue(value)}
						</p>
					</div>
				);
			})}
		</div>
	);
};

export default ProductOptionsDataRenderer;
