import React from 'react';
import TabRoutes from '../TabRoutes';
import {MemoryRouter} from 'react-router-dom';
import {render} from '@testing-library/react';

jest.unmock('react-dom');

describe('TabRoutes', () => {
	it('should render', () => {
		const Component = () => <div>{'Foo Bar'}</div>;

		const {container, getByText} = render(
			<MemoryRouter initialEntries={['/foo/path']}>
				<TabRoutes
					routes={[{component: Component, path: 'foo/path'}]}
				/>
			</MemoryRouter>
		);

		expect(getByText('Foo Bar')).toBeTruthy();
		expect(container).toMatchSnapshot();
	});
});
