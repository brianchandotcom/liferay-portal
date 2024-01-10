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
	additionalProps: {deleteEntriesCmd, deleteEntriesURL, trashEnabled},
	portletNamespace,
	...otherProps
}) {
	return {
		...otherProps,
		onActionButtonClick: (event, {item}) => {
			if (item?.data?.action === 'deleteEntries') {
				const deleteAction = () => {
					const form = document.getElementById(
						`${portletNamespace}fm`
					);

					if (!form) {
						return;
					}

					const searchContainer = Liferay.SearchContainer.get(
						`${portletNamespace}blogEntries`
					);

					postForm(form, {
						data: {
							cmd: deleteEntriesCmd,
							deleteEntryIds: getCheckedCheckboxes(
								form,
								`${portletNamespace}allRowIds`
							),
							selectAll: searchContainer.select?.get(
								'bulkSelection'
							),
						},
						url: deleteEntriesURL,
					});
				};

				if (trashEnabled) {
					deleteAction();
				}
				else {
					openConfirmModal({
						message: Liferay.Language.get(
							'are-you-sure-you-want-to-delete-this'
						),
						onConfirm: (isConfimed) => {
							if (isConfimed) {
								deleteAction();
							}
						},
					});
				}
			}
		},
	};
}
