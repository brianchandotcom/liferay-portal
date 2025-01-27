/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import LearnMessage from 'frontend-js-components-web';
import {openModal, sub} from 'frontend-js-web';
import React from 'react';

export default function EmbeddedWidgetsModal({onPublish}) {
	openModal({
		bodyHTML: `
			<div class="text-secondary">
				${sub(
					Liferay.Language.get(
						'embedding-widgets-within-fragments-x'
					),
					<LearnMessage
						resource="fragment-web"
						resourceKey="deprecated-embedded-widgets"
					/>
				)}
			</div>
			<div class="text-secondary">
				${Liferay.Language.get('are-you-sure-you-want-to-publish')}
			</div>`,
		buttons: [
			{
				autoFocus: true,
				displayType: 'secondary',
				label: Liferay.Language.get('cancel'),
				type: 'cancel',
			},
			{
				displayType: 'warning',
				label: Liferay.Language.get('publish'),
				onClick: ({processClose}) => {
					processClose();
					onPublish();
				},
			},
		],
		center: true,
		iframeBodyCssClass: '',
		size: 'md',
		status: 'warning',
		title: Liferay.Language.get('fragment-with-embedded-widget'),
	});
}
