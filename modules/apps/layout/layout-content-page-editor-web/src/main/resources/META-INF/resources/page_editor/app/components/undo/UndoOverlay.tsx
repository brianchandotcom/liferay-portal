/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ReactPortal, useEventListener} from '@liferay/frontend-js-react-web';
import React from 'react';

export default function UndoOverlay() {
	useEventListener(
		'keydown',
		(event) => {
			event.preventDefault();
			event.stopPropagation();
			event.stopImmediatePropagation();
		},
		true,

		// @ts-ignore

		window
	);

	return (
		<ReactPortal className="cadmin">
			<div
				className="page-editor__undo-history__overlay"
				onClickCapture={(event) => {
					event.preventDefault();
					event.stopPropagation();
				}}
			></div>
		</ReactPortal>
	);
}
