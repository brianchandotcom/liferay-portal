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

	return (
		<MockRouter client={clientMock}>
			<InstanceListContext.Provider value={{instanceId, setInstanceId}}>
				<ModalContext.Provider
					value={{
						closeModal: jest.fn(),
						processId: '12345',
						visibleModal: 'instanceDetails',
					}}
				>
					{children}
				</ModalContext.Provider>
			</InstanceListContext.Provider>
		</MockRouter>
	);
};

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
			status: 'STOPPED',
		},
		{
			dateOverdue: '2020-01-24T10:08:30Z',
			id: 37318,
			name: 'Update',
			onTime: false,
			remainingTime: -13427723,
			status: 'RUNNING',
		},
	],
	slaStatus: 'Overdue',
};

describe('The InstanceDetailsModal component should', () => {
	let getByText, renderResult;

	const renderComponent = (clientMock) => {
		cleanup();

		renderResult = render(
			<ContainerMock clientMock={clientMock}>
				<InstanceDetailsModal />
			</ContainerMock>
		);

		getByText = renderResult.getByText;

		jest.runAllTimers();
	};

	describe('render with a completed Instance', () => {
		beforeAll(() => {
			renderComponent({
				get: jest.fn().mockResolvedValue({data}),
			});
		});

		test('Render Modal title with correct item id and status icon', () => {
			const instanceDetailsTitle = getByText('item #37634');
			const instanceIconTitle = document.querySelector(
				'.lexicon-icon-check-circle'
			);

			expect(instanceDetailsTitle).toBeTruthy();
			expect(instanceIconTitle).toBeTruthy();
		});

		test('Render SLA details with correct status', () => {
			const resultIcons = document.querySelectorAll('.sticker');
			const resultStatus = document.querySelectorAll('.sla-result');

			expect(getByText('OPEN (1)')).toBeTruthy();
			expect(resultStatus[0]).toHaveTextContent(
				'Jan 24, 2020, 10:08 AM (0d 03h 43min overdue)'
			);
			expect(resultIcons[0].children[0].classList).toContain(
				'lexicon-icon-exclamation-circle'
			);
			expect(getByText('RESOLVED (1)')).toBeTruthy();
			expect(resultStatus[1]).toHaveTextContent('(resolved-on-time)');
			expect(resultIcons[1].children[0].classList).toContain(
				'lexicon-icon-check-circle'
			);
		});

		test('Render Process details with correct infos', () => {
			const instanceDetailsRows = document.querySelectorAll('p.row');

			expect(instanceDetailsRows.length).toBe(6);
			expect(instanceDetailsRows[0]).toHaveTextContent('completed');
			expect(instanceDetailsRows[1]).toHaveTextContent('Test Test');
			expect(instanceDetailsRows[2]).toHaveTextContent(
				'Jan 20, 2020, 11:08 AM'
			);
			expect(instanceDetailsRows[3]).toHaveTextContent('Blogs Entry');
			expect(instanceDetailsRows[4]).toHaveTextContent('Blog 01');
			expect(instanceDetailsRows[5]).toHaveTextContent(
				'Jan 21, 2020, 10:08 AM'
			);
		});

		test('Render Go to Submission Page button with correct link', () => {
			const submissionPageButton = getByText('go-to-submission-page');

			expect(submissionPageButton.getAttribute('href')).toContain(
				'37634'
			);
		});
	});

	describe('render with a pending Instance', () => {
		beforeAll(() => {
			renderComponent({
				get: jest.fn().mockResolvedValue({
					data: {
						...data,
						completed: false,
						taskNames: ['Review'],
					},
				}),
			});
		});

		test('Render Process details with correct infos', () => {
			const instanceDetailsRows = document.querySelectorAll('p.row');

			expect(instanceDetailsRows.length).toBe(7);
			expect(instanceDetailsRows[0]).toHaveTextContent('pending');
			expect(instanceDetailsRows[1]).toHaveTextContent('Test Test');
			expect(instanceDetailsRows[2]).toHaveTextContent(
				'Jan 20, 2020, 11:08 AM'
			);
			expect(instanceDetailsRows[3]).toHaveTextContent('Blogs Entry');
			expect(instanceDetailsRows[4]).toHaveTextContent('Blog 01');
			expect(instanceDetailsRows[5]).toHaveTextContent('Review');
			expect(instanceDetailsRows[6]).toHaveTextContent('unassigned');
		});
	});
});
