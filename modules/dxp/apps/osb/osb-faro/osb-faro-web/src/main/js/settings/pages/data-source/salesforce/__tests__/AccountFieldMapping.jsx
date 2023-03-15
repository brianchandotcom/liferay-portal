import * as data from 'test/data';
import mockStore from 'test/mock-store';
import React from 'react';
import {AccountFieldMapping} from '../AccountFieldMapping';
import {DataSource, User} from 'shared/util/records';
import {Provider} from 'react-redux';
import {render} from '@testing-library/react';
import {StaticRouter} from 'react-router';

jest.unmock('react-dom');

const defaultProps = {
	currentUser: data.getImmutableMock(User, data.mockUser),
	dataSource: data.getImmutableMock(
		DataSource,
		data.mockSalesforceDataSource
	),
	groupId: '23',
	id: '27'
};

describe('AccountFieldMapping', () => {
	it('should render', () => {
		const {container} = render(
			<Provider store={mockStore()}>
				<StaticRouter>
					<AccountFieldMapping {...defaultProps} />
				</StaticRouter>
			</Provider>
		);

		jest.runAllTimers();

		expect(container).toMatchSnapshot();
	});
});
