/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';

import HomeRecentAssetsFDSPropsTransformer from '../../../../src/main/resources/META-INF/resources/js/main_view/props_transformer/HomeRecentAssetsFDSPropsTransformer';

jest.mock('@liferay/frontend-data-set-web', () => ({
	replaceTokens: jest.fn(),
}));

jest.mock('frontend-js-web', () => ({
	sub: jest.fn(),
}));

jest.mock(
	'../../../../src/main/resources/META-INF/resources/js/common/components/asset_usage/utils',
	() => ({
		openAssetUsageListModal: jest.fn(),
	})
);

jest.mock(
	'../../../../src/main/resources/META-INF/resources/js/common/utils/constants',
	() => ({
		OBJECT_ENTRY_FOLDER_CLASS_NAME:
			'com.liferay.portal.kernel.repository.model.Folder',
	})
);

jest.mock(
	'../../../../src/main/resources/META-INF/resources/js/common/utils/openCMSModal',
	() => ({
		openCMSModal: jest.fn(),
	})
);

jest.mock(
	'../../../../src/main/resources/META-INF/resources/js/main_view/default_permission/DefaultPermissionModalContent',
	() => ({
		__esModule: true,
		default: jest.fn(),
	})
);

jest.mock(
	'../../../../src/main/resources/META-INF/resources/js/main_view/default_permission/ResetPermissionModalContent',
	() => ({
		__esModule: true,
		default: jest.fn(),
	})
);

jest.mock(
	'../../../../src/main/resources/META-INF/resources/js/main_view/modal/ExportTranslationModalContent',
	() => ({
		__esModule: true,
		default: jest.fn(),
	})
);

jest.mock(
	'../../../../src/main/resources/META-INF/resources/js/main_view/modal/asset_navigation_view/AssetNavigationModalContent',
	() => ({
		__esModule: true,
		default: jest.fn(),
	})
);

jest.mock(
	'../../../../src/main/resources/META-INF/resources/js/main_view/props_transformer/AssetsFDSPropsTransformer',
	() => ({
		__esModule: true,
		default: jest.fn(),
	})
);

jest.mock(
	'../../../../src/main/resources/META-INF/resources/js/main_view/props_transformer/actions/creationMenuActions',
	() => ({
		__esModule: true,
		default: {importTranslation: jest.fn()},
	})
);

jest.mock(
	'../../../../src/main/resources/META-INF/resources/js/main_view/props_transformer/actions/deleteItemAction',
	() => ({
		__esModule: true,
		default: jest.fn(),
	})
);

jest.mock(
	'../../../../src/main/resources/META-INF/resources/js/main_view/props_transformer/actions/openFolderItemSelectorAction',
	() => ({
		__esModule: true,
		default: jest.fn(),
	})
);

jest.mock(
	'../../../../src/main/resources/META-INF/resources/js/main_view/props_transformer/actions/shareAction',
	() => ({
		__esModule: true,
		default: jest.fn(),
	})
);

jest.mock(
	'../../../../src/main/resources/META-INF/resources/js/main_view/props_transformer/cell_renderers/AssetRenderer',
	() => ({
		__esModule: true,
		default: jest.fn(),
	})
);

describe('HomeRecentAssetsFDSPropsTransformer', () => {
	it('hoist additionalAPIURLParameters to the top level so FrontendDataSet forwards it to loadData', () => {
		const additionalAPIURLParameters =
			"emptySearch=true&filter=cmsKind eq 'object' and " +
			"(cmsSection eq 'contents' or cmsSection eq 'files')";

		const result = HomeRecentAssetsFDSPropsTransformer({
			additionalProps: {
				additionalAPIURLParameters,
				contentViewURL: 'https://example.com/view',
			} as any,
			otherProps: {},
		} as any);

		expect(result.additionalAPIURLParameters).toBe(
			additionalAPIURLParameters
		);
		expect(result.additionalProps).not.toHaveProperty(
			'additionalAPIURLParameters'
		);
		expect(result.additionalProps).toEqual({
			contentViewURL: 'https://example.com/view',
		});
	});

	it('forward other top-level props untouched', () => {
		const result = HomeRecentAssetsFDSPropsTransformer({
			additionalProps: {
				additionalAPIURLParameters: 'foo=bar',
			} as any,
			apiURL: '/o/search/v1.0/search',
		} as any);

		expect(result).toMatchObject({
			additionalAPIURLParameters: 'foo=bar',
			apiURL: '/o/search/v1.0/search',
			hideManagementBarInEmptyState: true,
		});
	});

	it('handle missing additionalProps without throwing', () => {
		const result = HomeRecentAssetsFDSPropsTransformer({
			additionalProps: undefined,
			otherProps: {},
		} as any);

		expect(result.additionalAPIURLParameters).toBeUndefined();
		expect(result.additionalProps).toEqual({});
	});
});
