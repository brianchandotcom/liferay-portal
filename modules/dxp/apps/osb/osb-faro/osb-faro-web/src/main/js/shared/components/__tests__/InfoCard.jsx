import InfoCard from '../InfoCard';
import React from 'react';
import {cleanup, render} from '@testing-library/react';

jest.unmock('react-dom');

const mockItems = [
	{name: 'name', value: 'Test Test'},
	{name: 'description', value: 'Test Test Description'},
	{
		name: 'shippingAddress',
		value: '123 DB Lane Diamond Bar, CA 91765 USA'
	}
];

describe('InfoCard', () => {
	afterEach(cleanup);

	it('should render', () => {
		const {container} = render(<InfoCard header='foo' items={mockItems} />);
		expect(container).toMatchSnapshot();
	});

	it('should render with an image', () => {
		const {container} = render(
			<InfoCard header='foo' image='image-foo.com' items={mockItems} />
		);
		expect(container.querySelector('.image-container')).toBeTruthy();
	});
});
