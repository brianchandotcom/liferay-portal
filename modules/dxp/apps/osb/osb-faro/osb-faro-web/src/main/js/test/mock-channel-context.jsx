import React from 'react';
import {ChannelContext} from 'shared/context/channel';
import {mockChannel} from './data';

export const mockChannelContext = () => ({
	channelDispatch: jest.fn(() => null),
	channels: [mockChannel(1), mockChannel(2)],
	selectedChannel: mockChannel()
});

export function withChannelProvider(Component) {
	return props => (
		<ChannelContext.Provider value={mockChannelContext()}>
			<Component {...props} />
		</ChannelContext.Provider>
	);
}
