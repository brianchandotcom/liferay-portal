/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import '@testing-library/jest-dom';
import {render} from '@testing-library/react';
import {ClientContext, GraphQLClient} from 'graphql-hooks';
import {MemoryRouter} from 'react-router';

import {AppContext} from '../src/main/resources/META-INF/resources/js/AppContext.es';

export function renderComponent({contextValue = {}, fetch, ui, route = '/'}) {
	window.scrollTo = jest.fn();

	const client = new GraphQLClient({
		fetch,
		method: 'POST',
		url: '/o/graphql',
	});

	const history = {
		location: {
			pathname: route,
		},
		push: (path) => {
			window.history.pushState({}, '', path);
		},
	};

	return {
		...render(
			<ClientContext.Provider value={client}>
				<MemoryRouter initialEntries={[route]}>
					<AppContext.Provider value={contextValue}>
						{ui}
					</AppContext.Provider>
				</MemoryRouter>
			</ClientContext.Provider>
		),
		history,
	};
}
