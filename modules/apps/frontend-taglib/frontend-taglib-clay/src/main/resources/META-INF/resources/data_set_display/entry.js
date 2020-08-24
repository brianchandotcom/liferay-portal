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

import {render, useThunk} from 'frontend-js-react-web';
import React, {useReducer} from 'react';

import {AppContext} from './AppContext';
import DataSetDisplay from './DataSetDisplay';
import ViewsContext, {viewsReducer} from './views/ViewsContext';

const App = ({apiURL, appURL, portletId, ...props}) => {
	const {
		activeViewSettings: {name: activeViewName, visibleFieldNames = {}},
		views,
	} = props;
	const activeView = activeViewName
		? views.find(({name}) => name === activeViewName)
		: views[0];
	const [state, dispatch] = useThunk(
		useReducer(viewsReducer, {
			activeView,
			views,
			visibleFieldNames,
		})
	);

	return (
		<AppContext.Provider value={{apiURL, appURL, portletId}}>
			<ViewsContext.Provider value={[state, dispatch]}>
				<DataSetDisplay {...props} />
			</ViewsContext.Provider>
		</AppContext.Provider>
	);
};

export default (...data) => render(App, ...data);
