/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import '@testing-library/jest-dom/extend-expect';
import {cleanup, render} from '@testing-library/react';
import React from 'react';

import HeaderReindexStatus from '../../../../src/main/resources/META-INF/resources/js/shared/components/header/HeaderReindexStatus.es';
import ToasterProvider from '../../../../src/main/resources/META-INF/resources/js/shared/components/toaster/ToasterProvider.es';
import {MockRouter} from '../../../mock/MockRouter.es';

describe('The HeaderTitle component should', () => {
	let containerWrapper;

	afterEach(cleanup);

	beforeEach(() => {
		document.body.innerHTML = '';

		const body = document.createElement('div');

		body.innerHTML =
			'<div data-testid="wrapper"><div id="workflow"></div></div>';

		document.body.appendChild(body);

		containerWrapper = document.getElementById('workflow');
	});

	test('Not render without statuses', () => {
		const {getByTestId} = render(
			<MockRouter>
				<ToasterProvider>
					<HeaderReindexStatus container={containerWrapper} />
				</ToasterProvider>
			</MockRouter>
		);

		const wrapper = getByTestId('wrapper');

		expect(wrapper.children.length).toBe(1);
	});

	test('Render with statuses and show loading status', () => {
		const {getByTestId} = render(
			<MockRouter
				initialReindexStatuses={[{completionPercentage: 0, key: 'All'}]}
			>
				<ToasterProvider>
					<HeaderReindexStatus container={containerWrapper} />
				</ToasterProvider>
			</MockRouter>
		);

		const wrapper = getByTestId('wrapper');
		const statusLoading = getByTestId('statusLoading');

		expect(wrapper.children.length).toBe(2);
		expect(statusLoading.getAttribute('title')).toBe(
			'the-workflow-metrics-data-is-currently-reindexing'
		);
	});
});
