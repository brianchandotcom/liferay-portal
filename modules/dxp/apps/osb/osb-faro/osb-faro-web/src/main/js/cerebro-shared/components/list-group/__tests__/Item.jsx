import Item from '../Item';
import React from 'react';
import {render} from '@testing-library/react';

jest.unmock('react-dom');

describe('Item', () => {
	it('should render', () => {
		const {container} = render(<Item children='Children' />);

		expect(container).toMatchSnapshot();
	});

	it('should render with accentColor', () => {
		const {container} = render(
			<Item accentColor='red' children='Children' />
		);

		expect(container.querySelector('.list-group-item')).toHaveStyle(
			'border-left: 4px solid red'
		);
	});
});
