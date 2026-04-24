import AccountsDataSet from '../AccountsDataSet';
import React from 'react';
import {cleanup, render, screen} from '@testing-library/react';

jest.unmock('react-dom');

const useFrontendDataSetMock = jest.fn();

jest.mock('shared/hooks/useFrontendDataSet', () => ({
	useFrontendDataSet: () => useFrontendDataSetMock()
}));

const FakeDataSet = ({id}: {id: string}) => (
	<div data-testid='fds-component' id={id} />
);

describe('AccountsDataSet', () => {
	beforeEach(() => {
		jest.clearAllMocks();
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
});
