/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';
import {act, render, screen, waitFor} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import React from 'react';

import PublishModal from '../../../src/main/resources/META-INF/resources/js/modals/PublishModal';

const DEFAULT_PROPS = {
	actionButton: 'publish',
	articleId: '123',
	buttonDisabled: false,
	displayDate: null,
	onCloseModal: jest.fn(),
	onPublishButtonClick: jest.fn(),
	permissionsURL: null,
	portletNamespace: 'portletNamespace',
	showPermissionsOptions: false,
	timeZone: 'UTC',
	workflowEnabled: false,
};

const STATUS_CODE_ERROR = {
	error: {
		message: 'upload-size-limit-has-been-exceeded',
		statusCode: 413,
	},
};

describe('PublishModal', () => {
	let ddmFormErrorHandler;

	beforeEach(() => {
		ddmFormErrorHandler = null;

		global.Liferay.on = jest.fn((event, handler) => {
			if (event === 'ddmFormError') {
				ddmFormErrorHandler = handler;
			}

			return {detach: jest.fn()};
		});
	});

	it('closes when a ddmFormError event with statusCode is fired after the publish button is clicked', async () => {
		const onCloseModal = jest.fn();

		render(<PublishModal {...DEFAULT_PROPS} onCloseModal={onCloseModal} />);

		expect(ddmFormErrorHandler).not.toBeNull();

		userEvent.click(await screen.findByText('publish'));

		act(() => {
			ddmFormErrorHandler(STATUS_CODE_ERROR);
		});

		await waitFor(() => {
			expect(onCloseModal).toHaveBeenCalled();
		});
	});

	it('does not close when a ddmFormError event with statusCode is fired before the publish button is clicked', () => {
		const onCloseModal = jest.fn();

		render(<PublishModal {...DEFAULT_PROPS} onCloseModal={onCloseModal} />);

		act(() => {
			ddmFormErrorHandler(STATUS_CODE_ERROR);
		});

		expect(onCloseModal).not.toHaveBeenCalled();
	});

	it('does not close when a ddmFormError event without statusCode is fired', () => {
		const onCloseModal = jest.fn();

		render(<PublishModal {...DEFAULT_PROPS} onCloseModal={onCloseModal} />);

		act(() => {
			ddmFormErrorHandler({});
		});

		expect(onCloseModal).not.toHaveBeenCalled();
	});
});
