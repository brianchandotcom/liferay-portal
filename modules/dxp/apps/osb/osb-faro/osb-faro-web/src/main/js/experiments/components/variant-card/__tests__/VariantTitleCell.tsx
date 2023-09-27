import React from 'react';
import VariantTitle from '../VariantTitleCell';
import {render} from '@testing-library/react';

jest.unmock('react-dom');

const DefaultComponent = props => (
	<VariantTitle title='Variant Title' {...props} />
);

describe('VariantTitle', () => {
	it('should render', () => {
		const {container} = render(<DefaultComponent />);

		expect(container.querySelector('.text-truncate')).toBeTruthy();
		expect(container).toMatchSnapshot();
	});

	it('should render label component when a label prop is set', () => {
		const {getByText} = render(
			<DefaultComponent labels={[{status: 'success', value: 'winner'}]} />
		);

		expect(getByText('winner')).toBeTruthy();
	});
});
