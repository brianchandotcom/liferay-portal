/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openModal} from 'frontend-js-components-web';

export default function ({actionURL, linkId}) {
	if (!actionURL || actionURL === '#') {
		return;
	}

	const linkElement = document.querySelector(`#${linkId}`);

	if (!linkElement) {
		return;
	}

	linkElement.addEventListener('click', (event) => {
		event.preventDefault();

		openModal({
			bodyHTML: `
				<div>
					<p>
						${Liferay.Language.get(
							'you-are-about-to-recalculate-the-order-summary-this-action-cannot-be-undone'
						)}
					</p>
				</div>
			`,
			buttons: [
				{
					displayType: 'secondary',
					label: Liferay.Language.get('cancel'),
					onClick: ({processClose}) => {
						processClose();
					},
					type: 'cancel',
				},
				{
					label: Liferay.Language.get('continue'),
					onClick: () => {
						location.href = actionURL;
					},
				},
			],
			center: true,
			status: 'warning',
			title: Liferay.Language.get('recalculate-order-summary'),
		});
	});
}
