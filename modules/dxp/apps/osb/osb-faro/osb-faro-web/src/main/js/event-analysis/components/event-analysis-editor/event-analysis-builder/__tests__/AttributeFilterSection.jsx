import client from 'shared/apollo/client';
import mockStore from 'test/mock-store';
import React from 'react';
import {ApolloProvider} from '@apollo/react-components';
import {AttributeFilterSection} from '../AttributeFilterSection';
import {DndProvider} from 'react-dnd';
import {HTML5Backend} from 'react-dnd-html5-backend';
import {MemoryRouter, Route} from 'react-router-dom';
import {Provider} from 'react-redux';
import {render} from '@testing-library/react';
import {Routes} from 'shared/util/router';

jest.unmock('react-dom');

describe('AttributeFilterSection', () => {
	const WrappedComponent = props => (
		<MemoryRouter initialEntries={['/workspace/23/event-analysis']}>
			<Route path={Routes.EVENT_ANALYSIS}>
				<ApolloProvider client={client}>
					<Provider store={mockStore()}>
						<DndProvider backend={HTML5Backend}>
							<AttributeFilterSection
								attributes={[]}
								filterOrder={[]}
								filters={[]}
								{...props}
							/>
						</DndProvider>
					</Provider>
				</ApolloProvider>
			</Route>
		</MemoryRouter>
	);

	it('renders', () => {
		const {container} = render(<WrappedComponent />);

		expect(container.querySelector('.add-attribute')).toBeNull();
		expect(container).toMatchSnapshot();
	});

	it('renders w/ add attribute button', () => {
		const {container} = render(<WrappedComponent eventId='1' />);

		expect(container.querySelector('.add-attribute')).toBeTruthy();
	});

	it('renders w/ filter', () => {
		const {container} = render(
			<WrappedComponent
				attributes={{
					123123: {
						dataType: 'STRING',
						displayName: 'Job Title',
						id: '123123',
						name: 'jobTitle'
					}
				}}
				eventId='1'
				filterOrder={['123123']}
				filters={{
					123123: {
						attributeId: '123123',
						dataType: 'STRING',
						operator: 'eq',
						type: 'event',
						value: ['Stuff']
					}
				}}
			/>
		);

		expect(container).toMatchSnapshot();
	});
});
