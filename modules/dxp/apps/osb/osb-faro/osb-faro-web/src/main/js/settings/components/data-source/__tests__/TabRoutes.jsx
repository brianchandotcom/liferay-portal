import React from 'react';
import TabRoutes from '../TabRoutes';
import {render} from '@testing-library/react';
import {StaticRouter} from 'react-router';

jest.unmock('react-dom');

describe('TabRoutes', () => {
	it('should render', () => {
		const Component = () => <div>{'Foo Bar'}</div>;

		const {container, getByText} = render(
			<StaticRouter location='foo/path'>
				<TabRoutes
					routes={[{component: Component, path: 'foo/path'}]}
				/>
			</StaticRouter>
		);

		expect(getByText('Foo Bar')).toBeTruthy();
		expect(container).toMatchSnapshot();
	});
});
