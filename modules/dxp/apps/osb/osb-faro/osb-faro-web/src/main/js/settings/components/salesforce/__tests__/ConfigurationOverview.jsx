import * as data from 'test/data';
import mockStore from 'test/mock-store';
import React from 'react';
import {addAlert} from 'shared/actions/alerts';
import {BrowserRouter} from 'react-router-dom';
import {DataSource} from 'shared/util/records';
import {Provider} from 'react-redux';
import {render} from '@testing-library/react';
import {ConfigurationOverview as SalesforceConfigurationOverview} from '../ConfigurationOverview';

jest.mock('shared/actions/alerts', () => ({
	actionTypes: {},
	addAlert: jest.fn(() => ({meta: {}, payload: {}, type: 'addAlert'}))
}));

jest.unmock('react-dom');

const pushSpy = jest.fn();

const mockHistory = {
	push: pushSpy
};

const defaultProps = {
	addAlert,
	dataSource: data.getImmutableMock(
		DataSource,
		data.mockSalesforceDataSource
	),
	groupId: '23',
	history: mockHistory,
	id: 'test'
};

const DefaultComponent = props => (
	<Provider store={mockStore()}>
		<BrowserRouter>
			<SalesforceConfigurationOverview {...defaultProps} {...props} />
		</BrowserRouter>
	</Provider>
);

describe('SalesforceConfigurationOverview', () => {
	afterEach(() => {
		jest.clearAllMocks();
	});

	it('should render', () => {
		const {container} = render(<DefaultComponent />);

		expect(container).toMatchSnapshot();
	});

	it('should render as disabled', () => {
		const {container} = render(<DefaultComponent disabled />);

		expect(container.querySelector('.link-disabled')).toBeTruthy();
	});

	it('should display an alert toast message and navigate to the datasource profile if a service permission error is received from fetching sync counts', () => {
		const errorString = JSON.stringify({status: 403});
		const mockError = new Error(errorString);

		const {rerender} = render(<DefaultComponent />);

		jest.runAllTimers();

		expect(addAlert).not.toBeCalled();
		expect(pushSpy).not.toHaveBeenCalled();

		rerender(<DefaultComponent pollingError={mockError} />);

		jest.runAllTimers();

		expect(addAlert).toBeCalled();
		expect(pushSpy).toHaveBeenCalled();
	});

	it('should display an alert toast message and navigate to the datasource profile if a service unresponsive error is received from fetching sync counts', () => {
		const errorString = JSON.stringify({status: 404});
		const mockError = new Error(errorString);

		const {rerender} = render(<DefaultComponent />);

		jest.runAllTimers();

		expect(addAlert).not.toBeCalled();
		expect(pushSpy).not.toHaveBeenCalled();

		rerender(<DefaultComponent pollingError={mockError} />);

		jest.runAllTimers();

		expect(addAlert).toBeCalled();
		expect(pushSpy).toHaveBeenCalled();
	});
});
