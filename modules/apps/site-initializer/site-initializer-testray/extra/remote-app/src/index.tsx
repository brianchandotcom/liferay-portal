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
import {ClayModalProvider} from '@clayui/modal';
import ReactDOM from 'react-dom';

import TestrayRouter from './TestrayRouter';
import ClayIconProvider from './context/ClayIconProvider';
import apolloClient from './graphql/apolloClient';

import './styles/index.scss';

class WebComponent extends HTMLElement {
	connectedCallback() {
		ReactDOM.render(
			<ApolloProvider client={apolloClient}>
				<ClayIconProvider>
					<ClayModalProvider>
						<TestrayRouter />
					</ClayModalProvider>
				</ClayIconProvider>
			</ApolloProvider>,
			this
		);
	}
}

const ELEMENT_ID = 'liferay-remote-app-testray';

if (!customElements.get(ELEMENT_ID)) {
	customElements.define(ELEMENT_ID, WebComponent);
}
