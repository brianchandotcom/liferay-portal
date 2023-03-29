import * as data from 'test/data';
import mockStore from 'test/mock-store';
import React from 'react';
import {BrowserRouter} from 'react-router-dom';
import {cleanup, render} from '@testing-library/react';
import {Provider} from 'react-redux';
import {User} from 'shared/util/records';
import {User as UserRoutes} from '../index';

jest.unmock('react-dom');

const defaultProps = {
	currentUser: data.getImmutableMock(User, data.mockUser),
	groupId: '23'
};

const DefaultComponent = props => (
	<Provider store={mockStore()}>
		<BrowserRouter>
			<UserRoutes {...defaultProps} {...props} />
		</BrowserRouter>
	</Provider>
);

describe('UserRoutes', () => {
	afterEach(cleanup);

	it('should render', () => {
		const {container} = render(
			<DefaultComponent location={{pathname: 'foo'}} />
		);

		expect(container).toMatchSnapshot();
	});

	it('if the user is AC Admin, then the tabs for toggling between users and user requests should render', () => {
		const {queryByText} = render(<DefaultComponent />);

		expect(queryByText('Manage Users')).toBeTruthy();
		expect(queryByText('Requests')).toBeTruthy();
	});

	it('if the user is NOT an AC Admin, then the tabs for toggling between users and user requests should NOT render', () => {
		const {queryByText} = render(
			<DefaultComponent
				currentUser={data.getImmutableMock(User, data.mockMemberUser)}
			/>
		);

		expect(queryByText('Manager Users')).toBeNull();
		expect(queryByText('Requests')).toBeNull();
	});
});
