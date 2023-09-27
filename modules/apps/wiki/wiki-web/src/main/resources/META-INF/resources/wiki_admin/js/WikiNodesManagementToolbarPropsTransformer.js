/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openConfirmModal, postForm} from 'frontend-js-web';

export default function propsTransformer({
	additionalProps: {deleteNodesCmd, deleteNodesURL, trashEnabled},
	portletNamespace,
	...otherProps
}) {
	return {
		...otherProps,
		onActionButtonClick: (event, {item}) => {
			if (item?.data?.action === 'deleteNodes') {
				const form = document.getElementById(`${portletNamespace}fm`);

				if (trashEnabled) {
					if (form) {
						postForm(form, {
							data: {
								cmd: deleteNodesCmd,
							},
							url: deleteNodesURL,
						});
					}
				}
				else {
					openConfirmModal({
						message: Liferay.Language.get(
							'are-you-sure-you-want-to-delete-the-selected-entries'
						),
						onConfirm: (isConfirmed) => {
							if (isConfirmed) {
								if (form) {
									postForm(form, {
										data: {
											cmd: deleteNodesCmd,
										},
										url: deleteNodesURL,
									});
								}
							}
						},
					});
				}
			}
		},
	};
}
