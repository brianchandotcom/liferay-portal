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

import {AppContextProvider} from 'app-builder-web/js/AppContext.es';
import {createMemoryHistory} from 'history';
import React from 'react';
import {HashRouter} from 'react-router-dom';

export default ({
	children,
	appContext = {},
	history = createMemoryHistory(),
}) => {
	return (
		<AppContextProvider {...appContext}>
			<div className="tools-control-group">
				<div className="control-menu-level-1-heading" />
			</div>

			<HashRouter>{React.cloneElement(children, {history})}</HashRouter>
		</AppContextProvider>
	);
};
