import * as data from 'test/data';
import mockStore from 'test/mock-store';
import OverviewCDP from '../OverviewCDP';
import React from 'react';
import {cleanup, render} from '@testing-library/react';
import {Individual} from 'shared/util/records';
import {Provider} from 'react-redux';
import {StaticRouter} from 'react-router';
import {useCurrentUser} from 'shared/hooks/useCurrentUser';
import {useDataSources} from 'shared/context/dataSources';
import {useRequest} from 'shared/hooks/useRequest';

jest.unmock('react-dom');

jest.mock('shared/hooks/useCurrentUser', () => ({
	useCurrentUser: jest.fn()
}));

jest.mock('shared/context/dataSources', () => ({
	useDataSources: jest.fn()
}));

jest.mock('shared/hooks/useRequest', () => ({
	useRequest: jest.fn()
}));

jest.mock('../../components/ContextualInformation', () => ({
	__esModule: true,
	default: ({children, showEmptyState}) =>
		showEmptyState ? <>{children}</> : null
}));

jest.mock('../../hoc/ProfileCardCDP', () => ({
	__esModule: true,
	default: ({children, showEmptyState}) =>
		showEmptyState ? <>{children}</> : null
}));

const mockedUseCurrentUser = useCurrentUser;
const mockedUseDataSource = useDataSources;
const mockedUseRequest = useRequest;

const mockIndividual = data.getImmutableMock(Individual, data.mockIndividual);

const renderComponent = () =>
	render(
		<Provider store={mockStore()}>
			<StaticRouter>
				<OverviewCDP groupId='23' individual={mockIndividual} />
			</StaticRouter>
		</Provider>
	);

describe('IndividualOverview', () => {
	afterEach(() => {
		jest.clearAllMocks();
		cleanup();
	});

	it('should show connect data source button when no data sources exist', () => {
		mockedUseCurrentUser.mockReturnValue({isAdmin: () => true});
		mockedUseDataSource.mockReturnValue({empty: true});
		mockedUseRequest.mockReturnValue({
			data: {items: [], total: 0},
			loading: false
		});

		const {getAllByText} = renderComponent();

		expect(getAllByText('Connect Data Source').length).toBeGreaterThan(0);
	});

	it('should not show connect data source button when connected but no site data synced', () => {
		mockedUseCurrentUser.mockReturnValue({isAdmin: () => true});
		mockedUseDataSource.mockReturnValue({empty: false});
		mockedUseRequest.mockReturnValue({
			data: {items: [{sitesSelected: false}], total: 1},
			loading: false
		});

		const {queryByText} = renderComponent();

		expect(queryByText('Connect Data Source')).not.toBeInTheDocument();
	});
});
