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

import {openToast} from 'frontend-js-web';

import addLoadingAnimation from './addLoadingAnimation';
import {LAYOUT_DATA_ITEM_TYPES} from './constants/layoutDataItemTypes';

const addPortlet = ({item, plid, targetItem, targetPosition}) => {
	const loading = addLoadingAnimation(targetItem, targetPosition);

	openToast({
		message: Liferay.Language.get('the-application-was-added-to-the-page'),
		type: 'success',
	});

	const portletData =
		item.type === LAYOUT_DATA_ITEM_TYPES.widget
			? ''
			: `${item.data.classPK},${item.data.className}`;

	Liferay.Portlet.add({
		beforePortletLoaded: () => null,
		placeHolder: loading,
		plid,
		portletData,
		portletId: item.data.portletId,
		portletItemId: item.data.portletItemId || '',
	});
};

export default addPortlet;
