import List from '../List';
import mockStore from 'test/mock-store';
import React from 'react';
import {MemoryRouter, Route} from 'react-router-dom';
import {Provider} from 'react-redux';
import {render} from '@testing-library/react';
import {Routes} from 'shared/util/router';
import {waitForLoadingToBeRemoved} from 'test/helpers';

jest.unmock('react-dom');

describe('List', () => {
	it('should render', async () => {
		const {container} = render(
			<Provider store={mockStore()}>
				<MemoryRouter
					initialEntries={['/workspace/23/123/contacts/accounts']}
				>
					<Route path={Routes.CONTACTS_LIST_ACCOUNT}>
						<List channelId='123' groupId='23' />
					</Route>
				</MemoryRouter>
			</Provider>
		);

		jest.runAllTimers();

		await waitForLoadingToBeRemoved(container);

		expect(container).toMatchSnapshot();
	});
});
