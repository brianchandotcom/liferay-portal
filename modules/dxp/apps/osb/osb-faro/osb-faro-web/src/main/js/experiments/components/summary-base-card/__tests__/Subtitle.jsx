import React from 'react';
import Subtitle from '../Subtitle';
import {render} from '@testing-library/react';

jest.unmock('react-dom');

describe('SummaryBaseCard Subtitle', () => {
	it('should render', () => {
		const {container} = render(<Subtitle label='My Subtitle' />);

		expect(container).toMatchSnapshot();
	});
});
