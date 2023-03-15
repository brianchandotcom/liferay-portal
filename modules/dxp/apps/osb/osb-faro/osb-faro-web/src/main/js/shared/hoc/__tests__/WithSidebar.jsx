import mockStore from 'test/mock-store';
import React from 'react';
import withSidebar from '../WithSidebar';
import {BrowserRouter} from 'react-router-dom';
import {ChannelContext} from 'shared/context/channel';
import {cleanup, render} from '@testing-library/react';
import {compose} from 'redux';
import {
	mockChannelContext,
	withChannelProvider
} from 'test/mock-channel-context';
import {Provider} from 'react-redux';
import {withStaticRouter} from 'test/mock-router';

jest.unmock('react-dom');

describe('withSidebar', () => {
	afterEach(cleanup);
	it('should render loading', () => {
		const WrappedComponent = withSidebar(() => <div>{'foobar'}</div>);

		const {container} = render(
			<Provider store={mockStore()}>
				<ChannelContext.Provider value={mockChannelContext()}>
					<BrowserRouter>
						<WrappedComponent />
					</BrowserRouter>
				</ChannelContext.Provider>
			</Provider>
		);

		expect(container).toMatchSnapshot();
	});

	it('should render with the sidebar', () => {
		const WrappedComponent = compose(
			withChannelProvider,
			withStaticRouter,
			withSidebar
		)(() => <p>{'bizbaz'}</p>);

		const {container} = render(
			<Provider store={mockStore()}>
				<BrowserRouter>
					<WrappedComponent
						groupId='23'
						location={{pathname: 'foo'}}
					/>
				</BrowserRouter>
			</Provider>
		);

		jest.runAllTimers();

		expect(container).toMatchSnapshot();
	});
});
