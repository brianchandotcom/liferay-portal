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
