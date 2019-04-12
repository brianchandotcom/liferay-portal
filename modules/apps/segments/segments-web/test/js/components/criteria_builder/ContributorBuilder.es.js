import ContributorBuilder from 'components/criteria_builder/ContributorBuilder.es';
import React from 'react';
import {cleanup, render} from 'react-testing-library';
import {CONJUNCTIONS, SUPPORTED_CONJUNCTIONS, SUPPORTED_OPERATORS, SUPPORTED_PROPERTY_TYPES} from '../../../../src/main/resources/META-INF/resources/js/utils/constants.es';

const initialContributors = [
	{
		conjunctionId: CONJUNCTIONS.AND,
		conjunctionInputId: 'conjunction_input_test_id',
		initialQuery: '',
		inputId: 'input_test_id',
		propertyKey: 'user'
	}
];

const propertyGroups = [{
	entityName: 'User',
	name: 'User',
	properties: [{
		label: 'Ancestor Organization IDs',
		name: 'ancestorOrganizationIds',
		type: 'string'
	},
	{
		label: 'Class PK',
		name: 'classPK',
		type: 'string'
	},
	{
		label: 'Company ID',
		name: 'companyId',
		type: 'string'
	},
	{
		label: 'Date Modified',
		name: 'dateModified',
		type: 'date'
	},
	{
		label: 'Email Address',
		name: 'emailAddress',
		type: 'string'
	},
	{
		label: 'First Name',
		name: 'firstName',
		type: 'string'
	},
	{
		label: 'Group ID',
		name: 'groupId',
		type: 'string'
	},
	{
		label: 'Group IDs',
		name: 'groupIds',
		type: 'string'
	},
	{
		label: 'Job Title',
		name: 'jobTitle',
		type: 'string'
	},
	{
		label: 'Last Name',
		name: 'lastName',
		type: 'string'
	},
	{
		label: 'Organization IDs',
		name: 'organizationIds',
		type: 'string'
	},
	{
		label: 'Role IDs',
		name: 'roleIds',
		type: 'string'
	},
	{
		label: 'Scope Group ID',
		name: 'scopeGroupId',
		type: 'string'
	},
	{
		label: 'Screen Name',
		name: 'screenName',
		type: 'string'
	},
	{
		label: 'Team IDs',
		name: 'teamIds',
		type: 'string'
	},
	{
		label: 'User Group IDs',
		name: 'userGroupIds',
		type: 'string'
	},
	{
		label: 'User ID',
		name: 'userId',
		type: 'string'
	},
	{
		label: 'User Name',
		name: 'userName',
		type: 'string'
	}],
	propertyKey: 'user'
}, {
	entityName: 'User Organization',
	name: 'User Organization',
	properties: [{
		label: 'Class PK',
		name: 'classPK',
		type: 'string'
	},
	{
		label: 'Company ID',
		name: 'companyId',
		type: 'string'
	},
	{
		label: 'Date Modified',
		name: 'dateModified',
		type: 'date'
	},
	{
		label: 'Name',
		name: 'name',
		type: 'string'
	},
	{
		label: 'Name Tree Path',
		name: 'nameTreePath',
		type: 'string'
	},
	{
		label: 'Organization ID',
		name: 'organizationId',
		type: 'string'
	},
	{
		label: 'Parent Organization ID',
		name: 'parentOrganizationId',
		type: 'string'
	},
	{
		label: 'Tree Path',
		name: 'treePath',
		type: 'string'
	},
	{
		label: 'Type',
		name: 'type',
		type: 'string'
	}],
	propertyKey: 'user-organization'
}];

describe(
	'ContributorBuilder',
	() => {
		afterEach(cleanup);

		it(
			'should render builder with sidebar',
			() => {
				const editing = true;

				const {asFragment} = render(
					<ContributorBuilder
						editing={editing}
						empty={false}
						initialContributors={initialContributors}
						propertyGroups={propertyGroups}
						supportedConjunctions={SUPPORTED_CONJUNCTIONS}
						supportedOperators={SUPPORTED_OPERATORS}
						supportedPropertyTypes={SUPPORTED_PROPERTY_TYPES}
					/>
				);

				expect(asFragment()).toMatchSnapshot(
					'initialRenderEditing'
				);
			}
		);

		it(
			'should render builder without sidebar',
			() => {
				const editing = false;

				const {asFragment} = render(
					<ContributorBuilder
						editing={editing}
						empty={false}
						initialContributors={initialContributors}
						propertyGroups={propertyGroups}
						supportedConjunctions={SUPPORTED_CONJUNCTIONS}
						supportedOperators={SUPPORTED_OPERATORS}
						supportedPropertyTypes={SUPPORTED_PROPERTY_TYPES}
					/>
				);

				expect(asFragment()).toMatchSnapshot(
					'initialRenderNotEditing'
				);
			}
		);
	}
);