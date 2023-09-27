/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openModal} from '../../../../src/main/resources/META-INF/resources/liferay/modal/Modal';
import openAlertModal from '../../../../src/main/resources/META-INF/resources/liferay/modal/commands/open_alert_modal';

jest.mock(
	'../../../../src/main/resources/META-INF/resources/liferay/modal/Modal',
	() => ({
		...jest.requireActual(
			'../../../../src/main/resources/META-INF/resources/liferay/modal/Modal'
		),
		openModal: jest.fn((args) => args),
	})
);

describe('openAlertModal', () => {
	afterEach(() => {
		jest.resetAllMocks();
	});

	it('when the custom dialogs are enabled, calling openAlertModal, calls openModal with the proper arguments', () => {
		Liferay.CustomDialogs = {
			enabled: true,
		};

		openAlertModal({message: 'lala'});

		expect(openModal).toHaveBeenCalled();
		expect(openModal).toHaveBeenCalledWith({
			bodyHTML: 'lala',
			buttons: [
				{
					autoFocus: true,
					label: 'ok',
					onClick: expect.anything(),
				},
			],
			center: true,
			disableHeader: true,
		});
	});

	it('when the custom dialogs are disabled, calling openAlertModal, calls native alert', () => {
		Liferay.CustomDialogs = {
			enabled: false,
		};

		window.alert = jest.fn();

		openAlertModal({message: 'lala'});

		expect(window.alert).toHaveBeenCalled();
		expect(window.alert).toHaveBeenCalledWith('lala');
	});
});
