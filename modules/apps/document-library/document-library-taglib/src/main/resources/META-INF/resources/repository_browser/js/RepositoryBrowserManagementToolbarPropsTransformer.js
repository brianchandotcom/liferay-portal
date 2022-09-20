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

import {fetch, getCheckedCheckboxes, openConfirmModal} from 'frontend-js-web';

export default function propsTransformer({
	additionalProps: {deleteURL},
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
						`${deleteURL}?repositoryEntryIds=${repositoryEntryIds}`,
						{
							method: 'DELETE',
						}
					).then(() => {
						window.location.reload();
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
	};
}
