import * as data from 'test/data';
import AttributeList from '../AttributeList';
import client from 'shared/apollo/client';
import React from 'react';
import {ApolloProvider} from '@apollo/react-components';
import {AttributeTypes} from 'event-analysis/utils/types';
import {MemoryRouter, Route} from 'react-router-dom';
import {MockedProvider} from '@apollo/react-testing';
import {mockEventAttributeDefinitionsReq} from 'test/graphql-data';
import {render} from '@testing-library/react';
import {Routes} from 'shared/util/router';
import {waitForLoading} from 'test/helpers';

jest.unmock('react-dom');

describe('AttributeList', () => {
	const WrappedComponent = props => (
		<ApolloProvider client={client}>
			<MemoryRouter
				initialEntries={[
					'/workspace/23/settings/definitions/event-attributes/global?delta=1'
				]}
			>
				<Route
					path={Routes.SETTINGS_DEFINITIONS_EVENT_ATTRIBUTES_GLOBAL}
				>
					<MockedProvider
						mocks={[
							mockEventAttributeDefinitionsReq(
								[
									data.mockEventAttributeDefinition(0, {
										__typename: 'EventAttributeDefinition'
									})
								],
								{
									type: AttributeTypes.Local
								}
							)
						]}
					>
						<AttributeList groupId='23' {...props} />
					</MockedProvider>
				</Route>
			</MemoryRouter>
		</ApolloProvider>
	);

	it('should render', async () => {
		const {container} = render(<WrappedComponent />);

		await waitForLoading(container);

		jest.runAllTimers();

		expect(container).toMatchSnapshot();
	});

	it('should render Data Typecast column with a label', () => {
		const {getByText} = render(<WrappedComponent />);

		jest.runAllTimers();

		expect(getByText('STRING').parentElement).toHaveClass('label-info');
	});
});
