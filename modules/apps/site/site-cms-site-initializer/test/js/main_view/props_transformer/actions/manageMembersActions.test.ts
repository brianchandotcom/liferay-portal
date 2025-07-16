/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openModal} from 'frontend-js-components-web';

import manageMembersAction, {
	ManageMembersData,
} from '../../../../../src/main/resources/META-INF/resources/js/main_view/props_transformer/actions/manageMembersAction';
import SpaceMembersModal from '../../../../../src/main/resources/META-INF/resources/js/main_view/spaces/SpaceMembersModal';

jest.mock('frontend-js-components-web', () => ({
	openModal: jest.fn(),
}));

const mockSpaceMembersModal = SpaceMembersModal as jest.Mock;
jest.mock(
	'../../../../../src/main/resources/META-INF/resources/js/main_view/spaces/SpaceMembersModal',
	() => jest.fn()
);

describe('manageMembersAction', () => {
	afterEach(() => {
		jest.clearAllMocks();
	});

	it('calls openModal with the correct parameters', () => {
		const data: ManageMembersData = {
			assetLibraryCreatorUserId: 'user-123',
			assetLibraryId: 'lib-456',
			title: 'Manage Members Title',
		};
		const loadData = jest.fn();

		manageMembersAction(data, loadData);

		expect(openModal).toHaveBeenCalledTimes(1);

		const openModalConfig = (openModal as jest.Mock).mock.calls[0][0];

		expect(openModalConfig.center).toBe(true);
		expect(openModalConfig.onClose).toBe(loadData);
		expect(openModalConfig.size).toBe('md');
		expect(openModalConfig.title).toBe(data.title);

		const Content = openModalConfig.contentComponent;

		Content();

		expect(mockSpaceMembersModal).toHaveBeenCalledWith({
			assetLibraryCreatorUserId: data.assetLibraryCreatorUserId,
			assetLibraryId: data.assetLibraryId,
		});
	});

	it('calls openModal correctly when loadData is not provided', () => {
		const data: ManageMembersData = {
			assetLibraryCreatorUserId: 'user-789',
			assetLibraryId: 'lib-012',
			title: 'Another Title',
		};

		manageMembersAction(data);

		expect(openModal).toHaveBeenCalledTimes(1);

		const openModalConfig = (openModal as jest.Mock).mock.calls[0][0];

		expect(openModalConfig.onClose).toBeUndefined();
		expect(openModalConfig.title).toBe(data.title);
	});
});
