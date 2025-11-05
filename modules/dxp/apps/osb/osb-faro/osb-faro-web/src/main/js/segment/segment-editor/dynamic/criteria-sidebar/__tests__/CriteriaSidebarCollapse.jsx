import * as data from 'test/data';
import CriteriaSidebarCollapse from '../CriteriaSidebarCollapse';
import React from 'react';
import {cleanup, render, screen} from '@testing-library/react';
import {DndProvider} from 'react-dnd';
import {FieldOwnerTypes} from 'shared/util/constants';
import {HTML5Backend} from 'react-dnd-html5-backend';
import {List} from 'immutable';
import {Property, PropertyGroup, PropertySubgroup} from 'shared/util/records';

jest.mock('shared/hooks/useCurrentUser', () => ({
	useCurrentUser: () => ({isAdmin: () => true})
}));

jest.mock('react-router-dom', () => ({
	useParams: () => ({groupId: '12345'})
}));

const mockLiferayLanguage = key => {
	const messages = {
		'connect-a-data-source-containing-account-data':
			'connect-a-data-source-containing-account-data',
		'connect-data-source': 'connect-data-source',
		'learn-more-about-data-sources': 'learn-more-about-data-sources',
		'no-account-data-synced': 'no-account-data-synced',
		'no-results-found': 'no-results-found',
		'no-results-were-found': 'no-results-were-found',
		'review-your-search-and-try-again': 'review-your-search-and-try-again'
	};
	return messages[key] || key;
};

global.Liferay = {
	Language: {
		get: mockLiferayLanguage
	}
};

jest.unmock('react-dom');

describe('CriteriaSidebarCollapse', () => {
	const ACCOUNT_KEY = FieldOwnerTypes.Account;

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
		}),

		new PropertyGroup({
			label: 'Account Data',
			name: 'Account Data',
			propertyKey: ACCOUNT_KEY,
			propertySubgroups: List()
		})
	]);

	const propertyGroupsIListCustomEvents = new List([
		new PropertyGroup({
			label: 'Events',
			name: 'Events',
			propertyKey: 'events',
			propertySubgroups: new List([
				new PropertySubgroup({
					label: 'Custom Events',
					properties: new List([
						data.getImmutableMock(Property, data.mockProperty, 0, {
							label: 'Custom Event 1',
							name: 'Custom Event 1'
						}),

						data.getImmutableMock(Property, data.mockProperty, 0, {
							label: 'Custom Event 2',
							name: 'Custom Event 2'
						}),

						data.getImmutableMock(Property, data.mockProperty, 0, {
							label: 'Custom Event 3',
							name: 'Custom Event 3'
						})
					])
				})
			])
		})
	]);

	afterEach(cleanup);

	it('should render correctly', () => {
		const {container} = render(
			<DndProvider backend={HTML5Backend}>
				<CriteriaSidebarCollapse
					propertyGroupsIList={propertyGroupsIList}
					propertyKey='interests'
					searchValue=''
				/>
			</DndProvider>
		);

		expect(screen.getByText('Page Views')).toBeInTheDocument();
		expect(screen.getByText('DXP Custom Fields')).toBeInTheDocument();

		expect(container).toMatchSnapshot();
	});

	it('should filter properties and show "no-results-were-found" for the empty subgroup', () => {
		const {queryByText} = render(
			<DndProvider backend={HTML5Backend}>
				<CriteriaSidebarCollapse
					propertyGroupsIList={propertyGroupsIList}
					propertyKey='interests'
					searchValue='Views'
				/>
			</DndProvider>
		);

		expect(queryByText('Page Views')).toBeInTheDocument();

		expect(queryByText('Page Actions')).toBeNull();

		const secondListItem = document.querySelector(
			'.property-subgroups-list > li:nth-child(2)'
		);
		expect(secondListItem).toHaveTextContent('no-results-were-found');

		expect(queryByText('no-results-found')).toBeNull();
	});

	it('should render w/ global EmptyState "no-results-found" when no properties match the search', () => {
		const {queryByText} = render(
			<DndProvider backend={HTML5Backend}>
				<CriteriaSidebarCollapse
					propertyGroupsIList={propertyGroupsIList}
					propertyKey='interests'
					searchValue='should not exist'
				/>
			</DndProvider>
		);

		expect(queryByText('no-results-found')).toBeInTheDocument();
		expect(
			queryByText('review-your-search-and-try-again')
		).toBeInTheDocument();

		expect(queryByText('Page Views')).toBeNull();
		expect(queryByText('Page Actions')).toBeNull();
		expect(queryByText('no-results-were-found')).toBeNull();
	});

	it('should render custom events in the sidebar', () => {
		const {queryByText} = render(
			<DndProvider backend={HTML5Backend}>
				<CriteriaSidebarCollapse
					propertyGroupsIList={propertyGroupsIListCustomEvents}
					propertyKey='events'
					searchValue=''
				/>
			</DndProvider>
		);

		expect(queryByText('Custom Events')).toBeInTheDocument();
		expect(queryByText('Custom Event 1')).toBeInTheDocument();
		expect(queryByText('Custom Event 2')).toBeInTheDocument();
		expect(queryByText('Custom Event 3')).toBeInTheDocument();
	});

	it('should render the Account EmptyState when propertyKey is Account and there are no properties', () => {
		const {getByRole, queryByText} = render(
			<DndProvider backend={HTML5Backend}>
				<CriteriaSidebarCollapse
					propertyGroupsIList={propertyGroupsIList}
					propertyKey={ACCOUNT_KEY}
					searchValue=''
				/>
			</DndProvider>
		);

		expect(queryByText('no-account-data-synced')).toBeInTheDocument();
		expect(
			queryByText('connect-a-data-source-containing-account-data')
		).toBeInTheDocument();
		expect(
			queryByText('learn-more-about-data-sources')
		).toBeInTheDocument();
		expect(queryByText('connect-data-source')).toBeInTheDocument();

		const connectLink = getByRole('link', {
			name: /connect-data-source/i
		});
		expect(connectLink).toHaveAttribute(
			'href',
			'/workspace/12345/settings/data-source'
		);
	});

	it('should not render Account EmptyState when there are properties, even if propertyKey is Account', () => {
		const propertyGroupsWithAccountData = new List([
			new PropertyGroup({
				label: 'Account Data',
				name: 'Account Data',
				propertyKey: ACCOUNT_KEY,
				propertySubgroups: new List([
					new PropertySubgroup({
						properties: new List([
							data.getImmutableMock(
								Property,
								data.mockProperty,
								0,
								{
									label: 'Account Name',
									name: 'accountName'
								}
							)
						])
					})
				])
			})
		]);

		const {queryByText} = render(
			<DndProvider backend={HTML5Backend}>
				<CriteriaSidebarCollapse
					propertyGroupsIList={propertyGroupsWithAccountData}
					propertyKey={ACCOUNT_KEY}
					searchValue=''
				/>
			</DndProvider>
		);

		expect(queryByText('Account Name')).toBeInTheDocument();

		expect(queryByText('no-account-data-synced')).toBeNull();
	});
});
