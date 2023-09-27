/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {act, cleanup, render} from '@testing-library/react';
import React from 'react';

import PendingItemsCard from '../../../../src/main/resources/META-INF/resources/js/components/process-metrics/process-items/PendingItemsCard.es';
import {MockRouter} from '../../../mock/MockRouter.es';

const data = {
	id: 38803,
	instanceCount: 6,
	onTimeInstanceCount: 2,
	overdueInstanceCount: 1,
	title: 'Single Approver',
	untrackedInstanceCount: 3,
};

describe('The pending items card component should', () => {
	let container;

	afterEach(cleanup);

	beforeEach(async () => {
		fetch.mockResolvedValueOnce({
			json: () => Promise.resolve(data),
			ok: true,
		});

		const wrapper = ({children}) => <MockRouter>{children}</MockRouter>;

		const renderResult = render(
			<PendingItemsCard routeParams={{processId: 12345}} />,
			{wrapper}
		);

		container = renderResult.container;

		await act(async () => {
			jest.runAllTimers();
		});
	});

	it('Be rendered with overdue count "1"', () => {
		const panelBody = container.querySelector('.panel-body');

		const overdueLink = panelBody.children[0].children[0];
		const overdueHeader = overdueLink.children[0].children[0];
		const overdueBody = overdueLink.children[0].children[1];
		const overdueFooter = overdueLink.children[0].children[2].children[0];

		expect(overdueHeader.innerHTML).toContain('overdue');
		expect(overdueBody.innerHTML).toBe('1');
		expect(overdueFooter.innerHTML).toBe('16.67%');
		expect(overdueLink.getAttribute('href')).toContain(
			'filters.statuses%5B0%5D=Pending&filters.slaStatuses%5B0%5D=Overdue'
		);
	});

	it('Be rendered with ontime count "2"', () => {
		const panelBody = container.querySelector('.panel-body');

		const ontimeLink = panelBody.children[0].children[1];
		const ontimeHeader = ontimeLink.children[0].children[0];
		const ontimeBody = ontimeLink.children[0].children[1];
		const ontimeFooter = ontimeLink.children[0].children[2].children[0];

		expect(ontimeHeader.innerHTML).toContain('on-time');
		expect(ontimeBody.innerHTML).toBe('2');
		expect(ontimeFooter.innerHTML).toBe('33.33%');
		expect(ontimeLink.getAttribute('href')).toContain(
			'filters.statuses%5B0%5D=Pending&filters.slaStatuses%5B0%5D=OnTime'
		);
	});

	it('Be rendered with untracked count "3"', () => {
		const panelBody = container.querySelector('.panel-body');

		const untrackedLink = panelBody.children[0].children[2];
		const untrackedHeader = untrackedLink.children[0].children[0];
		const untrackedBody = untrackedLink.children[0].children[1];
		const untrackedFooter =
			untrackedLink.children[0].children[2].children[0];

		expect(untrackedHeader.innerHTML).toContain('untracked');
		expect(untrackedBody.innerHTML).toBe('3');
		expect(untrackedFooter.innerHTML).toBe('50%');
		expect(untrackedLink.getAttribute('href')).toContain(
			'filters.statuses%5B0%5D=Pending&filters.slaStatuses%5B0%5D=Untracked'
		);
	});

	it('Be rendered with total pending count "6"', () => {
		const panelBody = container.querySelector('.panel-body');

		const totalLink = panelBody.children[0].children[3];
		const totalHeader = totalLink.children[0].children[0];
		const totalBody = totalLink.children[0].children[1];

		expect(totalHeader.innerHTML).toContain('total-pending');
		expect(totalBody.innerHTML).toBe('6');
		expect(totalLink.getAttribute('href')).toContain(
			'filters.statuses%5B0%5D=Pending'
		);
	});
});
