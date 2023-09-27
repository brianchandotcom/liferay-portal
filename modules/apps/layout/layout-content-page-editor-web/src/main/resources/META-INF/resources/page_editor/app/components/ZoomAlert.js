/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import React, {useEffect, useRef, useState} from 'react';

export default function ZoomAlert() {
	const [showZoomAlert, setShowZoomAlert] = useState(false);
	const alertRef = useRef();

	useEffect(() => {
		const onChange = (event) => {
			setShowZoomAlert(event.matches);
		};

		const mediaQuery = window.matchMedia('(max-width: 576px)');

		if (mediaQuery.matches) {
			setShowZoomAlert(true);
		}

		mediaQuery.addEventListener('change', onChange);

		return () => mediaQuery.removeEventListener('change', onChange);
	}, []);

	useEffect(() => {
		if (showZoomAlert) {
			const height = alertRef.current?.getBoundingClientRect().height;

			document.body.style.setProperty(
				'--zoom-alert-height',
				`${height}px`
			);
		}
		else {
			document.body.style.setProperty('--zoom-alert-height', '0px');
		}
	}, [showZoomAlert]);

	return (
		showZoomAlert && (
			<div
				className="p-2 page-editor__resolution-info-alert w-100"
				ref={alertRef}
			>
				<ClayAlert
					className="mb-0"
					displayType="info"
					onClose={() => setShowZoomAlert(false)}
				>
					{Liferay.Language.get(
						'this-editor-is-not-optimized-for-mobile-or-400-zooming'
					)}
				</ClayAlert>
			</div>
		)
	);
}
