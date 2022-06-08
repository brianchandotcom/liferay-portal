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

import {ApolloClient, InMemoryCache, from} from '@apollo/client';
import {BatchHttpLink} from '@apollo/client/link/batch-http';
import {RestLink} from 'apollo-link-rest';

import {Liferay} from '../services/liferay';
import {bodySerializers} from './serializers';

const liferayHost =
	process.env.REACT_APP_LIFERAY_HOST || window.location.origin;

const graphqlPath = process.env.REACT_APP_GRAPHQL_PATH || '/o/graphql';

const headers = {
	'x-csrf-token': Liferay.authToken,
};

const httpLink = new BatchHttpLink({
	headers,
	uri: `${liferayHost}${graphqlPath}`,
});

const restLink = new RestLink({
	bodySerializers,
	headers,
	uri: `${liferayHost}/o/c/`,
});

const client = new ApolloClient({
	cache: new InMemoryCache({
		typePolicies: {
			Mutationc: {
				merge: true,
			},
			c: {
				merge: true,
			},
		},
	}),
	link: from([restLink, httpLink]),
});

export default client;
