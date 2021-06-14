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

import template from './RoleListItem.soy';

import '../autocomplete_item/AutocompleteItem.es';

class RoleListItem extends Component {
	syncSelectedRoles() {
		this._selected = this.selectedRoles.reduce(
			(itemSelected, item) => itemSelected || item.id === this.id,
			false
		);

		return this._selected;
	}

	_handleToggleItem(event) {
		event.preventDefault();

		return this.emit('toggleItem', {
			id: this.id,
			name: this.name,
		});
	}
}

Soy.register(RoleListItem, template);

RoleListItem.STATE = {
	_selected: Config.bool().value(false),
	id: Config.oneOfType([Config.number(), Config.string()]),
	name: Config.string(),
	query: Config.string(),
	selectedRoles: Config.array(
		Config.shapeOf({
			id: Config.oneOfType([Config.number(), Config.string()]),
			name: Config.string(),
		})
	).value([]),
};

export {RoleListItem};
export default RoleListItem;
