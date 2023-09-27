/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

export default function Color({hexColor}) {
	return (
		<div className="color row">
			<div className="col-md-1">
				<div
					className="color-viewer"
					style={{backgroundColor: hexColor}}
				></div>
			</div>

			<div className="color-text">{hexColor}</div>
		</div>
	);
}
