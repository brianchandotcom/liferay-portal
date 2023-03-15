import BaseScreen from '../BaseScreen';
import React from 'react';
import {cleanup, render} from '@testing-library/react';
import {noop} from 'lodash';

jest.unmock('react-dom');

describe('BaseScreen', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(
			<BaseScreen onClose={noop} title='Test Title'>
				{'Test Children'}
			</BaseScreen>
		);

		expect(container).toMatchSnapshot();
	});
});
