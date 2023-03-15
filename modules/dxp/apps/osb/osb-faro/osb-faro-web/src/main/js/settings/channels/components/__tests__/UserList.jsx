import 'test/mock-modal';

import mockStore from 'test/mock-store';
import React from 'react';
import UserList from '../UserList';
import {cleanup, fireEvent, render} from '@testing-library/react';
import {open} from 'shared/actions/modals';
import {Provider} from 'react-redux';
import {StaticRouter} from 'react-router';

jest.unmock('react-dom');

const defaultProps = {
	authorized: true,
	groupId: '23'
};

const DefaultComponent = props => (
	<Provider store={mockStore()}>
		<StaticRouter>
			<UserList {...defaultProps} {...props} />
		</StaticRouter>
	</Provider>
);

describe('ChannelUserList', () => {
	afterEach(cleanup);

	it('should render', () => {
		const {container} = render(<DefaultComponent />);

		jest.runAllTimers();

		expect(container).toMatchSnapshot();
	});

	it('should render without checkboxes if user is not an AC admin', () => {
		const {container, queryByText} = render(
			<DefaultComponent authorized={false} />
		);

		jest.runAllTimers();

		expect(container.querySelector('input[type=checkbox]')).toBeNull();
		expect(queryByText('Add User')).toBeNull();
	});

	it('should open a modal to add users', () => {
		const {queryByText} = render(<DefaultComponent />);

		jest.runAllTimers();

		fireEvent.click(queryByText('Add User'));

		expect(open).toBeCalled();
	});

	it('should open a modal to remove users', () => {
		const {queryByTestId} = render(<DefaultComponent />);

		jest.runAllTimers();

		fireEvent.click(queryByTestId('delete-user'));

		expect(open).toBeCalled();
	});
});
