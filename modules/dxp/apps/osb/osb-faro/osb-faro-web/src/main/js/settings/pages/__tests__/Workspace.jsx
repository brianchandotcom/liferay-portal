import * as data from 'test/data';
import mockStore from 'test/mock-store';
import React from 'react';
import {addAlert} from 'shared/actions/alerts';
import {cleanup, render} from '@testing-library/react';
import {Project, User} from 'shared/util/records';
import {Provider} from 'react-redux';
import {StaticRouter} from 'react-router';
import {Workspace} from '../Workspace';

jest.unmock('react-dom');

jest.mock('shared/actions/alerts', () => ({
	actionTypes: {},
	addAlert: jest.fn(() => ({meta: {}, payload: {}, type: 'addAlert'}))
}));

const defaultProps = {
	addAlert,
	currentUser: data.getImmutableMock(User, data.mockUser),
	emailAddressDomains: ['liferay.com'],
	groupId: '23',
	project: new Project(data.mockProject())
};

const DefaultComponent = props => (
	<Provider store={mockStore()}>
		<StaticRouter>
			<Workspace {...defaultProps} {...props} />
		</StaticRouter>
	</Provider>
);

describe('Workspace Settting', () => {
	afterEach(cleanup);

	it('should render', () => {
		const {container} = render(<DefaultComponent />);

		expect(container).toMatchSnapshot();
	});

	it('should render as disabled if the user is not an AC admin', () => {
		const {container, getByDisplayValue, getByLabelText} = render(
			<DefaultComponent
				currentUser={data.getImmutableMock(User, data.mockMemberUser)}
			/>
		);

		expect(getByLabelText('Workspace Name')).toBeDisabled();
		expect(getByDisplayValue('Oregon, USA')).toBeDisabled();
		expect(
			getByLabelText(/Set a Friendly Workspace URL/, {selector: 'input'})
		).toBeDisabled();
		expect(
			container.querySelector('.input-list-root input')
		).toBeDisabled();
	});
});
