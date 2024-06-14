import BasePage from 'shared/components/base-page';
import client from 'shared/apollo/client';
import MetricBaseCard from '../MetricBaseCard';
import React from 'react';
import {ApolloProvider} from '@apollo/react-hooks';
import {
	BounceRateMetric,
	CompositeMetric,
	Metric,
	SessionDurationMetric,
	SessionsPerVisitorMetric,
	VisitorsMetric
} from '../metrics';
import {fireEvent, render} from '@testing-library/react';
import {getSiteMetricsChartData} from 'shared/components/metric-card/util';
import {MockedProvider} from '@apollo/react-testing';
import {
	mockPreferenceReq,
	mockSitesMetricReq,
	mockSitesTabsReq
} from 'test/graphql-data';
import {
	RangeKeyTimeRanges,
	SEVEN_MONTHS,
	THIRTEEN_MONTHS
} from 'shared/util/constants';
import {SitesMetricQuery, SitesTabsQuery} from '../queries';
import {useLocation} from 'react-router-dom';
import {waitForLoadingToBeRemoved} from 'test/helpers';

jest.unmock('react-dom');

jest.mock('react-router-dom', () => ({
	...jest.requireActual('react-router-dom'),
	useLocation: jest.fn(),
	useParams: () => ({
		channelId: '456',
		groupId: '2000',
		query: {
			rangeKey: RangeKeyTimeRanges.Last30Days
		}
	})
}));

const metrics: Metric[] = [
	CompositeMetric,
	SessionsPerVisitorMetric,
	SessionDurationMetric,
	BounceRateMetric
];

const TOOLTIP_PAYLOAD = [
	{
		color: '#4B9BFF',
		dataKey: 'data_1',
		fill: '#4B9BFF',
		fillOpacity: 1,
		name: 'Known Visitors',
		payload: {
			data_1: 0,
			data_2: 0,
			data_previous: 0,
			date: 1705604400000,
			dateString: '7 PM'
		},
		value: 0
	},
	{
		color: '#FFB46E',
		dataKey: 'data_2',
		fill: '#FFB46E',
		fillOpacity: 1,
		name: 'Anonymous Visitors',
		payload: {
			data_1: 0,
			data_2: 0,
			data_previous: 0,
			date: 1705604400000,
			dateString: '7 PM'
		},
		value: 0
	}
];

/**
 * Override Recharts Responsive Container
 * width dimensions fixed to be able to render charts
 */

jest.mock('recharts', () => {
	const OriginalModule = jest.requireActual('recharts');

	return {
		...OriginalModule,
		ResponsiveContainer: ({children}) => (
			<OriginalModule.ResponsiveContainer height={350} width={800}>
				{children}
			</OriginalModule.ResponsiveContainer>
		),
		Tooltip: ({children, ...props}) => (
			<OriginalModule.Tooltip {...props} active payload={TOOLTIP_PAYLOAD}>
				{children}
			</OriginalModule.Tooltip>
		)
	};
});

const WrapperComponent = ({
	children,
	rangeKey = '30' as RangeKeyTimeRanges,
	retentionPeriodTimestamp
}) => (
	<ApolloProvider client={client}>
		<MockedProvider
			mocks={[
				mockSitesTabsReq({rangeKey}),
				mockSitesMetricReq(VisitorsMetric.name, {rangeKey}),
				mockPreferenceReq(retentionPeriodTimestamp)
			]}
		>
			<BasePage.Context.Provider
				value={{
					filters: {},
					router: {
						params: {},
						query: {
							rangeKey
						}
					}
				}}
			>
				{children}
			</BasePage.Context.Provider>
		</MockedProvider>
	</ApolloProvider>
);

describe('MetricBaseCard', () => {
	it('renders component', async () => {
		useLocation.mockReturnValue({
			search: `?rangeKey=${RangeKeyTimeRanges.Last30Days}`
		});

		const {container} = render(
			<WrapperComponent retentionPeriodTimestamp={THIRTEEN_MONTHS}>
				<MetricBaseCard
					chartDataMapFn={getSiteMetricsChartData}
					label={Liferay.Language.get('visitors-behavior')}
					metrics={metrics}
					queries={{
						MetricQuery: SitesMetricQuery,
						name: 'site',
						TabsQuery: SitesTabsQuery
					}}
					variables={() => ({
						channelId: '456',
						devices: 'Any',
						interval: 'D',
						location: 'Any',
						rangeEnd: '',
						rangeKey: RangeKeyTimeRanges.Last30Days,
						rangeStart: ''
					})}
				/>
			</WrapperComponent>
		);

		await waitForLoadingToBeRemoved(container);

		expect(container).toMatchSnapshot();
	});

	it('renders tooltip with retention period for 7 months', async () => {
		useLocation.mockReturnValue({
			search: `?rangeKey=${RangeKeyTimeRanges.Last180Days}`
		});

		const {container, getByRole, getByText} = render(
			<WrapperComponent
				rangeKey={RangeKeyTimeRanges.Last180Days}
				retentionPeriodTimestamp={SEVEN_MONTHS}
			>
				<MetricBaseCard
					chartDataMapFn={getSiteMetricsChartData}
					label={Liferay.Language.get('visitors-behavior')}
					metrics={metrics}
					queries={{
						MetricQuery: SitesMetricQuery,
						name: 'site',
						TabsQuery: SitesTabsQuery
					}}
					variables={() => ({
						channelId: '456',
						devices: 'Any',
						interval: 'D',
						location: 'Any',
						rangeEnd: '',
						rangeKey: RangeKeyTimeRanges.Last180Days,
						rangeStart: ''
					})}
				/>
			</WrapperComponent>
		);

		await waitForLoadingToBeRemoved(container);

		fireEvent.click(
			getByRole('checkbox', {
				name: /compare to previous/i
			})
		);

		expect(
			getByText(
				"There is no data available for dates prior to 7 months due to your workspace's data retention period."
			)
		);
	});

	it('renders tooltip with retention period for 13 months', async () => {
		useLocation.mockReturnValue({
			search: `?rangeKey=${RangeKeyTimeRanges.Last180Days}`
		});

		const {container, getByRole, getByText} = render(
			<WrapperComponent
				rangeKey={RangeKeyTimeRanges.Last180Days}
				retentionPeriodTimestamp={SEVEN_MONTHS}
			>
				<MetricBaseCard
					chartDataMapFn={getSiteMetricsChartData}
					label={Liferay.Language.get('visitors-behavior')}
					metrics={metrics}
					queries={{
						MetricQuery: SitesMetricQuery,
						name: 'site',
						TabsQuery: SitesTabsQuery
					}}
					variables={() => ({
						channelId: '456',
						devices: 'Any',
						interval: 'D',
						location: 'Any',
						rangeEnd: '',
						rangeKey: RangeKeyTimeRanges.Last180Days,
						rangeStart: ''
					})}
				/>
			</WrapperComponent>
		);

		await waitForLoadingToBeRemoved(container);

		fireEvent.click(
			getByRole('checkbox', {
				name: /compare to previous/i
			})
		);

		expect(
			getByText(
				"There is no data available for dates prior to 7 months due to your workspace's data retention period."
			)
		);
	});
});
