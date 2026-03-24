import * as API from 'shared/api';
import * as data from 'test/data';
import List from '../List';
import mockStore from 'test/mock-store';
import React from 'react';
import {ChannelContext} from 'shared/context/channel';
import {cleanup, render, screen} from '@testing-library/react';
import {MemoryRouter, Route} from 'react-router-dom';
import {mockChannelContext} from 'test/mock-channel-context';
import {Provider} from 'react-redux';
import {Routes} from 'shared/util/router';
import {UnassignedSegmentsContext} from 'shared/context/unassignedSegments';
import {User} from 'shared/util/records';
import {waitForLoadingToBeRemoved} from 'test/helpers';

jest.unmock('react-dom');

const MOCK_UNASSIGNED_SEGMENTS_CONTEXT = {
	showUnassignedAlert: false,
	unassignedSegments: [],
	unassignedSegmentsDispatch: jest.fn()
};

const store = mockStore();

const DefaultComponent = ({queryString = '', ...otherProps}) => (
	<Provider store={store}>
		<MemoryRouter
			initialEntries={[
				`/workspace/23/123/contacts/segments${queryString}`
			]}
		>
			<Route path={Routes.CONTACTS_LIST_SEGMENT}>
				<UnassignedSegmentsContext.Provider
					value={MOCK_UNASSIGNED_SEGMENTS_CONTEXT}
				>
					<ChannelContext.Provider value={mockChannelContext()}>
						<List
							channelId='123'
							currentUser={data.getImmutableMock(
								User,
								data.mockUser
							)}
							groupId='23'
							{...otherProps}
						/>
					</ChannelContext.Provider>
				</UnassignedSegmentsContext.Provider>
			</Route>
		</MemoryRouter>
	</Provider>
);

describe('List', () => {
	beforeEach(() => {
		jest.clearAllMocks();

		jest.useFakeTimers();
	});

	afterEach(() => {
		cleanup();

		jest.useRealTimers();
	});

	it('should disable batch segment when limit is reached', async () => {
		API.projects.fetchFeatureUsages.mockReturnValueOnce(
			Promise.resolve([
				{
					currentUsage: 1,
					limit: 3,
					name: 'Segment',
					type: 'Real Time'
				},
				{
					currentUsage: 5,
					limit: 5,
					name: 'Segment',
					type: 'Batch'
				}
			])
		);

		render(<DefaultComponent />);

		jest.runAllTimers();

		const batchOption = await screen.findByTestId(
			'batch-segment-dropdown-item'
		);

		expect(batchOption.closest('a')).toHaveClass('disabled');

		const realTimeOption = await screen.findByTestId(
			'real-time-segment-dropdown-item'
		);
		expect(realTimeOption.closest('a')).not.toHaveClass('disabled');
	});

	it('should disable real time segment when limit is reached', async () => {
		API.projects.fetchFeatureUsages.mockReturnValueOnce(
			Promise.resolve([
				{
					currentUsage: 3,
					limit: 3,
					name: 'Segment',
					type: 'Real Time'
				},
				{
					currentUsage: 2,
					limit: 5,
					name: 'Segment',
					type: 'Batch'
				}
			])
		);

		render(<DefaultComponent />);

		jest.runAllTimers();

		const realTimeOption = await screen.findByTestId(
			'real-time-segment-dropdown-item'
		);
		expect(realTimeOption.closest('a')).toHaveClass('disabled');

		const batchOption = await screen.findByTestId(
			'batch-segment-dropdown-item'
		);
		expect(batchOption.closest('a')).not.toHaveClass('disabled');
	});

	it('should enable segments when usage is under the limit', async () => {
		API.projects.fetchFeatureUsages.mockReturnValueOnce(
			Promise.resolve([
				{
					currentUsage: 2,
					limit: 3,
					name: 'Segment',
					type: 'Real Time'
				},
				{
					currentUsage: 2,
					limit: 5,
					name: 'Segment',
					type: 'Batch'
				}
			])
		);

		render(<DefaultComponent />);

		jest.runAllTimers();

		const realTimeOption = await screen.findByTestId(
			'real-time-segment-dropdown-item'
		);
		expect(realTimeOption.closest('a')).not.toHaveClass('disabled');

		const batchOption = await screen.findByTestId(
			'batch-segment-dropdown-item'
		);
		expect(batchOption.closest('a')).not.toHaveClass('disabled');
	});

	it('should enable segment options when the limit is set to -1 (unlimited)', async () => {
		API.projects.fetchFeatureUsages.mockReturnValueOnce(
			Promise.resolve([
				{
					currentUsage: 2,
					limit: -1,
					name: 'Segment',
					type: 'Real Time'
				},
				{
					currentUsage: 2,
					limit: -1,
					name: 'Segment',
					type: 'Batch'
				}
			])
		);

		render(<DefaultComponent />);

		jest.runAllTimers();

		const realTimeOption = await screen.findByTestId(
			'real-time-segment-dropdown-item'
		);
		expect(realTimeOption.closest('a')).not.toHaveClass('disabled');

		const batchOption = await screen.findByTestId(
			'batch-segment-dropdown-item'
		);
		expect(batchOption.closest('a')).not.toHaveClass('disabled');
	});

	it('should render', async () => {
		API.projects.fetchFeatureUsages.mockResolvedValueOnce([
			{
				currentUsage: 0,
				limit: -1,
				name: 'Segment',
				type: 'Real Time'
			},
			{
				currentUsage: 0,
				limit: -1,
				name: 'Segment',
				type: 'Batch'
			}
		]);

		const {container} = render(<DefaultComponent />);

		await waitForLoadingToBeRemoved(container);

		expect(container).toMatchSnapshot();
	});
});
