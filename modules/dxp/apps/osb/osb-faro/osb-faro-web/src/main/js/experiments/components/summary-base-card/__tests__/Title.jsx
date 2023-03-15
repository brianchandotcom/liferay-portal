import React from 'react';
import Title from '../Title';
import {render} from '@testing-library/react';

jest.unmock('react-dom');

describe('SummaryBaseCard Title', () => {
	it('should render component', () => {
		const {container} = render(
			<Title className='testing-class' label='My Title' />
		);

		expect(container).toMatchSnapshot();
	});
});
