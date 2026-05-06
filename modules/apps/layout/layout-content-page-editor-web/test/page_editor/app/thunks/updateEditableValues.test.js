/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import updateEditableValuesAction from '../../../../src/main/resources/META-INF/resources/page_editor/app/actions/updateEditableValues';
import FragmentService from '../../../../src/main/resources/META-INF/resources/page_editor/app/services/FragmentService';
import updateEditableValues from '../../../../src/main/resources/META-INF/resources/page_editor/app/thunks/updateEditableValues';

jest.mock(
	'../../../../src/main/resources/META-INF/resources/page_editor/app/actions/updateEditableValues',
	() => jest.fn()
);

jest.mock(
	'../../../../src/main/resources/META-INF/resources/page_editor/app/services/FragmentService',
	() => ({
		updateEditableValues: jest.fn(),
	})
);

jest.mock(
	'../../../../src/main/resources/META-INF/resources/page_editor/app/utils/usePageContents',
	() => ({
		clearPageContents: jest.fn(),
	})
);

describe('updateEditableValues', () => {
	beforeEach(() => {
		jest.resetAllMocks();
	});

	it('keeps only the latest update when a fragment is updated twice', async () => {
		let resolveFirst;

		FragmentService.updateEditableValues
			.mockImplementationOnce(
				() =>
					new Promise((resolve) => {
						resolveFirst = () =>
							resolve({fragmentEntryLink: {content: 'first'}});
					})
			)
			.mockResolvedValueOnce({fragmentEntryLink: {content: 'second'}});

		const params = {editableValues: {}, fragmentEntryLinkId: 'fragment-1'};

		const first = updateEditableValues(params)(jest.fn(), () => ({}));
		const second = updateEditableValues(params)(jest.fn(), () => ({}));

		// Newer request resolves first

		await second;

		// Stale response arrives later

		resolveFirst();

		await first;

		expect(updateEditableValuesAction).toHaveBeenCalledTimes(1);
		expect(updateEditableValuesAction).toHaveBeenCalledWith(
			expect.objectContaining({content: 'second'})
		);
	});
});
