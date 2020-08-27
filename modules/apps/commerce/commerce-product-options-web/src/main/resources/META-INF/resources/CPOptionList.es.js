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
import Soy from 'metal-soy';
import {Config} from 'metal-state';

import templates from './CPOptionList.soy';

/**
 * CPOptionList
 *
 */

class CPOptionList extends Component {
	_handleEditValues(event) {
		var target = event.currentTarget;

		var cpOptionId = target.getAttribute('data-id');

		this.emit('optionSelected', cpOptionId);
		this.emit('editValues', cpOptionId);
	}

	_handleAddOptionClick(event) {
		event.stopImmediatePropagation();
		event.preventDefault();

		this.emit('addOption');
	}

	_handleSelectOptionClick(event) {
		var target = event.currentTarget;

		var cpOptionId = target.getAttribute('data-id');

		this.emit('optionSelected', cpOptionId);
	}
}

/**
 * State definition.
 * @type {!Object}
 * @static
 */

CPOptionList.STATE = {
	currentCPOptionId: Config.string(),
	options: Config.array().value([]),
};

// Register component

Soy.register(CPOptionList, templates);

export default CPOptionList;
