/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	getCheckedCheckboxes,
	openConfirmModal,
	postForm,
} from 'frontend-js-web';

export default function propsTransformer({
	additionalProps: {deleteDataProviderURL},
	portletNamespace,
	...otherProps
}) {
	return {
		...otherProps,
		onActionButtonClick(event, {item}) {
			if (item?.data?.action === 'deleteDataProviderInstances') {
				openConfirmModal({
					message: Liferay.Language.get(
						'are-you-sure-you-want-to-delete-this'
					),
					onConfirm: (isConfirmed) => {
						if (isConfirmed) {
							const form = document.getElementById(
								`${portletNamespace}searchContainerForm`
							);

							const searchContainer = document.getElementById(
								`${portletNamespace}dataProviderInstance`
							);

							if (form && searchContainer) {
								postForm(form, {
									data: {
										deleteDataProviderInstanceIds: getCheckedCheckboxes(
											searchContainer,
											`${portletNamespace}allRowIds`
										),
									},
									url: deleteDataProviderURL,
								});
							}
						}
					},
				});
			}
		},
	};
}
