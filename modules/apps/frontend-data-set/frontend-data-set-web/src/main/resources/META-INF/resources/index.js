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

import {useThunk} from '@liferay/frontend-js-react-web';
import PropTypes from 'prop-types';
import React, {useReducer} from 'react';

import {AppContext} from './AppContext';
import DataSet from './DataSet';
import ViewsContext, {viewsReducer} from './views/ViewsContext';

const App = ({
	activeViewSettings,
	apiURL,
	appURL,
	customViewsEnabled,
	portletId,
	views,
	...props
}) => {
	const activeViewName = activeViewSettings?.name;

	const activeView = activeViewName
		? views.find(({name}) => name === activeViewName)
		: views[0];
	const [state, dispatch] = useThunk(
		useReducer(viewsReducer, {
			activeView,
			customViewsEnabled,
			views,
			visibleFieldNames: activeViewSettings?.visibleFieldNames || {},
		})
	);

	return (
		<AppContext.Provider value={{apiURL, appURL, portletId}}>
			<ViewsContext.Provider value={[state, dispatch]}>
				<DataSet {...props} />
			</ViewsContext.Provider>
		</AppContext.Provider>
	);
};

App.proptypes = {
	activeViewSettings: PropTypes.shape({
		name: PropTypes.string,
		visibleFieldNames: PropTypes.array,
	}),
	apiURL: PropTypes.string,
	appURL: PropTypes.string,
	portletId: PropTypes.string,
	views: PropTypes.arrayOf(
		PropTypes.shape({
			component: PropTypes.any,
			contentRenderer: PropTypes.string,
			contentRendererModuleURL: PropTypes.string,
			label: PropTypes.string,
			schema: PropTypes.object,
			thumbnail: PropTypes.string,
		})
	).isRequired,
};

export {App as DataSet};

// Renderers API

export {
	dataRenderers as DataRenderers,
	inputRenderers as InputRenderers,
} from './data_renderers/index';
export {default as DateTimeRenderer} from './data_renderers/DateTimeRenderer';
export {default as StatusRenderer} from './data_renderers/StatusRenderer';

// Data Set Events API

export {default as DATA_SET_EVENT} from './utils/eventsDefinitions';
