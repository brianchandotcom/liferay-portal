import mockStore from 'test/mock-store';
import React from 'react';
import SearchCard from '../SearchCard';
import {cleanup, fireEvent, render} from '@testing-library/react';
import {MockedProvider} from '@apollo/react-testing';
import {mockSearchStringListReq} from 'test/graphql-data';
import {Provider} from 'react-redux';
import {StaticRouter} from 'react-router';
import {useCurrentUser} from 'shared/hooks/useCurrentUser';
import {waitForLoadingToBeRemoved} from 'test/helpers';

jest.unmock('react-dom');

jest.mock('shared/hooks/useCurrentUser', () => ({
	useCurrentUser: jest.fn()
}));

const DefaultComponent = props => (
	<StaticRouter>
		<MockedProvider mocks={[mockSearchStringListReq()]}>
			<Provider store={mockStore()}>
				<SearchCard groupId='23' {...props} />
			</Provider>
		</MockedProvider>
	</StaticRouter>
);

const changeInputValue = (input, newValue) => {
	input.focus();
	fireEvent.change(input, {target: {value: newValue}});
	input.blur();
};

describe('SearchCard', () => {
	afterEach(cleanup);

	it('should render', async () => {
		useCurrentUser.mockImplementation(() => ({isAdmin: () => true}));

		const {container} = render(<DefaultComponent />);

		await waitForLoadingToBeRemoved(container);

		expect(container).toMatchSnapshot();
	});

	it('should have a default uneditable field with value of q', async () => {
		useCurrentUser.mockImplementation(() => ({isAdmin: () => true}));

		const {container, getByDisplayValue} = render(<DefaultComponent />);

		await waitForLoadingToBeRemoved(container);

		expect(getByDisplayValue('q')).toBeTruthy();
	});

	it('should remove special characters on fields', async () => {
		useCurrentUser.mockImplementation(() => ({isAdmin: () => true}));

		const {container, getByDisplayValue} = render(<DefaultComponent />);

		await waitForLoadingToBeRemoved(container);

		const input = getByDisplayValue('jackson');

		changeInputValue(input, 'jackson@#!');

		expect(input.value).toBe('jackson');
	});

	it('should remove every character after equals sign', async () => {
		useCurrentUser.mockImplementation(() => ({isAdmin: () => true}));

		const {container, getByDisplayValue} = render(<DefaultComponent />);

		await waitForLoadingToBeRemoved(container);

		const input = getByDisplayValue('jackson');

		changeInputValue(input, 'jackson=testvalue');

		expect(input.value).toBe('jackson');
	});

	it('should render input as disabled when user is not admin', async () => {
		useCurrentUser.mockImplementation(() => ({isAdmin: () => false}));

		const {container} = render(<DefaultComponent />);

		await waitForLoadingToBeRemoved(container);

		container.querySelectorAll('.query-input input').forEach(el => {
			expect(el).toBeDisabled();
		});
	});

	it('should not render buttons when user is not admin', async () => {
		useCurrentUser.mockImplementation(() => ({isAdmin: () => false}));

		const {container} = render(<DefaultComponent />);

		await waitForLoadingToBeRemoved(container);

		expect(
			container.querySelectorAll('.query-card-root button')
		).toBeEmpty();
	});
});
