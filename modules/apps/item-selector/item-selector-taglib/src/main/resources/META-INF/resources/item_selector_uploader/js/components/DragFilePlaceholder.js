/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import React from 'react';

import getMimeTypeRestrictionData from '../utils/getMimeTypeRestrictionData';
import DragFileIcon from './DragFileIcon';

export default function DragFilePlaceholder({mimeTypeRestriction}) {
	const {icon, text} = getMimeTypeRestrictionData(mimeTypeRestriction);

	return (
		<>
			<div className="dropzone-drag-file-icon-wrapper">
				<DragFileIcon />

				<ClayIcon
					className="dropzone-drag-file-type-icon"
					symbol={icon}
				/>
			</div>

			{text}
		</>
	);
}
