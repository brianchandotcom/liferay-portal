import mockStore from 'test/mock-store';
import React from 'react';
import {BrowserRouter} from 'react-router-dom';
import {Provider} from 'react-redux';
import {render} from '@testing-library/react';

jest.unmock('react-dom');

const mockedIndividualMetrics = {
	data: {
		individualMetric: {
			__typename: 'IndividualMetric',

			anonymousIndividualsMetric: {
				__typename: 'Metric',
				trend: {
					__typename: 'Trend',
					percentage: 11.123,
					trendClassification: 'POSITIVE'
				},
				value: 5123
			},
			knownIndividualsMetric: {
				__typename: 'Metric',
				trend: {
					__typename: 'Trend',
					percentage: 28.12,
					trendClassification: 'NEGATIVE'
				},
				value: 1103
			},
			totalIndividualsMetric: {
				__typename: 'Metric',
				trend: {
					__typename: 'Trend',
					percentage: 30.23,
					trendClassification: 'POSITIVE'
				},
				value: 2120
			}
		}
	}
};

describe('IndividualsOverviewCDP', () => {
	afterEach(() => {
		jest.resetAllMocks();
		jest.resetModules();
	});

	it('should render Individuals Metrics Cards', async () => {
		jest.doMock('@apollo/react-hooks', () => ({
			useQuery: jest.fn(() => ({
				data: mockedIndividualMetrics.data,
				loading: false
			}))
		}));

		jest.doMock('shared/hooks/useRequest', () => ({
			useRequest: jest.fn(() => ({
				data: {total: 1},
				loading: false
			}))
		}));

		jest.doMock('shared/hooks/useCurrentUser', () => ({
			useCurrentUser: jest.fn(() => ({isAdmin: () => true}))
		}));

		jest.doMock('shared/hooks/useDataSource', () => ({
			useDataSource: jest.fn(() => ({empty: false}))
		}));

		jest.doMock('react-router-dom', () => ({
			...jest.requireActual('react-router-dom'),
			useParams: () => ({
				channelId: '123',
				groupId: '456'
			})
		}));

		const {default: IndividualsOverviewCDP} = await import(
			'../IndividualsOverviewCDP'
		);

		const {container} = render(
			<Provider store={mockStore()}>
				<BrowserRouter>
					<IndividualsOverviewCDP />
				</BrowserRouter>
			</Provider>
		);

		expect(container).toMatchSnapshot();
	});
});
