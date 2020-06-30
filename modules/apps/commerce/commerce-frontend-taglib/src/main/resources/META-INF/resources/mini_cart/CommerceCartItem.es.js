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

import template from './CommerceCartItem.soy';

import 'clay-icon';

import './Loader.es';

import '../price/Price.es';

import '../quantity_selector/QuantitySelector.es';

class CommerceCartItem extends Component {
	_handleUpdateQuantity(quantity) {
		return this.emit('submitQuantity', this.id, quantity);
	}

	_handleDeleteItem() {
		return this.emit('deleteItem', this.id);
	}

	_handleCancelDeletion() {
		return this.emit('cancelItemDeletion', this.id);
	}
}

Soy.register(CommerceCartItem, template);

CommerceCartItem.STATE = {
	collapsed: Config.bool().value(false),
	deleteDisabled: Config.bool().value(false),
	deleting: Config.bool().value(false),
	displayDiscountLevels: Config.bool().value(false),
	errorMessages: Config.array().value([]),
	id: Config.oneOfType([Config.string(), Config.number()]),
	inputChanged: Config.bool().value(false),
	options: Config.array(
		Config.shapeOf({
			key: Config.string(),
			value: Config.oneOfType([Config.string(), Config.number()])
		})
	),
	quantity: Config.number().value(0),
	settings: Config.object().value({}),
	spritemap: Config.string().required(),
	updating: Config.bool().value(false)
};

export {CommerceCartItem};
export default CommerceCartItem;
