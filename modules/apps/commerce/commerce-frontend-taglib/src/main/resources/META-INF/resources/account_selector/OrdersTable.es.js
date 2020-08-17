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

import {debounce} from 'frontend-js-web';
import Component from 'metal-component';
import Soy, {Config} from 'metal-soy';

import template from './OrdersTable.soy';

import '../autocomplete_item/AutocompleteItem.es';

class OrdersTable extends Component {
	created() {
		this._getOrders = debounce(this._getOrders.bind(this), 500);
	}

	_getOrders() {
		return this.emit('getOrders', this.filterString);
	}

	_handleBackButtonClick() {
		return this.emit('changeSelectedView', 'accounts');
	}

	_handleFilterChange(evt) {
		this.filterString = evt.target.value;

		return this._getOrders();
	}

	_handleSubmitFilter(evt) {
		evt.preventDefault();

		return this._getOrders();
	}
}

Soy.register(OrdersTable, template);

OrdersTable.STATE = {
	accountName: Config.string(),
	createNewOrderLink: Config.string(),
	currentOrderId: Config.oneOfType([Config.number(), Config.string()]),
	filterString: Config.string().value('').internal(),
	orders: Config.arrayOf(
		Config.shapeOf({
			addOrderLink: Config.string(),
			id: Config.oneOfType([Config.number(), Config.string()]).required(),
			lastEdit: Config.string(),
			status: Config.string(),
		})
	),
	showBack: Config.bool().value(true),
	spritemap: Config.string().required(),
	viewAllOrdersLink: Config.string(),
};

export {OrdersTable};
export default OrdersTable;
