import * as data from 'test/data';
import KnownIndividuals from '../KnownIndividuals';
import mockStore from 'test/mock-store';
import React from 'react';
import {Account} from 'shared/util/records';
import {Provider} from 'react-redux';
import {render} from '@testing-library/react';
import {StaticRouter} from 'react-router';

jest.unmock('react-dom');

describe('KnownIndividuals', () => {
	it('should render', () => {
		const {container} = render(
			<StaticRouter>
				<Provider store={mockStore()}>
					<KnownIndividuals
						account={data.getImmutableMock(
							Account,
							data.mockAccount
						)}
						channelId='123123'
						groupId='23'
						id='test'
					/>
				</Provider>
			</StaticRouter>
		);

		jest.runAllTimers();

		expect(container).toMatchSnapshot();
	});
});
