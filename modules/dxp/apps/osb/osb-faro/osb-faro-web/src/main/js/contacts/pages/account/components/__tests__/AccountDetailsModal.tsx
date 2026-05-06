import AccountDetailsModal from '../AccountDetailsModal';
import React from 'react';
import ReactDOM from 'react-dom';
import {act, cleanup, render, screen} from '@testing-library/react';
import {useFDSState} from 'shared/hooks/useFDSState';
import {useRequest} from 'shared/hooks/useRequest';

jest.unmock('react-dom');

jest.mock('shared/hooks/useFrontendDataSet', () => ({
	useFrontendDataSet: () => {
		const FakeDataSet = ({id, items}: {id: string; items: any[]}) => (
			<div data-testid='fds-component' id={id}>
				{(items ?? []).map((item: any) => (
					<div data-testid='fds-item' key={item.name}>
						{item.name}
					</div>
				))}
			</div>
		);

		return FakeDataSet;
	}
}));

jest.mock('shared/hooks/useFDSState', () => ({
	useFDSState: jest.fn(() => ({filters: [], search: ''}))
}));

jest.mock('shared/hooks/useRequest', () => ({
	useRequest: jest.fn()
}));

jest.mock('react-router-dom', () => ({
	...jest.requireActual('react-router-dom'),
	useParams: () => ({channelId: '456', groupId: '23'})
}));

const mockedUseFDSState = useFDSState as jest.Mock;
const mockedUseRequest = useRequest as jest.Mock;

const mockFields = [
	{
		dataSourceId: 'ds-1',
		dataSourceName: 'Salesforce',
		lastModified: '2024-11-20T08:30:00.000Z',
		name: 'website',
		sourceName: 'Web',
		value: 'https://acme.com'
	},
	{
		dataSourceId: 'ds-1',
		dataSourceName: 'Salesforce',
		lastModified: '2024-10-15T10:00:00.000Z',
		name: 'industry',
		sourceName: 'CRM',
		value: 'Technology'
	}
];

const renderModal = (
	overrides: Partial<{
		accountId: string;
		accountName: string;
		onClose: () => void;
	}> = {}
) => {
	const result = render(
		<AccountDetailsModal
			accountId='abc'
			accountName='Acme Corp'
			onClose={jest.fn()}
			{...overrides}
		/>
	);

	act(() => {
		jest.runAllTimers();
	});

	return result;
};

describe('AccountDetailsModal', () => {
	beforeAll(() => {
		// @ts-ignore

		ReactDOM.createPortal = jest.fn(element => element);
	});

	beforeEach(() => {
		jest.clearAllMocks();
		mockedUseFDSState.mockReturnValue({filters: [], search: ''});
		mockedUseRequest.mockReturnValue({data: {fields: mockFields}});
	});

	afterEach(cleanup);

	it("should render the modal title with the account name's possessive form", () => {
		renderModal();

		expect(screen.getByText("Acme Corp's Attributes")).toBeInTheDocument();
	});

	it('should call useRequest with the account, channel, and group ids', () => {
		renderModal();

		expect(mockedUseRequest).toHaveBeenCalledWith(
			expect.objectContaining({
				variables: {accountId: 'abc', channelId: '456', groupId: '23'}
			})
		);
	});

	it('should render a row in the data set for each field returned by the request', () => {
		renderModal();

		expect(screen.getByTestId('fds-component')).toBeInTheDocument();

		const items = screen.getAllByTestId('fds-item');

		expect(items).toHaveLength(2);
		expect(items[0]).toHaveTextContent('website');
		expect(items[1]).toHaveTextContent('industry');
	});

	it('should render no rows when the request has no data yet', () => {
		mockedUseRequest.mockReturnValue({data: undefined});

		renderModal();

		expect(screen.queryAllByTestId('fds-item')).toHaveLength(0);
	});

	it('should filter items by name when the data set search has a query', () => {
		mockedUseFDSState.mockReturnValue({filters: [], search: 'industry'});

		renderModal();

		const items = screen.getAllByTestId('fds-item');

		expect(items).toHaveLength(1);
		expect(items[0]).toHaveTextContent('industry');
	});

	it('should match the name filter case-insensitively', () => {
		mockedUseFDSState.mockReturnValue({filters: [], search: 'WEB'});

		renderModal();

		const items = screen.getAllByTestId('fds-item');

		expect(items).toHaveLength(1);
		expect(items[0]).toHaveTextContent('website');
	});
});
