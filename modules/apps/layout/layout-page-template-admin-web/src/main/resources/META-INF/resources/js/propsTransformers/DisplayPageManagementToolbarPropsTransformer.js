/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openCreationModal} from '@liferay/layout-js-components-web';

import openDeletePageTemplateModal from '../commands/openDeletePageTemplateModal';

export default function propsTransformer({portletNamespace, ...otherProps}) {
	const deleteSelectedEntries = (itemData) => {
		openDeletePageTemplateModal({
			onDelete: () => {
				const form = document.getElementById(`${portletNamespace}fm`);

				if (form) {
					submitForm(form, itemData?.deleteSelectedEntriesURL);
				}
			},
			title: Liferay.Language.get('entries'),
		});
	};

	const exportDisplayPages = (itemData) => {
		const form = document.getElementById(`${portletNamespace}fm`);

		if (form) {
			submitForm(form, itemData?.exportDisplayPageURL);
		}
	};

	return {
		...otherProps,
		onActionButtonClick(event, {item}) {
			const data = item?.data;

			const action = data?.action;

			if (action === 'deleteSelectedEntries') {
				deleteSelectedEntries(data);
			}
			else if (action === 'exportDisplayPages') {
				exportDisplayPages(data);
			}
		},
		onCreationMenuItemClick(event, {item}) {
			const data = item?.data;

			if (data?.action === 'addDisplayPageCollection') {
				openCreationModal({
					buttonLabel: Liferay.Language.get('create'),
					formSubmitURL: data.addDisplayPageCollectionURL,
					heading: Liferay.Language.get('new-folder'),
					portletNamespace,
				});
			}
		},
	};
}
