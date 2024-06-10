import * as data from 'test/data';
import BaseResults from '../BaseResults';
import React from 'react';
import {cleanup, fireEvent, render, waitFor} from '@testing-library/react';
import {noop, times} from 'lodash';
import {SelectionProvider} from 'shared/context/selection';
import {StaticRouter} from 'react-router';

jest.unmock('react-dom');

const MAX_LENGTH = 900;
const TOTAL = 5;
const INDIVIDUALS = times(TOTAL, i => data.mockIndividual(i));

const DefaultComponent = props => (
	<SelectionProvider>
		<StaticRouter>
			<BaseResults {...props} />
		</StaticRouter>
	</SelectionProvider>
);

describe('BaseResults', () => {
	afterEach(cleanup);

	it('should render', async () => {
		const {container} = render(
			<DefaultComponent
				dataSourceFn={() =>
					Promise.resolve({items: INDIVIDUALS, total: TOTAL})
				}
				groupId='23'
				resultsRenderer={noop}
			/>
		);

		await waitFor(() => {});

		expect(container).toMatchSnapshot();
	});

	it('should render w/ an error display', async () => {
		const {getByText} = render(
			<DefaultComponent
				dataSourceFn={() => Promise.reject({})}
				groupId='23'
				resultsRenderer={noop}
			/>
		);

		await waitFor(() => {});

		expect(getByText('An unexpected error occurred.')).toBeInTheDocument();
	});

	it('should render w/a no results display', async () => {
		const {getByText} = render(
			<DefaultComponent
				dataSourceFn={() => Promise.resolve({items: [], total: 0})}
				groupId='23'
				query='non-existent query'
				resultsRenderer={noop}
			/>
		);

		await waitFor(() => {});

		expect(getByText('There are no results found.')).toBeInTheDocument();
	});

	it('should put a size limit on the query based on maxLength', () => {
		const {container} = render(
			<DefaultComponent
				dataSourceFn={() =>
					Promise.resolve({items: INDIVIDUALS, total: TOTAL})
				}
				groupId='23'
				maxLength={MAX_LENGTH}
				query={Array(2000).join('a')}
				resultsRenderer={noop}
			/>
		);

		expect(container.querySelector('input.input-root').value).toHaveLength(
			MAX_LENGTH
		);
	});

	it('should not render a subnav if there is a datasourceFn error', async () => {
		const {queryByText} = render(
			<DefaultComponent
				dataSourceFn={() => Promise.reject(new Error())}
				groupId='23'
				renderSubnav={() => <div>{'subnav content'}</div>}
				resultsRenderer={noop}
			/>
		);

		await waitFor(() => {});

		expect(queryByText('subnav content')).toBeNull();
	});

	it('should render with search disabled when disableSearch is TRUE', async () => {
		const {container} = render(
			<DefaultComponent
				dataSourceFn={() =>
					Promise.resolve({
						disableSearch: true,
						items: INDIVIDUALS,
						total: TOTAL
					})
				}
				groupId='23'
				resultsRenderer={noop}
			/>
		);

		await waitFor(() => {});

		expect(container.querySelector('.search input').disabled).toBe(true);
	});

	it('should not include disabled items when calculating whether all the items are checked', async () => {
		const {getByTestId} = render(
			<DefaultComponent
				checkDisabled={item => item.id === '0'}
				dataSourceFn={() =>
					Promise.resolve({items: INDIVIDUALS, total: TOTAL})
				}
				groupId='23'
				resultsRenderer={noop}
				showCheckbox
			/>
		);

		const selectAllCheckbox = getByTestId('select-all-checkbox');

		await waitFor(() => {});

		fireEvent.click(selectAllCheckbox);

		expect(selectAllCheckbox.checked).toBe(true);
	});
});
