/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openModal} from 'frontend-js-web';

export default function openUnlockLayoutsModal({
	onUnlock,
}: {
	onUnlock: () => void;
}) {
	openModal({
		bodyHTML: `
			<p class="c-mb-0 font-weight-bold">
				${Liferay.Language.get('would-you-like-to-unlock-these-pages-now')}
			</p>
			<p class="text-secondary">
				${Liferay.Language.get(
					'please-note-that-the-current-user-may-lose-control-over-the-edition'
				)}
			</p>`,
		buttons: [
			{
				autoFocus: true,
				displayType: 'secondary',
				label: Liferay.Language.get('cancel'),
				type: 'cancel',
			},
			{
				displayType: 'primary',
				label: Liferay.Language.get('unlock'),
				onClick: ({processClose}: {processClose: () => void}) => {
					processClose();

					onUnlock();
				},
			},
		],
		title: Liferay.Language.get('unlock-pages'),
	});
}
