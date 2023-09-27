/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import './index.css';

const Bubble = ({value}) => (
	<div className="align-items-center bg-brand-primary-lighten-5 bubble-element d-flex flex-shrink-0 font-weight-bolder justify-content-center ml-3 rounded-xl text-brand-primary text-paragraph-sm">
		{value}
	</div>
);

export default Bubble;
