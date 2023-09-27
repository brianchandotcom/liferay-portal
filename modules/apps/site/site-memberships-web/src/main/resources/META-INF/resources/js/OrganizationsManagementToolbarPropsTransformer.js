/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openConfirmModal, openSelectionModal, sub} from 'frontend-js-web';

export default function propsTransformer({portletNamespace, ...otherProps}) {
	const deleteSelectedOrganizations = () => {
		openConfirmModal({
			message: Liferay.Language.get(
				'are-you-sure-you-want-to-delete-this'
			),
			onConfirm: (isConfirmed) => {
				if (isConfirmed) {
					const form = document.getElementById(
						`${portletNamespace}fm`
					);

					if (form) {
						submitForm(form);
					}
				}
			},
		});
	};

	const selectOrganizations = (itemData) => {
		openSelectionModal({
			buttonAddLabel: Liferay.Language.get('done'),
			multiple: true,
			onSelect(selectedItems) {
				if (selectedItems.length) {
					const addGroupOrganizationsFm = document.getElementById(
						`${portletNamespace}addGroupOrganizationsFm`
					);

					if (!addGroupOrganizationsFm) {
						return;
					}

					const input = document.createElement('input');

					input.name = `${portletNamespace}rowIds`;
					input.value = selectedItems
						.map((item) => {
							const selectedValue = JSON.parse(item.value);

							return selectedValue.organizationId;
						})
						.join(',');

					addGroupOrganizationsFm.appendChild(input);

					submitForm(addGroupOrganizationsFm);
				}
			},
			title: sub(
				Liferay.Language.get('assign-organizations-to-this-x'),
				itemData?.groupTypeLabel
			),
			url: itemData?.selectOrganizationsURL,
		});
	};

	return {
		...otherProps,
		onActionButtonClick(event, {item}) {
			const action = item?.data?.action;

			if (action === 'deleteSelectedOrganizations') {
				deleteSelectedOrganizations();
			}
		},
		onCreateButtonClick(event, {item}) {
			const data = item?.data;

			const action = data?.action;

			if (action === 'selectOrganizations') {
				selectOrganizations(data);
			}
		},
	};
}
