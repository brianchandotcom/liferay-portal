import client from 'shared/apollo/client';
import EventAnalysisEditor from '../index';
import mockStore from 'test/mock-store';
import React from 'react';
import {ApolloProvider} from '@apollo/react-components';
import {CalculationTypes} from 'event-analysis/utils/types';
import {Provider} from 'react-redux';
import {render} from '@testing-library/react';

jest.unmock('react-dom');

describe('Event Analysis Editor', () => {
	it('render', () => {
		const {container} = render(
			<ApolloProvider client={client}>
				<Provider store={mockStore()}>
					<EventAnalysisEditor type={CalculationTypes.Total} />
				</Provider>
			</ApolloProvider>
		);

		expect(container).toMatchSnapshot();
	});
});
