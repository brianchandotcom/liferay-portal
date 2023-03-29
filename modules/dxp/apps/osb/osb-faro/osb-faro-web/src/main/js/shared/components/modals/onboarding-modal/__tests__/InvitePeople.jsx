import InvitePeople from '../InvitePeople';
import mockStore from 'test/mock-store';
import React from 'react';
import {cleanup, fireEvent, render} from '@testing-library/react';
import {noop} from 'lodash';
import {Provider} from 'react-redux';

jest.unmock('react-dom');

const inviteSentMessage =
	'You can see the new members invitation status and role permissions under user management in settings.';

describe('InvitePeople', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(
			<Provider store={mockStore()}>
				<InvitePeople onClose={noop} onNext={noop} />
			</Provider>
		);

		expect(container).toMatchSnapshot();
	});

	it('renders the connected state when invitations have been sent', () => {
		const {queryByPlaceholderText, queryByText} = render(
			<Provider store={mockStore()}>
				<InvitePeople dxpConnected onClose={noop} onNext={noop} />
			</Provider>
		);

		expect(queryByText(inviteSentMessage)).toBeNull();

		fireEvent.change(queryByPlaceholderText('Enter Email Address'), {
			target: {
				value: 'test@liferay.com'
			}
		});

		fireEvent.click(queryByText('Send Invitations'));

		jest.runAllTimers();

		expect(queryByText(inviteSentMessage)).not.toBeNull();
		expect(queryByText('Next')).not.toBeNull();
	});

	it('renders the "Done" button invitations have been sent without connecting dxp', () => {
		const {queryByPlaceholderText, queryByText} = render(
			<Provider store={mockStore()}>
				<InvitePeople
					dxpConnected={false}
					onClose={noop}
					onNext={noop}
				/>
			</Provider>
		);

		expect(queryByText(inviteSentMessage)).toBeNull();

		fireEvent.change(queryByPlaceholderText('Enter Email Address'), {
			target: {
				value: 'test@liferay.com'
			}
		});

		fireEvent.click(queryByText('Send Invitations'));

		jest.runAllTimers();

		expect(queryByText(inviteSentMessage)).not.toBeNull();
		expect(queryByText('Done')).not.toBeNull();
	});
});
