import * as API from 'shared/api';
import client from 'shared/apollo/client';
import Promise from 'metal-promise';
import React from 'react';
import withPropertyGroups from '../WithPropertyGroups';
import {render} from '@testing-library/react';
import {StaticRouter} from 'react-router';

jest.mock('shared/apollo/client', () => ({
	query: jest.fn()
}));

jest.unmock('react-dom');

const TestComponent = ({propertyGroupsIList}) => (
	<div>
		{propertyGroupsIList.map((attribute, i) => {
			if (attribute) {
				return (
					<div key={i}>
						{attribute.label}

						{attribute.propertySubgroups.map(
							({label, properties}, i) => (
								<div key={i}>{`${
									label || attribute.label
								}-${i}: ${properties.size}`}</div>
							)
						)}
					</div>
				);
			}
		})}
	</div>
);

describe('WithPropertyGroups', () => {
	it('should pass propertyGroups to the WrappedComponent', () => {
		API.fieldMappings.search.mockReturnValueOnce(
			Promise.resolve({
				items: [
					{
						context: 'demographics',
						displayName: 'Individual Value',
						id: '123',
						name: 'Individual val',
						ownerType: 'individual',
						rawType: 'Text',
						type: 'Text'
					}
				],
				total: 1
			})
		);

		API.fieldMappings.search.mockReturnValueOnce(
			Promise.resolve({
				items: [
					{
						context: 'custom',
						displayName: 'Individual Custom',
						id: '123',
						name: 'Individual Custom',
						ownerType: 'individual',
						rawType: 'Text',
						type: 'Text'
					}
				],
				total: 1
			})
		);

		API.fieldMappings.search.mockReturnValueOnce(
			Promise.resolve({
				items: [
					{
						context: 'organization',
						displayName: 'Account Value',
						id: '123',
						name: 'Account Value',
						ownerType: 'account',
						rawType: 'Text',
						type: 'Text'
					}
				],
				total: 1
			})
		);

		API.fieldMappings.search.mockReturnValueOnce(
			Promise.resolve({
				items: [
					{
						context: 'custom',
						displayName: 'Organization Custom',
						id: '123',
						name: 'Organization Custom',
						ownerType: 'organization',
						rawType: 'Text',
						type: 'Text'
					}
				],
				total: 1
			})
		);

		client.query.mockReturnValueOnce(
			Promise.resolve({
				data: {
					eventDefinitions: {
						__typename: 'EventDefinitionBag',
						eventDefinitions: [
							{
								__typename: 'EventDefinition',
								description: null,
								displayName: 'displayName-1',
								id: '1',
								name: 'name-1',
								type: 'DEFAULT'
							}
						],
						total: 1
					}
				}
			})
		);

		const WrappedComponent = withPropertyGroups(TestComponent);

		const {container} = render(
			<StaticRouter>
				<WrappedComponent channelId='123' groupId='123' />
			</StaticRouter>
		);

		jest.runAllTimers();

		expect(container).toMatchSnapshot();
	});
});
