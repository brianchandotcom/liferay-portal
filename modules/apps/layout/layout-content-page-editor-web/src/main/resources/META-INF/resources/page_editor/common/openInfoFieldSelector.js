/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openSelectionModal} from 'frontend-js-components-web';
import {getPortletNamespace} from 'frontend-js-web';

import {config} from '../app/config/index';

export function openInfoFieldSelector({
	formItemId,
	itemType,
	onCancel,
	onSave,
	segmentsExperienceId,
	selectedFields = [],
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
		getSelectedItemsOnly: false,
		height: '70vh',
		multiple: true,
		onClose: onCancel,
		onSelect: (items) => {
			const fields = [];
			const uniqueIds = new Set();

			for (const item of items) {
				if (!item.value) {
					continue;
				}

				let field;

				try {
					field = JSON.parse(item.value);
				}
				catch {
					field = {uniqueId: item.value};
				}

				uniqueIds.add(field.uniqueId);

				if (item.checked) {
					fields.push(field);
				}
			}

			for (const uniqueId of selectedFields) {
				if (!uniqueIds.has(uniqueId)) {
					fields.push({uniqueId});
				}
			}

			onSave(fields);
		},
		size: 'lg',
		title: Liferay.Language.get('manage-form-fields'),
		url: url.toString(),
	});
}
