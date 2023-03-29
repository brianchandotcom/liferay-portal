import * as data from 'test/data';
import EventInput from '../EventInput';
import React from 'react';
import {AttributeTypes} from 'event-analysis/utils/types';
import {createNewGroup} from '../../utils/utils';
import {CustomValue, Property} from 'shared/util/records';
import {fromJS} from 'immutable';
import {MemoryRouter, Route} from 'react-router-dom';
import {MockedProvider} from '@apollo/react-testing';
import {mockEventAttributeDefinitionsReq} from 'test/graphql-data';
import {range} from 'lodash';
import {RelationalOperators} from '../../utils/constants';
import {render} from '@testing-library/react';
import {Routes} from 'shared/util/router';
import {waitForLoading} from 'test/helpers';

jest.unmock('react-dom');

describe('EventInput', () => {
	it('should render', async () => {
		const {container} = render(
			<MockedProvider
				mocks={[
					mockEventAttributeDefinitionsReq(
						range(10).map(i =>
							data.mockEventAttributeDefinition(i, {
								__typename: 'EventAttributeDefinition'
							})
						),
						{
							eventDefinitionId: '3',
							page: 0,
							size: 25,
							sort: {
								column: 'name',
								type: 'ASC'
							},
							type: AttributeTypes.Global
						}
					)
				]}
			>
				<MemoryRouter
					initialEntries={[
						'/workspace/23/123123/contacts/segments/create?type=DYNAMIC'
					]}
				>
					<Route path={Routes.CONTACTS_SEGMENT_CREATE}>
						<EventInput
							displayValue='Asset Clicked'
							onChange={jest.fn()}
							operatorRenderer={() => <div>{'test'}</div>}
							property={Property({
								entityName: 'Event',
								id: '3',
								label: 'assetDepthReached',
								name: '3',
								propertyKey: 'event',
								type: 'event'
							})}
							touched={{attribute: true, attributeValue: true}}
							valid={{attribute: true, attributeValue: true}}
							value={CustomValue(
								fromJS({
									criterionGroup: createNewGroup([
										{
											operatorName: 'eq',
											propertyName: 'eventDefinitionId',
											value: '1'
										},
										{
											operatorName: 'contains',
											propertyName: 'attribute/2',
											value: ''
										},
										{
											operatorName: 'gt',
											propertyName: 'day',
											value: 'last24Hours'
										}
									]),
									operator: RelationalOperators.GT,
									value: 1
								})
							)}
						/>
					</Route>
				</MemoryRouter>
			</MockedProvider>
		);

		await waitForLoading(container);

		jest.runAllTimers();

		expect(container).toMatchSnapshot();
	});
});
