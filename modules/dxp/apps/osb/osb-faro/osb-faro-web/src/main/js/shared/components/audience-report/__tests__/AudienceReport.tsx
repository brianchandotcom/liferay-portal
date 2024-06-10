import AudienceReport from '../AudienceReport';
import client from 'shared/apollo/client';
import React from 'react';
import {ApolloProvider} from '@apollo/react-hooks';
import {cleanup, fireEvent, render} from '@testing-library/react';
import {MetricName} from 'shared/types/MetricName';
import {
	mockAudienceReportReq,
	mockPreferenceReq,
	mockTimeRangeReq
} from 'test/graphql-data';
import {MockedProvider} from '@apollo/react-testing';
import {Name} from '../types';
import {PageAudienceReportQuery} from '../queries';
import {RangeKeyTimeRanges} from 'shared/util/constants';
import {StaticRouter} from 'react-router-dom';
import {waitForLoadingToBeRemoved} from 'test/helpers';

jest.unmock('react-dom');

jest.mock('react-router-dom', () => ({
	...jest.requireActual('react-router-dom'),
	useParams: () => ({
		channelId: '456',
		query: {
			rangeKey: RangeKeyTimeRanges.Last30Days
		},
		title: 'Home Page',
		touchpoint: 'https://www.liferay.com'
	})
}));

/**
 * Override Recharts Responsive Container
 * width dimensions fixed to be able to render charts
 */

const tooltipEnabled = jest.fn();

jest.mock('recharts', () => {
	const OriginalModule = jest.requireActual('recharts');

	return {
		...OriginalModule,
		ResponsiveContainer: ({children}) => (
			<OriginalModule.ResponsiveContainer height={350} width={800}>
				{children}
			</OriginalModule.ResponsiveContainer>
		),
		Tooltip: ({children, ...props}) => {
			if (props.active) {
				tooltipEnabled();
			}

			return (
				<OriginalModule.Tooltip {...props} active>
					{children}
				</OriginalModule.Tooltip>
			);
		}
	};
});

const WrappedComponent = ({queryProps}) => (
	<ApolloProvider client={client}>
		<StaticRouter>
			<MockedProvider
				mocks={[
					mockTimeRangeReq(),
					mockPreferenceReq(),
					mockAudienceReportReq({queryProps})
				]}
			>
				<AudienceReport
					filters={{devices: [], location: []}}
					mapper={result =>
						result?.[queryProps.name]?.[queryProps.metricName]
					}
					name={Name.Page}
					Query={PageAudienceReportQuery(queryProps)}
					rangeSelectors={{
						rangeEnd: '',
						rangeKey: RangeKeyTimeRanges.Last30Days,
						rangeStart: ''
					}}
				/>
			</MockedProvider>
		</StaticRouter>
	</ApolloProvider>
);

describe('CommerceMetricCard', () => {
	afterEach(cleanup);

	it('should render', async () => {
		const {container} = render(
			<WrappedComponent
				queryProps={{
					metricName: MetricName.Views,
					name: Name.Page
				}}
			/>
		);

		await waitForLoadingToBeRemoved(container);

		expect(container).toMatchSnapshot();
	});

	it('should render a tooltip when donuts mouse over', async () => {
		const {container} = render(
			<WrappedComponent
				queryProps={{
					metricName: MetricName.Views,
					name: Name.Page
				}}
			/>
		);

		await waitForLoadingToBeRemoved(container);

		const donut = document.querySelector('.recharts-pie-sector');

		expect(donut).toBeInTheDocument();

		fireEvent.mouseEnter(donut);

		expect(tooltipEnabled).toHaveBeenCalledTimes(1);
	});
});
