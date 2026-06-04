import * as API from 'shared/api';

import IndividualsList from '../IndividualsList';
import React from 'react';
import {createMemoryHistory} from 'history';
import {render} from '@testing-library/react';
import {Router} from 'react-router';
import {waitForLoadingToBeRemoved} from 'test/helpers';

jest.unmock('react-dom');

jest.mock('react-router-dom', () => ({
	...jest.requireActual('react-router-dom'),
	useParams: () => ({
		channelId: '123',
		groupId: '23'
	})
}));

describe('Individuals List', () => {
	beforeEach(() => {
		// @ts-ignore
		API.individuals.fetchFieldValues.mockReturnValue(
			Promise.resolve({items: ['United States', 'Canada']})
		);
	});

	it('renders', async () => {
		// @ts-ignore
		API.individuals.search.mockReturnValue(
			Promise.resolve({
				items: [
					{
						accountName: 'Liferay Inc.',
						activitiesCount: 8,
						dateCreated: 1769697362927,
						firstActivityDate: 1769697128235,
						id: '47ff64395860b1d498241d907069f649b98c198a95b3ba5303b87094058590c1',
						lastActivityDate: 1769697160365,
						name: 'Test Test',
						profileType: 'KNOWN',
						properties: {
							country: 'United States',
							email: 'test@liferay.com'
						}
					},
					{
						accountName: 'Liferay',
						activitiesCount: 8,
						dateCreated: 1769697362927,
						firstActivityDate: 1769697128235,
						id: '47ff64395860b1d498241d907069f649b98c198a95b3ba5303b87094058590c3',
						lastActivityDate: 1769697160365,
						name: 'John Doe',
						profileType: 'KNOWN',
						properties: {
							country: 'Canada',
							email: 'john.doe@liferay.com'
						}
					},
					{
						activitiesCount: 3,
						dateCreated: 1769697362927,
						firstActivityDate: 1769697128235,
						id: '47ff64395860b1d498241d907069f649b98c198a95b3ba5303b87094058590c2',
						lastActivityDate: 1769697160365,
						name: 'AC-79742349',
						profileType: 'ANONYMOUS',
						properties: {}
					}
				],
				total: 3
			})
		);

		const history = createMemoryHistory();

		const {getByText} = render(
			<Router history={history}>
				<IndividualsList />
			</Router>
		);

		await waitForLoadingToBeRemoved(document.body);

		expect(getByText('Individual Profiles')).toBeInTheDocument();
		expect(getByText('Test Test')).toBeInTheDocument();
	});

	it('renders empty state when no individuals are synced', async () => {
		// @ts-ignore
		API.individuals.search.mockReturnValue(
			Promise.resolve({items: [], total: 0})
		);

		const history = createMemoryHistory();

		const {getByText} = render(
			<Router history={history}>
				<IndividualsList />
			</Router>
		);

		await waitForLoadingToBeRemoved(document.body);

		expect(
			getByText('No Individuals Synced from Data Sources')
		).toBeInTheDocument();

		expect(
			getByText('Connect a data source with people data.')
		).toBeInTheDocument();

		expect(
			getByText('Access our documentation to learn more.')
		).toBeInTheDocument();
	});

	it('passes Last 30 Days as the default range key to the search API', async () => {
		// @ts-ignore
		API.individuals.search.mockReturnValue(
			Promise.resolve({items: [], total: 0})
		);

		const history = createMemoryHistory();

		render(
			<Router history={history}>
				<IndividualsList />
			</Router>
		);

		await waitForLoadingToBeRemoved(document.body);

		// @ts-ignore
		expect(API.individuals.search).toHaveBeenCalledWith(
			expect.objectContaining({
				rangeEnd: null,
				rangeKey: 30,
				rangeStart: null
			})
		);
	});
});
