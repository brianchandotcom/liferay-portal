/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';
import {useResource} from '@clayui/data-provider';
import {render, screen} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import React from 'react';

import {Space} from '../../../../src/main/resources/META-INF/resources/js/common/types/Space';
import CreationModalContent from '../../../../src/main/resources/META-INF/resources/js/main_view/modal/CreationModalContent';

const mockOnSubmit = jest.fn();
const mockNavigate = jest.fn();

const SPACES: Space[] = [
	{
		creatorUserId: '234',
		description: '',
		externalReferenceCode: 'space-1-erc',
		id: 123,
		name: 'Space 1',
		settings: {
			logoColor: 'outline-1',
		},
		siteId: 123,
	},
	{
		creatorUserId: '234',
		description: '',
		externalReferenceCode: 'space-2-erc',
		id: 456,
		name: 'Space 2',
		settings: {
			logoColor: 'outline-2',
		},
		siteId: 456,
	},
];

jest.mock('frontend-js-web', () => ({
	...((jest.requireActual('frontend-js-web') ?? {}) as any),
	navigate: (url: string) => mockNavigate(url),
}));

jest.mock('@clayui/data-provider', () => {
	const originalModule = jest.requireActual('@clayui/data-provider');

	return {
		__esModule: true,
		...originalModule,
		useResource: jest.fn(),
	};
});

const mockUseResource = useResource as jest.Mock;

mockUseResource.mockReturnValue({
	loadMore: jest.fn(),
	resource: SPACES,
});

const defaultProps = {
	action: 'createFolder' as const,
	assetLibraries: [
		{
			externalReferenceCode: 'erc-1',
			groupId: SPACES[0].siteId,
			name: SPACES[0].name,
		},
		{
			externalReferenceCode: 'erc-2',
			groupId: SPACES[1].siteId,
			name: SPACES[1].name,
		},
	],
	closeModal: () => {},
	onSubmit: mockOnSubmit,
	title: 'Create Folder',
};

describe('CreationModalContent', () => {
	const {ResizeObserver: ResizeObserverOriginal} = window;

	beforeAll(() => {
		window.ResizeObserver = jest.fn().mockImplementation(() => ({
			disconnect: jest.fn(),
			observe: jest.fn(),
			unobserve: jest.fn(),
		}));
	});

	beforeEach(() => {
		jest.clearAllMocks();
	});

	afterAll(() => {
		jest.restoreAllMocks();
		window.ResizeObserver = ResizeObserverOriginal;
	});

	test('renders the modal title in the header', () => {
		render(<CreationModalContent {...defaultProps} />);
		expect(screen.getByText('Create Folder')).toBeInTheDocument();
	});

	test('shows the name field only when action is "createFolder"', () => {
		const {rerender} = render(<CreationModalContent {...defaultProps} />);
		expect(screen.getByLabelText(/name/i)).toBeInTheDocument();

		rerender(
			<CreationModalContent {...defaultProps} action="createAsset" />
		);
		expect(screen.queryByLabelText('name')).not.toBeInTheDocument();
	});

	test('shows the space picker only when there are multiple asset libraries', () => {
		const {rerender} = render(<CreationModalContent {...defaultProps} />);
		expect(screen.getByLabelText(/space/i)).toBeInTheDocument();

		rerender(
			<CreationModalContent
				{...defaultProps}
				assetLibraries={[
					{
						externalReferenceCode: 'erc-1',
						groupId: SPACES[0].siteId,
						name: 'Only One Space',
					},
				]}
			/>
		);
		expect(screen.queryByLabelText(/space/i)).not.toBeInTheDocument();
	});

	test('calls onSubmit when form is submitted and there is no redirect', async () => {
		render(<CreationModalContent {...defaultProps} />);

		await userEvent.type(screen.getByLabelText(/name/i), 'Folder Name');
		await userEvent.click(screen.getByLabelText('spacemandatory'));
		await userEvent.click(screen.getByText('Space 1'));
		await userEvent.click(screen.getByText('save'));

		expect(mockOnSubmit).toHaveBeenCalledWith(
			expect.objectContaining({
				groupId: SPACES[0].siteId,
				name: 'Folder Name',
			}),
			expect.anything()
		);
	});

	test('navigates with correct parameters when form is submitted and redirect is provided', async () => {
		const originalURL = window.URL;

		try {
			window.URL = class extends URL {
				constructor(path: string, base = 'http://localhost:8080') {
					super(path, base);
				}
			} as typeof URL;

			render(
				<CreationModalContent
					{...defaultProps}
					redirect="/target-page"
				/>
			);

			await userEvent.type(screen.getByLabelText(/name/i), 'Folder Name');
			await userEvent.click(screen.getByLabelText('spacemandatory'));
			await userEvent.click(screen.getByText('Space 1'));
			await userEvent.click(screen.getByText('save'));

			expect(mockNavigate).toHaveBeenCalledWith(
				expect.stringContaining('/target-page?')
			);
			expect(mockNavigate).toHaveBeenCalledWith(
				expect.stringContaining('name=Folder+Name')
			);
			expect(mockNavigate).toHaveBeenCalledWith(
				expect.stringContaining(`groupId=${SPACES[0].siteId}`)
			);
		}
		finally {
			window.URL = originalURL;
		}
	});
});
