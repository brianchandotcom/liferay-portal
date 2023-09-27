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

export default function propsTransformer({
	additionalProps: {
		accountGroupName,
		assignAccountGroupAccountEntriesURL,
		removeAccountGroupAccountEntriesURL,
		selectAccountGroupAccountEntriesURL,
	},
	portletNamespace,
	...otherProps
}) {
	return {
		...otherProps,
		onActionButtonClick: (event, {item}) => {
			const action = item?.data?.action;

			if (action === 'removeAccountGroupAccountEntries') {
				openConfirmModal({
					message: Liferay.Language.get(
						'are-you-sure-you-want-to-remove-the-selected-accounts'
					),
					onConfirm: (isConfirmed) => {
						if (isConfirmed) {
							const form = document.getElementById(
								`${portletNamespace}fm`
							);

							if (form) {
								postForm(form, {
									data: {
										accountEntryIds: getCheckedCheckboxes(
											form,
											`${portletNamespace}allRowIds`
										),
									},
									url: removeAccountGroupAccountEntriesURL,
								});
							}
						}
					},
				});
			}
		},
		onCreateButtonClick: () => {
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
								accountEntryIds: values.join(','),
							},
							url: assignAccountGroupAccountEntriesURL,
						});
					}
				},
				title: sub(
					Liferay.Language.get('assign-accounts-to-x'),
					accountGroupName
				),
				url: selectAccountGroupAccountEntriesURL,
			});
		},
	};
}
