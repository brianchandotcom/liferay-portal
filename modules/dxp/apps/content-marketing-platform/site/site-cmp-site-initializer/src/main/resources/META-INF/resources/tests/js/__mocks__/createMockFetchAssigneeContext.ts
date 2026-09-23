/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default function createMockFetchAssigneeContext() {
	const mockFetch = jest.fn();

	mockFetch.mockImplementation((url: string, options?: RequestInit) => {
		if (url.includes('/o/headless-cmp/v1.0/task-assignees/')) {
			return Promise.resolve({
				json: () =>
					Promise.resolve([
						{
							body: {assignTo: {name: 'New Assignee'}},
							taskId: '123',
						},
					]),
				ok: true,
				status: 200,
			});
		}

		throw new Error(
			`global.fetch was not mocked for this call: ${JSON.stringify([url, options])}`
		);
	});

	return mockFetch;
}
