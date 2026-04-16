/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {formatStorage, sub} from 'frontend-js-web';
import React from 'react';

export default function DragZoneBackground({maxSize}: {maxSize: number}) {
	return (
		<div className="align-items-center d-flex flex-column">
			<img
				alt=""
				src={`${Liferay.ThemeDisplay.getPathContext()}/o/exportimport-web/revamp/images/drag_and_drop.svg`}
			/>

			<p className="font-weight-semi-bold mb-2">
				{Liferay.Language.get('drag-and-drop-your-file-or')}
			</p>

			<span className="btn btn-secondary mb-2">
				{Liferay.Language.get('select-files')}
			</span>

			<p className="mb-0 small">
				{Liferay.Language.get('only-lar-files-are-allowed')}
			</p>

			<p className="mb-0 small">
				{sub(
					Liferay.Language.get('max-file-size-is-x'),
					formatStorage(maxSize, {
						addSpaceBeforeSuffix: true,
					})
				)}
			</p>
		</div>
	);
}
