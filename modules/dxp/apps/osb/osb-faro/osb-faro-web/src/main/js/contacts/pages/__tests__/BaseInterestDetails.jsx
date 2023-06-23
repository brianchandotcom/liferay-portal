import * as data from 'test/data';
import BaseInterestDetails from '../BaseInterestDetails';
import mockDate from 'test/mock-date';
import React from 'react';
import {Account, Segment} from 'shared/util/records';
import {ACCOUNTS, Routes, SEGMENTS} from 'shared/util/router';
import {render} from '@testing-library/react';
import {StaticRouter} from 'react-router';

jest.unmock('react-dom');

describe('BaseInterestDetails', () => {
	beforeAll(() => mockDate());

	afterAll(() => jest.restoreMocks());

	it('should render', () => {
		const {container} = render(
			<StaticRouter>
				<BaseInterestDetails
					channelId='123'
					entity={new Segment(data.mockSegment())}
					groupId='23'
					id='test'
					interestDetailsRoute={
						Routes.CONTACTS_SEGMENT_INTEREST_DETAILS
					}
					interestId='1'
					tabId='individuals'
					type={SEGMENTS}
				/>
			</StaticRouter>
		);

		jest.runAllTimers();

		expect(container).toMatchSnapshot();
	});

	it('should render an individuals list tab', () => {
		const {getByText} = render(
			<StaticRouter>
				<BaseInterestDetails
					entity={new Account(data.mockAccount())}
					groupId='23'
					id='test'
					interestDetailsRoute={
						Routes.CONTACTS_ACCOUNT_INTEREST_DETAILS
					}
					interestId='1'
					tabId='individuals'
					type={ACCOUNTS}
				/>
			</StaticRouter>
		);

		jest.runAllTimers();

		const individualsGrandparentElement = getByText('Individuals')
			.parentElement?.parentElement;

		expect(getByText('Individuals')).toBeTruthy();

		expect(individualsGrandparentElement).toHaveClass('active');
	});

	it('should render an active pages list tab', () => {
		const {getByText} = render(
			<StaticRouter>
				<BaseInterestDetails
					active='true'
					entity={new Account(data.mockAccount())}
					groupId='23'
					id='test'
					interestDetailsRoute={
						Routes.CONTACTS_ACCOUNT_INTEREST_DETAILS
					}
					interestId='1'
					tabId='pages'
					type={ACCOUNTS}
				/>
			</StaticRouter>
		);

		jest.runAllTimers();

		const activePagesGrandparentElement = getByText('Active Pages')
			.parentElement?.parentElement;

		expect(getByText('Active Pages')).toBeTruthy();

		expect(activePagesGrandparentElement).toHaveClass('active');
	});

	it('should render a pages list tab of inactive pages', () => {
		const {getByText} = render(
			<StaticRouter>
				<BaseInterestDetails
					active='false'
					entity={new Account(data.mockAccount())}
					groupId='23'
					id='test'
					interestDetailsRoute={
						Routes.CONTACTS_ACCOUNT_INTEREST_DETAILS
					}
					interestId='1'
					tabId='pages'
					type={ACCOUNTS}
				/>
			</StaticRouter>
		);

		jest.runAllTimers();

		const InactivePagesGrandparentElement = getByText('Inactive Pages')
			.parentElement?.parentElement;

		expect(getByText('Inactive Pages')).toBeTruthy();

		expect(InactivePagesGrandparentElement).toHaveClass('active');
	});
});
