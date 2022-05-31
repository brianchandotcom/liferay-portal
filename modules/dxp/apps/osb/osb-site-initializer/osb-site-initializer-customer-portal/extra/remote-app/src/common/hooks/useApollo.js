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
import {SessionStorageWrapper, persistCache} from 'apollo3-cache-persist';
import {useEffect, useState} from 'react';
import {Liferay} from '../services/liferay';
import {liferayTypePolicies} from '../services/liferay/graphql/typePolicies';

const LiferayURI = `${Liferay.ThemeDisplay.getPortalURL()}/o`;

export default function useApollo() {
	const [client, setClient] = useState();

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

			const batchLink = new BatchHttpLink({
				headers: {
					'x-csrf-token': Liferay.authToken,
				},
				uri: `${LiferayURI}/graphql`,
			});

			const apolloClient = new ApolloClient({
				cache,
				link: batchLink,
			});

			setClient(apolloClient);
		};

		init();
	}, []);

	return client;
}
