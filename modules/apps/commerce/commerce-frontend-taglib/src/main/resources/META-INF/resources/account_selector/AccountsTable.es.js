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

import template from './AccountsTable.soy';

import './AccountsTableItem.es';

class AccountsTable extends Component {
	created() {
		this._handleFilterChange = debounce(
			this._handleFilterChange.bind(this),
			500
		);
	}

	handleSelectAccount(accountData) {
		this.emit('accountSelected', accountData);
	}

	_getAccounts() {
		return this.emit('getAccounts', this.filterString);
	}

	_handleFilterChange(evt) {
		this.filterString = evt.target.value;

		return this._getAccounts();
	}

	_handleSubmitFilter(evt) {
		evt.preventDefault();

		return this._getAccounts();
	}
}

Soy.register(AccountsTable, template);

AccountsTable.STATE = {
	accounts: Config.arrayOf(
		Config.shapeOf({
			accountId: Config.oneOfType([
				Config.string(),
				Config.number(),
			]).required(),
			name: Config.string(),
			thumbnail: Config.string(),
		})
	),
	createNewOrderLink: Config.string(),
	currentAccount: Config.shapeOf({
		accountId: Config.oneOfType([
			Config.string(),
			Config.number(),
		]).required(),
		name: Config.string(),
		thumbnail: Config.string(),
	}),
	filterString: Config.string().value('').internal(),
};

export {AccountsTable};
export default AccountsTable;
