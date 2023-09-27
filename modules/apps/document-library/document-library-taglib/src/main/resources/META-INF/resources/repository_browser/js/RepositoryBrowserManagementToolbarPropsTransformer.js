/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	fetch,
	getCheckedCheckboxes,
	openConfirmModal,
	openSimpleInputModal,
	openToast,
} from 'frontend-js-web';

function handleCreationMenuClick(
	event,
	item,
	portletNamespace,
	repositoryBrowserURL
) {
	if (item?.data?.action === 'addFolder') {
		const createFolderURL = `${repositoryBrowserURL}?repositoryId=${item.data.repositoryId}&parentFolderId=${item.data.parentFolderId}`;

		openSimpleInputModal({
			dialogTitle: Liferay.Language.get('add-folder'),
			formSubmitURL: createFolderURL,
			mainFieldLabel: Liferay.Language.get('name'),
			mainFieldName: 'name',
			method: 'PUT',
			namespace: '',
			onFormSuccess: () => window.location.reload(),
		});
	}
	else if (item?.data?.action === 'uploadFile') {
		const fileInput = document.getElementById(`${portletNamespace}file`);

		fileInput?.click();
	}
}

export default function propsTransformer({
	additionalProps: {repositoryBrowserURL},
	portletNamespace,
	...otherProps
}) {
	return {
		...otherProps,
		onActionButtonClick: (event, {item}) => {
			if (item?.data?.action === 'deleteEntries') {
				const deleteAction = () => {
					const container = document.getElementById(
						`${portletNamespace}repositoryEntries`
					);

					const repositoryEntryIds = getCheckedCheckboxes(
						container,
						`${portletNamespace}allRowIds`
					);

					fetch(
						`${repositoryBrowserURL}?repositoryEntryIds=${repositoryEntryIds}`,
						{
							method: 'DELETE',
						}
					)
						.then(() => {
							window.location.reload();
						})
						.catch(() => {
							openToast({
								message: Liferay.Language.get(
									'an-unexpected-error-occurred'
								),
								type: 'danger',
							});
						});
				};

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
		},

		onCreateButtonClick: (event, {item}) =>
			handleCreationMenuClick(
				event,
				item,
				portletNamespace,
				repositoryBrowserURL
			),

		onCreationMenuItemClick: (event, {item}) =>
			handleCreationMenuClick(
				event,
				item,
				portletNamespace,
				repositoryBrowserURL
			),
	};
}
