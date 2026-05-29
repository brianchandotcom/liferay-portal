import * as API from 'shared/api';
import * as data from 'test/data';
import List from '../List';
import mockStore from 'test/mock-store';
import React from 'react';
import {act} from '@testing-library/react';
import {ChannelContext} from 'shared/context/channel';
import {cleanup, render, screen} from '@testing-library/react';
import {fireEvent} from '@testing-library/react';
import {MemoryRouter, Route} from 'react-router-dom';
import {mockChannelContext} from 'test/mock-channel-context';
import {Provider} from 'react-redux';
import {Routes} from 'shared/util/router';
import {SegmentTypes} from 'shared/util/constants';
import {UnassignedSegmentsContext} from 'shared/context/unassignedSegments';
import {User} from 'shared/util/records';
import {waitForLoadingToBeRemoved} from 'test/helpers';

jest.unmock('react-dom');

jest.mock('shared/util/constants', () => {
	const actualConstants = jest.requireActual('shared/util/constants');

	return new Proxy(actualConstants, {
		get(target, property) {
			if (property === 'ENABLE_REAL_TIME_SEGMENTS') {
				return mockEnableRealTimeSegments;
			}

			return target[property];
		}
	});
});

let mockEnableRealTimeSegments = false;

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

		mockEnableRealTimeSegments = false;
	});

	afterEach(() => {
		cleanup();

		jest.useRealTimers();
	});

	it('should disable batch segment when limit is reached', async () => {
		mockEnableRealTimeSegments = true;

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

		await act(async () => {
			jest.runAllTimers();
		});

		const batchOption = screen.getByTestId('batch-segment-dropdown-item');
		expect(batchOption.closest('a')).toHaveClass('disabled');

		const realTimeOption = screen.getByTestId(
			'real-time-segment-dropdown-item'
		);
		expect(realTimeOption.closest('a')).not.toHaveClass('disabled');
	});

	it('should disable real time segment when limit is reached', async () => {
		mockEnableRealTimeSegments = true;

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

		await act(async () => {
			jest.runAllTimers();
		});

		const realTimeOption = screen.getByTestId(
			'real-time-segment-dropdown-item'
		);
		expect(realTimeOption.closest('a')).toHaveClass('disabled');

		const batchOption = screen.getByTestId('batch-segment-dropdown-item');
		expect(batchOption.closest('a')).not.toHaveClass('disabled');
	});

	it('should enable segments when usage is under the limit', async () => {
		mockEnableRealTimeSegments = true;

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

		await act(async () => {
			jest.runAllTimers();
		});

		const realTimeOption = screen.getByTestId(
			'real-time-segment-dropdown-item'
		);
		expect(realTimeOption.closest('a')).not.toHaveClass('disabled');

		const batchOption = screen.getByTestId('batch-segment-dropdown-item');
		expect(batchOption.closest('a')).not.toHaveClass('disabled');
	});

	it('should enable segment options when the limit is set to -1 (unlimited)', async () => {
		mockEnableRealTimeSegments = true;

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

		await act(async () => {
			jest.runAllTimers();
		});

		const realTimeOption = screen.getByTestId(
			'real-time-segment-dropdown-item'
		);
		expect(realTimeOption.closest('a')).not.toHaveClass('disabled');

		const batchOption = screen.getByTestId('batch-segment-dropdown-item');
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

		render(<DefaultComponent />);

		await waitForLoadingToBeRemoved(document.body);

		expect(screen.getByText('Segments')).toBeInTheDocument();
	});

	it('should show the sequential info icon for real time sequential segments', async () => {
		API.projects.fetchFeatureUsages.mockResolvedValueOnce([]);
		API.individualSegment.search.mockReturnValue(
			Promise.resolve(
				data.mockSearch(data.mockSegment, 1, {
					segmentType: SegmentTypes.RealTime,
					sequential: true
				})
			)
		);

		const {container} = render(<DefaultComponent />);

		await waitForLoadingToBeRemoved(document.body);

		expect(container.querySelector('.sticker-info')).toBeInTheDocument();
	});

	it('should not show the sequential info icon for real time non-sequential segments', async () => {
		API.projects.fetchFeatureUsages.mockResolvedValueOnce([]);
		API.individualSegment.search.mockReturnValue(
			Promise.resolve(
				data.mockSearch(data.mockSegment, 1, {
					segmentType: SegmentTypes.RealTime,
					sequential: false
				})
			)
		);

		const {container} = render(<DefaultComponent />);

		await waitForLoadingToBeRemoved(document.body);

		expect(
			container.querySelector('.sticker-info')
		).not.toBeInTheDocument();
	});

	it('should not show the sequential info icon for batch segments', async () => {
		API.projects.fetchFeatureUsages.mockResolvedValueOnce([]);
		API.individualSegment.search.mockReturnValue(
			Promise.resolve(
				data.mockSearch(data.mockSegment, 1, {
					segmentType: SegmentTypes.Batch,
					sequential: true
				})
			)
		);

		const {container} = render(<DefaultComponent />);

		await waitForLoadingToBeRemoved(document.body);

		expect(
			container.querySelector('.sticker-info')
		).not.toBeInTheDocument();
	});

	it('should hide the new segment dropdown and create a batch segment by default', async () => {
		mockEnableRealTimeSegments = false;

		const push = jest.fn();

		API.projects.fetchFeatureUsages.mockResolvedValueOnce([
			{
				currentUsage: 0,
				limit: -1,
				name: 'Segment',
				type: 'Batch'
			}
		]);

		render(<DefaultComponent history={{push}} />);

		await waitForLoadingToBeRemoved(document.body);

		expect(
			screen.queryByTestId('batch-segment-dropdown-item')
		).not.toBeInTheDocument();

		expect(
			screen.queryByTestId('real-time-segment-dropdown-item')
		).not.toBeInTheDocument();

		fireEvent.click(screen.getByTestId('new-segment-button'));

		expect(push).toHaveBeenCalledTimes(1);
		expect(push).toHaveBeenCalledWith(
			expect.stringContaining(`type=${SegmentTypes.Batch}`)
		);
	});

	it('should show the new segment dropdown when real time segments are enabled', async () => {
		mockEnableRealTimeSegments = true;

		API.projects.fetchFeatureUsages.mockResolvedValueOnce([]);

		render(<DefaultComponent />);

		await waitForLoadingToBeRemoved(document.body);

		expect(
			screen.getByTestId('batch-segment-dropdown-item')
		).toBeInTheDocument();

		expect(
			screen.getByTestId('real-time-segment-dropdown-item')
		).toBeInTheDocument();

		expect(
			screen.queryByTestId('new-segment-button')
		).not.toBeInTheDocument();
	});
});
