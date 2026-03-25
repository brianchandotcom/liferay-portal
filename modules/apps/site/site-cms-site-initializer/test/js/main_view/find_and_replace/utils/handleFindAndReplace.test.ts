/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ISearchAssetObjectEntry} from '../../../../../src/main/resources/META-INF/resources/js/common/types/AssetType';
import {StickerConfig} from '../../../../../src/main/resources/META-INF/resources/js/common/types/StickerConfig';
import {handleFindAndReplace} from '../../../../../src/main/resources/META-INF/resources/js/main_view/find_and_replace/utils/handleFindAndReplace';
import {openFindAndReplaceModal} from '../../../../../src/main/resources/META-INF/resources/js/main_view/find_and_replace/utils/openFindAndReplaceModal';

jest.mock('@liferay/frontend-data-set-web', () => ({
	readConfigFromURL: jest.fn(() => ({q: '"my-search"'})),
}));

jest.mock(
	'../../../../../src/main/resources/META-INF/resources/js/main_view/find_and_replace/utils/openFindAndReplaceModal',
	() => ({
		openFindAndReplaceModal: jest.fn(),
	})
);

const MOCK_STICKER_CONFIG: StickerConfig = {
	objectDefinitionCssClasses: {},
	objectDefinitionIcons: {},
};

function createFdsItem(statusCode: number): ISearchAssetObjectEntry {
	return {
		embedded: {
			status: {
				code: statusCode,
			},
		},
	} as ISearchAssetObjectEntry;
}

describe('handleFindAndReplace', () => {
	beforeEach(() => {
		jest.clearAllMocks();
	});

	it('does not pass expired items to openFindAndReplaceModal', async () => {
		const activeItem = createFdsItem(0);
		const expiredItem = createFdsItem(3);

		await handleFindAndReplace({
			availableLocales: [],
			dataSetId: 'dataSetId',
			fdsItems: [activeItem, expiredItem],
			stickerConfig: MOCK_STICKER_CONFIG,
		});

		expect(openFindAndReplaceModal).toHaveBeenCalledTimes(1);

		expect(openFindAndReplaceModal).toHaveBeenCalledWith(
			expect.objectContaining({
				fdsItems: [activeItem],
			})
		);
	});
});
