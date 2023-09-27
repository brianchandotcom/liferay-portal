/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import openDeleteTemplateModal from './modal/openDeleteTemplateModal';

export default function propsTransformer({portletNamespace, ...otherProps}) {
	const deleteSelectedDDMTemplates = () => {
		openDeleteTemplateModal({
			message: Liferay.Language.get(
				'some-of-these-templates-are-being-used-in-pages.-are-you-sure-you-want-to-delete-this'
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

			if (action === 'deleteSelectedDDMTemplates') {
				deleteSelectedDDMTemplates();
			}
		},
	};
}
