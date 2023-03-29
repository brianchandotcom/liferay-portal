import * as data from 'test/data';
import mockStore from 'test/mock-store';
import React from 'react';
import UserList from '../UserList';
import {cleanup, render} from '@testing-library/react';
import {MemoryRouter, Route} from 'react-router-dom';
import {Provider} from 'react-redux';
import {Routes} from 'shared/util/router';
import {User} from 'shared/util/records';
import {UserRoleNames} from 'shared/util/constants';

jest.unmock('react-dom');

const defaultProps = {
	currentUser: new User(data.mockUser()),
	groupId: '23'
};

const DefaultComponent = props => (
	<Provider store={mockStore()}>
		<MemoryRouter initialEntries={['/workspace/23/settings/users']}>
			<Route path={Routes.SETTINGS_USERS}>
				<UserList {...defaultProps} {...props} />
			</Route>
		</MemoryRouter>
	</Provider>
);

describe('UserList', () => {
	afterEach(cleanup);

	it('should render', () => {
		const {container} = render(<DefaultComponent />);

		jest.runAllTimers();

		expect(container).toMatchSnapshot();
	});

	it("should render rows as disabled without row actions, invite members button, or checkboxes if the current user's role is member", () => {
		const {container, queryByTestId, queryByText} = render(
			<DefaultComponent
				currentUser={
					new User(data.mockUser(0, {roleName: UserRoleNames.Member}))
				}
			/>
		);

		jest.runAllTimers();

		expect(
			container.querySelector(
				'.table > tbody:nth-of-type(1) > tr.disabled'
			)
		).not.toBeNull();

		expect(queryByTestId('select-all-checkbox')).not.toBeInTheDocument();

		expect(queryByText('Invite Users')).not.toBeInTheDocument();
	});
});
