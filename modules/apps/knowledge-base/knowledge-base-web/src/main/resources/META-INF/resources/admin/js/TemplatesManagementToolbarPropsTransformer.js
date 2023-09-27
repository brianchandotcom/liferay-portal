/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {getCheckedCheckboxes, openConfirmModal} from 'frontend-js-web';

export default function propsTransformer({
	additionalProps: {deleteKBTemplatesURL},
	portletNamespace,
	...otherProps
}) {
	return {
		...otherProps,
		onActionButtonClick(event, {item}) {
			if (item?.data?.action === 'deleteKBTemplates') {
				openConfirmModal({
					message: Liferay.Language.get(
						'are-you-sure-you-want-to-delete-the-selected-templates'
					),
					onConfirm: (isConfirmed) => {
						if (isConfirmed) {
							const form = document.getElementById(
								`${portletNamespace}fm`
							);

							if (!form) {
								return;
							}

							form.setAttribute('method', 'post');

							const kbTemplateIds = form.querySelector(
								`#${portletNamespace}kbTemplateIds`
							);

							if (kbTemplateIds) {
								kbTemplateIds.setAttribute(
									'value',
									getCheckedCheckboxes(
										form,
										`${portletNamespace}allRowIds`
									)
								);
							}

							submitForm(form, deleteKBTemplatesURL);
						}
					},
				});
			}
		},
	};
}
