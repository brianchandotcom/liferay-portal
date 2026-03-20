/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';

import {OBJECT_ENTRY_FOLDER_CLASS_NAME} from '../../../../src/main/resources/META-INF/resources/js/common/utils/constants';
import AssetsFilesDropFDSPropsTransformer from '../../../../src/main/resources/META-INF/resources/js/main_view/props_transformer/AssetsFilesDropFDSPropsTransformer';
import fileDropAction from '../../../../src/main/resources/META-INF/resources/js/main_view/props_transformer/actions/fileDropAction';

jest.mock(
	'../../../../src/main/resources/META-INF/resources/js/main_view/props_transformer/AssetsFDSPropsTransformer',
	() => ({
		__esModule: true,
		default: jest.fn(() => ({

			// Return a dummy object so the spread operator ...assetsData doesn't fail

			mockedBaseProp: true,
		})),
	})
);

jest.mock(
	'../../../../src/main/resources/META-INF/resources/js/main_view/props_transformer/actions/fileDropAction',
	() => ({
		__esModule: true,
		default: jest.fn(),
	})
);

jest.mock(
	'../../../../src/main/resources/META-INF/resources/js/common/utils/constants',
	() => ({
		OBJECT_ENTRY_FOLDER_CLASS_NAME:
			'com.liferay.portal.kernel.repository.model.Folder',
	})
);

describe('AssetsFilesDropFDSPropsTransformer', () => {
	const mockAdditionalProps = {some: 'props'} as any;
	const mockViews = [] as any;

	beforeEach(() => {
		jest.clearAllMocks();
	});

	it('disable fileDropSettings when primaryItems is empty', () => {
		const creationMenu = {primaryItems: []};

		const result = AssetsFilesDropFDSPropsTransformer({
			additionalProps: mockAdditionalProps,
			creationMenu,
			otherProps: {},
			views: mockViews,
		});

		expect(result.fileDropSettings.enabled).toBe(false);

		result.fileDropSettings.onFileDrop(['file1.png']);

		expect(fileDropAction).not.toHaveBeenCalled();
	});

	it('enable fileDropSettings when primaryItems has items', () => {
		const creationMenu = {primaryItems: [{id: 1}]};

		const result = AssetsFilesDropFDSPropsTransformer({
			additionalProps: mockAdditionalProps,
			creationMenu,
			otherProps: {},
			views: mockViews,
		});

		expect(result.fileDropSettings.enabled).toBe(true);

		result.fileDropSettings.onFileDrop(['file1.png'], 'targetFolder');

		expect(fileDropAction).toHaveBeenCalledWith(
			mockAdditionalProps,
			['file1.png'],
			'targetFolder'
		);
	});

	it('handle cases where primaryItems is missing or undefined', () => {
		const creationMenu = {} as any;

		const result = AssetsFilesDropFDSPropsTransformer({
			additionalProps: mockAdditionalProps,
			creationMenu,
			otherProps: {},
			views: mockViews,
		});

		expect(result.fileDropSettings.enabled).toBe(false);
	});

	it('correctly identify a drop target via entryClassName', () => {
		const result = AssetsFilesDropFDSPropsTransformer({
			additionalProps: mockAdditionalProps,
			creationMenu: {primaryItems: [{id: 1}]},
			otherProps: {},
			views: mockViews,
		});

		const folderItem = {entryClassName: OBJECT_ENTRY_FOLDER_CLASS_NAME};
		const fileItem = {entryClassName: 'some.other.Class'};

		expect(result.fileDropSettings.isDropTarget({item: folderItem})).toBe(
			true
		);
		expect(result.fileDropSettings.isDropTarget({item: fileItem})).toBe(
			false
		);
	});
});
