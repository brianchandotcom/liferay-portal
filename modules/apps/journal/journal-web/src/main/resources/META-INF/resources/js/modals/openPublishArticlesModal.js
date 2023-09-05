/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openModal, sub} from 'frontend-js-web';

export default function openPublishArticlesModal({onPublish}) {
	openModal({
		bodyHTML: Liferay.Language.get(
			'are-you-sure-you-want-to-publish-the-selected-entities'
		),
		buttons: [
			{
				autoFocus: true,
				displayType: 'secondary',
				label: Liferay.Language.get('cancel'),
				type: 'cancel',
			},
			{
				displayType: 'primary',
				label: Liferay.Language.get('publish-to-live'),
				onClick: ({processClose}) => {
					processClose();

					onPublish();
				},
			},
		],
		status: 'info',
		title: sub(
			Liferay.Language.get('publish-x'),
			Liferay.Language.get('web-content')
		),
	});
}
