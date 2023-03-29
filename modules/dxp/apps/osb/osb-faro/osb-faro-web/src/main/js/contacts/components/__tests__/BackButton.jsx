import BackButton from '../BackButton.tsx';
import React from 'react';
import {render} from '@testing-library/react';
import {StaticRouter} from 'react-router';

jest.unmock('react-dom');

describe('BackButton', () => {
	it('should render', () => {
		const {container} = render(
			<StaticRouter>
				<BackButton href='foo.url' label='foo label' />
			</StaticRouter>
		);

		expect(container).toMatchSnapshot();
	});
});
