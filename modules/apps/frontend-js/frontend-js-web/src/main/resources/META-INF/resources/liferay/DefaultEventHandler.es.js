/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import PortletBase from './PortletBase.es';

class DefaultEventHandler extends PortletBase {
	callAction(event) {
		const itemData = event.data.item.data;

		if (itemData && itemData.action && this[itemData.action]) {
			this[itemData.action](itemData);
		}
	}

	handleActionItemClicked(event) {
		this.callAction(event);
	}

	handleCreationButtonClicked(event) {
		const itemData = event.data.data;

		if (itemData && itemData.action && this[itemData.action]) {
			this[itemData.action](itemData);
		}
	}

	handleCreationMenuItemClicked(event) {
		this.callAction(event);
	}

	handleFilterItemClicked(event) {
		this.callAction(event);
	}

	handleItemClicked(event) {
		this.callAction(event);
	}
}

export default DefaultEventHandler;
