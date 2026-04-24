import AccountsDataSet from '../AccountsDataSet';
import React from 'react';
import {cleanup, render, screen} from '@testing-library/react';

jest.unmock('react-dom');

const useFrontendDataSetMock = jest.fn();

jest.mock('shared/hooks/useFrontendDataSet', () => ({
	useFrontendDataSet: () => useFrontendDataSetMock()
}));

type FakeFilter = {
	id: string;
	preloadedData?: {
		exclude: boolean;
		selectedItems: Array<{label?: string; value: string}>;
	};
};

let lastFilters: FakeFilter[] | undefined;

const FakeDataSet = ({filters, id}: {filters: FakeFilter[]; id: string}) => {
	lastFilters = filters;

	return <div data-testid='fds-component' id={id} />;
};

describe('AccountsDataSet', () => {
	beforeEach(() => {
		jest.clearAllMocks();
		lastFilters = undefined;
		useFrontendDataSetMock.mockReturnValue(FakeDataSet);
	});

	afterEach(cleanup);

	it('should render nothing while FrontendDataSet is loading', () => {
		useFrontendDataSetMock.mockReturnValue(null);

		const {container} = render(
			<AccountsDataSet channelId='123' groupId='23' />
		);

		expect(container).toBeEmptyDOMElement();
	});

	it('should render the FrontendDataSet with id "accounts-list-dataset"', () => {
		render(<AccountsDataSet channelId='123' groupId='23' />);

		expect(screen.getByTestId('fds-component')).toHaveAttribute(
			'id',
			'accounts-list-dataset'
		);
	});

	it('should leave country/industry filters without preloadedData when no props are passed', () => {
		render(<AccountsDataSet channelId='123' groupId='23' />);

		const countryFilter = lastFilters?.find(f => f.id === 'country');
		const industryFilter = lastFilters?.find(f => f.id === 'industry');

		expect(countryFilter?.preloadedData).toBeUndefined();
		expect(industryFilter?.preloadedData).toBeUndefined();
	});

	it('should preload the country filter when countryFilter prop is provided', () => {
		render(
			<AccountsDataSet channelId='123' countryFilter='US' groupId='23' />
		);

		const countryFilter = lastFilters?.find(f => f.id === 'country');

		expect(countryFilter?.preloadedData).toEqual({
			exclude: false,
			selectedItems: [{label: 'US', value: 'US'}]
		});
	});

	it('should preload the industry filter when industryFilter prop is provided', () => {
		render(
			<AccountsDataSet
				channelId='123'
				groupId='23'
				industryFilter='Tech'
			/>
		);

		const industryFilter = lastFilters?.find(f => f.id === 'industry');

		expect(industryFilter?.preloadedData).toEqual({
			exclude: false,
			selectedItems: [{label: 'Tech', value: 'Tech'}]
		});
	});
});
