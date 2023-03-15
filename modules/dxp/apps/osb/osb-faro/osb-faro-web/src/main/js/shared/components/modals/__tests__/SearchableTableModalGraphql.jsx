import OrganizationsQuery from 'segment/segment-editor/dynamic/queries/OrganizationsQuery';
import React from 'react';
import SearchableTableModalGraphql from '../SearchableTableModalGraphql';
import {cleanup, render} from '@testing-library/react';
import {createOrderIOMap} from 'shared/util/pagination';
import {
	getMapResultToProps,
	mapPropsToOptions
} from 'segment/segment-editor/dynamic/mappers/dxp-entity-bag-mapper';
import {MemoryRouter, Route} from 'react-router-dom';
import {MockedProvider} from '@apollo/react-testing';
import {mockOrganizationsListReq} from 'test/graphql-data';
import {noop} from 'lodash';
import {Routes} from 'shared/util/router';

jest.unmock('react-dom');

const COLUMNS = [
	{
		accessor: 'name',
		label: 'name'
	}
];

const defaultProps = {
	columns: COLUMNS,
	graphqlQuery: OrganizationsQuery,
	groupId: '23',
	initialDelta: 5,
	initialOrderIOMap: createOrderIOMap('name'),
	mapPropsToOptions,
	mapResultToProps: getMapResultToProps('organizations'),
	onClose: noop
};

const DefaultComponent = props => (
	<MemoryRouter initialEntries={['/workspace/23/settings/data-source']}>
		<Route path={Routes.SETTINGS_DATA_SOURCE_LIST}>
			<MockedProvider mocks={[mockOrganizationsListReq()]}>
				<SearchableTableModalGraphql {...defaultProps} {...props} />
			</MockedProvider>
		</Route>
	</MemoryRouter>
);

describe('SearchableTableModalGraphql', () => {
	afterEach(cleanup);

	it('should render', () => {
		const {container} = render(<DefaultComponent />);

		jest.runAllTimers();

		expect(container).toMatchSnapshot();
	});

	it('should render with a custom title', () => {
		const {container} = render(<DefaultComponent title='Custom Title' />);

		expect(container.querySelector('.modal-title')).toHaveTextContent(
			'Custom Title'
		);
	});

	it('should render with a custom submit button message', () => {
		const {container} = render(
			<DefaultComponent submitMessage='Custom Submit Message' />
		);

		expect(container.querySelector('.btn-primary')).toHaveTextContent(
			'Custom Submit Message'
		);
	});

	it('should render with preselected items', () => {
		const {container} = render(
			<DefaultComponent
				selectedItems={[{id: 0, name: 'fooOrganization-0'}]}
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
