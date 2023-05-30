import * as data from 'test/data';
import CriteriaSidebar from '../index';
import React from 'react';
import {cleanup, render} from '@testing-library/react';
import {DndProvider} from 'react-dnd';
import {HTML5Backend} from 'react-dnd-html5-backend';
import {List} from 'immutable';
import {Property, PropertyGroup, PropertySubgroup} from 'shared/util/records';

jest.unmock('react-dom');

describe('CriteriaSidebar', () => {
	afterEach(cleanup);

	it('should render', () => {
		const {container} = render(
			<DndProvider backend={HTML5Backend}>
				<CriteriaSidebar
					propertyGroupsIList={
						new List([
							new PropertyGroup({
								label: 'Interests',
								name: 'Interests',
								propertyKey: 'interests',
								propertySubgroups: new List([
									new PropertySubgroup({
										properties: new List([
											data.getImmutableMock(
												Property,
												data.mockProperty,
												0,
												{
													label: 'Page Views',
													name: 'Page Views'
												}
											)
										])
									}),
									new PropertySubgroup({
										label: 'DXP Custom Fields',
										properties: new List([
											data.getImmutableMock(
												Property,
												data.mockProperty,
												0,
												{
													label: 'Page Actions',
													name: 'Page Actions'
												}
											)
										])
									})
								])
							})
						])
					}
				/>
			</DndProvider>
		);

		expect(container).toMatchSnapshot();
	});

	it('should render w/ no results', () => {
		const {getByText} = render(
			<CriteriaSidebar propertyGroupsIList={new List()} />
		);

		expect(getByText('No results were found.')).toBeTruthy();
	});
});
