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

import Diagram from '../src/main/resources/META-INF/resources/js/Diagram/Diagram';
import DiagramTable from '../src/main/resources/META-INF/resources/js/DiagramTable/DiagramTable';
import DiagramWithAutomapping from '../src/main/resources/META-INF/resources/js/DiagramWithAutomapping/Diagram';

render(
	Diagram,
	{
		channelId: '42522',
		datasetDisplayId: 'csDiagramMappedProducts',
		diagramId: '43987',
		imageURL:
			'/documents/20118/0/login_portlet_splash.jpg/b2759a4d-8c53-3c7e-b165-2db2a00ef460?version=1.0&t=1636104336285&download=true',
		isAdmin: true,
		pinsRadius: 1.0,
		productId: '43967',
	},
	document.getElementById('shop-by-diagram')
);

render(
	DiagramTable,
	{
		isAdmin: true,
		productId: '43967',
	},
	document.getElementById('shop-by-diagram-table')
);

render(
	Diagram,
	{
		datasetDisplayId: 'csDiagramMappedProducts',
		orderUUID: '2e5185eb-e978-706d-1928-eefbe94f13d8',
		channelGroupId: '42523',
		commerceAccountId: '44009',
		diagramId: '43987',
		pinsRadius: 1.0,
		productId: '43967',
		cartId: '44139',
		isAdmin: false,
		imageURL:
			'/documents/20118/0/login_portlet_splash.jpg/b2759a4d-8c53-3c7e-b165-2db2a00ef460?version=1.0&t=1636104336285&download=true',
		commerceCurrencyCode: 'USD',
		productBaseURL: 'http://localhost:8080/group/minium/p/',
		channelId: '42522',
	},
	document.getElementById('shop-by-diagram-front')
);

render(
	DiagramTable,
	{
		isAdmin: false,
		productId: '43967',
	},
	document.getElementById('shop-by-diagram-table-front')
);

// render(
// 	DiagramWithAutomapping,
// 	{
// 		diagramId: '46522',
// 		imageURL:
// 			'/documents/20123/0/308056.svg/b77d6493-ab31-d7e5-cb6a-616c9d37ab9b?version=1.0&t=1634286500938&download=true',
// 		isAdmin: false,
// 		pinsCSSSelectors: ['#Livello_Testi > text', '[id*=MTEXT] > text'],
// 		productId: '46505',
// 	},
// 	document.getElementById('shop-by-diagram-automapping')
// );

// render(
// 	DiagramTable,
// 	{
// 		isAdmin: false,
// 		productId: '46505',
// 	},
// 	document.getElementById('shop-by-diagram-automapping-table')
// );
