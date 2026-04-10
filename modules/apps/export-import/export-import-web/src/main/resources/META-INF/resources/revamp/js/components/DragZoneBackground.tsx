/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {formatStorage, sub} from 'frontend-js-web';
import React from 'react';

export default function DragZoneBackground({maxSize}: {maxSize: number}) {
	const dragAndDropPath = `${Liferay.ThemeDisplay.getPathContext()}/o/exportimport-web/revamp/images/drag_and_drop.svg`;

	return (
		<>
			<img
				alt={Liferay.Language.get('drag-and-drop-your-file')}
				src={dragAndDropPath}
			/>

			<p className="my-2 text-weight-semi-bold">
				{Liferay.Language.get('drag-and-drop-your-file-or')}
			</p>

			<ClayButton displayType="secondary">
				{Liferay.Language.get('select-files')}
			</ClayButton>

			<p className="my-2">
				{sub(
					Liferay.Language.get(
						'only-lar-files-are-allowed-max-file-size-is-x'
					),
					formatStorage(maxSize, {
						addSpaceBeforeSuffix: true,
					})
				)}
			</p>
		</>
	);
}
