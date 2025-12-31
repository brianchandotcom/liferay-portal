/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';
import {waitFor} from '@testing-library/react';
import {openModal} from 'frontend-js-components-web';

import CMSAssetPermissionService from '../../../../src/main/resources/META-INF/resources/js/common/services/CMSAssetPermissionService';
import {OBJECT_ENTRY_FOLDER_CLASS_NAME} from '../../../../src/main/resources/META-INF/resources/js/common/utils/constants';
import openResetAssetPermissionModal from '../../../../src/main/resources/META-INF/resources/js/main_view/default_permission/ResetPermissionModalContent';

jest.mock('frontend-js-components-web', () => ({
	openModal: jest.fn(),
}));

describe('ResetPermissionModalContent', () => {
	let resetAssetPermissionSpy: jest.SpyInstance;

	beforeEach(() => {
		global.Liferay.Language = {
			...global.Liferay?.Language,
			get: (key: string) => key,
		};
		global.Liferay.Util = {
			...global.Liferay?.Util,
			openModal: jest.fn(),
			openToast: jest.fn(),
		};

		(openModal as jest.Mock).mockClear();
		(Liferay.Util.openToast as jest.Mock).mockClear();

		resetAssetPermissionSpy = jest.spyOn(
			CMSAssetPermissionService,
			'resetAssetPermission'
		);
	});

	afterEach(() => {
		resetAssetPermissionSpy.mockRestore();
	});

	it('handles Confirm button and successfully resets permissions', async () => {
		resetAssetPermissionSpy.mockResolvedValue({});

		const loadDataFn = jest.fn();

		const props = {
			className: OBJECT_ENTRY_FOLDER_CLASS_NAME,
			classPK: 12345,
			loadData: loadDataFn,
		};

		openResetAssetPermissionModal(props);

		expect(openModal).toHaveBeenCalledTimes(1);

		const modalConfig = (openModal as jest.Mock).mock.calls[0][0];
		const confirmButton = modalConfig.buttons.find(
			(button: any) => button.label === 'confirm'
		);
		const processCloseFn = jest.fn();

		await confirmButton.onClick({processClose: processCloseFn});

		await waitFor(() => {
			expect(resetAssetPermissionSpy).toHaveBeenCalledWith({
				className: props.className,
				classPK: props.classPK,
			});
			expect(Liferay.Util.openToast).toHaveBeenCalledWith({
				message: 'permissions-reset-successfully',
				type: 'success',
			});
			expect(loadDataFn).toHaveBeenCalledTimes(1);
			expect(processCloseFn).toHaveBeenCalledTimes(1);
		});
	});

	it('handles Confirm button and shows error on failure', async () => {
		const error = new Error('Failed to reset');
		resetAssetPermissionSpy.mockRejectedValue(error);

		const loadDataFn = jest.fn();
		const props = {
			className: 'com.liferay.document.library.kernel.model.DLFolder',
			classPK: 0,
			loadData: loadDataFn,
		};

		openResetAssetPermissionModal(props);

		expect(openModal).toHaveBeenCalledTimes(1);

		const modalConfig = (openModal as jest.Mock).mock.calls[0][0];
		const confirmButton = modalConfig.buttons.find(
			(button: any) => button.label === 'confirm'
		);
		const processCloseFn = jest.fn();

		await confirmButton.onClick({processClose: processCloseFn});

		await waitFor(() => {
			expect(resetAssetPermissionSpy).toHaveBeenCalledWith({
				className: props.className,
				classPK: props.classPK,
			});
			expect(Liferay.Util.openToast).toHaveBeenCalledWith({
				message: 'an-error-occurred',
				type: 'danger',
			});
			expect(loadDataFn).not.toHaveBeenCalled();
			expect(processCloseFn).toHaveBeenCalledTimes(1);
		});
	});

	it('handles CANCEL button', async () => {
		const loadDataFn = jest.fn();

		const props = {
			className: OBJECT_ENTRY_FOLDER_CLASS_NAME,
			classPK: 12345,
			loadData: loadDataFn,
		};

		openResetAssetPermissionModal(props);

		expect(openModal).toHaveBeenCalledTimes(1);

		const modalConfig = (openModal as jest.Mock).mock.calls[0][0];
		const cancelButton = modalConfig.buttons.find(
			(button: any) => button.type === 'cancel'
		);

		expect(cancelButton).toBeDefined();
		expect(cancelButton.onClick).toBeUndefined();

		expect(resetAssetPermissionSpy).not.toHaveBeenCalled();
		expect(loadDataFn).not.toHaveBeenCalled();
	});
});
