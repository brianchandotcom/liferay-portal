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
import openConfirmModal from '../../../../src/main/resources/META-INF/resources/liferay/modal/commands/open_confirm_modal';

jest.mock(
	'../../../../src/main/resources/META-INF/resources/liferay/modal/Modal',
	() => ({
		...jest.requireActual(
			'../../../../src/main/resources/META-INF/resources/liferay/modal/Modal'
		),
		openModal: jest.fn((args) => args),
	})
);

describe('openConfirmModal', () => {
	afterEach(() => {
		jest.resetAllMocks();
	});

	it('when the custom dialogs are enabled, calling openConfirmModal, calls openModal with the proper arguments', () => {
		Liferay.CustomDialogs = {
			enabled: true,
		};

		openConfirmModal({
			message: 'lala',
			onConfirm: () => {},
			title: 'fiona',
		});

		expect(openModal).toHaveBeenCalled();

		expect(openModal).toHaveBeenCalledWith({
			bodyHTML: 'lala',
			buttons: [
				{
					displayType: 'secondary',
					label: 'cancel',
					type: 'cancel',
				},
				{
					autoFocus: true,
					label: 'ok',
					onClick: expect.anything(),
				},
			],
			center: true,
			disableHeader: true,
			onClose: expect.anything(),
			title: 'fiona',
		});
	});

	it('when the custom dialogs are disabled, calling openConfirmModal, calls native confirm', () => {
		Liferay.CustomDialogs = {
			enabled: false,
		};

		window.confirm = jest.fn();

		openConfirmModal({
			message: 'lala2',
			onConfirm: () => {},
			title: 'fiona',
		});

		expect(window.confirm).toHaveBeenCalled();
		expect(window.confirm).toHaveBeenCalledWith('lala2');
	});
});
