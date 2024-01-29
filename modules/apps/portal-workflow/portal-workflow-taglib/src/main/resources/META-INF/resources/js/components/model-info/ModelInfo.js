/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

export default function ModelInfo({label, value}) {
	return (
		value && (
			<span className={`mr-2 workflow-${label}`}>
				<span className="workflow-label">{`${label}: `}</span>

				<span className="workflow-value">{value}</span>
			</span>
		)
	);
}
