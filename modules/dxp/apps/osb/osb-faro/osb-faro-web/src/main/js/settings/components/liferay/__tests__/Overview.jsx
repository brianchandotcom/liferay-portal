import * as API from 'shared/api';
import * as data from 'test/data';
import LiferayOverview from '../Overview';
import mockStore from 'test/mock-store';
import Promise from 'metal-promise';
import React from 'react';
import {cleanup, fireEvent, render} from '@testing-library/react';
import {DataSource, User} from 'shared/util/records';
import {DataSourceStates, DataSourceStatuses} from 'shared/util/constants';
import {Provider} from 'react-redux';
import {StaticRouter} from 'react-router';

jest.unmock('react-dom');

const defaultProps = {
	currentUser: data.getImmutableMock(User, data.mockUser),
	dataSource: data.getImmutableMock(DataSource, data.mockLiferayDataSource),
	groupId: '23',
	id: 'test'
};

const DefaultComponent = props => (
	<Provider store={mockStore()}>
		<StaticRouter>
			<LiferayOverview {...defaultProps} {...props} />
		</StaticRouter>
	</Provider>
);

describe('LiferayOverview', () => {
	afterEach(cleanup);

	it('should render', () => {
		const {container} = render(<DefaultComponent />);

		expect(container).toMatchSnapshot();
	});

	it('should render sites as configured', () => {
		const {container} = render(
			<DefaultComponent
				dataSource={data.getImmutableMock(
					DataSource,
					data.mockLiferayDataSource,
					0,
					{sitesSelected: true}
				)}
			/>
		);

		expect(container.querySelectorAll('.label')[0]).toHaveTextContent(
			/^configured/i
		);
	});

	it('should render contacts as configured', () => {
		const {container} = render(
			<DefaultComponent
				dataSource={data.getImmutableMock(
					DataSource,
					data.mockLiferayDataSource,
					0,
					{contactsSelected: true}
				)}
			/>
		);

		expect(container.querySelectorAll('.label')[1]).toHaveTextContent(
			/^configured/i
		);
	});

	it('should render the "Disconnect" button as disabled if the user is not an AC admin', () => {
		const {getByTestId} = render(
			<DefaultComponent
				currentUser={data.getImmutableMock(User, data.mockMemberUser)}
			/>
		);

		expect(getByTestId('disconnect-button')).toBeDisabled();
	});

	it('should not try to validate the name on an existing DataSource unless the name has actually changed', () => {
		API.dataSource.search.mockReturnValueOnce(
			Promise.resolve(data.mockSearch(data.mockLiferayDataSource, 1))
		);

		const {container, getByLabelText, queryByText} = render(
			<DefaultComponent />
		);

		const nameInput = container.querySelector('input[name=dataSourceName]');

		fireEvent.click(getByLabelText(/edit/i));

		jest.runAllTimers();

		fireEvent.blur(nameInput);

		jest.runAllTimers();

		expect(
			queryByText(
				/A Data Source already exists with that name. Please enter a different name./
			)
		).toBeNull();
	});

	it('should render the "Reconnect" button if the Data Source state is DISCONNECTED', () => {
		const {queryByText} = render(
			<DefaultComponent
				dataSource={data.getImmutableMock(
					DataSource,
					data.mockLiferayDataSource,
					0,
					{
						state: DataSourceStates.Disconnected,
						status: DataSourceStatuses.Inactive
					}
				)}
			/>
		);

		expect(queryByText('Reconnect')).not.toBeNull();
	});
});
