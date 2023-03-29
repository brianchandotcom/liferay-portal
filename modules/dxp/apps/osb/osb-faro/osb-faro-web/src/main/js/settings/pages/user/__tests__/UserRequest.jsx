import mockStore from 'test/mock-store';
import React from 'react';
import UserRequest from '../UserRequest';
import {cleanup, render} from '@testing-library/react';
import {MemoryRouter, Route} from 'react-router-dom';
import {noop} from 'lodash';
import {Provider} from 'react-redux';
import {Routes} from 'shared/util/router';

jest.unmock('react-dom');

const DefaultComponent = props => (
	<Provider store={mockStore()}>
		<MemoryRouter
			initialEntries={['/workspace/23/settings/users/requests']}
		>
			<Route path={Routes.SETTINGS_USERS_REQUESTS}>
				<UserRequest {...props} onSetUserRequest={noop} />
			</Route>
		</MemoryRouter>
	</Provider>
);

describe('UserRequest', () => {
	afterEach(cleanup);

	it('should render', () => {
		const {container} = render(<DefaultComponent />);

		jest.runAllTimers();

		expect(container).toMatchSnapshot();
	});
});
