import EventSection from '../EventSection';
import mockStore from 'test/mock-store';
import React from 'react';
import {InMemoryCache} from '@apollo/client';
import {MemoryRouter, Route} from 'react-router-dom';
import {MockedProvider} from '@apollo/client/testing';
import {Provider} from 'react-redux';
import {render} from '@testing-library/react';
import {Routes} from 'shared/util/router';

jest.unmock('react-dom');

const DefaultComponent = props => (
	<Provider store={mockStore()}>
		<MemoryRouter initialEntries={['/workspace/23/event-analysis']}>
			<Route path={Routes.EVENT_ANALYSIS}>
				<MockedProvider
					cache={
						new InMemoryCache({
							addTypename: false,
							freezeResults: false
						})
					}
				>
					<EventSection {...props} />
				</MockedProvider>
			</Route>
		</MemoryRouter>
	</Provider>
);

describe('EventSection', () => {
	it('render', () => {
		const {container} = render(<DefaultComponent />);

		expect(container).toMatchSnapshot();
	});

	it('render with event', () => {
		const {container} = render(
			<DefaultComponent event={{name: 'View Article'}} />
		);

		expect(container).toMatchSnapshot();
	});
});
