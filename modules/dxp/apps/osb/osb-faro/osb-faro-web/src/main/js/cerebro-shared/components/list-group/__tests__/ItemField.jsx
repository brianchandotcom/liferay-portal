import ItemField from '../ItemField';
import React from 'react';
import {render} from '@testing-library/react';

jest.unmock('react-dom');

describe('ItemField', () => {
	it('should render', () => {
		const {container} = render(<ItemField children='Children' />);

		expect(container).toMatchSnapshot();
	});

	it('should render with expanded column', () => {
		const {container} = render(<ItemField expand />);

		expect(container.querySelector('.autofit-col-expand')).toBeTruthy();
	});
});
