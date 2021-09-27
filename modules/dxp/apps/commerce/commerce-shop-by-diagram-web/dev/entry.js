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
		datasetDisplayId: 'test_id',
		diagramId: '42908',
		imageURL:
			'/documents/20118/0/download.png/22b2e42d-9b2c-ae98-0f44-de5b79dc7cca?version=1.0&t=1633353975448&download=true',
		isAdmin: true,
		pinsRadius: 1.0,
		productId: '42900',
	},
	document.getElementById('shop-by-diagram')
);

render(
	DiagramWithAutomapping,
	{
		datasetDisplayId: 'test_id',
		diagramId: '44896',
		imageURL:
			'/documents/42923/0/308056.svg/fa9dcbf5-a06f-b3ba-d11b-e4cd28402758?version=1.0&t=1633361640529&download=true',
		isAdmin: true,
		pinsCSSSelectors: ['#Livello_Testi > text', '[id*=MTEXT] > text'],
		productId: '44878',
	},
	document.getElementById('shop-by-diagram-automapping')
);
