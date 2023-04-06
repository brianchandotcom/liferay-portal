import Interests from '../Interests';
import React from 'react';
import {MemoryRouter, Route} from 'react-router-dom';
import {MockedProvider} from '@apollo/react-testing';
import {mockIndividualInterestsReq} from 'test/graphql-data';
import {render} from '@testing-library/react';
import {Routes} from 'shared/util/router';
import {waitForLoadingToBeRemoved} from 'test/helpers';

jest.unmock('react-dom');

describe('Interests', () => {
	it('renders', async () => {
		const {container} = render(
			<MockedProvider
				mocks={[
					mockIndividualInterestsReq(defaultVars => ({
						...defaultVars,
						id: undefined,
						keywords: '',
						size: 2
					}))
				]}
			>
				<MemoryRouter
					initialEntries={[
						'/workspace/123/456/contacts/individuals/interests'
					]}
				>
					<Route path={Routes.CONTACTS_INDIVIDUALS_INTERESTS}>
						<Interests />
					</Route>
				</MemoryRouter>
			</MockedProvider>
		);

		await waitForLoadingToBeRemoved(container);

		jest.runAllTimers();

		expect(container).toMatchSnapshot();
	});
});
