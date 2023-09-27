/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup} from '@testing-library/react';
import {openToast} from 'frontend-js-web';

import {
	errorToast,
	successToast,
} from '../../../src/main/resources/META-INF/resources/js/utils/toast';

jest.mock('frontend-js-web', () => {
	return {
		openToast: jest.fn(),
	};
});

describe('toast', () => {
	afterEach(() => {
		cleanup();
		jest.restoreAllMocks();
	});

	it('executes success toast with default values', () => {
		successToast();

		expect(openToast).toBeCalledWith({
			message: Liferay.Language.get(
				'your-request-completed-successfully'
			),
			title: Liferay.Language.get('success'),
			type: 'success',
		});
	});

	it('executes success toast with values', () => {
		successToast('message', 'title');

		expect(openToast).toBeCalledWith({
			message: 'message',
			title: 'title',
			type: 'success',
		});
	});

	it('executes error toast with default values', () => {
		errorToast();

		expect(openToast).toBeCalledWith({
			message: Liferay.Language.get('an-unexpected-error-occurred'),
			title: Liferay.Language.get('error'),
			type: 'danger',
		});
	});

	it('executes error toast with values', () => {
		errorToast('message', 'title');

		expect(openToast).toBeCalledWith({
			message: 'message',
			title: 'title',
			type: 'danger',
		});
	});
});
