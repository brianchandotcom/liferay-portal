import client from 'shared/apollo/client';
import EventChip from '../EventChip';
import mockStore from 'test/mock-store';
import React from 'react';
import {ApolloProvider} from '@apollo/client';
import {fireEvent, render} from '@testing-library/react';
import {Provider} from 'react-redux';

jest.unmock('react-dom');

describe('EventChip', () => {
	it('render', () => {
		const {container} = render(
			<ApolloProvider client={client}>
				<Provider store={mockStore()}>
					<EventChip event={{name: 'View Article'}} />
				</Provider>
			</ApolloProvider>
		);

		expect(container).toMatchSnapshot();
	});

	it('calls onEventChange with null when the remove button is clicked', () => {
		const onEventChange = jest.fn();

		const {container} = render(
			<ApolloProvider client={client}>
				<Provider store={mockStore()}>
					<EventChip
						event={{id: '1', name: 'View Article'}}
						onEventChange={onEventChange}
					/>
				</Provider>
			</ApolloProvider>
		);

		fireEvent.click(container.querySelector('.remove-button'));

		expect(onEventChange).toHaveBeenCalledTimes(1);
		expect(onEventChange).toHaveBeenCalledWith(null);
	});
});
