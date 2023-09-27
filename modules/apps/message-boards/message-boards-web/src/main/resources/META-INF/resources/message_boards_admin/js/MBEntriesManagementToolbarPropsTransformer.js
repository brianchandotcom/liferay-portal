/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openConfirmModal, postForm} from 'frontend-js-web';

export default function propsTransformer({
	additionalProps: {
		deleteEntriesCmd,
		editEntryURL,
		lockCmd,
		trashEnabled,
		unlockCmd,
	},
	portletNamespace,
	...otherProps
}) {
	const deleteEntries = () => {
		const form = document.getElementById(`${portletNamespace}fm`);

		if (!form) {
			return;
		}

		const message = trashEnabled
			? Liferay.Language.get(
					'are-you-sure-you-want-to-move-the-selected-entries-to-the-recycle-bin'
			  )
			: Liferay.Language.get(
					'are-you-sure-you-want-to-delete-the-selected-entries'
			  );

		if (trashEnabled) {
			postForm(form, {
				data: {
					cmd: deleteEntriesCmd,
				},
				url: editEntryURL,
			});
		}
		else {
			openConfirmModal({
				message,
				onConfirm: (isConfirmed) => {
					if (isConfirmed) {
						postForm(form, {
							data: {
								cmd: deleteEntriesCmd,
							},
							url: editEntryURL,
						});
					}
				},
			});
		}
	};

	const lockEntries = () => {
		const form = document.getElementById(`${portletNamespace}fm`);

		if (form) {
			postForm(form, {
				data: {
					cmd: lockCmd,
				},
				url: editEntryURL,
			});
		}
	};

	const unlockEntries = () => {
		const form = document.getElementById(`${portletNamespace}fm`);

		if (form) {
			postForm(form, {
				data: {
					cmd: unlockCmd,
				},
				url: editEntryURL,
			});
		}
	};

	return {
		...otherProps,
		onActionButtonClick: (event, {item}) => {
			const action = item?.data?.action;

			if (action === 'deleteEntries') {
				deleteEntries();
			}
			else if (action === 'lockEntries') {
				lockEntries();
			}
			else if (action === 'unlockEntries') {
				unlockEntries();
			}
		},
	};
}
