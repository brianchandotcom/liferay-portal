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
		diagramId: '44132',
		imageURL:
			'/documents/20118/0/Zoom+Background+for+Italy_Light+Blue%402x.png/5ecfa67a-e340-6d3c-987f-bf44ebc3ba60?version=1.0&t=1636016213786&download=true',
		isAdmin: true,
		pinsRadius: 1.0,
		productId: '44112',
	},
	document.getElementById('shop-by-diagram')
);

render(
	DiagramTable,
	{
		isAdmin: true,
		productId: '44647',
	},
	document.getElementById('shop-by-diagram-table')
);

render(
	Diagram,
	{
		channelId: '42160',
		datasetDisplayId: 'csDiagramMappedProducts',
		diagramId: '44132',
		imageURL:
			'/documents/20118/0/Zoom+Background+for+Italy_Light+Blue%402x.png/5ecfa67a-e340-6d3c-987f-bf44ebc3ba60?version=1.0&t=1636016213786&download=true',
		isAdmin: false,
		pinsRadius: 1.0,
		productId: '44112',
	},
	document.getElementById('shop-by-diagram-front')
);

render(
	DiagramTable,
	{
		isAdmin: false,
		productId: '44647',
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
