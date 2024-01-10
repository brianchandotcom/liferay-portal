/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

export default function NoPreview() {
	return (
		<div className="no-preview-container text-light">
			<strong>
				{Liferay.Language.get('there-is-no-preview-available')}
			</strong>
		</div>
	);
}
