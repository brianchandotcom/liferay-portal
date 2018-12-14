import React from 'react';
import CriteriaSidebar from 'components/criteria_sidebar/CriteriaSidebar.es';
import {cleanup, render} from 'react-testing-library';

describe(
	'CriteriaSidebar',
	() => {
		afterEach(cleanup);

		it(
			'should render',
			() => {
				const {asFragment} = render(
					<CriteriaSidebar />
				);

				expect(asFragment()).toMatchSnapshot();
			}
		);
	}
);