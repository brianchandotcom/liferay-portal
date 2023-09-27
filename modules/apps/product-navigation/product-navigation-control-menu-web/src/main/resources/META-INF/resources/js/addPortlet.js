/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
