import client from 'shared/apollo/client';
import EventSection from '../EventSection';
import mockStore from 'test/mock-store';
import React from 'react';
import {ApolloProvider} from '@apollo/react-components';
import {MemoryRouter, Route} from 'react-router-dom';
import {Provider} from 'react-redux';
import {render} from '@testing-library/react';
import {Routes} from 'shared/util/router';

jest.unmock('react-dom');

describe('EventSection', () => {
	const WrappedComponent = props => (
		<MemoryRouter initialEntries={['/workspace/23/event-analysis']}>
			<Route path={Routes.EVENT_ANALYSIS}>
				<ApolloProvider client={client}>
					<Provider store={mockStore()}>
						<EventSection {...props} />
					</Provider>
				</ApolloProvider>
			</Route>
		</MemoryRouter>
	);

	it('render', () => {
		const {container} = render(<WrappedComponent />);

		expect(container).toMatchSnapshot();
	});

	it('render with event', () => {
		const {container} = render(
			<WrappedComponent event={{name: 'View Article'}} />
		);

		expect(container).toMatchSnapshot();
	});
});
