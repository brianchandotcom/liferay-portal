/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	createPortletURL,
	getCheckedCheckboxes,
	navigate,
	openConfirmModal,
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
		openConfirmModal({
			message: Liferay.Language.get(
				'are-you-sure-you-want-to-deactivate-the-selected-users'
			),
			onConfirm: (isConfirmed) => {
				if (isConfirmed) {
					updateAccountUsers(
						portletNamespace,
						itemData?.deactivateAccountUsersURL
					);
				}
			},
		});
	};

	const deleteAccountUsers = (itemData) => {
		openConfirmModal({
			message: Liferay.Language.get(
				'are-you-sure-you-want-to-delete-the-selected-users'
			),
			onConfirm: (isConfirmed) => {
				if (isConfirmed) {
					updateAccountUsers(
						portletNamespace,
						itemData?.deleteAccountUsersURL
					);
				}
			},
		});
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
