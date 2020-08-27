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

import '../css/main.scss';

import 'clay-css/lib/css/atlas.css';
import {render} from 'frontend-js-react-web';
import React from 'react';

import Container from './Container';

window.Liferay = {
	Language: {
		available: {
			en_US: 'aosidopaisd',
			es_ES: 'aosidopaisd',
		},
	},
};

export default function (id) {
	render(<Container />, document.getElementById(id));
}
