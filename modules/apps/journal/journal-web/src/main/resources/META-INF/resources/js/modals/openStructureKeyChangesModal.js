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
					'remember-that-all-places-where-the-structure-key-was-changed-manually-must-be-reviewed-to-be-consistent-with-the-new-key,-otherwise,-the-relationship-will-get-lost'
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
