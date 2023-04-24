/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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
