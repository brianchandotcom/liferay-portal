import React from 'react';
import SelectFieldInput from '../SelectFieldInput';
import {render} from '@testing-library/react';

jest.unmock('react-dom');

describe('SelectFieldInput', () => {
	it('should render', () => {
		const {container} = render(<SelectFieldInput groupId='23' />);

		expect(container).toMatchSnapshot();
	});
});
