import mockStore from 'test/mock-store';
import React from 'react';
import Sidebar from '../index';
import {Provider} from 'react-redux';
import {render} from '@testing-library/react';
import {StaticRouter} from 'react-router';
import {User} from 'shared/util/records';

const defaultProps = {
	activePathname: '',
	channelId: '123',
	currentUser: new User({emailAddress: 'test@test.com', name: 'Test Test'}),
	groupId: '23'
};

jest.unmock('react-dom');

describe('Sidebar', () => {
	it('should render', () => {
		const {container} = render(
			<Provider store={mockStore()}>
				<StaticRouter>
					<Sidebar {...defaultProps} />
				</StaticRouter>
			</Provider>
		);

		expect(container).toMatchSnapshot();
	});

	it('should render as collapsed', () => {
		const {container} = render(
			<Provider store={mockStore()}>
				<StaticRouter>
					<Sidebar {...defaultProps} collapsed />
				</StaticRouter>
			</Provider>
		);

		expect(container.querySelector('.sidebar-root')).toHaveClass(
			'collapsed'
		);
	});

	it('should render with a specific sidebar id active', () => {
		const activePathName = '/workspace/23/123/contacts/individuals';

		const {container} = render(
			<Provider store={mockStore()}>
				<StaticRouter>
					<Sidebar
						{...defaultProps}
						activePathname={activePathName}
					/>
				</StaticRouter>
			</Provider>
		);

		expect(
			container.querySelector('.sidebar-item-root.active').firstChild
		).toHaveAttribute('href', activePathName);
	});
});
