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

import Diagram from '../../src/main/resources/META-INF/resources/js/Diagram';

import '../../src/main/resources/META-INF/resources/css/diagram.scss';

render(
	Diagram,
	{
		imageURL: './assets/lfr-diagram-engine.png',
		namespace: 'portlet_shop_by_diagram_namespace_',
		spritemap: './assets/clay/icons.svg',
	},
	document.getElementById('shop-by-diagram')
);
