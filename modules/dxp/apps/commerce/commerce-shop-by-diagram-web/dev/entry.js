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

import Diagram from '../src/main/resources/META-INF/resources/js/diagram/Diagram';
import DiagramWithAutomapping from '../src/main/resources/META-INF/resources/js/diagram/DiagramWithAutomapping';

render(
	Diagram,
	{
		diagramId: 1231,
		imageURL: './assets/308056.svg',
		isAdmin: true,
		productId: 44212,
	},
	document.getElementById('shop-by-diagram')
);

render(
	DiagramWithAutomapping,
	{
		diagramId: 1231,
		imageURL: './assets/308056.svg',
		isAdmin: true,
		productId: 44212,
	},
	document.getElementById('shop-by-diagram-automapping')
);
