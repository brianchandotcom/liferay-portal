jest.mock('shared/components/workspaces/SuccessDisplay', () => () =>
	'SuccessDisplay'
);

import * as API from 'shared/api';
import checkProjectState from '../CheckProjectState';
import mockStore from 'test/mock-store';
import Promise from 'metal-promise';
import React from 'react';
import {cleanup, render} from '@testing-library/react';
import {Provider} from 'react-redux';
import {StaticRouter} from 'react-router';

jest.unmock('react-dom');

class TestComponent extends React.Component {
	render() {
		return <div>{'component body'}</div>;
	}
}

describe('SuccessDisplayIf', () => {
	afterEach(cleanup);

	it('should render the WrappedComponent', () => {
		const WrappedComponent = checkProjectState(TestComponent);

		const {container} = render(
			<Provider store={mockStore()}>
				<WrappedComponent groupId='23' />
			</Provider>
		);

		expect(container).toMatchSnapshot();
	});

	it('should render the SuccessDisplay component if the project is not ready', () => {
		const WrappedComponent = checkProjectState(TestComponent);

		const {container} = render(
			<Provider store={mockStore()}>
				<WrappedComponent groupId='24' />
			</Provider>
		);

		expect(container).toMatchSnapshot();
	});

	it('should render a scheduled maintenance error page if the project state is "maintenance"', () => {
		const WrappedComponent = checkProjectState(TestComponent);

		const {container} = render(
			<Provider store={mockStore()}>
				<WrappedComponent groupId='25' />
			</Provider>
		);

		expect(container).toMatchSnapshot();
	});

	it('should render an unavailable error page if the project state is "unavailable"', () => {
		const WrappedComponent = checkProjectState(TestComponent);

		const {container} = render(
			<Provider store={mockStore()}>
				<WrappedComponent groupId='26' />
			</Provider>
		);

		expect(container).toMatchSnapshot();
	});

	it('should render an unavailable error page if the project state is "scheduled"', () => {
		const WrappedComponent = checkProjectState(TestComponent);

		const {container} = render(
			<Provider store={mockStore()}>
				<WrappedComponent groupId='27' />
			</Provider>
		);

		expect(container).toMatchSnapshot();
	});

	it('should render activating display if project state is "activating"', () => {
		const WrappedComponent = checkProjectState(TestComponent);

		const {container} = render(
			<StaticRouter>
				<Provider store={mockStore()}>
					<WrappedComponent groupId='28' />
				</Provider>
			</StaticRouter>
		);

		expect(container).toMatchSnapshot();
	});

	it('should render activating display if project state is "deactivated"', () => {
		const WrappedComponent = checkProjectState(TestComponent);

		const {container} = render(
			<StaticRouter>
				<Provider store={mockStore()}>
					<WrappedComponent groupId='29' />
				</Provider>
			</StaticRouter>
		);

		expect(container).toMatchSnapshot();
	});

	it('should render an error page if there was an error fetching the project', () => {
		API.projects.fetch.mockReturnValue(
			Promise.reject({message: 'foo rejection from server'})
		);

		const WrappedComponent = checkProjectState(TestComponent);

		const {container} = render(
			<StaticRouter>
				<Provider store={mockStore()}>
					<WrappedComponent groupId='23' />
				</Provider>
			</StaticRouter>
		);

		jest.runAllTimers();

		expect(container).toMatchSnapshot();
	});
});
