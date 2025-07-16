/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openModal} from 'frontend-js-components-web';

import connectSitesAction, {
	ConnectSitesData,
} from '../../../../../src/main/resources/META-INF/resources/js/main_view/props_transformer/actions/connectSitesAction';

jest.mock('frontend-js-components-web', () => ({
	openModal: jest.fn(),
}));

describe('manageMembersAction', () => {
	afterEach(() => {
		jest.clearAllMocks();
	});

	it('calls openModal with the correct parameters', () => {
		const data: ConnectSitesData = {
			title: 'Connect to Sites',
		};

		connectSitesAction(data);

		expect(openModal).toHaveBeenCalledTimes(1);

		const openModalConfig = (openModal as jest.Mock).mock.calls[0][0];

		expect(openModalConfig.bodyHTML).toBe(`<p>${data.title}</p>`);
		expect(openModalConfig.center).toBe(true);
		expect(openModalConfig.containerProps).toEqual({});
		expect(openModalConfig.size).toBe('lg');
		expect(openModalConfig.title).toBe(data.title);
	});
});
