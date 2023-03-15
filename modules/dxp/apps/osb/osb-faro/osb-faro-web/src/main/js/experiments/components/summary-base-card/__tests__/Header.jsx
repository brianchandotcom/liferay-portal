import Header from '../Header';
import React from 'react';
import {render} from '@testing-library/react';

jest.unmock('react-dom');

describe('SummaryBaseCard Header', () => {
	it('should render component', () => {
		const {container} = render(<Header title='My Header' />);

		expect(container).toMatchSnapshot();
	});

	it('should render component with Description', () => {
		const {getByText} = render(
			<Header
				Description={() => <div>{'My Description'}</div>}
				title='My Header'
			/>
		);

		expect(getByText('My Header'));
		expect(getByText('My Description'));
	});
});
