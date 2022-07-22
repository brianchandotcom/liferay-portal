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
