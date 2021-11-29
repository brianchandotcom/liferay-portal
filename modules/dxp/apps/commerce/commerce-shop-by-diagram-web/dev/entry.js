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
		cartId: '43882',
		channelGroupId: '42398',
		channelId: '42397',
		commerceAccountId: '43879',
		commerceCurrencyCode: 'USD',
		datasetDisplayId: 'csDiagramMappedProducts',
		diagramId: '43917',
		imageURL:
			'/documents/20123/0/photo1636565451.jpeg/90d4ac8f-b028-0a3e-c6b4-a5f71ed26258?version=1.0&t=1637676256470&download=true',
		isAdmin: true,
		orderUUID: 'a711bf49-a2d3-2c8d-23c9-abaff7d288a5',
		pinsRadius: 1.0,
		productBaseURL: 'http://localhost:8080/group/minium/p/',
		productId: '43898',
	},
	document.getElementById('shop-by-diagram')
);

render(
	DiagramTable,
	{
		cartId: '43882',
		channelGroupId: '42398',
		channelId: '42397',
		commerceAccountId: '43879',
		commerceCurrencyCode: 'USD',
		datasetDisplayId: 'csDiagramMappedProducts',
		diagramId: '43917',
		imageURL:
			'/documents/20123/0/photo1636565451.jpeg/90d4ac8f-b028-0a3e-c6b4-a5f71ed26258?version=1.0&t=1637676256470&download=true',
		isAdmin: true,
		orderUUID: 'a711bf49-a2d3-2c8d-23c9-abaff7d288a5',
		pinsRadius: 1.0,
		productBaseURL: 'http://localhost:8080/group/minium/p/',
		productId: '43898',
	},
	document.getElementById('shop-by-diagram-table')
);

render(
	Diagram,
	{
		cartId: '43882',
		channelGroupId: '42398',
		channelId: '42397',
		commerceAccountId: '43879',
		commerceCurrencyCode: 'USD',
		datasetDisplayId: 'csDiagramMappedProducts',
		diagramId: '43917',
		imageURL:
			'/documents/20123/0/photo1636565451.jpeg/90d4ac8f-b028-0a3e-c6b4-a5f71ed26258?version=1.0&t=1637676256470&download=true',
		isAdmin: false,
		orderUUID: 'a711bf49-a2d3-2c8d-23c9-abaff7d288a5',
		pinsRadius: 1.0,
		productBaseURL: 'http://localhost:8080/group/minium/p/',
		productId: '43898',
	},
	document.getElementById('shop-by-diagram-front')
);

render(
	DiagramTable,
	{
		cartId: '43882',
		channelGroupId: '42398',
		channelId: '42397',
		commerceAccountId: '43879',
		commerceCurrencyCode: 'USD',
		datasetDisplayId: 'csDiagramMappedProducts',
		diagramId: '43917',
		imageURL:
			'/documents/20123/0/photo1636565451.jpeg/90d4ac8f-b028-0a3e-c6b4-a5f71ed26258?version=1.0&t=1637676256470&download=true',
		isAdmin: false,
		orderUUID: 'a711bf49-a2d3-2c8d-23c9-abaff7d288a5',
		pinsRadius: 1.0,
		productBaseURL: 'http://localhost:8080/group/minium/p/',
		productId: '43898',
	},
	document.getElementById('shop-by-diagram-table-front')
);

render(
	DiagramWithAutomapping,
	{
		channelGroupId: '42299',
		channelId: '42298',
		commerceAccountId: '42490',
		commerceCurrencyCode: 'USD',
		componentId: null,
		datasetDisplayId: 'csDiagramMappedProducts',
		diagramId: '44282',
		imageURL:
			'/documents/20118/0/308056.svg/9b34c14b-e0de-0a9f-edcf-bbe5c65453c3?version=1.0&t=1636995007978&download=true',
		isAdmin: false,
		pinsCSSSelectors: ['#Livello_Testi > text', '[id*=MTEXT] > text'],
		productBaseURL: 'http://localhost:8080/group/demo/p/',
		productId: '43898',
	},
	document.getElementById('shop-by-diagram-automapping')
);

render(
	DiagramTable,
	{
		cartId: '44095',
		channelGroupId: '42621',
		channelId: '42620',
		commerceAccountId: '44092',
		commerceCurrencyCode: 'USD',
		diagramId: '44070',
		isAdmin: false,
		orderUUID: '4442e7de-97b3-d418-4db9-d04f4f6ce8ac',
		productId: '43898',
	},
	document.getElementById('shop-by-diagram-automapping-table')
);
