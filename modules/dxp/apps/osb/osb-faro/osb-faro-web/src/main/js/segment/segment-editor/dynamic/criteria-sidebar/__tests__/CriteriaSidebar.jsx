import * as data from 'test/data';
import CriteriaSidebar from '../index';
import React from 'react';
import {cleanup, render} from '@testing-library/react';
import {List} from 'immutable';
import {Property, PropertyGroup, PropertySubgroup} from 'shared/util/records';
import {wrapInTestContext} from 'react-dnd-test-utils';

jest.unmock('react-dom');

describe('CriteriaSidebar', () => {
	afterEach(cleanup);

	it('should render', () => {
		const CriteriaSidebarWithContext = wrapInTestContext(CriteriaSidebar);

		const {container} = render(
			<CriteriaSidebarWithContext
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
