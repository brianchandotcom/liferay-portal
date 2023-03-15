import Label from '../Label';
import React from 'react';
import {render} from '@testing-library/react';

jest.unmock('react-dom');

describe('Label', () => {
	it('should render', () => {
		const {container} = render(<Label label='Label' />);

		expect(container).toMatchSnapshot();
	});

	it('should render closeable Label', () => {
		const {container} = render(<Label closeable />);

		expect(container.querySelector('.close')).toBeTruthy();
	});
});
