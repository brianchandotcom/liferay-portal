import * as data from 'test/data';
import mockStore from 'test/mock-store';
import React from 'react';
import SalesforceOverview from '../SalesforceOverview';
import {cleanup, render} from '@testing-library/react';
import {DataSource} from 'shared/util/records';
import {Provider} from 'react-redux';
import {StaticRouter} from 'react-router';

jest.unmock('react-dom');

jest.mock('react-router-dom', () => ({
	...jest.requireActual('react-router-dom'),
	useParams: () => ({
		groupId: '23',
		id: 'test'
	})
}));

jest.mock('shared/hooks/useRequest', () => ({
	useRequest: jest.fn
}));

jest.mock('shared/hooks/useCurrentUser', () => ({
	useCurrentUser: () => ({isAdmin: () => true})
}));

const defaultProps = {
	dataSource: data.getImmutableMock(DataSource, data.mockSalesforceDataSource)
};

const DefaultComponent = props => (
	<Provider store={mockStore()}>
		<StaticRouter>
			<SalesforceOverview {...defaultProps} {...props} />
		</StaticRouter>
	</Provider>
);

describe('SalesforceOverview', () => {
	afterEach(cleanup);

	it('should render', () => {
		const {container} = render(<DefaultComponent />);

		expect(container).toMatchSnapshot();
	});
});
