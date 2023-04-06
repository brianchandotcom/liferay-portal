import client from 'shared/apollo/client';
import CommerceTotalOrderValueQuery, {
	CommerceTotalOrderValueData
} from 'commerce/queries/TotalOrderValueQuery';
import React from 'react';
import {ApolloProvider} from '@apollo/react-hooks';
import {cleanup, render} from '@testing-library/react';
import {CommerceMetricCard} from 'commerce/components/CommerceMetricCard';
import {
	mockCommerceTotalOrderValueReq,
	mockTimeRangeReq
} from 'test/graphql-data';
import {MockedProvider} from '@apollo/react-testing';
import {mockUser} from 'test/data';
import {RangeKeyTimeRanges} from 'shared/util/constants';
import {StaticRouter} from 'react-router-dom';
import {User} from 'shared/util/records';
import {waitForLoadingToBeRemoved} from 'test/helpers';

jest.unmock('react-dom');

jest.mock('react-router-dom', () => ({
	...jest.requireActual('react-router-dom'),
	useParams: () => ({
		channelId: '123',
		query: {
			rangeKey: RangeKeyTimeRanges.Last30Days
		}
	})
}));

const COMMERCE_TOTAL_ORDER_VALUE = '10000000';
const COMMERCE_TREND_PERCENTAGE = 50;

const getData = ({
	classification = 'POSITIVE',
	currencyCode = 'USD',
	percentage = COMMERCE_TREND_PERCENTAGE
}) => ({
	orderTotalCurrencyValues: [
		{
			__typename: 'orderTotalCurrencyValues',
			currencyCode,
			trend: {
				__typename: 'orderTotalCurrencyValuesTrend',
				percentage,
				trendClassification: classification
			},
			value: COMMERCE_TOTAL_ORDER_VALUE
		}
	]
});

const variables = {
	channelId: '123',
	rangeEnd: null,
	rangeKey: 30,
	rangeStart: null
};

const WrappedComponent = ({
	data,
	defaultLanguageId = 'en_US'
}: {
	data?: any;
	defaultLanguageId?: string;
}) => (
	<ApolloProvider client={client}>
		<StaticRouter>
			<MockedProvider
				mocks={[
					mockTimeRangeReq(),
					mockCommerceTotalOrderValueReq({
						data,
						Query: CommerceTotalOrderValueQuery,
						variables
					})
				]}
			>
				<CommerceMetricCard<CommerceTotalOrderValueData>
					currentUser={
						new User(mockUser(1, {languageId: defaultLanguageId}))
					}
					description='this is the description'
					emptyTitle='There are no orders on the selected period.'
					label='this is the label'
					mapper={result => result?.orderTotalCurrencyValues}
					Query={CommerceTotalOrderValueQuery}
				/>
			</MockedProvider>
		</StaticRouter>
	</ApolloProvider>
);

describe('CommerceMetricCard', () => {
	afterEach(cleanup);

	it('should render', async () => {
		const {container, getByText} = render(
			<WrappedComponent data={getData({})} />
		);

		await waitForLoadingToBeRemoved(container);

		const dropdownRangeSelector = document.querySelector(
			'.dropdown-range-key-menu-root'
		);

		expect(getByText('this is the description')).toBeInTheDocument();
		expect(getByText('this is the label')).toBeInTheDocument();
		expect(dropdownRangeSelector).toBeInTheDocument();
		expect(getByText('$10,000,000.00')).toBeInTheDocument();
		expect(getByText(`${COMMERCE_TREND_PERCENTAGE}%`)).toBeInTheDocument();
		expect(container).toMatchSnapshot();
	});

	it('should render with empty state message', async () => {
		const {container, getByText} = render(<WrappedComponent data={[]} />);

		await waitForLoadingToBeRemoved(container);

		expect(
			getByText('There are no orders on the selected period.')
		).toBeInTheDocument();
		expect(
			getByText(
				'Check back later to verify if data has been received from your data sources.'
			)
		).toBeInTheDocument();
	});
});

describe('CommerceMetricCard Classifications', () => {
	afterEach(cleanup);

	it('should render with POSITIVE classification', async () => {
		const {container} = render(
			<WrappedComponent data={getData({classification: 'POSITIVE'})} />
		);

		await waitForLoadingToBeRemoved(container);

		const trendElement = container.querySelector('.analytics-trend');
		expect(window.getComputedStyle(trendElement).color).toEqual(
			'rgb(40, 125, 60)'
		);
		expect(
			trendElement.querySelector('.lexicon-icon-caret-top-l')
		).toBeInTheDocument();
	});

	it('should render with NEGATIVE classification', async () => {
		const {container} = render(
			<WrappedComponent data={getData({classification: 'NEGATIVE'})} />
		);

		await waitForLoadingToBeRemoved(container);

		const trendElement = container.querySelector('.analytics-trend');
		expect(window.getComputedStyle(trendElement).color).toEqual(
			'rgb(218, 20, 20)'
		);
		expect(
			trendElement.querySelector('.lexicon-icon-caret-top-l')
		).toBeInTheDocument();
	});

	it('should render with NEUTRAL classification', async () => {
		const {container} = render(
			<WrappedComponent data={getData({classification: 'NEUTRAL'})} />
		);

		await waitForLoadingToBeRemoved(container);

		const trendElement = container.querySelector('.analytics-trend');
		expect(window.getComputedStyle(trendElement).color).toEqual(
			'rgb(174, 176, 187)'
		);
		expect(
			trendElement.querySelector('.lexicon-icon-caret-top-l')
		).toBeInTheDocument();
	});
});

describe('CommerceMetricCard Trend', () => {
	afterEach(cleanup);

	it('should render with POSITIVE trend', async () => {
		const {container} = render(
			<WrappedComponent
				data={getData({classification: 'POSITIVE', percentage: 50})}
			/>
		);

		await waitForLoadingToBeRemoved(container);

		const trendElement = container.querySelector('.analytics-trend');
		expect(
			trendElement.querySelector('.lexicon-icon-caret-top-l')
		).toBeInTheDocument();
	});

	it('should render with NEGATIVE trend', async () => {
		const {container} = render(
			<WrappedComponent
				data={getData({classification: 'NEGATIVE', percentage: -50})}
			/>
		);

		await waitForLoadingToBeRemoved(container);

		const trendElement = container.querySelector('.analytics-trend');
		expect(
			trendElement.querySelector('.lexicon-icon-caret-bottom-l')
		).toBeInTheDocument();
	});

	it('should render with NEUTRAL trend', async () => {
		const {container} = render(
			<WrappedComponent
				data={getData({classification: 'NEUTRAL', percentage: 0})}
			/>
		);

		await waitForLoadingToBeRemoved(container);

		const trendElement = container.querySelector('.analytics-trend');
		expect(
			trendElement.querySelector('.lexicon-icon-caret-top-l')
		).not.toBeInTheDocument();
		expect(
			trendElement.querySelector('.lexicon-icon-caret-bottom-l')
		).not.toBeInTheDocument();
	});
});

describe('CommerceMetricCard Format Currency', () => {
	afterEach(cleanup);

	it('should format currency and display it in BRL', async () => {
		const {container} = render(
			<WrappedComponent
				data={getData({currencyCode: 'BRL'})}
				defaultLanguageId='pt_BR'
			/>
		);

		await waitForLoadingToBeRemoved(container);

		const currencyValue = document.querySelector('.commerce-card-currency');

		expect(currencyValue.textContent.includes('R$')).toBeTruthy();
	});

	it('should format currency and display it in USD', async () => {
		const {container} = render(
			<WrappedComponent
				data={getData({currencyCode: 'USD'})}
				defaultLanguageId='en_US'
			/>
		);

		await waitForLoadingToBeRemoved(container);

		const currencyValue = document.querySelector('.commerce-card-currency');

		expect(currencyValue.textContent.includes('$')).toBeTruthy();
	});

	it('should format currency and display it in EUR', async () => {
		const {container} = render(
			<WrappedComponent
				data={getData({currencyCode: 'EUR'})}
				defaultLanguageId='es-ES'
			/>
		);

		await waitForLoadingToBeRemoved(container);

		const currencyValue = document.querySelector('.commerce-card-currency');

		expect(currencyValue.textContent.includes('€')).toBeTruthy();
	});
});
