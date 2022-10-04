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

import {rest} from 'msw';
import {setupServer} from 'msw/node';

import '@testing-library/jest-dom';
import {fetch} from 'cross-fetch';

global.fetch = fetch;

const server = setupServer(
	rest.get('/o/c/projects', (req, res, ctx) => {
		return res(
			ctx.status(200),
			ctx.json({
				actions: {
					create: {},
				},
				items: [
					{
						description: 'DXP Version',
						id: 1,
						name: 'Liferay Portal 7.4',
					},
				],
				totalCount: 1,
			})
		);
	}),

	rest.get('*', (req, res, ctx) => {
		console.error(`Please add request handler for ${req.url.toString()}`);

		return res(
			ctx.status(500),
			ctx.json({error: 'You must add request handler.'})
		);
	})
);

beforeAll(() => server.listen());

afterAll(() => server.close());

afterEach(() => server.resetHandlers());

export {server, rest};
