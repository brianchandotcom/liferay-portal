import * as useDataSource from 'shared/hooks/useDataSource';
import KnownIndividuals from '../KnownIndividuals';
import mockStore from 'test/mock-store';
import React from 'react';
import {cleanup, render} from '@testing-library/react';
import {MemoryRouter, Route} from 'react-router-dom';
import {mockEmptyState, mockSuccessState} from 'test/__mocks__/mock-objects';
import {Provider} from 'react-redux';
import {Routes} from 'shared/util/router';
import {waitForLoadingToBeRemoved} from 'test/helpers';

jest.unmock('react-dom');

const mockUseDataSource = useDataSource;

const WrappedComponent = () => (
	<Provider store={mockStore()}>
		<MemoryRouter
			initialEntries={[
				'/workspace/23/321321/contacts/individuals/known-individuals'
			]}
		>
			<Route path={Routes.CONTACTS_INDIVIDUALS_KNOWN_INDIVIDUALS}>
				<KnownIndividuals />
			</Route>
		</MemoryRouter>
	</Provider>
);

describe('Individuals Dashboard KnownIndividuals List', () => {
	afterEach(cleanup);

	it('renders', async () => {
		mockUseDataSource.useDataSource = jest.fn(() => mockSuccessState);

		const {container} = render(<WrappedComponent />);

		await waitForLoadingToBeRemoved(container);

		jest.runAllTimers();

		expect(container).toMatchSnapshot();
	});

	it('renders with data source empty state', () => {
		mockUseDataSource.useDataSource = jest.fn(() => mockEmptyState);

		const {container} = render(<WrappedComponent />);

		expect(container).toMatchSnapshot();
	});
});
