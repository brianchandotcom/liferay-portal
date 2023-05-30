import * as data from 'test/data';
import CriteriaSidebarCollapse from '../CriteriaSidebarCollapse';
import React from 'react';
import {cleanup, render} from '@testing-library/react';
import {DndProvider} from 'react-dnd';
import {HTML5Backend} from 'react-dnd-html5-backend';
import {List} from 'immutable';
import {Property, PropertyGroup, PropertySubgroup} from 'shared/util/records';

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
		const {container} = render(
			<DndProvider backend={HTML5Backend}>
				<CriteriaSidebarCollapse
					propertyGroupsIList={propertyGroupsIList}
					propertyKey='interests'
				/>
			</DndProvider>
		);

		expect(container).toMatchSnapshot();
	});

	it('should render w/ no results for only one subgroup', () => {
		const {queryByText} = render(
			<DndProvider backend={HTML5Backend}>
				<CriteriaSidebarCollapse
					propertyGroupsIList={propertyGroupsIList}
					propertyKey='interests'
					searchValue='Actions'
				/>
			</DndProvider>
		);

		expect(queryByText('No results were found.')).toBeTruthy();
		expect(queryByText('Page Views')).toBeNull();
		expect(queryByText('Page Actions')).toBeTruthy();
	});

	it('should render w/ no results', () => {
		const {queryByText} = render(
			<DndProvider backend={HTML5Backend}>
				<CriteriaSidebarCollapse
					propertyGroupsIList={propertyGroupsIList}
					searchValue='should not exist'
				/>
			</DndProvider>
		);

		expect(queryByText('No results were found.')).toBeTruthy();
		expect(queryByText('Page Views')).toBeNull();
		expect(queryByText('Page Actions')).toBeNull();
	});
});
