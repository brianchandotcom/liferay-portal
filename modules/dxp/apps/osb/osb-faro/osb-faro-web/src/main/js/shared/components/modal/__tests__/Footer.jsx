import Footer from '../Footer';
import React from 'react';
import {render} from '@testing-library/react';

jest.unmock('react-dom');

describe('Modal Footer', () => {
	it('should render', () => {
		const {container} = render(<Footer />);

		expect(container).toMatchSnapshot();
	});

	it('should render with children', () => {
		const {queryByText} = render(
			<Footer>
				<button type='button'>{'Footer button'}</button>
			</Footer>
		);

		expect(queryByText('Footer button')).toBeTruthy();
	});
});
