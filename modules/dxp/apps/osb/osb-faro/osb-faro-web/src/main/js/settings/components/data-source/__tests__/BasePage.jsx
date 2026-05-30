import * as data from 'test/data';
import BaseDataSourcePage from '../BasePage';
import mockStore from 'test/mock-store';
import React from 'react';
import {cleanup, render} from '@testing-library/react';
import {DataSource, User} from 'shared/util/records';
import {DataSourceStates, UserRoleNames} from 'shared/util/constants';
import {MemoryRouter} from 'react-router-dom';
import {Provider} from 'react-redux';

jest.unmock('react-dom');

jest.mock('react-router-dom', () => ({
	...jest.requireActual('react-router-dom'),
	useParams: () => ({
		groupId: '23'
	})
}));

describe('BaseDataSourcePage', () => {
	afterEach(cleanup);

	it('should render', () => {
		const {container} = render(
			<MemoryRouter>
				<Provider store={mockStore()}>
					<BaseDataSourcePage
						currentUser={data.getImmutableMock(User, data.mockUser)}
						dataSource={data.getImmutableMock(
							DataSource,
							data.mockLiferayDataSource
						)}
						groupId='23'
						id='test'
					/>
				</Provider>
			</MemoryRouter>
		);

		expect(container).toMatchSnapshot();
	});

	// TODO: remove "skip" on test below when Delete Property and Delete Data Source btns are back in the UI (LRAC-13389)

	it.skip('should render a delete button if showDelete is true', () => {
		const {queryByText} = render(
			<MemoryRouter>
				<Provider store={mockStore()}>
					<BaseDataSourcePage
						currentUser={data.getImmutableMock(User, data.mockUser)}
						dataSource={data.getImmutableMock(
							DataSource,
							data.mockLiferayDataSource
						)}
						groupId='23'
						id='test'
						showDelete
					/>
				</Provider>
			</MemoryRouter>
		);

		expect(queryByText('Delete Data Source')).toBeTruthy();
	});

	it('should NOT render a delete button if the user is not an admin level', () => {
		const {queryByText} = render(
			<MemoryRouter>
				<Provider store={mockStore()}>
					<BaseDataSourcePage
						currentUser={data.getImmutableMock(
							User,
							data.mockUser,
							'23',
							{
								roleName: UserRoleNames.Member
							}
						)}
						dataSource={data.getImmutableMock(
							DataSource,
							data.mockLiferayDataSource
						)}
						groupId='23'
						id='test'
						showDelete
					/>
				</Provider>
			</MemoryRouter>
		);

		expect(queryByText('Delete Data Source')).toBeNull();
	});

	it('should render with an UNDEFINED_ERROR message in the datasource status column', () => {
		const {queryByText} = render(
			<MemoryRouter>
				<Provider store={mockStore()}>
					<BaseDataSourcePage
						currentUser={data.getImmutableMock(
							User,
							data.mockUser,
							'23',
							{
								roleName: UserRoleNames.Member
							}
						)}
						dataSource={data.getImmutableMock(
							DataSource,
							data.mockSalesforceDataSource,
							'test',
							{state: DataSourceStates.UndefinedError}
						)}
						groupId='23'
						id='test'
						showDelete
					/>
				</Provider>
			</MemoryRouter>
		);

		expect(queryByText(/A server error occurred/)).toBeTruthy();
	});

	it('should render w/o datasource', () => {
		const {queryByText} = render(
			<MemoryRouter>
				<Provider store={mockStore()}>
					<BaseDataSourcePage
						currentUser={data.getImmutableMock(User, data.mockUser)}
						groupId='23'
						id='test'
					/>
				</Provider>
			</MemoryRouter>
		);

		expect(
			queryByText(
				'Data source has not been created. Please authorize and save to get started.'
			)
		).toBeTruthy();
	});
});
