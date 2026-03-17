/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';

// eslint-disable-next-line
import {checkAccessibility} from '@liferay/layout-js-components-web/test/__lib__/index';
import {render, screen} from '@testing-library/react';
import React from 'react';

import AssetPreview from '../../../../src/main/resources/META-INF/resources/js/common/components/AssetPreview';
import {OBJECT_ENTRY_FOLDER_CLASS_NAME} from '../../../../src/main/resources/META-INF/resources/js/common/utils/constants';

jest.mock(
	'@clayui/empty-state',
	() =>
		function ClayEmptyState() {
			return <div data-testid="clay-empty-state" />;
		}
);

const mockLiferayLanguageGet = jest.fn((key: string) => key);

(global as any).Liferay = {
	Language: {
		get: mockLiferayLanguageGet,
	},
	ThemeDisplay: {
		getPathThemeImages: () => '/path/to/images',
	},
};

jest.mock(
	'../../../../src/main/resources/META-INF/resources/js/common/components/FilePreview',
	() =>
		function FilePreview() {
			return <div data-testid="file-preview" />;
		}
);

jest.mock(
	'../../../../src/main/resources/META-INF/resources/js/common/components/FolderPreview',
	() =>
		function FolderPreview() {
			return <div data-testid="folder-preview" />;
		}
);

jest.mock(
	'../../../../src/main/resources/META-INF/resources/js/common/components/ContentPreview',
	() =>
		function ContentPreview() {
			return <div data-testid="content-preview" />;
		}
);

const mockFileAsset = {
	className: 'file',
	embedded: {
		file: {
			alternativeText: 'file-alt-text',
			link: {
				href: '/file-href',
				label: 'file-label',
			},
			mimeType: 'image/jpeg',
			name: 'file-name',
			previewURL: '/file-preview-url',
			thumbnailURL: '/file-thumbnail-url',
		},
	},
	entryClassName: 'DLFileEntry',
};

const mockFolderAsset = {
	className: 'folder',
	embedded: {
		numberOfObjectEntries: 2,
		numberOfObjectEntryFolders: 3,
		title: 'folder-title',
	},
	entryClassName: OBJECT_ENTRY_FOLDER_CLASS_NAME,
};

const mockContentAsset = {
	className: 'content',
	embedded: {
		title: 'content-title',
	},
	entryClassName: 'WebAsset',
};

describe('AssetPreview', () => {
	describe.each([
		['FilePreview', mockFileAsset, 'file-preview'],
		['FolderPreview', mockFolderAsset, 'folder-preview'],
		['ContentPreview', mockContentAsset, 'content-preview'],
	])('When the item is a %s', (_, mockedAsset, mockedComponentTestId) => {
		it('renders correctly', async () => {
			render(<AssetPreview item={mockedAsset as any} url="/some-url" />);

			expect(
				screen.getByTestId(mockedComponentTestId)
			).toBeInTheDocument();
		});

		it('checks the accessibility', async () => {
			const {container} = render(
				<AssetPreview item={mockedAsset as any} url="/some-url" />
			);

			await checkAccessibility({bestPractices: true, context: container});
		});
	});

	it('renders ClayEmptyState when the item is a WebAsset and showContentPreview is false', async () => {
		render(
			<AssetPreview
				item={mockContentAsset as any}
				showContentPreview={false}
				url="/some-url"
			/>
		);

		expect(screen.getByTestId('clay-empty-state')).toBeInTheDocument();
	});
});
