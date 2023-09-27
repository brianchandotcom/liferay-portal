/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import openDeleteLayoutModal from './openDeleteLayoutModal';

export default function propsTransformer({portletNamespace, ...otherProps}) {
	const deleteSelectedLayoutUtilityPageEntries = (itemData) => {
		openDeleteLayoutModal({
			message: Liferay.Language.get(
				'are-you-sure-you-want-to-delete-the-selected-utility-pages'
			),
			multiple: true,
			onDelete: () => {
				const form = document.getElementById(`${portletNamespace}fm`);

				if (form) {
					submitForm(
						form,
						itemData?.deleteSelectedLayoutUtilityPageEntriesURL
					);
				}
			},
		});
	};

	const exportLayoutUtilityPageEntries = (itemData) => {
		const form = document.getElementById(`${portletNamespace}fm`);

		if (form) {
			submitForm(form, itemData?.exportLayoutUtilityPageEntriesURL);
		}
	};

	return {
		...otherProps,
		onActionButtonClick: (event, {item}) => {
			const data = item?.data;

			const action = data?.action;

			if (action === 'deleteSelectedLayoutUtilityPageEntries') {
				deleteSelectedLayoutUtilityPageEntries(data);
			}
			else if (action === 'exportLayoutUtilityPageEntries') {
				exportLayoutUtilityPageEntries(data);
			}
		},
	};
}
