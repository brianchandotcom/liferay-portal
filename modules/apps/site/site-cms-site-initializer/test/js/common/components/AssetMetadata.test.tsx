/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';
import {render, screen} from '@testing-library/react';
import React from 'react';

import AssetMetadata from '../../../../src/main/resources/META-INF/resources/js/main_view/info_panel/components/AssetMetadata';
import {
	AssetTypeInfoPanelContext,
	IAssetTypeInfoPanelContext,
} from '../../../../src/main/resources/META-INF/resources/js/main_view/info_panel/context';
import {ASSET_TYPE} from '../../../../src/main/resources/META-INF/resources/js/main_view/info_panel/util/constants';

jest.mock(
	'../../../../src/main/resources/META-INF/resources/js/common/utils/dateFormat'
);

const mockLiferayLanguageGet = jest.fn((key: string) => key);

(global as any).Liferay = {
	Language: {
		get: mockLiferayLanguageGet,
	},
	ThemeDisplay: {
		getTimeZone: () => 'UTC',
	},
};

const mockAsset = {
	className: 'file',
	embedded: {
		creator: {
			name: 'Test User',
		},
		dateCreated: '2025-01-01T00:00:00Z',
		dateModified: '2025-01-02T00:00:00Z',
		file: {
			alternativeText: 'file-alt-text',
			extension: 'jpg',
			link: {
				href: '/file-href',
				label: 'file-label',
			},
			metadata: {
				aspectRatio: 'tall',
				resolution: '1920x1080',
			},
			mimeType: 'image/jpeg',
			name: 'file-name',
			previewURL: '/file-preview-url',
			size: '1.2 MB',
			thumbnailURL: '/file-thumbnail-url',
		},
		title_i18n: {
			en_US: 'Test Asset',
		},
	},
	entryClassName: 'DLFileEntry',
};

const mockContextValue: IAssetTypeInfoPanelContext = {
	actions: {},
	asset: mockAsset.embedded as any,
	cmsGroupId: 123,
	commentsProps: {},
	selectedAssets: [],
	type: ASSET_TYPE.FILES,
};

describe('AssetMetadata', () => {
	it('renders file metadata correctly', async () => {
		render(
			<AssetTypeInfoPanelContext.Provider value={mockContextValue}>
				<AssetMetadata />
			</AssetTypeInfoPanelContext.Provider>
		);

		expect(screen.getByText('metadata')).toBeInTheDocument();
		expect(screen.getByText('jpg')).toBeInTheDocument();
		expect(screen.getByText('1.2 MB')).toBeInTheDocument();
		expect(screen.getByText('1920x1080')).toBeInTheDocument();
		expect(screen.getByText('tall')).toBeInTheDocument();
		expect(screen.getByText('Test User')).toBeInTheDocument();
	});
});
