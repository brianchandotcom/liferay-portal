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
	additionalProps: {deleteFormInstanceURL, deleteStructureURL},
	portletNamespace,
	...otherProps
}) {
	const deleteFormInstances = () => {
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
						otherProps.searchContainerId
					);

					if (form && searchContainer) {
						postForm(form, {
							data: {
								deleteFormInstanceIds: getCheckedCheckboxes(
									searchContainer,
									`${portletNamespace}allRowIds`
								),
							},
							url: deleteFormInstanceURL,
						});
					}
				}
			},
		});
	};

	const deleteStructures = () => {
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
						otherProps.searchContainerId
					);

					if (form && searchContainer) {
						postForm(form, {
							data: {
								deleteStructureIds: getCheckedCheckboxes(
									searchContainer,
									`${portletNamespace}allRowIds`
								),
							},
							url: deleteStructureURL,
						});
					}
				}
			},
		});
	};

	return {
		...otherProps,
		onActionButtonClick(event, {item}) {
			const action = item?.data?.action;

			if (action === 'deleteFormInstances') {
				deleteFormInstances();
			}
			else if (action === 'deleteStructures') {
				deleteStructures();
			}
		},
	};
}
