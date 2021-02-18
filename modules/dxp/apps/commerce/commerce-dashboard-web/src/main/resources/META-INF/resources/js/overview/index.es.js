/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
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
