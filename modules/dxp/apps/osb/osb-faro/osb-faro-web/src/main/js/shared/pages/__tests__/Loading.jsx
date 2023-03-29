import Loading from '../Loading';
import React from 'react';
import {render} from '@testing-library/react';

jest.unmock('react-dom');

describe('Loading', () => {
	it('should render', () => {
		const {container} = render(<Loading />);

		expect(container).toMatchSnapshot();
	});

	it('should render a classname to align loading in the center of the card', () => {
		const {container} = render(<Loading displayCard />);

		expect(
			container
				.querySelector('.loading-root')
				.classList.contains('display-card')
		).toBeTruthy();
	});
});
