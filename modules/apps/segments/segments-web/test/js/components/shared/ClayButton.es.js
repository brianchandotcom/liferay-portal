import ClayButton from 'components/shared/ClayButton.es';
import React from 'react';
import {cleanup, render} from 'react-testing-library';

describe('ClayButton', () => {
	afterEach(cleanup);

	it('should render', () => {
		const {asFragment} = render(<ClayButton />);

		expect(asFragment()).toMatchSnapshot();
	});

	it('should render with a label', () => {
		const {container} = render(<ClayButton label='test' />);

		const button = container.querySelector('button');

		expect(button.textContent).toEqual('test');
	});
});
