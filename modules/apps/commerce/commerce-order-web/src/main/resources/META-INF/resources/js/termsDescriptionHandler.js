/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default function ({selectId, terms}) {
	const descriptionContainer = document.getElementById(
		'description-container'
	);
	const descriptionLabel = document.getElementById('description-label');

	document.getElementById(selectId).addEventListener('change', ({target}) => {
		if (target.value) {
			descriptionContainer.innerHTML = terms[target.value];

			descriptionLabel.classList.remove('d-none');
		}
		else {
			descriptionContainer.innerHTML = '';

			descriptionLabel.classList.add('d-none');
		}
	});
}
