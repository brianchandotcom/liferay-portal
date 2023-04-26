import CopyButton from '../CopyButton';
import React from 'react';
import {cleanup, render} from '@testing-library/react';

jest.unmock('react-dom');

describe('CopyButton', () => {
	afterEach(cleanup);

	it('should render', () => {
		const {container} = render(
			<CopyButton displayType='secondary' text='foo' />
		);

		expect(container).toMatchSnapshot();
	});
});
