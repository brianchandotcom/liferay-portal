/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import Body from './SLAFormPageBody.es';
import SLAFormPageProvider from './SLAFormPageProvider.es';

function SLAFormPage() {
	return (
		<div className="sla-form">
			<SLAFormPageProvider>
				<SLAFormPage.Body />
			</SLAFormPageProvider>
		</div>
	);
}

SLAFormPage.Body = Body;

export default SLAFormPage;
