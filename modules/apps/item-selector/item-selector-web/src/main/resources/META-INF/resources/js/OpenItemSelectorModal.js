/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {createPortletURL, openSelectionModal} from 'frontend-js-web';

const openItemSelectorModal = ({multiple, params, url, ...props}) => {
	openSelectionModal({
		multiple,
		url: createPortletURL(url, {
			multipleSelection: multiple || false,
			p_p_id: 'com_liferay_item_selector_web_portlet_ItemSelectorPortlet',
			...params,
		}),
		...props,
	});
};

export default openItemSelectorModal;
