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

'use strict';

import Component from 'metal-component';
import Soy, {Config} from 'metal-soy';

import template from './ProductsCompareItem.soy';

class ProductsCompareItem extends Component {
	_hideItem() {
		this.emit('updateProductVisibility', this.id, 'hidden');

		return setTimeout(() => {
			return this.emit('removeProduct', {
				id: this.id,
			});
		}, 300);
	}

	_handleRemoveProduct() {
		return this._hideItem();
	}
}

Soy.register(ProductsCompareItem, template);

ProductsCompareItem.STATE = {
	id: Config.oneOfType([Config.string(), Config.number()]),
	spritemap: Config.string().required(),
	thumbnail: Config.string(),
	visibility: Config.oneOf(['showing', 'visible', 'hiding', 'hidden']),
};

export {ProductsCompareItem};
export default ProductsCompareItem;
