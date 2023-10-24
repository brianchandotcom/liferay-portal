/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default function ({namespace: portletNamespace}) {
	const frame = document.getElementById(`${portletNamespace}frame`);

	const receiveMessage = () => {
		frame.style.height =
			frame.contentWindow?.document.body?.scrollHeight + 'px';
	};

	window.addEventListener('message', receiveMessage);

	return () => {
		window.removeEventListener('message', receiveMessage);
	};
}
