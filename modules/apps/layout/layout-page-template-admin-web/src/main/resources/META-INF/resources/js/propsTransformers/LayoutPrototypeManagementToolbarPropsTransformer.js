/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import openDeletePageTemplateModal from '../modal/openDeletePageTemplateModal';

export default function propsTransformer({portletNamespace, ...otherProps}) {
	return {
		...otherProps,
		onActionButtonClick(event, {item}) {
			const data = item?.data;

			if (data?.action === 'deleteSelectedLayoutPrototypes') {
				openDeletePageTemplateModal({
					onDelete: () => {
						const form = document.getElementById(
							`${portletNamespace}fm`
						);

						if (form) {
							submitForm(form);
						}
					},
					title: Liferay.Language.get('page-templates'),
				});
			}
		},
	};
}
