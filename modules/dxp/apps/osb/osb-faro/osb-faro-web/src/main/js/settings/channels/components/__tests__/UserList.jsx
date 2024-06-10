import 'test/mock-modal';

import mockStore from 'test/mock-store';
import React from 'react';
import UserList from '../UserList';
import {cleanup, fireEvent, render} from '@testing-library/react';
import {open} from 'shared/actions/modals';
import {Provider} from 'react-redux';
import {StaticRouter} from 'react-router';
import {waitForLoadingToBeRemoved} from 'test/helpers';

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

	it('should render', async () => {
		const {container} = render(<DefaultComponent />);

		await waitForLoadingToBeRemoved(container);

		expect(container).toMatchSnapshot();
	});

	it('should render without checkboxes if user is not an AC admin', () => {
		const {container, queryByText} = render(
			<DefaultComponent authorized={false} />
		);

		expect(container.querySelector('input[type=checkbox]')).toBeNull();
		expect(queryByText('Add User')).toBeNull();
	});

	it('should open a modal to add users', async () => {
		const {container, queryByText} = render(<DefaultComponent />);

		await waitForLoadingToBeRemoved(container);

		fireEvent.click(queryByText('Add User'));

		expect(open).toBeCalled();
	});

	it('should open a modal to remove users', async () => {
		const {container, queryByTestId} = render(<DefaultComponent />);

		await waitForLoadingToBeRemoved(container);

		fireEvent.click(queryByTestId('delete-user'));

		expect(open).toBeCalled();
	});
});
