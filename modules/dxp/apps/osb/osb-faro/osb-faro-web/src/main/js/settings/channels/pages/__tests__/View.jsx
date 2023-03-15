import * as API from 'shared/api';
import * as data from 'test/data';
import mockStore from 'test/mock-store';
import ModalRenderer from 'shared/components/ModalRenderer';
import Promise from 'metal-promise';
import React from 'react';
import View from '../View';
import {
	cleanup,
	fireEvent,
	getByText,
	queryByText,
	render
} from '@testing-library/react';
import {Provider} from 'react-redux';
import {StaticRouter} from 'react-router';
import {User} from 'shared/util/records';

jest.unmock('react-dom');

const defaultProps = {
	currentUser: new User(data.mockUser()),
	groupId: '23'
};

const DefaultComponent = props => {
	API.preferences.fetchEmailReport = jest.fn(() => Promise.resolve());

	return (
		<Provider store={mockStore()}>
			<ModalRenderer />

			<StaticRouter>
				<View {...defaultProps} {...props} />
			</StaticRouter>
		</Provider>
	);
};

describe('View Channel', () => {
	afterEach(() => {
		cleanup();

		jest.clearAllMocks();
	});

	it('should render', () => {
		const {container} = render(<DefaultComponent />);

		jest.runAllTimers();

		expect(container).toMatchSnapshot();
	});

	it('should render w/ Select Users view', () => {
		API.channels.fetch.mockReturnValueOnce(
			Promise.resolve(data.mockChannel(1, 1))
		);

		const {container, getByLabelText} = render(<DefaultComponent />);

		jest.runAllTimers();

		expect(getByLabelText('Select Users').checked).toBeTrue();
		expect(container.querySelector('.table-root')).toBeTruthy();
	});

	it('should not edit or delete property when the user is not an admin', () => {
		API.user.fetchCurrentUser.mockReturnValueOnce(
			Promise.resolve(data.mockMemberUser())
		);

		const {queryByLabelText, queryByText} = render(<DefaultComponent />);

		jest.runAllTimers();

		expect(queryByText('Delete')).toBeNull();
		expect(queryByLabelText('Edit')).toBeNull();
	});

	it('should render a warning modal when the user toggles from All User to Select User property permissions', () => {
		const {container, getByLabelText} = render(<DefaultComponent />);
		const modalContainer = container.querySelector('.modal-renderer-root');
		const customMatcher = content => content === 'Permissions Change';

		jest.runAllTimers();

		expect(queryByText(modalContainer, customMatcher)).toBeNull();

		fireEvent.click(getByLabelText('Select Users'));

		jest.runAllTimers();

		expect(getByText(modalContainer, customMatcher)).toBeTruthy();
	});
});
