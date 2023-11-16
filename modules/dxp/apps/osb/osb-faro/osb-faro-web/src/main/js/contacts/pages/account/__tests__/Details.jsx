import * as data from 'test/data';
import Details from '../Details';
import mockStore from 'test/mock-store';
import React from 'react';
import {Account} from 'shared/util/records';
import {Provider} from 'react-redux';
import {render} from '@testing-library/react';
import {StaticRouter} from 'react-router';
import {waitForLoadingToBeRemoved} from 'test/helpers';

jest.unmock('react-dom');

describe('AccountDetails', () => {
	it('should render', async () => {
		const {container} = render(
			<Provider store={mockStore()}>
				<StaticRouter>
					<Details
						account={data.getImmutableMock(
							Account,
							data.mockAccount
						)}
						groupId='23'
						id='test'
					/>
				</StaticRouter>
			</Provider>
		);

		jest.runAllTimers();

		await waitForLoadingToBeRemoved(container);

		expect(container).toMatchSnapshot();
	});
});
