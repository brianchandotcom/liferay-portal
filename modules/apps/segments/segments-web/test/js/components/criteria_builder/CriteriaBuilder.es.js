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
						entityName="User"
						emptyContributors={false}
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