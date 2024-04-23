/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {getPortletNamespace, openSelectionModal} from 'frontend-js-web';

import {config} from '../app/config/index';

export function openInfoFieldSelector({itemType, onCancel, onSave}) {
	const url = new URL(config.infoFieldItemSelectorURL);

	url.searchParams.set(
		`${getPortletNamespace(Liferay.PortletKeys.ITEM_SELECTOR)}itemType`,
		itemType
	);

	openSelectionModal({
		multiple: true,
		onClose: onCancel,
		onSelect: (items) =>
			onSave(items.map((item) => JSON.parse(item.value))),
		title: Liferay.Language.get('manage-form-fields'),
		url: url.toString(),
	});
}
