/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import {getCheckedCheckboxes} from 'frontend-js-web';

import {openConfirmModal} from 'frontend-js-web';

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
