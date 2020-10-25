/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

'use strict';

import App from './app/App';
import dataAttributeHandler from './app/dataAttributeHandler';
import version from './app/version';
import Route from './route/Route';
import HtmlScreen from './screen/HtmlScreen';
import RequestScreen from './screen/RequestScreen';
import Screen from './screen/Screen';
import utils from './utils/utils';

export default App;
export {
	dataAttributeHandler,
	utils,
	App,
	HtmlScreen,
	Route,
	RequestScreen,
	Screen,
	version,
};
