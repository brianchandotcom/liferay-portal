/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';

// eslint-disable-next-line
import {checkAccessibility} from '@liferay/layout-js-components-web/test/__lib__/index';
import {render, screen, waitFor} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import React from 'react';

import ObjectEntryService from '../../../../src/main/resources/META-INF/resources/js/main_view/info_panel/services/ObjectEntryService';
import ViewAsset from '../../../../src/main/resources/META-INF/resources/js/main_view/view_asset/ViewAsset';

jest.mock(
	'../../../../src/main/resources/META-INF/resources/js/common/components/AssetPreview',
	() =>
		function AssetPreview() {
			return <div data-testid="asset-preview" />;
		}
);

jest.mock(
	'../../../../src/main/resources/META-INF/resources/js/common/components/Toolbar',
	() => {
		const Toolbar = ({
			children,
			title,
		}: {
			children: React.ReactNode;
			title: string;
		}) => (
			<div data-testid="toolbar">
				<h1>{title}</h1>

				{children}
			</div>
		);

		Toolbar.Item = ({children}: {children: React.ReactNode}) => (
			<div data-testid="toolbar-item">{children}</div>
		);

		return Toolbar;
	}
);

jest.mock(
	'../../../../src/main/resources/META-INF/resources/js/content_editor/components/panels/CommentsPanel',
	() =>
		function CommentsPanel() {
			return <div data-testid="comments-panel" />;
		}
);

const mockLiferayLanguageGet = jest.fn((key: string) => key);

(global as any).Liferay = {
	Language: {
		get: mockLiferayLanguageGet,
	},
};

const mockItem = {
	actions: {},
	dateCreated: '2023-01-01T00:00:00Z',
	dateModified: '2023-01-01T00:00:00Z',
	embedded: {
		entryClassName: 'com.liferay.portal.kernel.model.DLFileEntry',
		file: {
			id: 456,
			link: {
				href: 'http://download-link',
				label: 'Download',
			},
			name: 'test-file.png',
		},
		id: 123,
	},
	entryClassName: 'com.liferay.portal.kernel.model.DLFileEntry',
	score: 1,
	title: 'Mock Asset Title',
};

const defaultProps = {
	backURL: '/back-url',
	className: 'com.liferay.portal.kernel.model.DLFileEntry',
	commentsProps: {
		addCommentURL: '/add-comment',
		deleteCommentURL: '/delete-comment',
		editCommentURL: '/edit-comment',
		editorConfig: {},
		getCommentsURL: '/get-comments',
	},
	contentViewURL: '/content-view',
	getObjectEntryURL: '/get-object-entry',
	hasCommentPermission: true,
};

describe('ViewAsset', () => {
	beforeEach(() => {
		jest.clearAllMocks();

		jest.spyOn(ObjectEntryService, 'getObjectEntry').mockResolvedValue({
			data: mockItem,
		} as any);
	});

	it('renders loading indicator initially', () => {
		render(<ViewAsset {...defaultProps} />);

		expect(screen.getByRole('status')).toBeInTheDocument();
	});

	it('renders content after fetching the item', async () => {
		render(<ViewAsset {...defaultProps} />);

		await waitFor(() => {
			expect(screen.getByText('Mock Asset Title')).toBeInTheDocument();
		});

		expect(screen.getByTestId('asset-preview')).toBeInTheDocument();
		expect(screen.getByTestId('toolbar')).toBeInTheDocument();
	});

	it('checks the accessibility', async () => {
		const {container} = render(<ViewAsset {...defaultProps} />);

		await waitFor(() => {
			expect(screen.getByText('Mock Asset Title')).toBeInTheDocument();
		});

		await checkAccessibility({bestPractices: true, context: container});
	});

	it('renders download button when link is available', async () => {
		render(<ViewAsset {...defaultProps} />);

		await waitFor(() => {
			expect(screen.getByText('download')).toBeInTheDocument();
		});

		const downloadLink = screen.getByRole('link', {name: /download/i});

		expect(downloadLink).toHaveAttribute('href', 'http://download-link');
	});

	it('renders comments button when hasCommentPermission is true', async () => {
		render(<ViewAsset {...defaultProps} />);

		await waitFor(() => {
			expect(
				screen.getByRole('button', {name: 'show-comments'})
			).toBeInTheDocument();
		});
	});

	it('does not render comments button when hasCommentPermission is false', async () => {
		render(<ViewAsset {...defaultProps} hasCommentPermission={false} />);

		await waitFor(() => {
			expect(screen.getByText('Mock Asset Title')).toBeInTheDocument();
		});

		expect(
			screen.queryByRole('button', {name: 'show-comments'})
		).not.toBeInTheDocument();
	});

	it('opens side panel when clicking comments button', async () => {
		render(<ViewAsset {...defaultProps} />);

		await waitFor(() => {
			expect(
				screen.getByRole('button', {name: 'show-comments'})
			).toBeInTheDocument();
		});

		const commentsButton = screen.getByRole('button', {
			name: 'show-comments',
		});

		await userEvent.click(commentsButton);

		expect(screen.getByText('comments')).toBeInTheDocument();
		expect(screen.getByTestId('comments-panel')).toBeInTheDocument();
	});

	it('transforms item if it does not have embedded property', async () => {
		const itemWithoutEmbedded = {
			file: mockItem.embedded.file,
			id: mockItem.embedded.id,
			title: 'Transformed Item Title',
		};

		jest.spyOn(ObjectEntryService, 'getObjectEntry').mockResolvedValue({
			data: itemWithoutEmbedded,
		} as any);

		render(<ViewAsset {...defaultProps} />);

		await waitFor(() => {
			expect(
				screen.getByText('Transformed Item Title')
			).toBeInTheDocument();
		});

		expect(screen.getByTestId('asset-preview')).toBeInTheDocument();
	});
});
