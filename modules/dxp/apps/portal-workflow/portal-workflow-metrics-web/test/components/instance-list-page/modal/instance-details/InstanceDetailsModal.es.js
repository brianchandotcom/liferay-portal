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

import {cleanup, render} from '@testing-library/react';
import React, {useState} from 'react';

import '@testing-library/jest-dom/extend-expect';

import {InstanceListContext} from '../../../../../src/main/resources/META-INF/resources/js/components/instance-list-page/InstanceListPageProvider.es';
import {ModalContext} from '../../../../../src/main/resources/META-INF/resources/js/components/instance-list-page/modal/ModalProvider.es';
import InstanceDetailsModal from '../../../../../src/main/resources/META-INF/resources/js/components/instance-list-page/modal/instance-details/InstanceDetailsModal.es';
import {MockRouter} from '../../../../mock/MockRouter.es';

const ContainerMock = ({children, clientMock}) => {
	const [instanceId, setInstanceId] = useState(37634);
	const [visibleModal, setVisibleModal] = useState('instanceDetails');

	return (
		<MockRouter client={clientMock}>
			<InstanceListContext.Provider value={{instanceId, setInstanceId}}>
				<ModalContext.Provider
					value={{processId: '12345', setVisibleModal, visibleModal}}
				>
					{children}
				</ModalContext.Provider>
			</InstanceListContext.Provider>
		</MockRouter>
	);
};

describe('The InstanceDetailsModal component should', () => {
	let getAllByTestId, getByTestId, renderResult;

	const data = {
		assetTitle: 'Blog 01',
		assetType: 'Blogs Entry',
		completed: true,
		creator: {
			id: 1,
			name: 'Test Test',
		},
		dateCompletion: '2020-01-21T10:08:30Z',
		dateCreated: '2020-01-20T11:08:30Z',
		id: 37634,
		processId: 30000,
		slaResults: [
			{
				dateOverdue: '2020-01-23T07:08:30Z',
				id: 37315,
				name: 'Review',
				onTime: true,
				remainingTime: 13427723,
				status: 'Stopped',
			},
			{
				dateOverdue: '2020-01-24T10:08:30Z',
				id: 37318,
				name: 'Update',
				onTime: false,
				remainingTime: -13427723,
				status: 'Running',
			},
		],
		slaStatus: 'Overdue',
	};

	const clientMock = {
		get: jest.fn().mockResolvedValue({data}),
	};

	beforeAll(() => {
		renderResult = render(
			<ContainerMock clientMock={clientMock}>
				<InstanceDetailsModal />
			</ContainerMock>
		);

		getAllByTestId = renderResult.getAllByTestId;
		getByTestId = renderResult.getByTestId;

		jest.runAllTimers();
	});

	test('Render Modal title with correct item id and status icon', () => {
		const instanceDetailsTitle = getByTestId('instanceDetailsTitle');
		const instanceIconTitle = getByTestId('iconTitle');

		expect(instanceDetailsTitle).toHaveTextContent('item #37634');
		expect([...instanceIconTitle.classList]).toContain(
			'lexicon-icon-exclamation-circle'
		);
	});

	test('Render SLA details with correct status', () => {
		const instanceSubTitles = getAllByTestId('instanceSectionSubTitle');
		const resultIcons = getAllByTestId('resultIcon');
		const resultStatus = getAllByTestId('resultStatus');

		expect(instanceSubTitles[0]).toHaveTextContent('OPEN (1)');
		expect(resultStatus[0]).toHaveTextContent(
			'Jan 24, 2020, 10:08 AM (0d 03h 43min overdue)'
		);
		expect([...resultIcons[0].classList]).toContain(
			'lexicon-icon-exclamation-circle'
		);
		expect(instanceSubTitles[1]).toHaveTextContent('RESOLVED (1)');
		expect(resultStatus[1]).toHaveTextContent('(resolved-on-time)');
		expect([...resultIcons[1].classList]).toContain(
			'lexicon-icon-check-circle'
		);
	});

	test('Render Process details with correct infos', () => {
		const instanceDetailSpan = getAllByTestId('instanceDetailSpan');

		expect(instanceDetailSpan.length).toBe(6);
		expect(instanceDetailSpan[0]).toHaveTextContent('completed');
		expect(instanceDetailSpan[1]).toHaveTextContent('Test Test');
		expect(instanceDetailSpan[2]).toHaveTextContent(
			'Jan 20, 2020, 11:08 AM'
		);
		expect(instanceDetailSpan[3]).toHaveTextContent('Blogs Entry');
		expect(instanceDetailSpan[4]).toHaveTextContent('Blog 01');
		expect(instanceDetailSpan[5]).toHaveTextContent(
			'Jan 21, 2020, 10:08 AM'
		);
	});

	test('Render Go to Submission Page button with correct link', () => {
		const submissionPageButton = getByTestId('submissionPageButton');

		expect(submissionPageButton.getAttribute('href')).toContain('37634');
	});
});

describe('The InstanceDetailsModal component should', () => {
	let getAllByTestId, renderResult;

	const data = {
		assetTitle: 'Blog 01',
		assetType: 'Blogs Entry',
		completed: false,
		creator: {
			id: 1,
			name: 'Test Test',
		},
		dateCreated: '2020-01-20T11:08:30Z',
		id: 37634,
		processId: 30000,
		slaResults: [
			{
				dateOverdue: '2020-01-23T07:08:30Z',
				id: 37315,
				name: 'Review',
				onTime: true,
				remainingTime: 13427723,
				status: 'Stopped',
			},
			{
				dateOverdue: '2020-01-24T10:08:30Z',
				id: 37318,
				name: 'Update',
				onTime: false,
				remainingTime: -13427723,
				status: 'Running',
			},
		],
		slaStatus: 'Overdue',
		taskNames: ['Review'],
	};

	const clientMock = {
		get: jest.fn().mockResolvedValue({data}),
	};

	beforeAll(() => {
		cleanup();

		renderResult = render(
			<ContainerMock clientMock={clientMock}>
				<InstanceDetailsModal />
			</ContainerMock>
		);

		getAllByTestId = renderResult.getAllByTestId;

		jest.runAllTimers();
	});

	test('Render Process details with correct infos', () => {
		const instanceDetailSpan = getAllByTestId('instanceDetailSpan');

		expect(instanceDetailSpan.length).toBe(7);
		expect(instanceDetailSpan[0]).toHaveTextContent('pending');
		expect(instanceDetailSpan[5]).toHaveTextContent('Review');
		expect(instanceDetailSpan[6]).toHaveTextContent('unassigned');
	});
});
