import Promise from 'metal-promise';
import React from 'react';
import SearchableModal from '../SearchableModal';
import {cleanup, render} from '@testing-library/react';
import {createOrderIOMap} from 'shared/util/pagination';
import {MemoryRouter, Route} from 'react-router-dom';
import {mockSegment} from 'test/data';
import {noop} from 'lodash';
import {Routes} from 'shared/util/router';
import {times} from 'lodash';

jest.unmock('react-dom');

describe('SearchableModal', () => {
	afterEach(cleanup);

	it('should render', () => {
		const {container} = render(
			<MemoryRouter
				initialEntries={['/workspace/23/settings/data-source']}
			>
				<Route path={Routes.SETTINGS_DATA_SOURCE_LIST}>
					<SearchableModal
						dataSourceFn={() =>
							Promise.resolve({
								items: times(3, i => mockSegment(i)),
								total: 3
							})
						}
						initialOrderIOMap={createOrderIOMap('name')}
						onChange={noop}
						onClose={noop}
					/>
				</Route>
			</MemoryRouter>
		);

		jest.runAllTimers();

		expect(container).toMatchSnapshot();
	});

	it('should render with an empty state', () => {
		const {queryByText} = render(
			<MemoryRouter
				initialEntries={['/workspace/23/settings/data-source']}
			>
				<Route path={Routes.SETTINGS_DATA_SOURCE_LIST}>
					<SearchableModal
						dataSourceFn={() =>
							Promise.resolve({items: [], total: 0})
						}
						onChange={noop}
						onClose={noop}
					/>
				</Route>
			</MemoryRouter>
		);

		jest.runAllTimers();

		expect(queryByText('There are no items found.')).toBeTruthy();
	});
});
