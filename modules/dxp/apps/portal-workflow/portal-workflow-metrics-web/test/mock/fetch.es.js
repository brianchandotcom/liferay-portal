/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
