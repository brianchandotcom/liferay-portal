/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import validateFragmentCompositionAction from '../../../../src/main/resources/META-INF/resources/page_editor/app/actions/validateFragmentComposition';
import FragmentService from '../../../../src/main/resources/META-INF/resources/page_editor/app/services/FragmentService';
import validateFragmentComposition from '../../../../src/main/resources/META-INF/resources/page_editor/app/thunks/validateFragmentComposition';

jest.mock(
	'../../../../src/main/resources/META-INF/resources/page_editor/app/actions/validateFragmentComposition'
);
jest.mock(
	'../../../../src/main/resources/META-INF/resources/page_editor/app/services/FragmentService'
);

describe('validateFragmentComposition', () => {
	let dispatch;
	let getState;
	let mockResponse;

	beforeEach(() => {
		dispatch = jest.fn();
		getState = jest.fn(() => ({
			segmentsExperienceId: 12345,
		}));

		mockResponse = {
			invalidFragmentsCount: 5,
		};

		FragmentService.validateFragmentComposition.mockReturnValue(
			Promise.resolve(mockResponse)
		);

		validateFragmentCompositionAction.mockReturnValue({
			payload: {invalidFragmentsCount: 5},
			type: 'VALIDATE_FRAGMENT_COMPOSITION',
		});
	});

	it('calls FragmentService.validateFragmentComposition with correct arguments', () => {
		const payload = {
			itemId: 'item-1',
			saveInlineContent: true,
			saveMappingConfiguration: false,
		};

		return validateFragmentComposition(payload)(dispatch, getState).then(
			() => {
				expect(
					FragmentService.validateFragmentComposition
				).toHaveBeenCalledWith({
					itemId: 'item-1',
					onNetworkStatus: dispatch,
					saveInlineContent: true,
					saveMappingConfiguration: false,
					segmentsExperienceId: 12345,
				});
			}
		);
	});

	it('dispatches validateFragmentCompositionAction with the response data', () => {
		const payload = {
			itemId: 'item-1',
			saveInlineContent: true,
			saveMappingConfiguration: false,
		};

		return validateFragmentComposition(payload)(dispatch, getState).then(
			() => {
				expect(validateFragmentCompositionAction).toHaveBeenCalledWith({
					invalidFragmentsCount: 5,
				});

				expect(dispatch).toHaveBeenCalledWith({
					payload: {invalidFragmentsCount: 5},
					type: 'VALIDATE_FRAGMENT_COMPOSITION',
				});
			}
		);
	});

	it('returns the response promise', () => {
		const payload = {
			itemId: 'item-1',
			saveInlineContent: true,
			saveMappingConfiguration: false,
		};

		return validateFragmentComposition(payload)(dispatch, getState).then(
			(response) => {
				expect(response).toEqual(mockResponse);
			}
		);
	});
});
