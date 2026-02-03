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

jest.mock('shared/hooks/useRequest', () => ({
	useRequest: jest.fn(() => ({
		data: {
			items: ['United States', 'Canada']
		},
		error: null,
		loading: false
	}))
}));

describe('Individuals List', () => {
	it('renders', async () => {
		const {container} = render(<IndividualsList />);

		await waitForLoadingToBeRemoved(container);

		expect(container).toMatchSnapshot();
	});
});
