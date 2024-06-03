/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {getPortletNamespace, openSelectionModal} from 'frontend-js-web';

import {config} from '../app/config/index';

export function openInfoFieldSelector({
	formItemId,
	itemType,
	onCancel,
	onSave,
	segmentsExperienceId,
}) {
	const url = new URL(config.infoFieldItemSelectorURL);

	url.searchParams.set(
		`${getPortletNamespace(Liferay.PortletKeys.ITEM_SELECTOR)}formItemId`,
		formItemId
	);
	url.searchParams.set(
		`${getPortletNamespace(Liferay.PortletKeys.ITEM_SELECTOR)}itemType`,
		itemType
	);
	url.searchParams.set(
		`${getPortletNamespace(
			Liferay.PortletKeys.ITEM_SELECTOR
		)}segmentsExperienceId`,
		segmentsExperienceId
	);

	openSelectionModal({
		buttonAddLabel: Liferay.Language.get('save'),
		height: '70vh',
		multiple: true,
		onClose: onCancel,
		onSelect: (items) =>
			onSave(items.map((item) => JSON.parse(item.value))),
		size: 'lg',
		title: Liferay.Language.get('manage-form-fields'),
		url: url.toString(),
	});
}
