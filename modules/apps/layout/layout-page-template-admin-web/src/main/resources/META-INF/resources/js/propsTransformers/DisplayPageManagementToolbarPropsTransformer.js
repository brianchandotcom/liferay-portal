/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openSimpleInputModal} from 'frontend-js-web';

import openDeletePageTemplateModal from '../modal/openDeletePageTemplateModal';

export default function propsTransformer({portletNamespace, ...otherProps}) {
	const deleteSelectedDisplayPages = () => {
		openDeletePageTemplateModal({
			onDelete: () => {
				const form = document.getElementById(`${portletNamespace}fm`);

				if (form) {
					submitForm(form);
				}
			},
			title: Liferay.Language.get('display-page-templates'),
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

			if (action === 'deleteSelectedDisplayPages') {
				deleteSelectedDisplayPages();
			}
			else if (action === 'exportDisplayPages') {
				exportDisplayPages(data);
			}
		},
		onCreationMenuItemClick(event, {item}) {
			const data = item?.data;

			if (data?.action === 'addDisplayPageCollection') {
				openSimpleInputModal({
					dialogTitle: Liferay.Language.get('add-folder'),
					formSubmitURL: data.addDisplayPageCollectionURL,
					mainFieldLabel: Liferay.Language.get('name'),
					mainFieldName: 'name',
					mainFieldPlaceholder: Liferay.Language.get('name'),
					namespace: portletNamespace,
				});
			}
		},
	};
}
