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

import {
	KEYWORD_QUERY_CONTRIBUTORS,
	MODEL_PREFILTER_CONTRIBUTORS,
	QUERY_PREFILTER_CONTRIBUTORS,
	SEARCHABLE_TYPES,
} from './data';

async function mockFetch(url) {
	switch (url) {
		case '/o/search-experiences-rest/v1.0/searchable-asset-names/en_US': {
			return {
				json: async () => ({
					items: SEARCHABLE_TYPES,
					page: 1,
					totalCount: SEARCHABLE_TYPES.length,
				}),
				ok: true,
				status: 200,
			};
		}
		case '/o/search-experiences-rest/v1.0/keyword-query-contributors': {
			return {
				json: async () => ({
					items: KEYWORD_QUERY_CONTRIBUTORS.map((className) => ({
						className,
					})),
					page: 1,
					totalCount: KEYWORD_QUERY_CONTRIBUTORS.length,
				}),
				ok: true,
				status: 200,
			};
		}
		case '/o/search-experiences-rest/v1.0/model-prefilter-contributors': {
			return {
				json: async () => ({
					items: MODEL_PREFILTER_CONTRIBUTORS.map((className) => ({
						className,
					})),
					page: 1,
					totalCount: MODEL_PREFILTER_CONTRIBUTORS.length,
				}),
				ok: true,
				status: 200,
			};
		}
		case '/o/search-experiences-rest/v1.0/query-prefilter-contributors': {
			return {
				json: async () => ({
					items: QUERY_PREFILTER_CONTRIBUTORS.map((className) => ({
						className,
					})),
					page: 1,
					totalCount: QUERY_PREFILTER_CONTRIBUTORS.length,
				}),
				ok: true,
				status: 200,
			};
		}
		default: {
			throw new Error(`Unhandled request: ${url}`);
		}
	}
}

beforeAll(() => jest.spyOn(window, 'fetch'));

beforeEach(() => window.fetch.mockImplementation(mockFetch));
