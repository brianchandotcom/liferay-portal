import * as useDataSource from 'shared/hooks/useDataSource';
import Interests from '../Interests';
import mockStore from 'test/mock-store';
import React from 'react';
import {cleanup, render} from '@testing-library/react';
import {MemoryRouter, Route} from 'react-router-dom';
import {MockedProvider} from '@apollo/react-testing';
import {mockEmptyState, mockSuccessState} from 'test/__mocks__/mock-objects';
import {mockIndividualInterestsReq} from 'test/graphql-data';
import {Provider} from 'react-redux';
import {Routes} from 'shared/util/router';
import {waitForLoadingToBeRemoved} from 'test/helpers';

jest.unmock('react-dom');

const mockUseDataSource = useDataSource;

const WrappedComponent = () => (
	<MockedProvider
		mocks={[
			mockIndividualInterestsReq(defaultVars => ({
				...defaultVars,
				id: undefined,
				keywords: '',
				size: 2
			}))
		]}
	>
		<Provider store={mockStore()}>
			<MemoryRouter
				initialEntries={[
					'/workspace/123/456/contacts/individuals/interests'
				]}
			>
				<Route path={Routes.CONTACTS_INDIVIDUALS_INTERESTS}>
					<Interests />
				</Route>
			</MemoryRouter>
		</Provider>
	</MockedProvider>
);

describe('Individuals Dashboard Individuals Interests', () => {
	afterEach(cleanup);

	it('renders', async () => {
		mockUseDataSource.useDataSource = jest.fn(() => mockSuccessState);

		const {container} = render(<WrappedComponent />);

		await waitForLoadingToBeRemoved(container);

		jest.runAllTimers();

		expect(container).toMatchSnapshot();
	});

	it('renders with data source empty state', async () => {
		mockUseDataSource.useDataSource = jest.fn(() => mockEmptyState);

		const {container} = render(<WrappedComponent />);

		expect(container).toMatchSnapshot();
	});
});
