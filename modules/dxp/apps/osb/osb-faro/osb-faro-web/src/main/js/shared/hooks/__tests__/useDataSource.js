import * as API from 'shared/api';
import * as data from 'test/data';
import React from 'react';
import {BrowserRouter} from 'react-router-dom';
import {
	cleanup,
	render,
	waitForElementToBeRemoved
} from '@testing-library/react';
import {useDataSource} from '../useDataSource';

jest.unmock('react-dom');

const WrapperComponent = props => (
	<BrowserRouter>
		<MockComponent {...props} />
	</BrowserRouter>
);
const MockComponent = queryParams => {
	const {empty, error, items, loading} = useDataSource(queryParams);

	return (
		<>
			{empty && <p>{'empty'}</p>}

			{error && <p>{'error'}</p>}

			{items && (
				<div id='items'>
					{items.map((_, index) => (
						<p key={index}>{'item'}</p>
					))}
				</div>
			)}

			{loading && <p>{'loading'}</p>}
		</>
	);
};

describe('useDataSource', () => {
	afterEach(cleanup);

	it('should render empty', async () => {
		API.dataSource.search.mockReturnValueOnce(
			Promise.resolve(data.mockSearch([], 0))
		);
		const {getByText} = render(<WrapperComponent />);

		await waitForElementToBeRemoved(() => getByText('loading'));

		expect(getByText('empty')).toBeInTheDocument();
	});

	it('should render error', async () => {
		API.dataSource.search.mockReturnValueOnce(
			Promise.reject({IS_CANCELLATION_ERROR: ''})
		);
		const {getByText} = render(<WrapperComponent />);

		await waitForElementToBeRemoved(() => getByText('loading'));

		expect(getByText('error')).toBeInTheDocument();
	});

	it('should render loading', () => {
		API.dataSource.search.mockReturnValueOnce(
			Promise.resolve(data.mockSearch(data.mockLiferayDataSource, 1))
		);
		const {getByText} = render(<WrapperComponent />);

		expect(getByText('loading')).toBeInTheDocument();
	});

	it('should render success', async () => {
		API.dataSource.search.mockReturnValueOnce(
			Promise.resolve(data.mockSearch(data.mockLiferayDataSource, 1))
		);
		const {container, getByText} = render(<WrapperComponent />);

		await waitForElementToBeRemoved(() => getByText('loading'));

		const itemsSelector = container.querySelector('#items');

		expect(itemsSelector.children).toHaveLength(1);
		expect(getByText('item')).toBeInTheDocument();
	});

	it('should render success when queryPagination true', async () => {
		API.dataSource.search.mockReturnValueOnce(
			Promise.resolve(data.mockSearch(data.mockLiferayDataSource, 1))
		);
		const {container, getByText} = render(
			<WrapperComponent
				queryParams={{
					delta: 1,
					page: 1,
					query: ''
				}}
			/>
		);

		await waitForElementToBeRemoved(() => getByText('loading'));

		const itemsSelector = container.querySelector('#items');

		expect(itemsSelector.children).toHaveLength(1);
		expect(getByText('item')).toBeInTheDocument();
	});
});
