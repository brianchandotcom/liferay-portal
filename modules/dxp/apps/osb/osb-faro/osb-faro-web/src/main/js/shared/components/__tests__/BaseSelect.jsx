import BaseSelect, {Item} from '../BaseSelect';
import client from 'shared/apollo/client';
import EventAttributeValuesQuery from 'event-analysis/queries/EventAttributeValuesQuery';
import mockStore from 'test/mock-store';
import Promise from 'metal-promise';
import React from 'react';
import {ApolloProvider} from '@apollo/react-components';
import {fireEvent, render} from '@testing-library/react';
import {MockedProvider} from '@apollo/react-testing';
import {mockEventAttributeValues} from 'test/graphql-data';
import {noop} from 'lodash';
import {Provider} from 'react-redux';
import {waitForLoadingToBeRemoved} from 'test/helpers';

jest.unmock('react-dom');

const MOCK_APOLLO_QUERY = {
	mapResultsToProps: data => {
		if (data) {
			return {
				data: data.eventAttributeValues.eventAttributeValues,
				total: data.eventAttributeValues.total
			};
		}

		return {
			data: [],
			total: 0
		};
	},
	query: EventAttributeValuesQuery,
	variables: {
		channelId: '123',
		eventAttributeDefinitionId: '456',
		eventDefinitionId: '789',
		size: 100,
		start: 0
	}
};

describe('BaseSelect', () => {
	it('should render', () => {
		const {container} = render(
			<BaseSelect
				dataSourceFn={() => Promise.resolve([])}
				itemRenderer={jest.fn()}
			/>
		);

		expect(container).toMatchSnapshot();
	});

	it('should render as disabled', () => {
		const dataSourceFn = jest.fn();

		const {container} = render(
			<BaseSelect
				dataSourceFn={dataSourceFn}
				disabled
				itemRenderer={({name}) => name}
			/>
		);

		jest.runAllTimers();

		expect(dataSourceFn).not.toHaveBeenCalled();

		fireEvent.click(container.querySelector('.input-group'));

		jest.runAllTimers();

		expect(dataSourceFn).not.toHaveBeenCalled();

		expect(container).toMatchSnapshot();
	});

	it('should render w/ selectedItem', () => {
		const {container} = render(
			<BaseSelect
				dataSourceFn={() =>
					Promise.resolve([
						{name: 'test'},
						{name: 'foo'},
						{name: 'bar'}
					])
				}
				itemRenderer={({name}) => name}
				onFocus={noop}
				selectedItem={{name: 'foo'}}
			/>
		);

		jest.runAllTimers();

		expect(
			container.querySelector('.selected-item-container').innerHTML
		).toEqual('foo');
	});

	it('should fetch items with focus', async () => {
		const {container} = render(
			<BaseSelect
				dataSourceFn={() => Promise.resolve([{name: 'test'}])}
				focusOnInit
				itemRenderer={({name}) => name}
				onFocus={noop}
			/>
		);

		await waitForLoadingToBeRemoved(container).then(() => {
			const dropdownMenu = document.body.getElementsByClassName(
				'dropdown-root'
			)[0];

			expect(dropdownMenu).toMatchSnapshot();
		});
	});

	it('should render w/ menu title', async () => {
		const {container} = render(
			<BaseSelect
				dataSourceFn={() => Promise.resolve([{name: 'test'}])}
				focusOnInit
				itemRenderer={({name}) => name}
				menuTitle='Test Menu Title'
				onFocus={noop}
			/>
		);

		await waitForLoadingToBeRemoved(container).then(() => {
			const dropdownMenu = document.body.getElementsByClassName(
				'dropdown-root'
			)[0];

			expect(
				dropdownMenu.getElementsByClassName('dropdown-header')[0]
					.innerHTML
			).toEqual('Test Menu Title');
		});
	});

	it('should focus on the previous item', async () => {
		const {container} = render(
			<BaseSelect
				dataSourceFn={() =>
					Promise.resolve([
						{name: 'test'},
						{name: 'foo'},
						{name: 'bar'}
					])
				}
				focusOnInit
				itemRenderer={({name}) => name}
				onFocus={noop}
			/>
		);

		await waitForLoadingToBeRemoved(container).then(async () => {
			const dropdownMenu = document.body.getElementsByClassName(
				'dropdown-root'
			)[0];

			fireEvent.keyDown(container.querySelector('.input-root'), {
				key: 'ArrowUp',
				keyCode: 38
			});

			expect(
				dropdownMenu.getElementsByClassName('active')[0].innerHTML
			).toEqual('bar');
		});
	});

	it('should focus on the next item', async () => {
		const {container} = render(
			<BaseSelect
				dataSourceFn={() =>
					Promise.resolve([
						{name: 'test'},
						{name: 'foo'},
						{name: 'bar'}
					])
				}
				focusOnInit
				itemRenderer={({name}) => name}
				onFocus={noop}
			/>
		);

		await waitForLoadingToBeRemoved(container).then(async () => {
			const dropdownMenu = document.body.getElementsByClassName(
				'dropdown-root'
			)[0];

			fireEvent.keyDown(container.querySelector('.input-root'), {
				key: 'ArrowDown',
				keyCode: 40
			});

			expect(
				dropdownMenu.getElementsByClassName('active')[0].innerHTML
			).toEqual('foo');
		});
	});

	it('should render with Graphql', () => {
		const {container} = render(
			<ApolloProvider client={client}>
				<Provider store={mockStore()}>
					<MockedProvider mocks={[mockEventAttributeValues()]}>
						<BaseSelect
							graphqlQuery={MOCK_APOLLO_QUERY}
							itemRenderer={jest.fn()}
						/>
					</MockedProvider>
				</Provider>
			</ApolloProvider>
		);

		expect(container).toMatchSnapshot();
	});

	it('should render w/ selectedItem with Graphql', () => {
		const {container} = render(
			<ApolloProvider client={client}>
				<Provider store={mockStore()}>
					<MockedProvider mocks={[mockEventAttributeValues()]}>
						<BaseSelect
							graphqlQuery={MOCK_APOLLO_QUERY}
							itemRenderer={({name}) => name}
							onFocus={noop}
							selectedItem={{name: 'test1'}}
						/>
					</MockedProvider>
				</Provider>
			</ApolloProvider>
		);

		jest.runAllTimers();

		expect(
			container.querySelector('.selected-item-container').innerHTML
		).toEqual('test1');
	});
});

describe('Item', () => {
	it('should render', () => {
		const {container} = render(
			<Item item={{name: 'test'}} itemRenderer={({name}) => name} />
		);
		expect(container).toMatchSnapshot();
	});
	it('should select an item', () => {
		const {container, getByText} = render(
			<Item
				item={{name: 'test'}}
				itemRenderer={({name}) => name}
				onSelect={noop}
			/>
		);
		fireEvent.click(getByText('test'));
		jest.runAllTimers();
		expect(container).toMatchSnapshot();
	});
});
