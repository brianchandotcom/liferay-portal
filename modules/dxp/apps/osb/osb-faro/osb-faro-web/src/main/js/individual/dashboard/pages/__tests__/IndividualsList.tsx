import * as API from 'shared/api';

import IndividualsList from '../IndividualsList';
import React from 'react';
import {render} from '@testing-library/react';
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
	// @ts-ignore
	API.individuals.fetchFieldValues.mockReturnValue(
		Promise.resolve({items: ['United States', 'Canada']})
	);

	// @ts-ignore
	API.individuals.search.mockReturnValue(
		Promise.resolve({
			items: [
				{
					accountName: 'Liferay Inc.',
					activitiesCount: 8,
					dateCreated: 1769697362927,
					firstActivityDate: 1769697128235,
					id:
						'47ff64395860b1d498241d907069f649b98c198a95b3ba5303b87094058590c1',
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
					id:
						'47ff64395860b1d498241d907069f649b98c198a95b3ba5303b87094058590c3',
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
					id:
						'47ff64395860b1d498241d907069f649b98c198a95b3ba5303b87094058590c2',
					lastActivityDate: 1769697160365,
					name: 'AC-79742349',
					profileType: 'ANONYMOUS',
					properties: {}
				}
			],
			total: 3
		})
	);

	it('renders', async () => {
		const {container} = render(<IndividualsList />);

		await waitForLoadingToBeRemoved(container);

		expect(container).toMatchSnapshot();
	});
});
