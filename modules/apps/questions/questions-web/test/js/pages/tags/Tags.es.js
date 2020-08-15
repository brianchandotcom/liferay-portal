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

import {HttpLink} from '@apollo/client';
import {cleanup} from '@testing-library/react';
import React from 'react';
import {Route} from 'react-router-dom';

import Tags from '../../../../src/main/resources/META-INF/resources/js/pages/tags/Tags.es';
import {renderComponent} from '../../../helpers.es';

import '@testing-library/jest-dom/extend-expect';

const mockTags = {
	data: {
		keywordsRanked: {
			items: [
				{
					id: 37018,
					keywordUsageCount: 1,
					name: 'new',
				},
				{
					id: 37019,
					keywordUsageCount: 1,
					name: 'osgi',
				},
				{
					id: 37020,
					keywordUsageCount: 1,
					name: 'tag',
				},
			],
			lastPage: 1,
			page: 1,
			pageSize: 20,
			totalCount: 3,
		},
	},
};

describe('Tags', () => {
	afterEach(() => {
		jest.clearAllMocks();
		cleanup();
	});

	it('Shows list of tags', async () => {
		const route = '/tags';

		const link = new HttpLink({
			credentials: 'include',
			fetch: global.fetch,
			uri: '/o/graphql',
		});

		global.fetch.mockImplementation(() =>
			Promise.resolve({
				json: () => Promise.resolve(mockTags),
				text: () => Promise.resolve(JSON.stringify(mockTags)),
			})
		);

		const {findByText} = renderComponent({
			contextValue: {siteKey: '20020'},
			link,
			route,
			ui: <Route component={Tags} />,
		});

		const firstTag = await findByText('new');
		const secondTag = await findByText('osgi');
		const thirdTag = await findByText('tag');

		expect(firstTag).toBeInTheDocument();
		expect(secondTag).toBeInTheDocument();
		expect(thirdTag).toBeInTheDocument();
	});
});
