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
		cartId: '44217',
		channelGroupId: '42720',
		channelId: '42719',
		commerceAccountId: '44204',
		commerceCurrencyCode: 'USD',
		datasetDisplayId: 'csDiagramMappedProducts',
		diagramId: '44184',
		imageURL:
			'/documents/20118/0/giphy.gif/dcf2c620-05fc-3eeb-f582-3e77198d9e44?version=1.0&t=1636543710147&download=true',
		isAdmin: true,
		orderUUID: '4f5d1e8a-4590-fa24-bc57-da90f45e99a1',
		pinsRadius: 1.0,
		productBaseURL: 'http://localhost:8080/group/minium/p/',
		productId: '44164',
	},
	document.getElementById('shop-by-diagram')
);

render(
	DiagramTable,
	{
		isAdmin: false,
		productId: '44164',
	},
	document.getElementById('shop-by-diagram-table')
);

render(
	Diagram,
	{
		cartId: '44217',
		channelGroupId: '42720',
		channelId: '42719',
		commerceAccountId: '44204',
		commerceCurrencyCode: 'USD',
		datasetDisplayId: 'csDiagramMappedProducts',
		diagramId: '44184',
		imageURL:
			'/documents/20118/0/giphy.gif/dcf2c620-05fc-3eeb-f582-3e77198d9e44?version=1.0&t=1636543710147&download=true',
		isAdmin: false,
		orderUUID: '4f5d1e8a-4590-fa24-bc57-da90f45e99a1',
		pinsRadius: 1.0,
		productBaseURL: 'http://localhost:8080/group/minium/p/',
		productId: '44164',
	},
	document.getElementById('shop-by-diagram-front')
);

render(
	DiagramTable,
	{
		isAdmin: false,
		productId: '44164',
	},
	document.getElementById('shop-by-diagram-table-front')
);

render(
	DiagramWithAutomapping,
	{
		cartId: '44217',
		channelGroupId: '42720',
		channelId: '42719',
		commerceAccountId: '44204',
		commerceCurrencyCode: 'USD',
		datasetDisplayId: 'csDiagramMappedProducts',
		diagramId: '44184',
		imageURL:
			'/documents/20118/0/giphy.gif/dcf2c620-05fc-3eeb-f582-3e77198d9e44?version=1.0&t=1636543710147&download=true',
		isAdmin: true,
		orderUUID: '4f5d1e8a-4590-fa24-bc57-da90f45e99a1',
		pinsCSSSelectors: ['#Livello_Testi > text', '[id*=MTEXT] > text'],
		productBaseURL: 'http://localhost:8080/group/minium/p/',
		productId: '44164',
	},
	document.getElementById('shop-by-diagram-automapping')
);

render(
	DiagramTable,
	{
		isAdmin: false,
		productId: '46505',
	},
	document.getElementById('shop-by-diagram-automapping-table')
);
