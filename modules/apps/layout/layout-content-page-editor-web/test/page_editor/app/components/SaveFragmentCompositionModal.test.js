/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render, screen, waitFor} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import React from 'react';

import SaveFragmentCompositionModal from '../../../../src/main/resources/META-INF/resources/page_editor/app/components/SaveFragmentCompositionModal';
import {useActiveItemIds} from '../../../../src/main/resources/META-INF/resources/page_editor/app/contexts/ControlsContext';
import {
	useDispatch,
	useSelector,
} from '../../../../src/main/resources/META-INF/resources/page_editor/app/contexts/StoreContext';
import addFragmentComposition from '../../../../src/main/resources/META-INF/resources/page_editor/app/thunks/addFragmentComposition';
import validateFragmentComposition from '../../../../src/main/resources/META-INF/resources/page_editor/app/thunks/validateFragmentComposition';

jest.mock('@liferay/frontend-js-react-web', () => ({
	useIsMounted: () => () => true,
}));
jest.mock('@clayui/icon', () => {
	return ({symbol}) => <span data-testid={`icon-${symbol}`} />;
});
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
	'../../../../src/main/resources/META-INF/resources/page_editor/common/components/InvisibleFieldset',
	() => {
		return ({children}) => <div>{children}</div>;
	}
);
jest.mock(
	'../../../../src/main/resources/META-INF/resources/page_editor/app/contexts/ControlsContext'
);
jest.mock(
	'../../../../src/main/resources/META-INF/resources/page_editor/app/contexts/StoreContext'
);
jest.mock(
	'../../../../src/main/resources/META-INF/resources/page_editor/app/thunks/addFragmentComposition'
);
jest.mock(
	'../../../../src/main/resources/META-INF/resources/page_editor/app/thunks/validateFragmentComposition'
);
jest.mock(
	'../../../../src/main/resources/META-INF/resources/page_editor/common/openImageSelector',
	() => ({
		openImageSelector: jest.fn(),
	})
);

global.Liferay = {
	Language: {
		get: (key) => key,
	},
};

describe('SaveFragmentCompositionModal', () => {
	const mockDispatch = jest.fn((action) => {
		if (typeof action === 'function') {
			return action(jest.fn(), () => ({}));
		}

		return Promise.resolve(action);
	});
	const mockOnCloseModal = jest.fn();
	const mockCollections = [
		{fragmentCollectionId: '1', name: 'Collection A'},
		{fragmentCollectionId: '2', name: 'Collection B'},
	];

	const renderComponent = () =>
		render(
			<SaveFragmentCompositionModal
				itemId="item-1"
				onCloseModal={mockOnCloseModal}
			/>
		);

	beforeEach(() => {
		jest.clearAllMocks();

		useDispatch.mockReturnValue(mockDispatch);
		useActiveItemIds.mockReturnValue(['active-item-1']);
		useSelector.mockImplementation((selector) =>
			selector({
				collections: mockCollections,
			})
		);

		addFragmentComposition.mockReturnValue(async () => Promise.resolve({}));
		validateFragmentComposition.mockReturnValue(async () =>
			Promise.resolve({invalidFragmentsCount: 0})
		);
	});

	it('renders the form view when validation returns 0 errors', async () => {
		renderComponent();

		const nameInput = await screen.findByPlaceholderText(/name/i);
		expect(nameInput).toBeInTheDocument();

		expect(screen.getByRole('button', {name: /save/i})).toBeInTheDocument();

		expect(
			screen.queryByText(/the-composition-cannot-be-created/i)
		).not.toBeInTheDocument();
	});

	it('renders the error view when validation returns > 0 errors', async () => {
		const user = userEvent.setup();

		validateFragmentComposition.mockReturnValue(async () =>
			Promise.resolve({invalidFragmentsCount: 5})
		);

		renderComponent();

		const errorMsg = await screen.findByText(
			/the-composition-cannot-be-created-because-some-fragment-references-are-missing-reimport-the-fragments-and-try-again/i
		);
		expect(errorMsg).toBeInTheDocument();

		expect(
			screen.queryByRole('button', {name: /save/i})
		).not.toBeInTheDocument();

		const doneButton = screen.getByRole('button', {name: /done/i});
		await user.click(doneButton);

		await waitFor(() => {
			expect(mockOnCloseModal).toHaveBeenCalled();
		});
	});

	it('submits the form when valid and data is entered', async () => {
		const user = userEvent.setup();

		renderComponent();

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
			expect(mockOnCloseModal).toHaveBeenCalled();
		});
	});

	it('shows validation error if name is empty on submit', async () => {
		const user = userEvent.setup();

		renderComponent();

		const saveButton = await screen.findByRole('button', {name: /save/i});
		await user.click(saveButton);

		expect(addFragmentComposition).not.toHaveBeenCalled();

		const errorMsg = await screen.findByText(/this-field-is-required/i);
		expect(errorMsg).toBeInTheDocument();
	});

	it('updates configuration checkboxes and collection selection', async () => {
		const user = userEvent.setup();

		renderComponent();

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

		renderComponent();

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
