import * as data from 'test/data';
import client from 'shared/apollo/client';
import mockStore from 'test/mock-store';
import React from 'react';
import Workspaces, {routingFn} from '../Workspaces';
import {ApolloProvider} from '@apollo/react-components';
import {BrowserRouter} from 'react-router-dom';
import {cleanup, render} from '@testing-library/react';
import {Map} from 'immutable';
import {noop} from 'lodash';
import {Project} from 'shared/util/records';
import {Provider} from 'react-redux';
import {Routes, toRoute} from 'shared/util/router';
import {useFetchProjects} from 'shared/hooks/useProjects';

const corpProjectUuid = 'corpProjectUuid24';

const mockBasicProject = id =>
	data.getImmutableMock(Project, data.mockProject, id, {
		faroSubscription: new Map(
			data.mockSubscription({
				name: 'Liferay Analytics Cloud Basic'
			})
		)
	});

const mockBasicProjectUnconfigured = id =>
	data.getImmutableMock(Project, data.mockProject, id, {
		corpProjectUuid,
		faroSubscription: new Map(
			data.mockSubscription({
				name: 'Liferay Analytics Cloud Basic'
			})
		)
	});

const DefaultComponent = props => (
	<ApolloProvider client={client}>
		<Provider store={mockStore()}>
			<BrowserRouter>
				<Workspaces {...props} clearStore={jest.fn()} />
			</BrowserRouter>
		</Provider>
	</ApolloProvider>
);

jest.mock('shared/hooks/useProjects', () => ({
	useFetchJoinableProjects: () => ({data: [], loading: false}),
	useFetchProjects: jest.fn()
}));

jest.unmock('react-dom');

describe('Workspaces', () => {
	afterEach(cleanup);

	const client = {
		clearStore: noop
	};

	it('should render empty state', () => {
		useFetchProjects.mockImplementation(() => ({data: [], loading: false}));

		const {container} = render(<DefaultComponent />);

		expect(container).toMatchSnapshot();
	});

	it('should render with a message that one or more basic tier workspaces are available to be configured', () => {
		const mockProjects = [
			mockBasicProjectUnconfigured(124),
			data.getImmutableMock(Project, data.mockProject, 123, {
				faroSubscription: new Map(
					data.mockSubscription({
						name: 'Liferay Analytics Cloud Business'
					})
				)
			})
		];

		useFetchProjects.mockImplementation(() => ({
			data: mockProjects,
			loading: false
		}));

		const {container} = render(<DefaultComponent />);

		expect(container).toMatchSnapshot();
	});

	it('should render with a message that all basic tier plans have been configured if all of the basic tier plans have been configured and the allBasicConfigured url prop is true', () => {
		useFetchProjects.mockImplementation(() => ({
			data: [mockBasicProject(123), mockBasicProject(124)],
			loading: false
		}));

		const {container} = render(
			<DefaultComponent allBasicConfigured client={client} />
		);

		expect(container).toMatchSnapshot();
	});

	it('should render a list of projects if there is only one project and it is configured', () => {
		useFetchProjects.mockImplementation(() => ({
			data: [mockBasicProject(23)],
			loading: false
		}));

		const {container} = render(<DefaultComponent />);

		expect(container).toMatchSnapshot();
	});
});

describe('routingFn', () => {
	it('should route the user to the create workspace page if there is only one workspace and it is NOT configured', () => {
		const expectedRoute = toRoute(
			Routes.WORKSPACE_ADD_WITH_CORP_PROJECT_UUID,
			{
				corpProjectUuid
			}
		);

		expect(routingFn({projects: [mockBasicProjectUnconfigured(0)]})).toBe(
			expectedRoute
		);
	});
});
