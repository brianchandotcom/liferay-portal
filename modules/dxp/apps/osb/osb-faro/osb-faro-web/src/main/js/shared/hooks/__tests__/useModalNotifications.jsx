import * as API from 'shared/api';
import * as data from 'test/data';
import mockStore from 'test/mock-store';
import ModalRenderer from 'shared/components/ModalRenderer';
import React from 'react';
import {close, open} from 'shared/actions/modals';
import {connect} from 'react-redux';
import {fireEvent, render, waitFor} from '@testing-library/react';
import {mockGetDateNow} from 'test/mock-date';
import {
	NotificationSubtypes,
	NotificationTypes
} from 'shared/util/records/Notification';
import {Provider} from 'react-redux';
import {range} from 'lodash';
import {useModalNotifications} from 'shared/hooks/useModalNotifications';

jest.unmock('react-dom');

const WrapperComponent = connect(null, {close, open})(({close, open}) => {
	useModalNotifications(close, '23', open);

	return (
		<div>
			<span>{'testing'}</span>
		</div>
	);
});

describe('useModalNotifications', () => {
	it('should open a notification modal', async () => {
		mockGetDateNow(data.getTimestamp(0));

		API.notifications.fetchNotifications.mockReturnValueOnce(
			Promise.resolve(
				range(1).map(i =>
					data.mockNotification(i, {
						subtype: NotificationSubtypes.TimeZoneAdmin,
						type: NotificationTypes.Modal
					})
				)
			)
		);

		const {container} = render(
			<Provider store={mockStore()}>
				<ModalRenderer />
				<WrapperComponent />
			</Provider>
		);

		await waitFor(() => {});

		jest.runAllTimers();

		await waitFor(() => {});

		expect(container).toMatchSnapshot();
	});

	it('should open another notification modal after closing one when having multiple modals', async () => {
		mockGetDateNow(data.getTimestamp(0));

		API.notifications.fetchNotifications.mockReturnValue(
			Promise.resolve(
				range(2).map(i =>
					data.mockNotification(i, {
						subtype: NotificationSubtypes.TimeZoneAdmin,
						type: NotificationTypes.Modal
					})
				)
			)
		);

		const {getByText} = render(
			<Provider store={mockStore()}>
				<ModalRenderer />
				<WrapperComponent />
			</Provider>
		);

		jest.runAllTimers();

		await waitFor(() => {});

		fireEvent.click(getByText('Do This Later'));

		expect(getByText('Set Timezone')).toBeTruthy();
	});
});
