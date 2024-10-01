/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {commerceEvents} from 'commerce-frontend-js';

function returnableOrderItemsPropsTransformer({...props}) {
	return {
		...props,
		onSelectedItemsChange: (selectedItems) => {
			window.top.Liferay.fire(commerceEvents.SELECTED_RETURNABLE_ITEMS, {
				selectedItems,
			});
		},
	};
}

export default returnableOrderItemsPropsTransformer;
