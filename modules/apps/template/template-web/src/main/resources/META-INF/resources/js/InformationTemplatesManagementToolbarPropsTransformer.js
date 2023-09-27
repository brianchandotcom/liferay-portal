/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import openDeleteTemplateModal from './modal/openDeleteTemplateModal';
import openTemplateModal from './modal/openTemplateModal';

export default function propsTransformer({
	additionalProps: {addTemplateEntryURL, itemTypes} = {},
	portletNamespace,
	...otherProps
}) {
	const deleteSelectedTemplateEntries = () => {
		openDeleteTemplateModal({
			message: Liferay.Language.get(
				'are-you-sure-you-want-to-delete-this'
			),
			multiple: true,
			onDelete: () => {
				const form = document.getElementById(`${portletNamespace}fm`);

				if (form) {
					submitForm(form);
				}
			},
		});
	};

	return {
		...otherProps,
		onActionButtonClick(event, {item}) {
			const data = item?.data;

			const action = data?.action;

			if (action === 'deleteSelectedTemplateEntries') {
				deleteSelectedTemplateEntries();
			}
		},
		onCreateButtonClick(event, {item}) {
			const action = item?.data?.action;

			if (action === 'addInformationTemplateEntry') {
				openTemplateModal({
					addTemplateEntryURL,
					itemTypes,
					namespace: portletNamespace,
				});
			}
		},
	};
}
