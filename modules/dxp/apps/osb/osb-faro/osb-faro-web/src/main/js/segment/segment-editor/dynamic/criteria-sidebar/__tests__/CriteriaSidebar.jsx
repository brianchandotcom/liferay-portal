import * as data from 'test/data';
import CriteriaSidebar from '../index';
import React from 'react';
import {cleanup, render, screen} from '@testing-library/react';
import {DndProvider} from 'react-dnd';
import {HTML5Backend} from 'react-dnd-html5-backend';
import {List} from 'immutable';
import {Property, PropertyGroup, PropertySubgroup} from 'shared/util/records';

const mockLiferayLanguage = key => {
	const messages = {
		'no-results-were-found': 'No results were found.'
	};
	return messages[key] || key;
};

global.Liferay = {
	Language: {
		get: mockLiferayLanguage
	}
};

jest.mock('shared/hooks/useCurrentUser', () => ({
	useCurrentUser: () => ({isAdmin: () => true})
}));

jest.mock('react-router-dom', () => ({
	useParams: () => ({groupId: '12345'})
}));

jest.unmock('react-dom');

describe('CriteriaSidebar', () => {
	const fullPropertyGroupList = new List([
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

	it('should render the sidebar structure with property groups', () => {
		render(
			<DndProvider backend={HTML5Backend}>
				<CriteriaSidebar propertyGroupsIList={fullPropertyGroupList} />
			</DndProvider>
		);

		expect(
			screen.getByText('Interests', {selector: '.dropdown-toggle *'})
		).toBeInTheDocument();
		expect(screen.getByText('Page Views')).toBeInTheDocument();
		expect(screen.getByText('DXP Custom Fields')).toBeInTheDocument();
	});

	it('should render w/ "No results were found." when propertyGroupsIList is empty', () => {
		render(<CriteriaSidebar propertyGroupsIList={new List()} />);

		expect(
			screen.queryByText('No results were found.')
		).not.toBeInTheDocument();
	});
});
