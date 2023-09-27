/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

export function StepList({children}) {
	return (
		<div className="c-mr-6 c-mt-10 d-flex flex-column step-list">
			{children}
		</div>
	);
}
