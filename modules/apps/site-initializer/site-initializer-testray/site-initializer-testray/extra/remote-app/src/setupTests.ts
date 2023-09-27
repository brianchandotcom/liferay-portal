/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
