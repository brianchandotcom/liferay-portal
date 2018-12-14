import React from 'react';
import ODataQueryBuilder from 'components/odata_query_builder/ODataQueryBuilder.es';
import {cleanup, render} from 'react-testing-library';

const QUERY_INPUT_TESTID = 'query-input';

describe(
	'ODataQueryBuilder',
	() => {
		afterEach(cleanup);

		it(
			'should render with no properties',
			() => {
				const {asFragment} = render(
					<ODataQueryBuilder properties={[]} />
				);

				expect(asFragment()).toMatchSnapshot();
			}
		);

		it(
			'should render with properties',
			() => {
				const {asFragment} = render(
					<ODataQueryBuilder
						properties={[
							{
								label: 'First Name',
								name: 'firstName',
								type: 'string'
							},
							{
								label: 'Last Name',
								name: 'lastName',
								type: 'string'
							},
						]}
					/>
				);

				expect(asFragment()).toMatchSnapshot();
			}
		);

		it(
			'should populate the query input with a valid initial query',
			() => {
				const {getByTestId} = render(
					<ODataQueryBuilder
						properties={[
							{
								label: 'First Name',
								name: 'firstName',
								type: 'string'
							}
						]}
						initialQuery={`(firstName eq 'test')`}
					/>
				);

				const queryInput = getByTestId(QUERY_INPUT_TESTID);

				expect(queryInput.value).toEqual(`(firstName eq 'test')`);
			}
		);

		it(
			'should not break if the property in the initial query does not exist in the accepted properties',
			() => {
				const {getByTestId} = render(
					<ODataQueryBuilder
						properties={[]}
						initialQuery={`(firstName eq 'test')`}
					/>
				);

				const queryInput = getByTestId(QUERY_INPUT_TESTID);

				expect(queryInput.value).toEqual(`(firstName eq 'test')`);
			}
		);

		it(
			'should render a blank input if initial query is invalid',
			() => {
				const {getByTestId} = render(
					<ODataQueryBuilder
						properties={[]}
						initialQuery={`(test)`}
					/>
				);

				const queryInput = getByTestId(QUERY_INPUT_TESTID);

				expect(queryInput.value).toEqual('');
			}
		);
	}
);