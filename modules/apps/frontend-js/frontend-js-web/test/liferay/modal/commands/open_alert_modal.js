/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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
