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
	createPortletURL,
	getCheckedCheckboxes,
	navigate,
	openSelectionModal,
	postForm,
} from 'frontend-js-web';

const updateAccountUsers = (portletNamespace, url) => {
	const form = document.getElementById(`${portletNamespace}fm`);

	if (form) {
		postForm(form, {
			data: {
				accountUserIds: getCheckedCheckboxes(
					form,
					`${portletNamespace}allRowIds`
				),
			},
			url,
		});
	}
};

export default function propsTransformer({portletNamespace, ...otherProps}) {
	const activateAccountUsers = (itemData) => {
		updateAccountUsers(portletNamespace, itemData?.activateAccountUsersURL);
	};

	const deactivateAccountUsers = (itemData) => {
		if (
			confirm(
				Liferay.Language.get(
					'are-you-sure-you-want-to-deactivate-the-selected-users'
				)
			)
		) {
			updateAccountUsers(
				portletNamespace,
				itemData?.deactivateAccountUsersURL
			);
		}
	};

	const deleteAccountUsers = (itemData) => {
		if (
			confirm(
				Liferay.Language.get(
					'are-you-sure-you-want-to-delete-the-selected-users'
				)
			)
		) {
			updateAccountUsers(
				portletNamespace,
				itemData?.deleteAccountUsersURL
			);
		}
	};

	const selectAccountEntries = (itemData) => {
		openSelectionModal({
			buttonAddLabel: Liferay.Language.get('select'),
			containerProps: {
				className: '',
			},
			iframeBodyCssClass: '',
			multiple: true,
			onSelect: (selectedItems) => {
				if (!selectedItems?.length) {
					return;
				}

				const values = selectedItems.map((item) => item.value);

				const redirectURL = createPortletURL(itemData?.redirectURL, {
					accountEntriesNavigation: 'selected-accounts',
					accountEntryIds: values.join(','),
				});

				navigate(redirectURL);
			},
			title: itemData?.dialogTitle,
			url: itemData?.accountEntriesSelectorURL,
		});
	};

	return {
		...otherProps,
		onActionButtonClick: (event, {item}) => {
			const data = item?.data;

			const action = data?.action;

			if (action === 'activateAccountUsers') {
				activateAccountUsers(data);
			}
			else if (action === 'deactivateAccountUsers') {
				deactivateAccountUsers(data);
			}
			else if (action === 'deleteAccountUsers') {
				deleteAccountUsers(data);
			}
		},
		onCreateButtonClick: (event, {item}) => {
			const data = item?.data;

			openSelectionModal({
				id: `${portletNamespace}addAccountUser`,
				onSelect: (selectedItem) => {
					const addAccountUserURL = createPortletURL(
						data?.addAccountUserURL,
						{
							accountEntryId: selectedItem.accountentryid,
						}
					);

					navigate(addAccountUserURL);
				},
				selectEventName: `${portletNamespace}selectAccountEntry`,
				title: data?.dialogTitle,
				url: data?.accountEntrySelectorURL,
			});
		},
		onFilterDropdownItemClick(event, {item}) {
			if (item?.data?.action === 'selectAccountEntries') {
				selectAccountEntries(item?.data);
			}
		},
	};
}
