import * as API from 'shared/api';
import * as data from 'test/data';
import ActivitiesCard from '../ActivitiesCard';
import Promise from 'metal-promise';
import React from 'react';
import {Account} from 'shared/util/records';
import {render} from '@testing-library/react';
import {StaticRouter} from 'react-router';

jest.unmock('react-dom');

describe('ActivitiesCard', () => {
	it('should render', () => {
		const {container} = render(
			<StaticRouter>
				<ActivitiesCard
					account={data.getImmutableMock(
						Account,
						data.mockAccount,
						'test'
					)}
					groupId='23'
				/>
			</StaticRouter>
		);

		jest.runAllTimers();

		expect(container).toMatchSnapshot();
	});

	it('should render w/ loading', () => {
		const {container} = render(
			<StaticRouter>
				<ActivitiesCard
					account={data.getImmutableMock(
						Account,
						data.mockAccount,
						'test'
					)}
					groupId='23'
				/>
			</StaticRouter>
		);

		expect(container.querySelector('.loading-root')).toBeTruthy();
	});

	it('should render w/ ErrorDisplay', () => {
		API.activities.fetchHistory.mockReturnValueOnce(Promise.reject({}));

		const {getByText} = render(
			<StaticRouter>
				<ActivitiesCard
					account={data.getImmutableMock(
						Account,
						data.mockAccount,
						'test'
					)}
					groupId='23'
				/>
			</StaticRouter>
		);

		jest.runAllTimers();

		expect(getByText('An unexpected error occurred.')).toBeTruthy();
	});
});
