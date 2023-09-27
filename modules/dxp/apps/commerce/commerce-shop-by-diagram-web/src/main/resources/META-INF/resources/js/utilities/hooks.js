/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect} from 'react';

export function useEscapeKeyHandler(
	isFullscreen,
	isTooltipOpen,
	compress,
	closeTooltip
) {
	useEffect(() => {
		function handleEscapeKeyPress(event) {
			if (event.key === 'Escape') {
				if (isTooltipOpen) {
					return closeTooltip();
				}

				if (isFullscreen) {
					return compress();
				}
			}
		}

		if (isFullscreen || isTooltipOpen) {
			document.addEventListener('keyup', handleEscapeKeyPress, {
				once: true,
			});
		}
		else {
			document.removeEventListener('keyup', handleEscapeKeyPress);
		}

		return () => {
			document.removeEventListener('keyup', handleEscapeKeyPress);
		};
	}, [closeTooltip, compress, isFullscreen, isTooltipOpen]);
}
