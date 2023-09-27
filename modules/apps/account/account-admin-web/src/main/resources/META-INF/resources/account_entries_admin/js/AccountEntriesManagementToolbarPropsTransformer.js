/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	getCheckedCheckboxes,
	openConfirmModal,
	postForm,
} from 'frontend-js-web';

const updateAccountEntries = (portletNamespace, url) => {
	const form = document.getElementById(`${portletNamespace}fm`);

	if (form) {
		postForm(form, {
			data: {
				accountEntryIds: getCheckedCheckboxes(
					form,
					`${portletNamespace}allRowIds`
				),
			},
			url,
		});
	}
};

export default function propsTransformer({portletNamespace, ...otherProps}) {
	const activateAccountEntries = (itemData) => {
		updateAccountEntries(
			portletNamespace,
			itemData?.activateAccountEntriesURL
		);
	};

	const deactivateAccountEntries = (itemData) =>
		openConfirmModal({
			message: Liferay.Language.get(
				'are-you-sure-you-want-to-deactivate-this'
			),
			onConfirm: (isConfirmed) =>
				isConfirmed &&
				updateAccountEntries(
					portletNamespace,
					itemData?.deactivateAccountEntriesURL
				),
		});

	const deleteAccountEntries = (itemData) =>
		openConfirmModal({
			message: Liferay.Language.get(
				'are-you-sure-you-want-to-delete-this'
			),
			onConfirm: (isConfirmed) =>
				isConfirmed &&
				updateAccountEntries(
					portletNamespace,
					itemData?.deleteAccountEntriesURL
				),
		});

	return {
		...otherProps,
		onActionButtonClick: (event, {item}) => {
			const data = item?.data;

			const action = data?.action;

			if (action === 'activateAccountEntries') {
				activateAccountEntries(data);
			}
			else if (action === 'deactivateAccountEntries') {
				deactivateAccountEntries(data);
			}
			else if (action === 'deleteAccountEntries') {
				deleteAccountEntries(data);
			}
		},
	};
}
