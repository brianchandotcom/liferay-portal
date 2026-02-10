/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render, screen, waitFor} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import {openModal} from 'frontend-js-components-web';
import React from 'react';

import FragmentService from '../../../../src/main/resources/META-INF/resources/page_editor/app/services/FragmentService';
import addFragmentComposition from '../../../../src/main/resources/META-INF/resources/page_editor/app/thunks/addFragmentComposition';
import openFragmentCompositionModal from '../../../../src/main/resources/META-INF/resources/page_editor/app/utils/openFragmentCompositionModal';

jest.mock('frontend-js-components-web', () => ({
	openModal: jest.fn(),
}));

jest.mock(
	'../../../../src/main/resources/META-INF/resources/page_editor/app/config/index',
	() => ({
		config: {
			fragmentCompositionDescriptionMaxLength: 200,
			fragmentCompositionNameMaxLength: 100,
			portletNamespace: 'mock_namespace_',
		},
	})
);

jest.mock(
	'../../../../src/main/resources/META-INF/resources/page_editor/app/thunks/addFragmentComposition'
);

jest.mock(
	'../../../../src/main/resources/META-INF/resources/page_editor/app/services/FragmentService'
);

jest.mock(
	'../../../../src/main/resources/META-INF/resources/page_editor/common/openImageSelector',
	() => ({
		openImageSelector: jest.fn(),
	})
);

global.Liferay = {
	Browser: {
		isFirefox: () => false,
	},
	Language: {
		get: (key) => key,
	},
};

const MOCK_FRAGMENTS = [
	{fragmentCollectionId: '1', name: 'Collection A'},
	{fragmentCollectionId: '2', name: 'Collection B'},
];

const MOCK_DISPATCH = jest.fn((action) => {
	if (typeof action === 'function') {
		return action(jest.fn(), () => ({}));
	}

	return Promise.resolve(action);
});

const MOCK_ON_CLOSE = jest.fn();

const openAndRenderModal = async (
	{dispatch, fragments, itemId, segmentsExperienceId} = {
		dispatch: MOCK_DISPATCH,
		fragments: MOCK_FRAGMENTS,
		itemId: 'item-1',
		segmentsExperienceId: 'experience-1',
	}
) => {
	await openFragmentCompositionModal({
		dispatch,
		fragments,
		itemId,
		segmentsExperienceId,
	});

	const modalConfig = openModal.mock.calls[0][0];
	const {contentComponent: ContentComponent} = modalConfig;

	render(<ContentComponent closeModal={MOCK_ON_CLOSE} />);

	return modalConfig;
};

describe('openFragmentCompositionModal', () => {
	beforeEach(() => {
		jest.clearAllMocks();

		FragmentService.validateFragmentComposition.mockResolvedValue({
			valid: true,
		});
		addFragmentComposition.mockReturnValue(async () => Promise.resolve({}));
	});

	it('renders the form view when validation is successful', async () => {
		await openAndRenderModal();

		const nameInput = await screen.findByPlaceholderText(/name/i);
		expect(nameInput).toBeInTheDocument();

		expect(screen.getByRole('button', {name: /save/i})).toBeInTheDocument();

		expect(
			screen.queryByText(/the-composition-cannot-be-created/i)
		).not.toBeInTheDocument();
	});

	it('shows a blocked modal when validation fails', async () => {
		FragmentService.validateFragmentComposition.mockResolvedValue({
			valid: false,
		});

		await openFragmentCompositionModal({
			dispatch: MOCK_DISPATCH,
			fragments: MOCK_FRAGMENTS,
			itemId: 'item-1',
			segmentsExperienceId: 'experience-1',
		});

		expect(openModal).toHaveBeenCalledWith(
			expect.objectContaining({
				bodyHTML: Liferay.Language.get(
					'the-composition-cannot-be-created-because-some-fragment-references-are-missing-reimport-the-fragments-and-try-again'
				),
				status: 'danger',
			})
		);
	});

	it('submits the form when valid and data is entered', async () => {
		const user = userEvent.setup();

		await openAndRenderModal();

		const nameInput = await screen.findByPlaceholderText(/name/i);
		await user.type(nameInput, 'My Composition');

		const descInput = await screen.findByPlaceholderText(/description/i);
		await user.type(descInput, 'My Desc');

		const saveButton = await screen.findByRole('button', {name: /save/i});
		await user.click(saveButton);

		await waitFor(() => {
			expect(addFragmentComposition).toHaveBeenCalledWith(
				expect.objectContaining({
					description: 'My Desc',
					fragmentCollectionId: '1',
					itemId: 'item-1',
					name: 'My Composition',
					saveInlineContent: false,
					saveMappingConfiguration: false,
				})
			);
		});

		await waitFor(() => {
			expect(MOCK_ON_CLOSE).toHaveBeenCalled();
		});
	});

	it('shows validation error if name is empty on submit', async () => {
		const user = userEvent.setup();

		await openAndRenderModal();

		const saveButton = await screen.findByRole('button', {name: /save/i});
		await user.click(saveButton);

		expect(addFragmentComposition).not.toHaveBeenCalled();

		const errorMsg = await screen.findByText(/this-field-is-required/i);

		expect(errorMsg).toBeInTheDocument();
	});

	it('updates configuration checkboxes and collection selection', async () => {
		const user = userEvent.setup();

		await openAndRenderModal();

		const nameInput = await screen.findByPlaceholderText(/name/i);
		await user.type(nameInput, 'Test');

		const inlineCheckbox =
			await screen.findByLabelText(/save-inline-content/i);
		await user.click(inlineCheckbox);

		const mappingCheckbox = await screen.findByLabelText(
			/save-mapping-configuration/i
		);
		await user.click(mappingCheckbox);

		const collectionBCard = await screen.findByText('Collection B');
		await user.click(collectionBCard);

		const saveButton = await screen.findByRole('button', {name: /save/i});
		await user.click(saveButton);

		await waitFor(() => {
			expect(addFragmentComposition).toHaveBeenCalledWith(
				expect.objectContaining({
					fragmentCollectionId: '2',
					name: 'Test',
					saveInlineContent: true,
					saveMappingConfiguration: true,
				})
			);
		});
	});

	it('updates payload when a thumbnail is selected', async () => {
		const user = userEvent.setup();

		const {
			openImageSelector,
		} = require('../../../../src/main/resources/META-INF/resources/page_editor/common/openImageSelector');

		openImageSelector.mockImplementation((callback) => {
			callback({
				fileEntryId: 999,
				title: 'my-image.jpg',
			});
		});

		await openAndRenderModal();

		const nameInput = await screen.findByPlaceholderText(/name/i);
		await user.type(nameInput, 'Test');

		const uploadBtn = await screen.findByRole('button', {
			name: /upload-thumbnail/i,
		});
		await user.click(uploadBtn);

		const imageTitle = await screen.findByText('my-image.jpg');
		expect(imageTitle).toBeInTheDocument();

		const saveButton = await screen.findByRole('button', {name: /save/i});
		await user.click(saveButton);

		await waitFor(() => {
			expect(addFragmentComposition).toHaveBeenCalledWith(
				expect.objectContaining({
					fileEntryId: 999,
				})
			);
		});
	});
});
