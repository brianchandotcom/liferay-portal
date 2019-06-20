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

import {getConnectedComponent} from '../../../store/ConnectedComponent.es';
import templates from './FragmentsEditorSidebarCard.soy';

/**
 * FragmentsEditorSidebarCard
 * @review
 */
class FragmentsEditorSidebarCard extends Component {
	/**
	 * Callback that is executed when a item entry is clicked.
	 * It propagates a itemClick event with the item information.
	 * @param {!MouseEvent} event
	 * @private
	 * @review
	 */
	_handleClick(event) {
		const {itemId, itemName} = event.delegateTarget.dataset;

		this.emit('itemClick', {
			itemId,
			itemName
		});
	}
}

const ConnectedFragmentsEditorSidebarCard = getConnectedComponent(
	FragmentsEditorSidebarCard,
	['spritemap']
);

Soy.register(ConnectedFragmentsEditorSidebarCard, templates);

export {ConnectedFragmentsEditorSidebarCard, FragmentsEditorSidebarCard};
export default ConnectedFragmentsEditorSidebarCard;
