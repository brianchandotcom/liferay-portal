/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/* global Behaviour */

Behaviour.specify(
	'a.op-connect-reveal-token',
	'op-connect-reveal-token',
	0,
	(revealLink) => {
		revealLink.onclick = function (event) {
			event.preventDefault();

			const accessTokenInput =
				document.getElementById('op-connect-token');

			if (accessTokenInput === null) {
				return;
			}

			if (accessTokenInput.type === 'password') {
				accessTokenInput.type = 'text';

				revealLink.textContent = 'Hide';
			}
			else {
				accessTokenInput.type = 'password';

				revealLink.textContent = 'Show';
			}
		};
	}
);
