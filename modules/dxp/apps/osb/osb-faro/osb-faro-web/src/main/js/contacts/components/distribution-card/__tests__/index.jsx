import DistributionCard from '../index';
import mockStore from 'test/mock-store';
import Promise from 'metal-promise';
import React from 'react';
import {BrowserRouter} from 'react-router-dom';
import {cleanup, render} from '@testing-library/react';
import {fetchDistributionTabs} from 'shared/actions/preferences';
import {Provider} from 'react-redux';

jest.mock('shared/actions/preferences');

jest.unmock('react-dom');

describe('DistributionCard', () => {
	afterEach(() => {
		fetchDistributionTabs.mockClear();

		cleanup();
	});

	it('renders', () => {
		fetchDistributionTabs.mockReturnValue(() => Promise.resolve({}));

		const {container} = render(
			<Provider store={mockStore()}>
				<BrowserRouter>
					<DistributionCard
						distributionKey='individualsDashboard'
						fetchDistribution={() => Promise.resolve()}
					/>
				</BrowserRouter>
			</Provider>
		);

		expect(container).toMatchSnapshot();
	});

	it('renders empty state via props with noResultsRenderer', () => {
		fetchDistributionTabs.mockReturnValue(() => Promise.resolve({}));

		const {container} = render(
			<Provider store={mockStore()}>
				<BrowserRouter>
					<DistributionCard
						distributionKey='individualsDashboard'
						fetchDistribution={() => Promise.resolve()}
						noResultsRenderer={() => <div>{'empty state'}</div>}
					/>
				</BrowserRouter>
			</Provider>
		);

		expect(container).toMatchSnapshot();
	});

	it('renders w/ showAddDataSource', () => {
		fetchDistributionTabs.mockReturnValue(() => Promise.resolve({}));

		const {container} = render(
			<Provider store={mockStore()}>
				<BrowserRouter>
					<DistributionCard
						distributionKey='individualsDashboard'
						fetchDistribution={() => Promise.resolve()}
						groupId='123'
						showAddDataSource
					/>
				</BrowserRouter>
			</Provider>
		);

		expect(container).toMatchSnapshot();
	});

	it('renders /w AddPropertyForm', () => {
		fetchDistributionTabs.mockReturnValue(() => Promise.resolve({}));

		const {container} = render(
			<Provider store={mockStore()}>
				<BrowserRouter>
					<DistributionCard
						distributionKey='123'
						fetchDistribution={() => Promise.resolve()}
						id='123'
					/>
				</BrowserRouter>
			</Provider>
		);

		expect(container).toMatchSnapshot();
	});
});
