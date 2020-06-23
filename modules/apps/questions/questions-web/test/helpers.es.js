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

import {MockedProvider} from '@apollo/client/testing';
import React from 'react';

import '@testing-library/jest-dom/extend-expect';
import {render} from '@testing-library/react';
import {createMemoryHistory} from 'history';
import {Router} from 'react-router-dom';

import {AppContext} from '../src/main/resources/META-INF/resources/js/AppContext.es';

export const renderComponent = ({
	apolloAddTypename = false,
	apolloMocks = null,
	contextValue = {},
	link,
	ui,
	route = '/',
	history = createMemoryHistory({initialEntries: [route]}),
}) => ({
	...render(
		<Router history={history}>
			<AppContext.Provider value={contextValue}>
				<MockedProvider
					addTypename={apolloAddTypename}
					link={link}
					mocks={apolloMocks}
				>
					{ui}
				</MockedProvider>
			</AppContext.Provider>
		</Router>
	),
	history,
});
