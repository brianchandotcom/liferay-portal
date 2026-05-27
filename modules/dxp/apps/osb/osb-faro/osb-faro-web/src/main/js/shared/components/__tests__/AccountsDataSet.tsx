import AccountsDataSet from '../AccountsDataSet';
import React from 'react';
import {cleanup, render, screen} from '@testing-library/react';
import {LifecycleStages} from 'contacts/pages/account/utils/constants';
import {RangeKeyTimeRanges} from 'shared/util/constants';

const defaultRangeSelectors = {
	rangeEnd: null,
	rangeKey: RangeKeyTimeRanges.Last30Days,
	rangeStart: null
};

jest.unmock('react-dom');

type FakeFilter = {
	id: string;
	preloadedData?: {
		exclude: boolean;
		selectedItems: Array<{label?: string; value: string}>;
	};
};

type FakeCustomDataRenderers = {
	accountActivityStatusRenderer: (props: {
		value: string;
	}) => React.ReactElement | null | false;
	accountNameRenderer: (props: {
		itemData: {id: string | number};
		value: string;
	}) => React.ReactElement;
};

let lastApiURL: string | undefined;
let lastCustomDataRenderers: FakeCustomDataRenderers | undefined;
let lastFilters: FakeFilter[] | undefined;

jest.mock('@liferay/frontend-data-set-web', () => ({
	...jest.requireActual('@liferay/frontend-data-set-web'),
	FrontendDataSet: ({
		apiURL,
		customDataRenderers,
		filters,
		id
	}: {
		apiURL: string;
		customDataRenderers: FakeCustomDataRenderers;
		filters: FakeFilter[];
		id: string;
	}) => {
		lastApiURL = apiURL;
		lastCustomDataRenderers = customDataRenderers;
		lastFilters = filters;

		return <div data-testid='fds-component' id={id} />;
	}
}));

describe('AccountsDataSet', () => {
	beforeEach(() => {
		jest.clearAllMocks();
		lastApiURL = undefined;
		lastCustomDataRenderers = undefined;
		lastFilters = undefined;
	});

	afterEach(cleanup);

	it('should render the FrontendDataSet with id "accounts-list-dataset"', () => {
		render(
			<AccountsDataSet
				apiURL='fake-url'
				channelId='123'
				groupId='23'
				rangeSelectors={defaultRangeSelectors}
			/>
		);

		expect(screen.getByTestId('fds-component')).toHaveAttribute(
			'id',
			'accounts-list-dataset'
		);
	});

	it('should leave activityStatus/country/industry/lifecycleStatus filters without preloadedData when no props are passed', () => {
		render(
			<AccountsDataSet
				apiURL='fake-url'
				channelId='123'
				groupId='23'
				rangeSelectors={defaultRangeSelectors}
			/>
		);

		const activityStatusFilter = lastFilters?.find(
			f => f.id === 'activityStatus'
		);
		const countryFilter = lastFilters?.find(f => f.id === 'country');
		const industryFilter = lastFilters?.find(f => f.id === 'industry');
		const lifecycleStatusFilter = lastFilters?.find(
			f => f.id === 'lifecycleStatus'
		);

		expect(activityStatusFilter?.preloadedData).toBeUndefined();
		expect(countryFilter?.preloadedData).toBeUndefined();
		expect(industryFilter?.preloadedData).toBeUndefined();
		expect(lifecycleStatusFilter?.preloadedData).toBeUndefined();
	});

	it('should preload the activityStatus filter when activityStatusFilter prop is provided', () => {
		render(
			<AccountsDataSet
				activityStatusFilter='ACTIVE'
				apiURL='fake-url'
				channelId='123'
				groupId='23'
				rangeSelectors={defaultRangeSelectors}
			/>
		);

		const activityStatusFilter = lastFilters?.find(
			f => f.id === 'activityStatus'
		);

		expect(activityStatusFilter?.preloadedData).toEqual({
			exclude: false,
			selectedItems: [{label: 'Active', value: 'ACTIVE'}]
		});
	});

	it('should preload the country filter when countryFilter prop is provided', () => {
		render(
			<AccountsDataSet
				apiURL='fake-url'
				channelId='123'
				countryFilter='US'
				groupId='23'
				rangeSelectors={defaultRangeSelectors}
			/>
		);

		const countryFilter = lastFilters?.find(f => f.id === 'country');

		expect(countryFilter?.preloadedData).toEqual({
			exclude: false,
			selectedItems: [{label: 'US', value: 'US'}]
		});
	});

	it('should preload the industry filter when industryFilter prop is provided', () => {
		render(
			<AccountsDataSet
				apiURL='fake-url'
				channelId='123'
				groupId='23'
				industryFilter='Tech'
				rangeSelectors={defaultRangeSelectors}
			/>
		);

		const industryFilter = lastFilters?.find(f => f.id === 'industry');

		expect(industryFilter?.preloadedData).toEqual({
			exclude: false,
			selectedItems: [{label: 'Tech', value: 'Tech'}]
		});
	});

	it('should preload the lifecycleStatus filter when lifecycleStageFilter prop is provided', () => {
		render(
			<AccountsDataSet
				apiURL='fake-url'
				channelId='123'
				groupId='23'
				lifecycleStageFilter={LifecycleStages.AT_RISK}
				rangeSelectors={defaultRangeSelectors}
			/>
		);

		const lifecycleStatusFilter = lastFilters?.find(
			f => f.id === 'lifecycleStatus'
		);

		expect(lifecycleStatusFilter?.preloadedData).toEqual({
			exclude: false,
			selectedItems: [{label: 'At Risk', value: 'AT_RISK'}]
		});
	});

	it('should render the account name link with channelId in the href', () => {
		render(
			<AccountsDataSet
				apiURL='fake-url'
				channelId='123'
				groupId='23'
				rangeSelectors={defaultRangeSelectors}
			/>
		);

		const {container} = render(
			lastCustomDataRenderers!.accountNameRenderer({
				itemData: {id: 'abc'},
				value: 'Acme Corp'
			})
		);

		expect(container.querySelector('a')).toHaveAttribute(
			'href',
			'/workspace/23/123/contacts/accounts/abc'
		);
	});

	it('should append rangeKey as query param when a preset range is provided', () => {
		render(
			<AccountsDataSet
				apiURL='fake-url'
				channelId='123'
				groupId='23'
				rangeSelectors={{
					rangeEnd: null,
					rangeKey: RangeKeyTimeRanges.Last30Days,
					rangeStart: null
				}}
			/>
		);

		expect(lastApiURL).toBe('fake-url&rangeKey=30');
	});

	it('should append rangeStart and rangeEnd as query params when a custom range is provided', () => {
		render(
			<AccountsDataSet
				apiURL='fake-url'
				channelId='123'
				groupId='23'
				rangeSelectors={{
					rangeEnd: '2024-01-31',
					rangeKey: RangeKeyTimeRanges.CustomRange,
					rangeStart: '2024-01-01'
				}}
			/>
		);

		expect(lastApiURL).toBe(
			'fake-url&rangeKey=CUSTOM&rangeEnd=2024-01-31&rangeStart=2024-01-01'
		);
	});

	it('should render "Active" label for ACTIVE activity status', () => {
		render(
			<AccountsDataSet
				apiURL='fake-url'
				channelId='123'
				groupId='23'
				rangeSelectors={defaultRangeSelectors}
			/>
		);

		const {getByText} = render(
			lastCustomDataRenderers!.accountActivityStatusRenderer({
				value: 'ACTIVE'
			}) as React.ReactElement
		);

		expect(getByText('Active')).toBeInTheDocument();
	});

	it('should render "Inactive" label for INACTIVE activity status', () => {
		render(
			<AccountsDataSet
				apiURL='fake-url'
				channelId='123'
				groupId='23'
				rangeSelectors={defaultRangeSelectors}
			/>
		);

		const {getByText} = render(
			lastCustomDataRenderers!.accountActivityStatusRenderer({
				value: 'INACTIVE'
			}) as React.ReactElement
		);

		expect(getByText('Inactive')).toBeInTheDocument();
	});

	it('should render nothing when activity status value is empty', () => {
		render(
			<AccountsDataSet
				apiURL='fake-url'
				channelId='123'
				groupId='23'
				rangeSelectors={defaultRangeSelectors}
			/>
		);

		const result = lastCustomDataRenderers!.accountActivityStatusRenderer({
			value: ''
		});

		expect(result).toBeFalsy();
	});
});
