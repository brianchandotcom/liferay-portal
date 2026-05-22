/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

export default function preventIframeNavigation(
	event: React.SyntheticEvent<HTMLIFrameElement>
): void {
	const iframe = event.target as HTMLIFrameElement;

	const iframeWin = iframe.contentWindow;
	const iframeDoc = iframeWin?.document;

	iframeDoc?.addEventListener('click', (clickEvent) => {
		const target = clickEvent.target as HTMLElement;

		const link = target.closest('a');

		if (link && link.href) {
			clickEvent.preventDefault();
		}
	});

	(iframeWin as any)?.Liferay.on('beforeNavigate', (navigationEvent: any) => {
		navigationEvent.preventDefault();
		navigationEvent.originalEvent.preventDefault();
	});
}
