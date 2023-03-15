import * as data from 'test/data';
import Promise from 'metal-promise';
import React from 'react';
import SearchableTableModal from '../SearchableTableModal';
import {cleanup, render} from '@testing-library/react';
import {createOrderIOMap} from 'shared/util/pagination';
import {MemoryRouter, Route} from 'react-router-dom';
import {noop} from 'lodash';
import {Routes} from 'shared/util/router';

jest.unmock('react-dom');

const COLUMNS = [
	{
		accessor: 'name',
		className: 'table-cell-expand',
		label: 'name'
	},
	{
		accessor: 'email',
		label: 'email'
	}
];

const defaultProps = {
	columns: COLUMNS,
	dataSourceFn: () => Promise.resolve(),
	groupId: '23',
	initialDelta: 5,
	initialOrderIOMap: createOrderIOMap('name'),
	onClose: noop
};

const DefaultComponent = props => (
	<MemoryRouter initialEntries={['/workspace/23/settings/data-source']}>
		<Route path={Routes.SETTINGS_DATA_SOURCE_LIST}>
			<SearchableTableModal {...defaultProps} {...props} />
		</Route>
	</MemoryRouter>
);

describe('SearchableTableModal', () => {
	afterEach(cleanup);

	it('should render', () => {
		const {container} = render(<DefaultComponent />);

		jest.runAllTimers();

		expect(container).toMatchSnapshot();
	});

	it('should render with a custom title', () => {
		const {container} = render(<DefaultComponent title='Custom Title' />);

		jest.runAllTimers();

		expect(container.querySelector('.modal-title')).toHaveTextContent(
			'Custom Title'
		);
	});

	it('should render with a custom submit button message', () => {
		const {container} = render(
			<DefaultComponent submitMessage='Custom Submit Message' />
		);

		jest.runAllTimers();

		expect(container.querySelector('.btn-primary')).toHaveTextContent(
			'Custom Submit Message'
		);
	});

	it('should render with preselected items', () => {
		const {container} = render(
			<DefaultComponent
				dataSourceFn={() =>
					Promise.resolve(
						data.mockSearch(data.mockSegment, 1, {id: 'foo'})
					)
				}
				initialSelectedItems={[{id: 'foo', name: 'fooSegmentName'}]}
				submitMessage='Custom Submit Message'
			/>
		);

		jest.runAllTimers();

		expect(
			container.querySelector(
				'.table > tbody:nth-of-type(1) > tr .custom-checkbox input:checked'
			).checked
		).toBeTrue();
	});
});
