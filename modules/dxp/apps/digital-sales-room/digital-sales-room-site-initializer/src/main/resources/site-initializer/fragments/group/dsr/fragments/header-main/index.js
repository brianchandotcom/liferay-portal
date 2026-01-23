/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

document
	.querySelector('.page-editor__sidebar__fragments-widgets-panel div')
	.setAttribute('style', 'display: none !important;');
document
	.querySelector('.page-editor__sidebar__fragments-widgets-panel .nav-tabs')
	.classList.add('d-none');
document
	.querySelectorAll(
		'.page-editor__sidebar__fragments-widgets-panel .tab-content li'
	)
	.forEach((item) => {
		const title = item.querySelector('.panel-title span');

		if (!title || title.innerHTML !== 'Digital Sales Room') {
			item.classList.add('d-none');
		}
		else {
			setTimeout(() => {
				item.querySelector('button').click();
			}, 100);
		}
	});
