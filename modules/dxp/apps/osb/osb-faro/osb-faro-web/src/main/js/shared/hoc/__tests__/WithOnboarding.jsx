import 'test/mock-modal';

import * as API from 'shared/api';
import mockStore from 'test/mock-store';
import Promise from 'metal-promise';
import React from 'react';
import withOnboarding from '../WithOnboarding';
import {cleanup, render} from '@testing-library/react';
import {mockDataSourcesReq} from 'test/graphql-data';
import {MockedProvider} from '@apollo/react-testing';
import {mockMemberUser} from 'test/data';
import {OnboardingContext} from 'shared/context/onboarding';
import {open} from 'shared/actions/modals';
import {Provider} from 'react-redux';

jest.unmock('react-dom');

const WrappedComponent = withOnboarding(() => 'wrapped component text');

const defaultContext = {
	onboardingTriggered: false,
	setOnboardingTriggered: jest.fn()
};

const DefaultComponent = props => (
	<Provider store={mockStore()}>
		<OnboardingContext.Provider value={defaultContext}>
			<MockedProvider mocks={[mockDataSourcesReq()]}>
				<WrappedComponent {...props} />
			</MockedProvider>
		</OnboardingContext.Provider>
	</Provider>
);

describe('WithOnboarding', () => {
	afterEach(() => {
		cleanup();
		jest.clearAllMocks();
	});

	it('should render the wrapped component', () => {
		const {container} = render(
			<Provider store={mockStore()}>
				<OnboardingContext.Provider value={defaultContext}>
					<MockedProvider
						mocks={[
							mockDataSourcesReq([
								{
									__typename: 'DataSource',
									id: '123',
									name: 'foo datasource',
									url: 'foo.url'
								}
							])
						]}
					>
						<WrappedComponent />
					</MockedProvider>
				</OnboardingContext.Provider>
			</Provider>
		);

		expect(container.textContent).toBe('wrapped component text');
	});

	it('should trigger the onboarding modal if there are no dxp datasources', () => {
		render(<DefaultComponent />);

		jest.runAllTimers();

		expect(open).toBeCalled();
	});

	it('should not trigger the onboarding modal for non-admin users', () => {
		API.user.fetchCurrentUser.mockReturnValueOnce(
			Promise.resolve(mockMemberUser('23'))
		);

		render(<DefaultComponent />);

		jest.runAllTimers();

		expect(open).not.toBeCalled();
	});

	it('should not trigger the onboarding modal if it has already been triggered', () => {
		render(
			<Provider store={mockStore()}>
				<OnboardingContext.Provider
					value={{
						...defaultContext,
						onboardingTriggered: true
					}}
				>
					<MockedProvider mocks={[mockDataSourcesReq()]}>
						<WrappedComponent />
					</MockedProvider>
				</OnboardingContext.Provider>
			</Provider>
		);

		jest.runAllTimers();

		expect(open).not.toBeCalled();
	});

	it('should not trigger the onboarding modal when a prop happens to match a field in the SitesDashboardQuery', () => {
		render(
			<Provider store={mockStore()}>
				<OnboardingContext.Provider value={defaultContext}>
					<MockedProvider
						mocks={[mockDataSourcesReq([], {type: 'DYNAMIC'})]}
					>
						<WrappedComponent type='DYNAMIC' />
					</MockedProvider>
				</OnboardingContext.Provider>
			</Provider>
		);

		jest.runAllTimers();

		expect(open).not.toBeCalled();
	});
});
