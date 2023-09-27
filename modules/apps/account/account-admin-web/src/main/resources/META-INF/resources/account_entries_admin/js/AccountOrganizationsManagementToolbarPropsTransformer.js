/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	getCheckedCheckboxes,
	openConfirmModal,
	openSelectionModal,
	postForm,
	sub,
} from 'frontend-js-web';

export default function propsTransformer({portletNamespace, ...otherProps}) {
	return {
		...otherProps,
		onActionButtonClick: (event, {item}) => {
			const data = item?.data;

			const action = data?.action;

			if (action === 'removeOrganizations') {
				openConfirmModal({
					message: Liferay.Language.get(
						'are-you-sure-you-want-to-remove-the-selected-organizations'
					),
					onConfirm: (isConfirmed) => {
						if (isConfirmed) {
							const form = document.getElementById(
								`${portletNamespace}fm`
							);

							if (form) {
								postForm(form, {
									data: {
										accountOrganizationIds: getCheckedCheckboxes(
											form,
											`${portletNamespace}allRowIds`
										),
									},
									url: data?.removeOrganizationsURL,
								});
							}
						}
					},
				});
			}
		},
		onCreateButtonClick: (event, {item}) => {
			const data = item?.data;

			openSelectionModal({
				buttonAddLabel: Liferay.Language.get('assign'),
				containerProps: {
					className: '',
				},
				iframeBodyCssClass: '',
				multiple: true,
				onSelect: (selectedItems) => {
					if (!selectedItems?.length) {
						return;
					}

					const form = document.getElementById(
						`${portletNamespace}fm`
					);

					if (form) {
						const values = selectedItems.map((item) => item.value);

						postForm(form, {
							data: {
								accountOrganizationIds: values.join(','),
							},
							url: data?.assignAccountOrganizationsURL,
						});
					}
				},
				title: sub(
					Liferay.Language.get('assign-organizations-to-x'),
					data?.accountEntryName
				),
				url: data?.selectAccountOrganizationsURL,
			});
		},
	};
}
