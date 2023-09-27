/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';

import Loading from '../../../../src/main/resources/META-INF/resources/js/components/loading/Loading';

describe('Loading', () => {
	beforeEach(() => {
		cleanup();
	});

	it('renders with loading', () => {
		const {container, queryByText, rerender} = render(
			<Loading isLoading>
				<h1>Liferay</h1>
			</Loading>
		);

		expect(container.querySelector('.loading-animation')).toBeTruthy();
		expect(queryByText('Liferay')).toBeFalsy();

		rerender(
			<Loading isLoading={false}>
				<h1>Liferay</h1>
			</Loading>
		);

		expect(container.querySelector('.loading-animation')).toBeFalsy();
		expect(queryByText('Liferay')).toBeTruthy();
	});
});
