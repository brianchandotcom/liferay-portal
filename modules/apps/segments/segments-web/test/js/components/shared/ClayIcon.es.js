import ClayIcon from 'components/shared/ClayIcon.es';
import React from 'react';
import {cleanup, render} from 'react-testing-library';

describe('ClayIcon', () => {
	afterEach(cleanup);

	it('should render', () => {
		const {asFragment} = render(<ClayIcon iconName='times' />);

		expect(asFragment()).toMatchSnapshot();
	});
});
