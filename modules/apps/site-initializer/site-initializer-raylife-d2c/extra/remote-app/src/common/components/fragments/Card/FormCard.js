/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayCard from '@clayui/card';
import React from 'react';

export default function FormCard({children}) {
	return (
		<div className="col-12">
			<ClayCard className="d-flex flex-column m-auto px-3 px-lg-6 px-md-6 px-sm-3 py-5 py-lg-6 py-md-6 py-sm-5 rounded shadow-lg">
				{children}
			</ClayCard>
		</div>
	);
}
