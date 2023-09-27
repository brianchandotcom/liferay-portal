/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openModal} from 'frontend-js-web';

export default function openStructureKeyChangesModal({onSave}) {
	openModal({
		bodyHTML: `
			<p class="text-secondary">
				${Liferay.Language.get(
					'changing-the-structure-key-will-reindex-all-the-web-content-articles-with-this-structure.-this-action-may-take-some-time,-and-the-affected-items-may-not-be-available-during-this-process'
				)}
			</p>
			<p class="text-secondary">
				${Liferay.Language.get(
					'remember-that-all-places-where-the-structure-key-was-changed-manually-must-be-reviewed-to-be-consistent-with-the-new-key'
				)}
			</p>
			<p class="text-secondary">
				${Liferay.Language.get('are-you-sure-you-want-to-continue-and-save-the-key')}
			</p>`,
		buttons: [
			{
				autoFocus: true,
				displayType: 'secondary',
				label: Liferay.Language.get('cancel'),
				type: 'cancel',
			},
			{
				displayType: 'warning',
				label: Liferay.Language.get('save'),
				onClick: ({processClose}) => {
					processClose();

					onSave();
				},
			},
		],
		status: 'warning',
		title: Liferay.Language.get('structure-key-changes'),
	});
}
