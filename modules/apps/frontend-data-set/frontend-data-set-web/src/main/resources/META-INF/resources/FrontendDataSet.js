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

const FrontendDataSet = ({
	activeViewSettings,
	apiURL,
	appURL,
	customViewsEnabled,
	portletId,
	views,
	...props
}) => {
	const getInitialViewsState = () => {
		let initialActiveView = views[0];
		let initialVisibleFieldNames = {};

		if (activeViewSettings) {
			const {name, visibleFieldNames} = JSON.parse(activeViewSettings);

			if (name) {
				initialActiveView = views.find(
					({viewName}) => viewName === name
				);
			}

			if (visibleFieldNames) {
				initialVisibleFieldNames = visibleFieldNames;
			}
		}

		return {
			activeView: initialActiveView,
			customViewsEnabled,
			views,
			visibleFieldNames: initialVisibleFieldNames,
		};
	};

	const [state, dispatch] = useThunk(
		useReducer(viewsReducer, getInitialViewsState())
	);

	return (
		<AppContext.Provider value={{apiURL, appURL, portletId}}>
			<ViewsContext.Provider value={[state, dispatch]}>
				<DataSet {...props} />
			</ViewsContext.Provider>
		</AppContext.Provider>
	);
};

FrontendDataSet.proptypes = {
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

export default FrontendDataSet;
