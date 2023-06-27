import * as API from 'shared/api';
import IndividualProfileCard from '../ProfileCard';
import Promise from 'metal-promise';
import React from 'react';
import {fireEvent, render} from '@testing-library/react';
import {Individual} from 'shared/util/records';
import {MemoryRouter, Route} from 'react-router-dom';
import {MockedProvider} from '@apollo/react-testing';
import {
	mockEventMetrics,
	mockSessions,
	mockTimeRangeReq
} from 'test/graphql-data';
import {mockIndividual} from 'test/data';
import {Routes} from 'shared/util/router';

jest.unmock('react-dom');

const DefaultComponent = ({children}) => (
	<MemoryRouter
		initialEntries={[
			'/workspace/23/123123/contacts/individuals/known-individuals/4423123123'
		]}
	>
		<Route path={Routes.CONTACTS_INDIVIDUAL}>{children}</Route>
	</MemoryRouter>
);

const inputValue = 'add to cart';
const searchKeyword = {keywords: inputValue};

describe('IndividualProfileCard', () => {
	it('should render', async () => {
		const {container} = render(
			<DefaultComponent>
				<MockedProvider
					mocks={[
						mockEventMetrics(),
						mockTimeRangeReq(),
						mockSessions()
					]}
				>
					<IndividualProfileCard
						channelId='123123'
						entity={new Individual(mockIndividual())}
						groupId='23'
						interval='D'
						rangeSelectors={{rangeKey: 30}}
					/>
				</MockedProvider>
			</DefaultComponent>
		);

		jest.runAllTimers();

		expect(container).toMatchSnapshot();
	});

	it('should clear search input when clear button is clicked', async () => {
		const {getByPlaceholderText, getByText} = render(
			<DefaultComponent>
				<MockedProvider
					mocks={[
						mockEventMetrics(),
						mockTimeRangeReq(),
						mockSessions(),
						mockEventMetrics(),
						mockSessions(),
						mockEventMetrics(),
						mockSessions(),
						mockEventMetrics(),
						mockSessions(),
						mockEventMetrics(searchKeyword),
						mockSessions(searchKeyword),
						mockEventMetrics(searchKeyword),
						mockSessions(searchKeyword),
						mockEventMetrics(searchKeyword),
						mockSessions(searchKeyword)
					]}
				>
					<IndividualProfileCard
						channelId='123123'
						entity={new Individual(mockIndividual())}
						groupId='23'
						interval='D'
						rangeSelectors={{rangeKey: 30}}
					/>
				</MockedProvider>
			</DefaultComponent>
		);

		jest.runAllTimers();

		const searchInput = getByPlaceholderText('Search');

		fireEvent.change(searchInput, {target: {value: inputValue}});

		fireEvent.keyDown(searchInput, {
			charCode: 13,
			code: 'Enter',
			key: 'Enter'
		});

		jest.runAllTimers();

		expect(getByPlaceholderText('Search')).toHaveValue(inputValue);

		fireEvent.click(getByText('Clear'));

		jest.runAllTimers();

		expect(getByPlaceholderText('Search')).toHaveValue('');
	});

	it('should clear search input when X clear button is clicked', async () => {
		const {container, getByPlaceholderText} = render(
			<DefaultComponent>
				<MockedProvider
					mocks={[
						mockEventMetrics(),
						mockTimeRangeReq(),
						mockSessions(),
						mockEventMetrics(),
						mockSessions(),
						mockEventMetrics(),
						mockSessions(),
						mockTimeRangeReq(),
						mockEventMetrics(),
						mockSessions(),
						mockTimeRangeReq(),
						mockEventMetrics(searchKeyword),
						mockTimeRangeReq(),
						mockSessions(searchKeyword),
						mockEventMetrics(searchKeyword),
						mockSessions(searchKeyword),
						mockEventMetrics(searchKeyword),
						mockSessions(searchKeyword)
					]}
				>
					<IndividualProfileCard
						channelId='123123'
						entity={new Individual(mockIndividual())}
						groupId='23'
						interval='D'
						rangeSelectors={{rangeKey: 30}}
					/>
				</MockedProvider>
			</DefaultComponent>
		);

		jest.runAllTimers();

		const searchInput = getByPlaceholderText('Search');

		fireEvent.change(searchInput, {target: {value: inputValue}});

		fireEvent.keyDown(searchInput, {
			charCode: 13,
			code: 'Enter',
			key: 'Enter'
		});

		jest.runAllTimers();

		expect(getByPlaceholderText('Search')).toHaveValue(inputValue);

		fireEvent.click(container.querySelector('.lexicon-icon-times'));

		jest.runAllTimers();

		expect(getByPlaceholderText('Search')).toHaveValue('');
	});

	xit('should render w/ an error display', () => {
		API.activities.fetchHistory.mockReturnValueOnce(Promise.reject({}));

		const {getByText} = render(<DefaultComponent />);

		jest.runAllTimers();

		expect(getByText('An unexpected error occurred.')).toBeTruthy();
	});

	it('should render w/ loading', () => {
		const {container} = render(
			<DefaultComponent>
				<MockedProvider mocks={[]}>
					<IndividualProfileCard
						channelId='123123'
						entity={new Individual(mockIndividual())}
						groupId='23'
						interval='D'
						rangeSelectors={{rangeKey: 30}}
					/>
				</MockedProvider>
			</DefaultComponent>
		);

		jest.runAllTimers();

		expect(container.querySelector('.loading-root')).toBeTruthy();
	});
});
