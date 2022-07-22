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

import {getCheckedCheckboxes, postForm} from 'frontend-js-web';

import openConfirm from '../../common/js/openConfirm.es';

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
		openConfirm({
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
		openConfirm({
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
