import * as data from 'test/data';
import mockStore from 'test/mock-store';
import React from 'react';
import {BrowserRouter} from 'react-router-dom';
import {DataSourceStates} from 'shared/util/constants';
import {fromJS} from 'immutable';
import {Project, User} from 'shared/util/records';
import {Provider} from 'react-redux';
import {render} from '@testing-library/react';
import {Routes, setUriQueryValue, toRoute} from 'shared/util/router';
import {routingFn, SelectWorkspaceAccount} from '../SelectWorkspaceAccount';

const mockBusinessProject = new Project(
	data.mockProject(123, {
		faroSubscription: fromJS(
			data.mockSubscription({
				name: 'Liferay Analytics Cloud Business'
			})
		),
		name: 'Project A'
	})
);

const mockBasicSubscription = data.mockSubscription({
	name: 'Liferay Analytics Cloud Basic'
});

jest.unmock('react-dom');

describe('SelectWorkspaceAccount', () => {
	it('should render', () => {
		const mockProjects = [
			new Project(
				data.mockProject(126, {
					faroSubscription: fromJS(mockBasicSubscription),
					name: 'Project C'
				})
			),
			new Project(
				data.mockProject(123, {
					faroSubscription: fromJS(mockBasicSubscription),
					name: '',
					state: DataSourceStates.Unconfigured
				})
			)
		];

		const {container} = render(
			<Provider store={mockStore()}>
				<BrowserRouter>
					<SelectWorkspaceAccount
						currentUser={data.getImmutableMock(User, data.mockUser)}
						projects={mockProjects}
					/>
				</BrowserRouter>
			</Provider>
		);

		jest.runAllTimers();
		expect(container).toMatchSnapshot();
	});
});

describe('routingFn', () => {
	it('should route the user to the workspaces list if there are multiple basic workspaces and all are configured', () => {
		const expectedRoute = setUriQueryValue(
			toRoute(Routes.WORKSPACES),
			'allBasicConfigured',
			true
		);

		const mockProjects = [
			new Project(
				data.mockProject(124, {
					faroSubscription: fromJS(mockBasicSubscription),
					name: 'Project B'
				})
			),
			new Project(
				data.mockProject(125, {
					faroSubscription: fromJS(mockBasicSubscription),
					name: 'Project C'
				})
			)
		];

		expect(routingFn({projects: mockProjects})).toBe(expectedRoute);
	});

	it('should route the user to the workspace homepage if there is only one basic tier workspace and it is configured', () => {
		const expectedRoute = toRoute(Routes.WORKSPACE_WITH_ID, {
			groupId: '127'
		});

		const mockProjects = [
			mockBusinessProject,
			new Project(
				data.mockProject(127, {
					faroSubscription: fromJS(mockBasicSubscription),
					name: 'Project B'
				})
			)
		];

		expect(routingFn({projects: mockProjects})).toBe(expectedRoute);
	});

	it('should route the user to the create workspace form if there is only one basic tier workspace and it is NOT configured', () => {
		const corpProjectUuid = 'corpProjectId-124';
		const expectedRoute = toRoute(
			Routes.WORKSPACE_ADD_WITH_CORP_PROJECT_UUID,
			{
				corpProjectUuid
			}
		);

		const mockProjects = [
			mockBusinessProject,
			new Project(
				data.mockProject(0, {
					corpProjectUuid,
					faroSubscription: fromJS(mockBasicSubscription),
					name: ''
				})
			)
		];

		expect(routingFn({projects: mockProjects})).toBe(expectedRoute);
	});
});
