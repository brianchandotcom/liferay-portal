import client from 'shared/apollo/client';
import mockStore from 'test/mock-store';
import React from 'react';
import {ApolloProvider} from '@apollo/react-components';
import {AttributeBreakdownSection} from '../AttributeBreakdownSection';
import {DndProvider} from 'react-dnd';
import {HTML5Backend} from 'react-dnd-html5-backend';
import {MemoryRouter, Route} from 'react-router-dom';
import {Provider} from 'react-redux';
import {render} from '@testing-library/react';
import {Routes} from 'shared/util/router';

jest.unmock('react-dom');

describe('AttributeBreakdownSection', () => {
	const WrappedComponent = props => (
		<MemoryRouter initialEntries={['/workspace/23/event-analysis']}>
			<Route path={Routes.EVENT_ANALYSIS}>
				<ApolloProvider client={client}>
					<Provider store={mockStore()}>
						<DndProvider backend={HTML5Backend}>
							<AttributeBreakdownSection
								attributes={[]}
								breakdownOrder={[]}
								breakdowns={[]}
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

	it('renders w/ add breakdown button', () => {
		const {container} = render(<WrappedComponent eventId='1' />);

		expect(container.querySelector('.add-attribute')).toBeTruthy();
	});

	it('renders w/o add breakdown button if 3 breakdowns exists', () => {
		const {container} = render(
			<WrappedComponent
				attributes={{
					1: {
						dataType: 'STRING',
						displayName: 'Title',
						id: '1',
						name: 'title'
					},
					123123: {
						dataType: 'STRING',
						displayName: 'Job Title',
						id: '123123',
						name: 'jobTitle'
					},
					321321: {
						dataType: 'STRING',
						displayName: 'Article Title',
						id: '321321',
						name: 'articleTitle'
					}
				}}
				breakdownOrder={['1', '321321', '123123']}
				breakdowns={{
					1: {
						attributeId: '1',
						dataType: 'STRING',
						type: 'event'
					},
					123123: {
						attributeId: '123123',
						dataType: 'STRING',
						type: 'event'
					},
					321321: {
						attributeId: '321321',
						dataType: 'STRING',
						type: 'event'
					}
				}}
				eventId='2'
			/>
		);

		expect(container.querySelector('.add-attribute')).toBeNull();
	});

	it('renders w/ breakdowns', () => {
		const {container} = render(
			<WrappedComponent
				attributes={{
					123123: {
						displayName: 'Job Title',
						id: '123123',
						name: 'jobTitle'
					},
					321321: {
						displayName: 'Article Title',
						id: '321321',
						name: 'articleTitle'
					}
				}}
				breakdownOrder={['321321', '123123']}
				breakdowns={{
					123123: {
						attributeId: '123123',
						dataType: 'STRING',
						type: 'event'
					},
					321321: {
						attributeId: '321321',
						dataType: 'STRING',
						type: 'event'
					}
				}}
				eventId='2'
			/>
		);

		expect(container).toMatchSnapshot();
	});
});
