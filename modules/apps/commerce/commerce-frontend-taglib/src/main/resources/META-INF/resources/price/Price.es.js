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

import Component from 'metal-component';
import Soy, {Config} from 'metal-soy';

import template from './Price.soy';

class Price extends Component {
	created() {
		window.Liferay.on('priceUpdated', this._updatePrice, this);
	}

	detached() {
		window.Liferay.detach('priceUpdated', this._updatePrice, this);
	}
	_updatePrice(e) {
		if (e.id === this.id) {
			this.displayDiscountLevels = e.displayDiscountLevels;
			this.prices = e.prices;
		}
	}
}

Price.STATE = {
	additionalDiscountClasses: Config.string(),
	additionalPriceClasses: Config.string(),
	additionalPromoPriceClasses: Config.string(),
	displayDiscountLevels: Config.bool().value(false),
	id: Config.string(),
	prices: Config.shapeOf({
		discountPercentage: Config.string(),
		discountPercentages: Config.array().value(null),
		finalPrice: Config.string(),
		price: Config.string().required(),
		promoPrice: Config.string(),
	}),
};

Soy.register(Price, template);

export {Price};
export default Price;
