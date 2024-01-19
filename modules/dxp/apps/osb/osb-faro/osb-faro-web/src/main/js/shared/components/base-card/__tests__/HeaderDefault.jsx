import client from 'shared/apollo/client';
import HeaderDefault from '../HeaderDefault';
import mockStore from 'test/mock-store';
import React from 'react';
import {ApolloProvider} from '@apollo/react-hooks';
import {createMemoryHistory} from 'history';
import {fireEvent, render} from '@testing-library/react';
import {INTERVAL_KEY_MAP} from 'shared/util/time';
import {MockedProvider} from '@apollo/react-testing';
import {mockPreferenceReq, mockTimeRangeReq} from 'test/graphql-data';
import {Provider} from 'react-redux';
import {Router} from 'react-router-dom';

jest.unmock('react-dom');

const DefaultComponent = props => {
	const history = createMemoryHistory();

	return (
		<ApolloProvider client={client}>
			<Provider store={mockStore()}>
				<Router history={history}>
					<MockedProvider
						mocks={[mockTimeRangeReq(), mockPreferenceReq()]}
					>
						<HeaderDefault label='Title' {...props} />
					</MockedProvider>
				</Router>
			</Provider>
		</ApolloProvider>
	);
};

describe('HeaderDefault', () => {
	it('should render', () => {
		const {container} = render(<DefaultComponent />);

		jest.runAllTimers();

		expect(container).toMatchSnapshot();
	});

	it('should call the onChangeInterval prop fn with "day" if the rangekey is changed to an hourly value', () => {
		const spy = jest.fn();
		const {getByText} = render(
			<DefaultComponent
				onChangeInterval={spy}
				rangeSelectors={{rangeKey: '30'}}
				showInterval
			/>
		);

		jest.runAllTimers();

		fireEvent.click(getByText('Last 24 hours'));

		expect(spy).toHaveBeenCalledWith(INTERVAL_KEY_MAP.day);
	});
});
