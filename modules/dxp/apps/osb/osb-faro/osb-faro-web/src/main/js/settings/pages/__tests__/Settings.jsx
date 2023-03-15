import * as data from 'test/data';
import React from 'react';
import {BrowserRouter} from 'react-router-dom';
import {cleanup, render} from '@testing-library/react';
import {Settings} from '../Settings';
import {User} from 'shared/util/records';

jest.unmock('react-dom');

const defaultProps = {
	currentUser: data.getImmutableMock(User, data.mockUser),
	groupId: '23',
	location: {pathname: 'foo'},
	project: data.mockProject()
};

const DefaultComponent = props => (
	<BrowserRouter>
		<Settings {...defaultProps} {...props} />
	</BrowserRouter>
);

describe('Setttings', () => {
	afterEach(cleanup);

	it('should render', () => {
		const {container} = render(
			<DefaultComponent location={{pathname: 'foo'}} />
		);

		expect(container).toMatchSnapshot();
	});

	it('if the user is AC Admin, then the APIs link should render in the sidebar', () => {
		const {queryByText} = render(<DefaultComponent />);

		expect(queryByText('APIs')).toBeTruthy();
	});

	it('if the user is not an AC Admin, then the APIs link should not render in the sidebar', () => {
		const {queryByText} = render(
			<DefaultComponent
				currentUser={data.getImmutableMock(User, data.mockMemberUser)}
			/>
		);

		expect(queryByText('APIs')).toBeNull();
	});
});
