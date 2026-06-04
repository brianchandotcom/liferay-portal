import * as API from 'shared/api';
import * as data from 'test/data';
import List from '../List';
import mockStore from 'test/mock-store';
import React from 'react';
import {act} from '@testing-library/react';
import {ChannelContext} from 'shared/context/channel';
import {cleanup, render, screen} from '@testing-library/react';
import {MemoryRouter, Route} from 'react-router-dom';
import {mockChannelContext} from 'test/mock-channel-context';
import {Provider} from 'react-redux';
import {Routes} from 'shared/util/router';
import {SegmentTypes} from 'shared/util/constants';
import {UnassignedSegmentsContext} from 'shared/context/unassignedSegments';
import {User} from 'shared/util/records';
import {waitForLoadingToBeRemoved} from 'test/helpers';

jest.unmock('react-dom');

jest.mock('shared/util/feature-flags', () => ({
	...jest.requireActual('shared/util/feature-flags'),
	ENABLE_REAL_TIME_SEGMENTS: false
}));

const featureFlags = jest.requireMock('shared/util/feature-flags');

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

		featureFlags.ENABLE_REAL_TIME_SEGMENTS = true;
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

	describe('when real time segments are disabled', () => {
		beforeEach(() => {
			featureFlags.ENABLE_REAL_TIME_SEGMENTS = false;
		});

		it('creates a batch segment directly without a type dropdown', async () => {
			API.projects.fetchFeatureUsages.mockResolvedValueOnce([]);

			render(<DefaultComponent />);

			await act(async () => {
				jest.runAllTimers();
			});

			expect(
				screen.getByTestId('batch-segment-button')
			).toBeInTheDocument();

			expect(
				screen.queryByTestId('batch-segment-dropdown-item')
			).not.toBeInTheDocument();
			expect(
				screen.queryByTestId('real-time-segment-dropdown-item')
			).not.toBeInTheDocument();
		});

		it('disables the new segment button when the batch limit is reached', async () => {
			API.projects.fetchFeatureUsages.mockReturnValueOnce(
				Promise.resolve([
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

			expect(screen.getByTestId('batch-segment-button')).toBeDisabled();
		});
	});

	it('shows the segment type dropdown when real time segments are enabled', async () => {
		API.projects.fetchFeatureUsages.mockResolvedValueOnce([]);

		render(<DefaultComponent />);

		await act(async () => {
			jest.runAllTimers();
		});

		expect(
			screen.getByTestId('batch-segment-dropdown-item')
		).toBeInTheDocument();
		expect(
			screen.getByTestId('real-time-segment-dropdown-item')
		).toBeInTheDocument();
		expect(
			screen.queryByTestId('batch-segment-button')
		).not.toBeInTheDocument();
	});
});
