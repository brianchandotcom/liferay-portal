import InputModal from '../InputModal';
import React from 'react';
import {cleanup, render} from '@testing-library/react';
import {noop} from 'lodash';

jest.unmock('react-dom');

describe('InputModal', () => {
	afterEach(cleanup);

	it('should render', () => {
		const {container} = render(
			<InputModal onClose={noop} placeholder='foo' title='bar' />
		);
		expect(container).toMatchSnapshot();
	});
});
