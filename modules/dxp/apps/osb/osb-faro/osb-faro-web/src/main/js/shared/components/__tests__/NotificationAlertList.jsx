import * as API from 'shared/api';
import * as data from 'test/data';
import mockStore from 'test/mock-store';
import NotificationAlertList from '../NotificationAlertList';
import Promise from 'metal-promise';
import React from 'react';
import {fireEvent, render} from '@testing-library/react';
import {Provider} from 'react-redux';
import {range} from 'lodash';
import {StaticRouter} from 'react-router';

jest.unmock('react-dom');

const defaultProps = {
	data: range(1).map(i => data.mockNotification(i)),
	groupId: '23',
	loading: false,
	refetch: () => {}
};

describe('NotificationAlertList', () => {
	API.notifications.fetchNotifications.mockReturnValue(
		Promise.resolve(range(1).map(i => data.mockNotification(i)))
	);

	it('should render', () => {
		const {container} = render(
			<Provider store={mockStore()}>
				<StaticRouter>
					<NotificationAlertList {...defaultProps} />
				</StaticRouter>
			</Provider>
		);

		jest.runAllTimers();

		expect(container).toMatchSnapshot();
	});

	it('should hide notification when click on close button', () => {
		const {container, queryByText} = render(
			<Provider store={mockStore()}>
				<StaticRouter>
					<NotificationAlertList {...defaultProps} />
				</StaticRouter>
			</Provider>
		);

		jest.runAllTimers();

		fireEvent.click(container.querySelector('.close'));

		expect(
			queryByText('Workspace timezone has changed to  as of today')
		).toBeNull();
	});
});
