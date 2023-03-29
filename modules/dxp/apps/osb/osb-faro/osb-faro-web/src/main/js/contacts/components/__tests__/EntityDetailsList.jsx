import * as data from 'test/data';
import EntityDetailsList from '../EntityDetailsList';
import React from 'react';
import {fireEvent, render} from '@testing-library/react';
import {fromJS, Map} from 'immutable';
import {MemoryRouter, Route} from 'react-router-dom';
import {Routes} from 'shared/util/router';

jest.unmock('react-dom');

const defaultProps = {
	groupId: '23',
	title: 'Test Test'
};

const DefaultComponent = props => (
	<MemoryRouter
		initialEntries={[
			'/workspace/23/321321/contacts/accounts/123123/interests/test'
		]}
	>
		<Route path={Routes.CONTACTS_ACCOUNT_INTEREST_DETAILS}>
			<EntityDetailsList {...defaultProps} {...props} />
		</Route>
	</MemoryRouter>
);

describe('EntityDetailsList', () => {
	it('should render', () => {
		const {container} = render(
			<DefaultComponent demographicsIMap={new Map()} />
		);

		expect(container).toMatchSnapshot();
	});

	it('should render with items', () => {
		const {container} = render(
			<DefaultComponent
				demographicsIMap={fromJS(data.mockAccountDetails())}
			/>
		);

		jest.runAllTimers();
		expect(container).toMatchSnapshot();
	});

	it('should filter results by query', () => {
		const {container, getByPlaceholderText} = render(
			<DefaultComponent
				demographicsIMap={fromJS(data.mockAccountDetails())}
			/>
		);

		jest.runAllTimers();

		fireEvent.change(getByPlaceholderText('Search'), {
			target: {value: 'Agriculture'}
		});

		jest.runAllTimers();

		expect(
			container.querySelector('.subnav-tbar .tbar-item')
		).toHaveTextContent('1 Result for "Agriculture"');

		expect(container.querySelectorAll('table > tbody').length).toBe(1);
	});
});
