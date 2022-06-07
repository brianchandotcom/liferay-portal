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
	getCheckedCheckboxes,
	openSelectionModal,
	postForm,
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
				if (
					confirm(
						Liferay.Language.get(
							'are-you-sure-you-want-to-remove-the-selected-accounts'
						)
					)
				) {
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
				title: Liferay.Util.sub(
					Liferay.Language.get('assign-accounts-to-x'),
					accountGroupName
				),
				url: selectAccountGroupAccountEntriesURL,
			});
		},
	};
}
