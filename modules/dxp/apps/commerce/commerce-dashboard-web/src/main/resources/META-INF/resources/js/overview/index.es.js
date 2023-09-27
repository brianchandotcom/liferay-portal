/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@liferay/frontend-js-react-web';
import React from 'react';

import OverviewChart from './OverviewChart.es';

const Wrapper = (props) => {
	return <OverviewChart {...props} />;
};

export default function (id, props) {
	render(Wrapper, props, document.getElementById(id));
}
