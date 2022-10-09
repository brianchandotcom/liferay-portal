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

import {ApolloClient, InMemoryCache} from '@apollo/client';
import {BatchHttpLink} from '@apollo/client/link/batch-http';
import {setContext} from '@apollo/client/link/context';
import {RestLink} from 'apollo-link-rest';
import {SessionStorageWrapper, persistCache} from 'apollo3-cache-persist';
import {useEffect, useState} from 'react';
import {createNetworkStatusNotifier} from 'react-apollo-network-status';
import {Liferay} from '../../services/liferay';
import {liferayTypePolicies} from '../../services/liferay/graphql/typePolicies';
import {networkIndicator} from './networkIndicator';

const LiferayURI = `${Liferay.ThemeDisplay.getPortalURL()}/o`;
const {link, useApolloNetworkStatusReducer} = createNetworkStatusNotifier();
const {initialState, reducer} = networkIndicator;

const liferaBatchLink = new BatchHttpLink({
	uri: `${LiferayURI}/graphql`,
});

const liferayRestLink = new RestLink({
	uri: LiferayURI,
});

const liferayAuthLink = setContext((_, {headers}) => ({
	headers: {
		...headers,
		'x-csrf-token': Liferay.authToken,
	},
}));

export default function useApollo() {
	const [client, setClient] = useState();
	const networkStatus = useApolloNetworkStatusReducer(reducer, initialState);

	useEffect(() => {
		const init = async () => {
			const cache = new InMemoryCache({
				typePolicies: {
					...liferayTypePolicies,
				},
			});

			await persistCache({
				cache,
				storage: new SessionStorageWrapper(window.sessionStorage),
			});

			const apolloClient = new ApolloClient({
				cache,
				defaultOptions: {
					watchQuery: {
						fetchPolicy: 'network-only',
					},
				},
				link: liferayAuthLink.concat(
					link.split(
						(operation) =>
							operation.getContext().type === 'liferay-rest',
						liferayRestLink,
						liferaBatchLink
					)
				),
			});

			setClient(apolloClient);
		};

		init();
	}, []);

	return {client, networkStatus};
}
