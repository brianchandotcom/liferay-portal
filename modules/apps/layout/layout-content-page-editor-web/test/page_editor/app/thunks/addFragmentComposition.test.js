/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openToast} from 'frontend-js-components-web';

import addFragmentCompositionAction from '../../../../src/main/resources/META-INF/resources/page_editor/app/actions/addFragmentComposition';
import FragmentService from '../../../../src/main/resources/META-INF/resources/page_editor/app/services/FragmentService';
import addFragmentComposition from '../../../../src/main/resources/META-INF/resources/page_editor/app/thunks/addFragmentComposition';

jest.mock('frontend-js-components-web', () => ({
	openToast: jest.fn(),
}));

jest.mock(
	'../../../../src/main/resources/META-INF/resources/page_editor/app/actions/addFragmentComposition'
);
jest.mock(
	'../../../../src/main/resources/META-INF/resources/page_editor/app/services/FragmentService'
);

describe('addFragmentComposition', () => {
	let dispatch;
	let getState;
	const mockState = {
		segmentsExperienceId: 12345,
	};

	beforeAll(() => {
		global.Liferay = {
			Language: {
				get: (key) => key,
			},
		};
	});

	beforeEach(() => {
		dispatch = jest.fn();
		getState = jest.fn(() => mockState);
		jest.clearAllMocks();
	});

	it('calls FragmentService and dispatches action on success (invalidFragmentsCount === 0)', () => {
		const mockResponse = {
			fragmentComposition: {id: 1, name: 'Test'},
			invalidFragmentsCount: 0,
			url: 'http://localhost/fragments',
		};

		FragmentService.addFragmentComposition.mockResolvedValue(mockResponse);
		addFragmentCompositionAction.mockReturnValue({
			type: 'ADD_FRAGMENT_COMPOSITION',
		});

		const params = {
			description: 'desc',
			fileEntryId: 101,
			fragmentCollectionId: 202,
			itemId: 'item-1',
			name: 'My Composition',
			saveInlineContent: true,
			saveMappingConfiguration: false,
		};

		return addFragmentComposition(params)(dispatch, getState).then(() => {
			expect(FragmentService.addFragmentComposition).toHaveBeenCalledWith(
				expect.objectContaining({
					...params,
					onNetworkStatus: dispatch,
					segmentsExperienceId: mockState.segmentsExperienceId,
				})
			);

			expect(addFragmentCompositionAction).toHaveBeenCalledWith({
				fragmentCollectionId: params.fragmentCollectionId,
				fragmentComposition: mockResponse.fragmentComposition,
				invalidFragmentsCount: 0,
			});
			expect(dispatch).toHaveBeenCalledWith({
				type: 'ADD_FRAGMENT_COMPOSITION',
			});

			expect(openToast).toHaveBeenCalledWith(
				expect.objectContaining({
					type: 'success',
				})
			);
		});
	});

	it('shows error toast and throws error when invalid fragments are detected (invalidFragmentsCount > 0)', () => {
		const mockResponse = {
			fragmentComposition: null,
			invalidFragmentsCount: 5,
			url: '',
		};

		FragmentService.addFragmentComposition.mockResolvedValue(mockResponse);

		const params = {
			itemId: 'item-1',
			name: 'Invalid Comp',
		};

		return expect(addFragmentComposition(params)(dispatch, getState))
			.rejects.toThrow('Invalid fragment composition')
			.then(() => {
				expect(openToast).toHaveBeenCalledWith(
					expect.objectContaining({
						title: 'error',
						type: 'error',
					})
				);

				expect(addFragmentCompositionAction).not.toHaveBeenCalled();
			});
	});
});
