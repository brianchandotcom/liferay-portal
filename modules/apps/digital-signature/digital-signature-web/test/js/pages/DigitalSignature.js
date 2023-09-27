/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {act, render} from '@testing-library/react';
import React from 'react';

import DigitalSignature from '../../../src/main/resources/META-INF/resources/js/pages/DigitalSignature';

const initialState = {
	allowedFileExtensions: '',
};

describe('DigitalSignature', () => {
	beforeEach(() => {
		jest.useFakeTimers();
	});

	it('renders', async () => {
		const {asFragment} = render(<DigitalSignature {...initialState} />);

		await act(async () => {
			await jest.runAllTimers();
		});

		expect(asFragment()).toMatchSnapshot();
	});
});
