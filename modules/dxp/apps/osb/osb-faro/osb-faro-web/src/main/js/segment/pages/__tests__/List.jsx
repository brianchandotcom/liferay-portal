import * as data from 'test/data';
import List from '../List';
import mockStore from 'test/mock-store';
import React from 'react';
import {ChannelContext} from 'shared/context/channel';
import {MemoryRouter, Route} from 'react-router-dom';
import {mockChannelContext} from 'test/mock-channel-context';
import {Provider} from 'react-redux';
import {render} from '@testing-library/react';
import {Routes} from 'shared/util/router';
import {UnassignedSegmentsContext} from 'shared/context/unassignedSegments';
import {User} from 'shared/util/records';

jest.unmock('react-dom');

const MOCK_UNASSIGNED_SEGMENTS_CONTEXT = {
	showUnassignedAlert: false,
	unassignedSegments: [],
	unassignedSegmentsDispatch: jest.fn()
};

const DefaultComponent = ({queryString = '', ...otherProps}) => (
	<Provider store={mockStore()}>
		<MemoryRouter
			initialEntries={[
				`/workspace/23/123/contacts/segments${queryString}`
			]}
		>
			<Route path={Routes.CONTACTS_LIST_SEGMENT}>
				<UnassignedSegmentsContext.Provider
					value={MOCK_UNASSIGNED_SEGMENTS_CONTEXT}
				>
					<ChannelContext.Provider value={mockChannelContext()}>
						<List
							channelId='123'
							currentUser={data.getImmutableMock(
								User,
								data.mockUser
							)}
							groupId='23'
							{...otherProps}
						/>
					</ChannelContext.Provider>
				</UnassignedSegmentsContext.Provider>
			</Route>
		</MemoryRouter>
	</Provider>
);

describe('List', () => {
	it('should render', () => {
		const {container} = render(<DefaultComponent />);

		jest.runAllTimers();

		expect(container).toMatchSnapshot();
	});
});
