/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import React from 'react';

const DropHereInfo = () => (
	<div className="drop-here-info">
		<div className="drop-here-indicator">
			<div className="drop-icons">
				<span className="drop-icon">
					<ClayIcon symbol="picture" />
				</span>

				<span className="drop-icon">
					<ClayIcon symbol="picture" />
				</span>

				<span className="drop-icon">
					<ClayIcon symbol="picture" />
				</span>
			</div>

			<div className="drop-text">
				{Liferay.Language.get('drop-files-here')}
			</div>
		</div>
	</div>
);

export default DropHereInfo;
