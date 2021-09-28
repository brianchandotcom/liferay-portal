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
		diagramId:'44860',
		imageURL:'/documents/20123/0/login_portlet_splash.jpg/a43294b3-5a06-983b-21ba-b35622fe4887?version=1.0&t=1632754073054&download=true',
		isAdmin: true,
		productId:'44841',
	},
	document.getElementById('shop-by-diagram')
);

render(
	DiagramWithAutomapping,
	{
		diagramId: '44879',
		imageURL: '/documents/20123/0/308056.svg/c134d3bf-3a1d-5850-a8d6-498096e79ffc?version=1.0&t=1632754810743&download=true',
		isAdmin: true,
		pinsCSSSelectors: [
		   '#Livello_Testi > text',
		   '[id*=MTEXT] > text'
		],
		productId: '44862',
	},
	document.getElementById('shop-by-diagram-automapping')
);
