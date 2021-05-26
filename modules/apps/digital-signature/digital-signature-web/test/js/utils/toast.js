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
