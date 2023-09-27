/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {getCheckedCheckboxes, openConfirmModal} from 'frontend-js-web';

export default function propsTransformer({
	additionalProps: {deleteRecordSetsURL},
	portletNamespace,
	...otherProps
}) {
	return {
		...otherProps,
		onActionButtonClick(event, {item}) {
			if (item?.data?.action === 'deleteRecordSets') {
				openConfirmModal({
					message: Liferay.Language.get(
						'are-you-sure-you-want-to-delete-this'
					),
					onConfirm: (isConfirmed) => {
						if (isConfirmed) {
							const form = document.getElementById(
								`${portletNamespace}fm`
							);

							if (!form) {
								return;
							}

							const searchContainer = form.querySelector(
								`#${otherProps.searchContainerId}`
							);

							form.setAttribute('method', 'post');

							if (searchContainer) {
								const recordSetIds = form.querySelector(
									`#${portletNamespace}recordSetIds`
								);

								if (recordSetIds) {
									recordSetIds.setAttribute(
										'value',
										getCheckedCheckboxes(
											searchContainer,
											`${portletNamespace}allRowIds`
										)
									);
								}
							}

							submitForm(form, deleteRecordSetsURL);
						}
					},
				});
			}
		},
	};
}
