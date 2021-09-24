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

import DiagramWithAutomapping from '../src/main/resources/META-INF/resources/js/diagram/DiagramWithAutomapping';

render(
	DiagramWithAutomapping,
	{
		diagramId: '44515',
		imageURL:
			'/documents/42538/0/308056.svg/1f724d61-012d-3c0b-644d-e492251dc512?version=1.0&t=1632488647671&download=true',
		isAdmin: true,
		pinsCSSSelector: '#Livello_Testi > text, [id*=MTEXT] > text',
		productId: '44498',
	},
	document.getElementById('shop-by-diagram-automapping')
);
