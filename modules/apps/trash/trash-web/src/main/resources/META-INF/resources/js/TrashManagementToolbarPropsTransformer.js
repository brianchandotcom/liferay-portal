/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import openDeleteEntryModal from './openDeleteEntryModal';

export default function propsTransformer({
	additionalProps: {restoreEntriesURL},
	portletNamespace,
	...otherProps
}) {
	const deleteSelectedEntries = () => {
		openDeleteEntryModal({
			onDelete: () => {
				const form = document.getElementById(`${portletNamespace}fm`);

				if (form) {
					submitForm(form);
				}
			},
		});
	};

	const restoreSelectedEntries = () => {
		const form = document.getElementById(`${portletNamespace}fm`);

		if (form) {
			submitForm(form, restoreEntriesURL);
		}
	};

	return {
		...otherProps,
		onActionButtonClick: (event, {item}) => {
			const action = item?.data?.action;

			if (action === 'deleteSelectedEntries') {
				deleteSelectedEntries();
			}
			else if (action === 'restoreSelectedEntries') {
				restoreSelectedEntries();
			}
		},
	};
}
