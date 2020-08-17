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

import template from './AccountsTableItem.soy';

import '../autocomplete_item/AutocompleteItem.es';

class AccountsTableItem extends Component {
	_handleItemClick() {
		this.emit('selectAccount', {
			accountId: this.accountId,
			name: this.name,
			thumbnail: this.thumbnail,
		});
	}
}

Soy.register(AccountsTableItem, template);

AccountsTableItem.STATE = {
	accountId: Config.oneOfType([Config.number(), Config.string()]).required(),
	name: Config.string(),
	query: Config.string(),
	thumbnail: Config.string(),
};

export {AccountsTableItem};
export default AccountsTableItem;
