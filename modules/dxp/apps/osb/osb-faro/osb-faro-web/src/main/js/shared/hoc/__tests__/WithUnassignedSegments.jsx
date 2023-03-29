import 'test/mock-modal';

import * as API from 'shared/api';
import mockStore from 'test/mock-store';
import Promise from 'metal-promise';
import React from 'react';
import withUnassignedSegments from '../WithUnassignedSegments';
import {ChannelContext} from 'shared/context/channel';
import {cleanup, render} from '@testing-library/react';
import {mockChannelContext} from 'test/mock-channel-context';
import {mockSearch} from 'test/data';
import {open} from 'shared/actions/modals';
import {Provider} from 'react-redux';
import {UnassignedSegmentsContext} from 'shared/context/unassignedSegments';

jest.unmock('react-dom');

const WrappedComponent = withUnassignedSegments(() => 'wrapped component text');

const mockedContext = {
	unassignedSegments: [],
	unassignedSegmentsDispatch: jest.fn()
};

const DefaultComponent = props => (
	<Provider store={mockStore()}>
		<ChannelContext.Provider value={mockChannelContext()}>
			<UnassignedSegmentsContext.Provider value={mockedContext}>
				<WrappedComponent {...props} />
			</UnassignedSegmentsContext.Provider>
		</ChannelContext.Provider>
	</Provider>
);

describe('WithUnassignedSegments', () => {
	afterEach(() => {
		cleanup();
		jest.clearAllMocks();
	});

	it('should render the wrapped component', () => {
		API.individualSegment.search.mockReturnValueOnce(
			Promise.resolve(mockSearch(() => {}, 0))
		);

		const {container} = render(<DefaultComponent />);

		expect(container.textContent).toBe('wrapped component text');
	});

	it('should trigger the unassigned segments modal if there are segments', () => {
		render(<DefaultComponent />);

		jest.runAllTimers();

		expect(open).toBeCalled();
	});

	it('should not trigger the unassigned segments modal if it has already been triggered', () => {
		API.preferences.fetchUpgradeModalSeen.mockReturnValueOnce(
			Promise.resolve(true)
		);

		render(
			<Provider store={mockStore()}>
				<ChannelContext.Provider value={mockChannelContext()}>
					<UnassignedSegmentsContext.Provider
						value={{
							...mockedContext
						}}
					>
						<WrappedComponent />
					</UnassignedSegmentsContext.Provider>
				</ChannelContext.Provider>
			</Provider>
		);

		jest.runAllTimers();

		expect(open).not.toBeCalled();
	});
});
