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

import template from './RoleInputItem.soy';

class RoleInputItem extends Component {
	_handleRemoveItem(event) {
		event.preventDefault();

		return this.emit('removeItem', {
			id: this.id,
		});
	}
}

Soy.register(RoleInputItem, template);

RoleInputItem.STATE = {
	id: Config.oneOfType([Config.number(), Config.string()]).required(),
	name: Config.string().required(),
	spritemap: Config.string(),
};

export {RoleInputItem};
export default RoleInputItem;
