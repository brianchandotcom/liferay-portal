/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

export default function ContentPreview({url}: {url: string}) {
	return (
		<div className="height-100">
			<iframe
				height="100%"
				src={url}
				style={{border: 'none'}}
				width="100%"
			/>
		</div>
	);
}
