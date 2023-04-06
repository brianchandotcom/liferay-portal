import Interests, {ContributionsCell} from '../Interests';
import React from 'react';
import {MemoryRouter, Route} from 'react-router-dom';
import {render} from '@testing-library/react';
import {Routes} from 'shared/util/router';
import {waitForLoadingToBeRemoved} from 'test/helpers';

jest.unmock('react-dom');

describe('Interests', () => {
	it('should render', async () => {
		const {container} = render(
			<MemoryRouter
				initialEntries={[
					'/workspace/23/123/contacts/individuals/known-individuals/321321/interests?delta=2&page=1&field=count&sortOrder=DESC'
				]}
			>
				<Route path={Routes.CONTACTS_INDIVIDUAL_INTERESTS}>
					<Interests channelId='123' groupId='23' id='test' />
				</Route>
			</MemoryRouter>
		);

		await waitForLoadingToBeRemoved(container);

		jest.runAllTimers();

		expect(container).toMatchSnapshot();
	});
});

describe('ContributionsCell', () => {
	it('should render', () => {
		const {container} = render(
			<ContributionsCell data={{relatedPagesCount: 8}} />
		);
		expect(container).toMatchSnapshot();
	});
});
