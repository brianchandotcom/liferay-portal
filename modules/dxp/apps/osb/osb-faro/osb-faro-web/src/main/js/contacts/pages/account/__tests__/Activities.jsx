import * as API from 'shared/api';
import * as data from 'test/data';
import Activities from '../Activities';
import mockStore from 'test/mock-store';
import Promise from 'metal-promise';
import React from 'react';
import {Account} from 'shared/util/records';
import {MemoryRouter, Route} from 'react-router-dom';
import {Provider} from 'react-redux';
import {render} from '@testing-library/react';
import {Routes} from 'shared/util/router';

jest.unmock('react-dom');

const DefaultComponent = props => (
	<MemoryRouter
		initialEntries={[
			'/workspace/23/321321/contacts/accounts/321321/activities'
		]}
	>
		<Route path={Routes.CONTACTS_ACCOUNT_ACTIVITIES}>
			<Provider store={mockStore()}>
				<Activities
					account={data.getImmutableMock(Account, data.mockAccount)}
					channelId='123123'
					groupId='23'
					interval='D'
					rangeSelectors={{rangeKey: 30}}
					{...props}
				/>
			</Provider>
		</Route>
	</MemoryRouter>
);

describe('Activities', () => {
	it('should render', async () => {
		const {container} = render(<DefaultComponent />);

		jest.runAllTimers();

		expect(container).toMatchSnapshot();
	});

	it('should render with error display', async () => {
		API.activities.fetchHistory.mockReturnValueOnce(Promise.reject({}));

		const {getByText} = render(<DefaultComponent pageDisplay />);

		jest.runAllTimers();

		expect(getByText('Page Not Found')).toBeTruthy();
	});
});
