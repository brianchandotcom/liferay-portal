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

import {ApolloProvider} from '@apollo/client';
import {Root, createRoot} from 'react-dom/client';

import TestrayRouter from './TestrayRouter';
import AccountContextProvider from './context/AccountContext';
import ClayIconProvider from './context/ClayIconProvider';
import apolloClient from './graphql/apolloClient';

import './styles/index.scss';

class Testray extends HTMLElement {
	private root: Root | undefined;

	connectedCallback() {
		if (!this.root) {
			this.root = createRoot(this);

			const properties = {
				skipRoleCheck: this.getAttribute('skiprolecheck') === 'true',
			};

			this.root.render(
				<ApolloProvider client={apolloClient}>
					<AccountContextProvider
						skipRoleCheck={properties.skipRoleCheck}
					>
						<ClayIconProvider>
							<TestrayRouter />
						</ClayIconProvider>
					</AccountContextProvider>
				</ApolloProvider>
			);
		}
	}
}

const ELEMENT_ID = 'liferay-remote-app-testray';

if (!customElements.get(ELEMENT_ID)) {
	customElements.define(ELEMENT_ID, Testray);
}
