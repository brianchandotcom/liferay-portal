/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openModal} from 'frontend-js-components-web';

import manageSitesAction, {
	ManageSitesData,
} from '../../../../../src/main/resources/META-INF/resources/js/main_view/props_transformer/actions/manageSitesAction';
import SpaceSitesModal from '../../../../../src/main/resources/META-INF/resources/js/main_view/spaces/SpaceSitesModal';

jest.mock('frontend-js-components-web', () => ({
	openModal: jest.fn(),
}));

const mockSpaceSitesModal = SpaceSitesModal as jest.Mock;
jest.mock(
	'../../../../../src/main/resources/META-INF/resources/js/main_view/spaces/SpaceSitesModal',
	() => jest.fn()
);

describe('manageSitesAction', () => {
	afterEach(() => {
		jest.clearAllMocks();
	});

	describe('openModal', () => {
		it.each([
			[true, jest.fn()],
			[false, undefined],
		])(
			'is called with hasConnectSitesPermission=%s and loadData=%s',
			(hasConnectSitesPermission, loadData) => {
				const data: ManageSitesData = {
					groupId: 'lib-456',
					hasConnectSitesPermission,
				};

				manageSitesAction(data, loadData);

				expect(openModal).toHaveBeenCalledTimes(1);

				const openModalConfig = (openModal as jest.Mock).mock
					.calls[0][0];

				expect(openModalConfig.onClose).toBe(loadData);
				expect(openModalConfig.size).toBe('md');

				const Content = openModalConfig.contentComponent;

				Content();

				expect(mockSpaceSitesModal).toHaveBeenCalledWith({
					groupId: data.groupId,
					hasConnectSitesPermission: data.hasConnectSitesPermission,
				});
			}
		);
	});
});
