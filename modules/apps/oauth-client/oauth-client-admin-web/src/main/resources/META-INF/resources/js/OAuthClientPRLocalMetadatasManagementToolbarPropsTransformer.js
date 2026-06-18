/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openConfirmModal} from 'frontend-js-components-web';
import {getCheckedCheckboxes, postForm} from 'frontend-js-web';

export default function propsTransformer({
	additionalProps: {deleteOAuthClientPRLocalMetadataURL},
	portletNamespace,
	...otherProps
}) {
	return {
		...otherProps,
		onActionButtonClick: (event, {item}) => {
			if (item?.data?.action === 'deleteOAuthClientPRLocalMetadata') {
				openConfirmModal({
					message: Liferay.Language.get(
						'are-you-sure-you-want-to-delete-the-selected-entries'
					),
					onConfirm: (isConfirmed) => {
						if (isConfirmed) {
							const form = document.getElementById(
								`${portletNamespace}fm`
							);

							if (form) {
								const ids = getCheckedCheckboxes(
									form,
									`${portletNamespace}allRowIds`
								);

								postForm(form, {
									data: {
										oAuthClientPRLocalMetadataIds: ids,
									},
									url: deleteOAuthClientPRLocalMetadataURL,
								});
							}
						}
					},
				});
			}
		},
	};
}
