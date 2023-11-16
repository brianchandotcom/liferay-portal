import * as API from 'shared/api';
import * as data from 'test/data';
import ChannelList from '../ChannelList';
import mockStore, {mockStoreData} from 'test/mock-store';
import React from 'react';
import {cleanup, render, screen} from '@testing-library/react';
import {MemoryRouter, Route} from 'react-router-dom';
import {Provider} from 'react-redux';
import {RemoteData} from 'shared/util/records';
import {Routes} from 'shared/util/router';
import {waitForLoadingToBeRemoved} from 'test/helpers';

jest.unmock('react-dom');

const defaultProps = {
	groupId: '23'
};

describe('Channels List', () => {
	afterEach(cleanup);

	it('should render', async () => {
		const {container} = render(
			<Provider store={mockStore()}>
				<MemoryRouter
					initialEntries={['/workspace/23/settings/properties']}
				>
					<Route path={Routes.SETTINGS_CHANNELS}>
						<ChannelList {...defaultProps} />
					</Route>
				</MemoryRouter>
			</Provider>
		);

		jest.runAllTimers();

		await waitForLoadingToBeRemoved(container);

		expect(container).toMatchSnapshot();
	});

	it('should not render add button if user is not an admin', () => {
		API.user.fetchCurrentUser.mockReturnValueOnce(
			Promise.resolve(data.mockMemberUser('24'))
		);

		const {queryByText} = render(
			<Provider
				store={mockStore(
					mockStoreData.setIn(
						['currentUser'],
						new RemoteData({data: '24', loading: false})
					)
				)}
			>
				<MemoryRouter
					initialEntries={['/workspace/23/settings/properties']}
				>
					<Route path={Routes.SETTINGS_CHANNELS}>
						<ChannelList {...defaultProps} />
					</Route>
				</MemoryRouter>
			</Provider>
		);

		expect(queryByText('New Property')).toBeNull();
	});

	it('should check if the number of synced sites and channels appears for each property', async () => {
		API.channels.search.mockReturnValue(
			Promise.resolve(data.mockChannels())
		);

		const {container} = render(
			<Provider store={mockStore()}>
				<MemoryRouter
					initialEntries={['/workspace/23/settings/properties']}
				>
					<Route path={Routes.SETTINGS_CHANNELS}>
						<ChannelList {...defaultProps} />
					</Route>
				</MemoryRouter>
			</Provider>
		);

		jest.runAllTimers();

		await waitForLoadingToBeRemoved(container);

		expect(screen.getByText('Sites')).toBeInTheDocument();

		expect(screen.getByText('Channels')).toBeInTheDocument();

		expect(screen.getByText('Liferay DXP')).toBeInTheDocument();

		expect(screen.getByText('6')).toBeInTheDocument();

		expect(screen.getByText('5')).toBeInTheDocument();
	});

	it('should check if actions "DELETE" and "CLEAR DATA" are displayed in the ellipsis and its icons', async () => {
		API.channels.search.mockReturnValueOnce(
			Promise.resolve(data.mockChannels())
		);

		const {container} = render(
			<Provider store={mockStore()}>
				<MemoryRouter
					initialEntries={['/workspace/23/settings/properties']}
				>
					<Route path={Routes.SETTINGS_CHANNELS}>
						<ChannelList {...defaultProps} />
					</Route>
				</MemoryRouter>
			</Provider>
		);

		jest.runAllTimers();

		await waitForLoadingToBeRemoved(container);

		const clearDataBtn = screen.getByRole('button', {
			name: /clear data/i
		});

		const deleteBtn = screen.getByRole('button', {
			name: /delete/i
		});

		expect(clearDataBtn).toBeInTheDocument();

		expect(deleteBtn).toBeInTheDocument();

		const clearDataIcon = container.querySelector(
			'svg.lexicon-icon-magic.icon-root'
		);

		const deleteIcon = container.querySelector(
			'svg.lexicon-icon-trash.icon-root'
		);

		expect(clearDataIcon).toBeInTheDocument();

		expect(clearDataIcon.parentElement).toBe(clearDataBtn);

		expect(deleteIcon).toBeInTheDocument();

		expect(deleteIcon.parentElement).toBe(deleteBtn);
	});
});
