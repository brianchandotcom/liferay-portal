import * as data from 'test/data';
import mockStore from 'test/mock-store';
import React from 'react';
import {cleanup, render} from '@testing-library/react';
import {DataSource, User} from 'shared/util/records';
import {LiferayDataSource} from '../Liferay';
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
			<LiferayDataSource {...defaultProps} {...props} />
		</StaticRouter>
	</Provider>
);

describe('LiferayDataSource', () => {
	afterEach(cleanup);

	it('should render', () => {
		const {container} = render(<DefaultComponent />);

		expect(container).toMatchSnapshot();
	});
});
