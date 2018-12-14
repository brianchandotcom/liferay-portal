import React from 'react';
import ClayToggle from 'components/shared/ClayToggle.es';
import {cleanup, render} from 'react-testing-library';

describe(
	'ClayToggle',
	() => {
		afterEach(cleanup);

		it(
			'should render',
			() => {
				const {asFragment} = render(
					<ClayToggle />
				);

				expect(asFragment()).toMatchSnapshot();
			}
		);
	}
);