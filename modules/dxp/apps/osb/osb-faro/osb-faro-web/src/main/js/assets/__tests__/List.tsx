import List from '../List';
import mockStore from 'test/mock-store';
import React from 'react';
import {ChannelContext} from 'shared/context/channel';
import {cleanup, fireEvent, render, screen} from '@testing-library/react';
import {createMemoryHistory} from 'history';
import {mockChannelContext} from 'test/mock-channel-context';
import {Provider} from 'react-redux';
import {RangeKeyTimeRanges} from 'shared/util/constants';
import {Router} from 'react-router-dom';

jest.unmock('react-dom');

jest.mock('shared/hooks/useFrontendDataSet', () => ({
	useFrontendDataSet: () => {
		const FakeDataSet = ({id}: {id: string}) => (
			<div data-testid='fds-component' id={id} />
		);

		return FakeDataSet;
	}
}));

jest.mock('shared/components/dropdown-range-key/DropdownRangeKey', () => ({
	DropdownRangeKey: ({
		onRangeSelectorChange,
		rangeSelectors
	}: {
		onRangeSelectorChange: (rs: any) => void;
		rangeSelectors: any;
	}) => (
		<div data-testid='dropdown-range-key'>
			<span data-testid='current-range-key'>
				{rangeSelectors.rangeKey}
			</span>

			<button
				data-testid='change-range-btn'
				onClick={() =>
					onRangeSelectorChange({
						rangeEnd: null,
						rangeKey: '7', // RangeKeyTimeRanges.Last7Days
						rangeStart: null
					})
				}
			>
				{'Change Range'}
			</button>

			<button
				data-testid='change-range-custom-btn'
				onClick={() =>
					onRangeSelectorChange({
						rangeEnd: '2024-03-01',
						rangeKey: 'CUSTOM', // RangeKeyTimeRanges.CustomRange
						rangeStart: '2024-01-01'
					})
				}
			>
				{'Change to Custom Range'}
			</button>
		</div>
	)
}));

// breadcrumbs.getHome is a pure utility – mocking it keeps the test focused
// on the page's own behaviour and avoids router-path side effects.

jest.mock('shared/util/breadcrumbs', () => ({
	getHome: jest.fn(({label}: {label?: string} = {}) => ({
		active: false,
		label: label || 'Home'
	}))
}));

jest.mock('react-router-dom', () => ({
	...jest.requireActual('react-router-dom'),
	useHistory: jest.fn(),
	useParams: () => ({
		channelId: '123',
		groupId: '23'
	})
}));

// Default push spy shared across tests, reset in beforeEach.

const mockHistoryPush = jest.fn();

const buildHistory = (path = '/workspace/23/123/assets') => {
	const history = createMemoryHistory({initialEntries: [path]});

	history.push = mockHistoryPush;

	return history;
};

const store = mockStore();

// Helper: wrap List in the minimum context providers it needs.

const renderList = (
	{queryString = ''}: {queryString?: string} = {},
	history = buildHistory(`/workspace/23/123/assets${queryString}`)
) =>
	render(
		<Provider store={store}>
			<ChannelContext.Provider value={mockChannelContext() as any}>
				<Router history={history}>
					<List />
				</Router>
			</ChannelContext.Provider>
		</Provider>
	);

// Obtain the mocked useHistory so we can configure it per test.

// eslint-disable-next-line @typescript-eslint/no-var-requires
const {useHistory} = require('react-router-dom');

describe('List', () => {
	beforeEach(() => {
		jest.clearAllMocks();

		useHistory.mockReturnValue({push: mockHistoryPush});
	});

	afterEach(cleanup);

	describe('rendering', () => {
		it('should render without crashing', () => {
			const {container} = renderList();

			expect(container).toBeInTheDocument();
		});

		it('should render the page title "Assets"', () => {
			renderList();

			expect(screen.getByText('Assets')).toBeInTheDocument();
		});

		it('should render the FrontendDataSet component', () => {
			renderList();

			expect(screen.getByTestId('fds-component')).toBeInTheDocument();
		});

		it('should render the FrontendDataSet with id "assetTable"', () => {
			renderList();

			expect(screen.getByTestId('fds-component')).toHaveAttribute(
				'id',
				'assetTable'
			);
		});

		it('should render the DropdownRangeKey', () => {
			renderList();

			expect(
				screen.getByTestId('dropdown-range-key')
			).toBeInTheDocument();
		});

		it('should match the snapshot', () => {
			const {container} = renderList();

			expect(container).toMatchSnapshot();
		});
	});

	describe('initial range selector state', () => {
		it('should default to Last30Days when no query string is present', () => {
			renderList();

			expect(screen.getByTestId('current-range-key')).toHaveTextContent(
				RangeKeyTimeRanges.Last30Days
			);
		});

		it('should pick up rangeKey from the URL query string', () => {
			// The real useQueryRangeSelectors reads from the URL; we provide a
			// URL carrying a rangeKey to verify the initial state is seeded
			// from the query params.

			renderList({
				queryString: `?rangeKey=${RangeKeyTimeRanges.Last7Days}`
			});

			expect(screen.getByTestId('current-range-key')).toHaveTextContent(
				RangeKeyTimeRanges.Last7Days
			);
		});
	});

	describe('onRangeSelectorChange', () => {
		it('should call history.push when the range selector changes', () => {
			renderList();

			fireEvent.click(screen.getByTestId('change-range-btn'));

			expect(mockHistoryPush).toHaveBeenCalledTimes(1);
		});

		it('should update the displayed range key after a change', () => {
			// List calls setRangeSelectors in the onRangeSelectorChange
			// handler, which causes a re-render passing the new rangeSelectors
			// to the stub DropdownRangeKey. Since history.push is mocked and
			// does not navigate, the state update drives the re-render.

			renderList();

			fireEvent.click(screen.getByTestId('change-range-btn'));

			expect(screen.getByTestId('current-range-key')).toHaveTextContent(
				RangeKeyTimeRanges.Last7Days
			);
		});

		it('should include the new rangeKey in the URL pushed to history', () => {
			renderList();

			fireEvent.click(screen.getByTestId('change-range-btn'));

			const pushedPath: string = mockHistoryPush.mock.calls[0][0];

			expect(pushedPath).toContain(RangeKeyTimeRanges.Last7Days);
		});

		it('should reset page to DEFAULT_CUR (1) when the range changes', () => {
			renderList();

			fireEvent.click(screen.getByTestId('change-range-btn'));

			// FaroConstants.pagination.cur === 1 in the jest config globals
			const pushedPath: string = mockHistoryPush.mock.calls[0][0];

			expect(pushedPath).toContain('page=1');
		});

		it('should strip rangeEnd and rangeStart from the URL when switching to a preset range', () => {
			// Start with a custom range in the URL so the strip logic is
			// exercised by removeUriQueryParam.

			renderList({
				queryString:
					'?rangeKey=CUSTOM&rangeStart=2024-01-01&rangeEnd=2024-03-01'
			});

			fireEvent.click(screen.getByTestId('change-range-btn'));

			const pushedPath: string = mockHistoryPush.mock.calls[0][0];

			expect(pushedPath).not.toContain('rangeEnd=2024-03-01');
			expect(pushedPath).not.toContain('rangeStart=2024-01-01');
		});

		it('should include rangeEnd and rangeStart in the URL for a custom range', () => {
			renderList();

			fireEvent.click(screen.getByTestId('change-range-custom-btn'));

			// pickBy strips null values; rangeEnd and rangeStart are truthy
			// for a custom range, so they should appear in the URL.

			const pushedPath: string = mockHistoryPush.mock.calls[0][0];

			expect(pushedPath).toContain('rangeEnd=2024-03-01');
			expect(pushedPath).toContain('rangeStart=2024-01-01');
		});

		it('should update the displayed range key to CustomRange after a custom range change', () => {
			renderList();

			fireEvent.click(screen.getByTestId('change-range-custom-btn'));

			expect(screen.getByTestId('current-range-key')).toHaveTextContent(
				RangeKeyTimeRanges.CustomRange
			);
		});
	});

	describe('breadcrumbs', () => {
		it('should build the home breadcrumb using the selected channel name', () => {
			// mockChannelContext() returns selectedChannel = mockChannel(1),
			// whose name is "Channel 1".

			// eslint-disable-next-line @typescript-eslint/no-var-requires
			const breadcrumbs = require('shared/util/breadcrumbs');

			renderList();

			expect(breadcrumbs.getHome).toHaveBeenCalledWith(
				expect.objectContaining({
					channelId: '123',
					groupId: '23',
					label: 'Channel 1'
				})
			);
		});

		it('should pass null label when no channel is selected', () => {
			// eslint-disable-next-line @typescript-eslint/no-var-requires
			const breadcrumbs = require('shared/util/breadcrumbs');

			const contextWithNoChannel = {
				...mockChannelContext(),
				selectedChannel: null
			};

			render(
				<Provider store={store}>
					<ChannelContext.Provider
						value={contextWithNoChannel as any}
					>
						<Router history={buildHistory()}>
							<List />
						</Router>
					</ChannelContext.Provider>
				</Provider>
			);

			expect(breadcrumbs.getHome).toHaveBeenCalledWith(
				expect.objectContaining({
					label: undefined
				})
			);
		});
	});

	describe('FDS remount key', () => {
		it('should reflect the updated rangeKey in component state after change, triggering FDS remount', () => {
			// List passes key={Object.values(rangeSelectors).join()} to FDS.
			// After setRangeSelectors is called the key changes, forcing FDS
			// to remount. We verify via the DropdownRangeKey stub that the
			// state was updated.

			renderList();

			fireEvent.click(screen.getByTestId('change-range-btn'));

			expect(screen.getByTestId('current-range-key')).toHaveTextContent(
				RangeKeyTimeRanges.Last7Days
			);
		});
	});
});
