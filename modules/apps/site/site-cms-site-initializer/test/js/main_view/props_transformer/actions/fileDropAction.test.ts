/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import fileDropAction from '../../../../../src/main/resources/META-INF/resources/js/main_view/props_transformer/actions/fileDropAction';
import multipleFilesUploadAction from '../../../../../src/main/resources/META-INF/resources/js/main_view/props_transformer/actions/multipleFilesUploadAction';

jest.mock(
	'../../../../../src/main/resources/META-INF/resources/js/main_view/props_transformer/actions/multipleFilesUploadAction',
	() => ({
		__esModule: true,
		default: jest.fn(),
	})
);

jest.mock('frontend-js-web', () => ({
	navigate: jest.fn(),
}));

const allAssetLibraries = [
	{externalReferenceCode: 'space-a', groupId: 1001, name: 'Space A'},
	{externalReferenceCode: 'space-b', groupId: 1002, name: 'Space B'},
];

const currentSpaceAssetLibraries = [allAssetLibraries[0]];

const baseAdditionalProps = {
	assetLibraries: allAssetLibraries,
	baseAssetLibraryViewURL: '/space/',
	baseFolderViewURL: '/folder/',
	candidateAssetLibraries: currentSpaceAssetLibraries,
	keywords: '',
	parentObjectEntryFolderExternalReferenceCode: 'L_FILES',
	redirect: '/back',
};

const droppedFile = {name: 'report.pdf', size: 123};

describe('fileDropAction', () => {
	beforeEach(() => {
		jest.clearAllMocks();
	});

	it('does nothing when no files are dropped', () => {
		fileDropAction(baseAdditionalProps, null);

		expect(multipleFilesUploadAction).not.toHaveBeenCalled();
	});

	it('passes candidateAssetLibraries to the upload modal so the current Space is implicit', () => {
		fileDropAction(baseAdditionalProps, [droppedFile]);

		const [data] = (multipleFilesUploadAction as jest.Mock).mock.calls[0];

		expect(data.assetLibraries).toEqual(currentSpaceAssetLibraries);
		expect(data.assetLibraries).not.toEqual(allAssetLibraries);
	});

	it('uses the parent folder ERC from additionalProps when no dropTarget is provided', () => {
		fileDropAction(baseAdditionalProps, [droppedFile]);

		const [data] = (multipleFilesUploadAction as jest.Mock).mock.calls[0];

		expect(data.parentObjectEntryFolderExternalReferenceCode).toBe(
			'L_FILES'
		);
	});

	it('uses the dropTarget folder ERC when a dropTarget is provided', () => {
		const dropTarget = {
			embedded: {externalReferenceCode: 'subfolder-erc', id: 42},
		};

		fileDropAction(baseAdditionalProps, [droppedFile], dropTarget);

		const [data] = (multipleFilesUploadAction as jest.Mock).mock.calls[0];

		expect(data.parentObjectEntryFolderExternalReferenceCode).toBe(
			'subfolder-erc'
		);
	});
});
