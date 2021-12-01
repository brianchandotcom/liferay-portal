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

const globalFetch = global.fetch;

const fetchMockResponse = async (response, ok = true) => ({
	json: async () => response,
	ok,
	text: async () => response,
});

function FetchMock(mockDatas = {}) {
	this.mock = () => {
		global.fetch = jest.fn(async (url, {method}) => {
			const mockFetchData = mockDatas[method][url.pathname || url];

			if (mockFetchData) {
				if (Array.isArray(mockFetchData)) {
					if (mockFetchData.length) {
						if (mockFetchData.length === 1) {
							if (mockFetchData[0]) {
								return await mockFetchData[0];
							}
						}
						else {
							return await mockFetchData.shift();
						}
					}
				}
				else {
					return await mockFetchData;
				}
			}

			const mockDataDefault = mockDatas[method].default;

			if (mockDataDefault) {
				if (Array.isArray(mockDataDefault)) {
					if (mockDataDefault.length) {
						if (mockDataDefault.length === 1) {
							if (mockDataDefault[0]) {
								return await mockDataDefault[0];
							}
						}
						else {
							return await mockDataDefault.shift();
						}
					}
				}
				else {
					return await mockDataDefault;
				}
			}

			throw new Error(
				`Request not mocked - method: ${method} - URL: ${
					url.pathname || url
				}`
			);
		});
	};

	this.reset = () => {
		global.fetch = globalFetch;
	};

	this.reset();
	this.mock();
}

export {fetchMockResponse};

export default FetchMock;
