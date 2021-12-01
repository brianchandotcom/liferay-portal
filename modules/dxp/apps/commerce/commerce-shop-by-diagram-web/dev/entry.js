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
		datasetDisplayId: 'csDiagramMappedProducts',
		diagramId: '43591',
		imageURL:
			'/documents/42139/0/login_portlet_splash.jpg/c088d83f-b864-aced-8847-07cc4b548264?version=1.0&t=1638206193193&download=true',
		isAdmin: true,
		pinsRadius: 1.0,
		productId: '43583',
	},
	document.getElementById('shop-by-diagram')
);

render(
	DiagramTable,
	{
		datasetDisplayId: 'csDiagramMappedProducts',
		diagramId: '43591',
		imageURL:
			'/documents/42139/0/login_portlet_splash.jpg/c088d83f-b864-aced-8847-07cc4b548264?version=1.0&t=1638206193193&download=true',
		isAdmin: true,
		pinsRadius: 1.0,
		productId: '43583',
	},
	document.getElementById('shop-by-diagram-table')
);

render(
	Diagram,
	{
		cartId: '43621',
		channelGroupId: '42150',
		channelId: '42149',
		commerceAccountId: '43615',
		commerceCurrencyCode: 'USD',
		datasetDisplayId: 'csDiagramMappedProducts',
		diagramId: '43591',
		imageURL:
			'/documents/42139/0/login_portlet_splash.jpg/c088d83f-b864-aced-8847-07cc4b548264?version=1.0&t=1638206193193&download=true',
		isAdmin: false,
		orderUUID: '1c25ed61-0a41-b490-fb52-9df18f3f2f33',
		pinsRadius: 1.0,
		productBaseURL: 'http://localhost:8080/group/minium/p/',
		productId: '43583',
	},
	document.getElementById('shop-by-diagram-front')
);

render(
	DiagramTable,
	{
		cartId: '43621',
		channelGroupId: '42150',
		channelId: '42149',
		commerceAccountId: '43615',
		commerceCurrencyCode: 'USD',
		datasetDisplayId: 'csDiagramMappedProducts',
		diagramId: '43591',
		imageURL:
			'/documents/42139/0/login_portlet_splash.jpg/c088d83f-b864-aced-8847-07cc4b548264?version=1.0&t=1638206193193&download=true',
		isAdmin: false,
		orderUUID: '1c25ed61-0a41-b490-fb52-9df18f3f2f33',
		pinsRadius: 1.0,
		productBaseURL: 'http://localhost:8080/group/minium/p/',
		productId: '43583',
	},
	document.getElementById('shop-by-diagram-table-front')
);

render(
	DiagramWithAutomapping,
	{
		datasetDisplayId: 'csDiagramMappedProducts',
		diagramId: '44239',
		imageURL:
			'/documents/42139/0/309196-01.svg/e232f94d-87c8-8ae5-22ae-d0e8e412697d?version=1.0&t=1638279100445&download=true',
		isAdmin: true,
		pinsCSSSelectors: ['#Livello_Testi > text', '[id*=MTEXT] > text'],
		productId: '44222',
	},
	document.getElementById('shop-by-diagram-automapping')
);

render(
	DiagramTable,
	{
		datasetDisplayId: 'csDiagramMappedProducts',
		diagramId: '44239',
		imageURL:
			'/documents/42139/0/309196-01.svg/e232f94d-87c8-8ae5-22ae-d0e8e412697d?version=1.0&t=1638279100445&download=true',
		isAdmin: true,
		pinsCSSSelectors: ['#Livello_Testi > text', '[id*=MTEXT] > text'],
		productId: '44222',
	},
	document.getElementById('shop-by-diagram-automapping-table')
);
render(
	DiagramWithAutomapping,
	{
		cartId: '43621',
		channelGroupId: '42150',
		channelId: '42149',
		commerceAccountId: '43615',
		commerceCurrencyCode: 'USD',
		datasetDisplayId: 'csDiagramMappedProducts',
		diagramId: '44239',
		imageURL:
			'/documents/42139/0/309196-01.svg/e232f94d-87c8-8ae5-22ae-d0e8e412697d?version=1.0&t=1638279100445&download=true',
		isAdmin: false,
		orderUUID: '1c25ed61-0a41-b490-fb52-9df18f3f2f33',
		pinsCSSSelectors: ['#Livello_Testi > text', '[id*=MTEXT] > text'],
		productBaseURL: 'http://localhost:8080/group/minium/p/',
		productId: '44222',
	},
	document.getElementById('shop-by-diagram-automapping-front')
);

render(
	DiagramTable,
	{
		cartId: '43621',
		channelGroupId: '42150',
		channelId: '42149',
		commerceAccountId: '43615',
		commerceCurrencyCode: 'USD',
		datasetDisplayId: 'csDiagramMappedProducts',
		diagramId: '44239',
		imageURL:
			'/documents/42139/0/309196-01.svg/e232f94d-87c8-8ae5-22ae-d0e8e412697d?version=1.0&t=1638279100445&download=true',
		isAdmin: false,
		orderUUID: '1c25ed61-0a41-b490-fb52-9df18f3f2f33',
		pinsCSSSelectors: ['#Livello_Testi > text', '[id*=MTEXT] > text'],
		productBaseURL: 'http://localhost:8080/group/minium/p/',
		productId: '44222',
	},
	document.getElementById('shop-by-diagram-automapping-table-front')
);
