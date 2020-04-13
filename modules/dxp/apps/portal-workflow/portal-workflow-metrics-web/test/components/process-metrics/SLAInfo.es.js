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

import {cleanup, fireEvent, render} from '@testing-library/react';
import React from 'react';

import SLAInfo from '../../../src/main/resources/META-INF/resources/js/components/process-metrics/SLAInfo.es';
import {MockRouter} from '../../mock/MockRouter.es';

import '@testing-library/jest-dom/extend-expect';

describe('The SLAInfo component should', () => {
	let container, getByTestId;

	describe('SLA count 0', () => {
		const clientMock = {
			get: jest
				.fn()
				.mockResolvedValue({data: {items: [], totalCount: 0}}),
		};

		beforeAll(() => {
			const renderResult = render(
				<MockRouter client={clientMock}>
					<SLAInfo processId="1" />
				</MockRouter>
			);

			container = renderResult.container;
			getByTestId = renderResult.getByTestId;
		});

		test('Show alert with correct data', () => {
			const slaInfoAlert = getByTestId('slaInfoAlert');
			const slaInfoLink = getByTestId('slaInfoLink');

			expect(slaInfoLink.getAttribute('href')).toContain('/sla/1/new');
			expect(slaInfoAlert).toHaveTextContent(
				'no-slas-are-defined-for-this-process add-a-new-sla'
			);

			fireEvent.click(slaInfoAlert.children[1]);

			expect(container.innerHTML).toBe('');

			cleanup();
		});
	});

	describe('SLA blocked count 1', () => {
		const clientMock = {
			get: jest
				.fn()
				.mockResolvedValue({data: {items: [], totalCount: 1}}),
		};

		beforeAll(() => {
			const renderResult = render(
				<MockRouter client={clientMock}>
					<SLAInfo processId="1" />
				</MockRouter>
			);

			container = renderResult.container;
			getByTestId = renderResult.getByTestId;
		});

		test('Show alert with correct data', () => {
			const slaInfoAlert = getByTestId('slaInfoAlert');
			const slaInfoLink = getByTestId('slaInfoLink');

			expect(slaInfoLink.getAttribute('href')).toContain(
				'/sla/1/list/20/1'
			);
			expect(slaInfoAlert).toHaveTextContent(
				'x-sla-is-blocked fix-the-sla-configuration-to-resume-accurate-reporting set-up-slas'
			);

			fireEvent.click(slaInfoAlert.children[1]);

			expect(container.innerHTML).toBe('');
		});
	});

	describe('SLA blocked count greater than 1', () => {
		const clientMock = {
			get: jest
				.fn()
				.mockResolvedValue({data: {items: [], totalCount: 2}}),
		};

		beforeAll(() => {
			const renderResult = render(
				<MockRouter client={clientMock}>
					<SLAInfo processId="1" />
				</MockRouter>
			);

			container = renderResult.container;
			getByTestId = renderResult.getByTestId;
		});

		test('Show alert with correct data', () => {
			const slaInfoAlert = getByTestId('slaInfoAlert');
			const slaInfoLink = getByTestId('slaInfoLink');

			expect(slaInfoLink.getAttribute('href')).toContain(
				'/sla/1/list/20/1'
			);
			expect(slaInfoAlert).toHaveTextContent(
				'x-slas-are-blocked fix-the-sla-configuration-to-resume-accurate-reporting set-up-slas'
			);

			fireEvent.click(slaInfoAlert.children[1]);

			expect(container.innerHTML).toBe('');
		});
	});
});
