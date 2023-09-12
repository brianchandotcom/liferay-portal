/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
			status: 'danger',
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
					displayType: 'danger',
					label: 'ok',
					onClick: expect.anything(),
				},
			],
			disableHeader: false,
			footerCssClass: 'border-0',
			headerCssClass: 'border-0',
			onClose: expect.anything(),
			role: 'alertdialog',
			status: 'danger',
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
