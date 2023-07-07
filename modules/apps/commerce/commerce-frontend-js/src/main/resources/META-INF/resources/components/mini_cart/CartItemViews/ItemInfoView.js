/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import ClayLabel from '@clayui/label';
import {ClayTooltipProvider} from '@clayui/tooltip';
import {sub} from 'frontend-js-web';
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

function ItemInfoViewReplacement({replacedSku}) {
	return (
		<div className="item-info-replacement">
			<ClayLabel displayType="info">
				{Liferay.Language.get('replacement')}
			</ClayLabel>

			<ClayTooltipProvider>
				<span
					data-tooltip-align="left"
					title={sub(
						Liferay.Language.get('replacement-product-for-x'),
						replacedSku
					)}
				>
					<ClayIcon aria-label="Info" symbol="info-circle" />
				</span>
			</ClayTooltipProvider>
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

function ItemInfoView({childItems = [], name, options = '', replacedSku, sku}) {
	const hasReplacement = !!replacedSku;
	const isBundle = !!childItems.length;
	const hasOptions = !!options;

	return (
		<>
			<ItemInfoViewBase name={name} sku={sku} />

			{hasReplacement && (
				<ItemInfoViewReplacement replacedSku={replacedSku} />
			)}

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
