import React, {useContext, useReducer} from 'react';
import {Channel} from 'shared/components/channels-menu';

export enum ActionType {
	setChannels = 'setChannels',
	setSelectedChannel = 'setSelectedChannel'
}

type Action = {
	payload: any;
	type: ActionType;
};

type State = {
	channels: Array<Channel>;
	selectedChannel: Channel;
};

type ChannelProviderProps = {
	children: React.ReactNode;
	selectedChannel?: Channel;
};

export const ChannelContext = React.createContext<{
	channelDispatch?: React.Dispatch<Action>;
	channels: Array<Channel>;
	selectedChannel: Channel;
}>({
	channels: [],
	selectedChannel: null
});

export const channelReducer = (state: State, {payload, type}: Action) => {
	switch (type) {
		case ActionType.setChannels: {
			return {
				...state,
				channels: payload
			};
		}
		case ActionType.setSelectedChannel: {
			return {
				...state,
				selectedChannel: payload
			};
		}
		default:
			return state;
	}
};

export const ChannelProvider = ({
	children,
	selectedChannel: channelProp = null
}: ChannelProviderProps) => {
	const [{channels, selectedChannel}, channelDispatch] = useReducer(
		channelReducer,
		{
			channels: [],
			selectedChannel: channelProp || null
		}
	);

	return (
		<ChannelContext.Provider
			value={{channelDispatch, channels, selectedChannel}}
		>
			{children}
		</ChannelContext.Provider>
	);
};

export default ChannelProvider;

export const useChannelContext = () => useContext(ChannelContext);
