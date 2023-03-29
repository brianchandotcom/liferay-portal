import * as data from 'test/data';
import CriteriaSidebarCollapse from '../CriteriaSidebarCollapse';
import React from 'react';
import {cleanup, render} from '@testing-library/react';
import {List} from 'immutable';
import {Property, PropertyGroup, PropertySubgroup} from 'shared/util/records';
import {wrapInTestContext} from 'react-dnd-test-utils';

jest.unmock('react-dom');

describe('CriteriaSidebarCollapse', () => {
	const propertyGroupsIList = new List([
		new PropertyGroup({
			label: 'Interests',
			name: 'Interests',
			propertyKey: 'interests',
			propertySubgroups: new List([
				new PropertySubgroup({
					properties: new List([
						data.getImmutableMock(Property, data.mockProperty, 0, {
							label: 'Page Views',
							name: 'Page Views'
						})
					])
				}),
				new PropertySubgroup({
					label: 'DXP Custom Fields',
					properties: new List([
						data.getImmutableMock(Property, data.mockProperty, 0, {
							label: 'Page Actions',
							name: 'Page Actions'
						})
					])
				})
			])
		})
	]);

	afterEach(cleanup);

	it('should render', () => {
		const CriteriaSidebarCollapseWithContext = wrapInTestContext(
			CriteriaSidebarCollapse
		);

		const {container} = render(
			<CriteriaSidebarCollapseWithContext
				propertyGroupsIList={propertyGroupsIList}
				propertyKey='interests'
			/>
		);

		expect(container).toMatchSnapshot();
	});

	it('should render w/ no results for only one subgroup', () => {
		const CriteriaSidebarCollapseWithContext = wrapInTestContext(
			CriteriaSidebarCollapse
		);

		const {queryByText} = render(
			<CriteriaSidebarCollapseWithContext
				propertyGroupsIList={propertyGroupsIList}
				propertyKey='interests'
				searchValue='Actions'
			/>
		);

		expect(queryByText('No results were found.')).toBeTruthy();
		expect(queryByText('Page Views')).toBeNull();
		expect(queryByText('Page Actions')).toBeTruthy();
	});

	it('should render w/ no results', () => {
		const {queryByText} = render(
			<CriteriaSidebarCollapse
				propertyGroupsIList={propertyGroupsIList}
				searchValue='should not exist'
			/>
		);

		expect(queryByText('No results were found.')).toBeTruthy();
		expect(queryByText('Page Views')).toBeNull();
		expect(queryByText('Page Actions')).toBeNull();
	});
});
