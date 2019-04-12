import CriteriaBuilder from 'components/criteria_builder/CriteriaBuilder.es';
import React from 'react';
import {cleanup, render} from 'react-testing-library';

describe(
	'CriteriaBuilder',
	() => {
		afterEach(cleanup);

		it(
			'should render',
			() => {
				const {asFragment} = render(
					<CriteriaBuilder
						editing={false}
						editingCriteria={false}
						emptyContributors={false}
						entityName="User"
						id="0"
						propertyKey="user"
						supportedProperties={[]}
					/>
				);

				expect(asFragment()).toMatchSnapshot();
			}
		);
	}
);