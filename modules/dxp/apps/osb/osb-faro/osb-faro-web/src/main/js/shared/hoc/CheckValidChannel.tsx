import ErrorPage from 'shared/pages/ErrorPage';
import React, {useEffect} from 'react';
import {Channel} from 'shared/components/channels-menu';
import {getDefaultChannel} from 'shared/components/channels-menu';
import {matchPath} from 'react-router-dom';
import {Routes, toRoute} from 'shared/util/router';

type History = {
	replace: (path: string) => void;
};

type Location = {
	pathname: string;
};

interface IWrappedComponentProps {
	channelId: string;
	channels: Array<Channel>;
	defaultChannelId: string;
	groupId: string;
	history: History;
	location: Location;
}

const checkValidChannel = (
	WrappedComponent: React.ComponentType<IWrappedComponentProps>
) => ({
	channelId,
	channels,
	defaultChannelId,
	groupId,
	history,
	location,
	...otherProps
}) => {
	useEffect(() => {
		const isHome = matchPath(location.pathname, {
			exact: true,
			path: Routes.WORKSPACE_WITH_ID
		});

		if (isHome) {
			const channel = getDefaultChannel(defaultChannelId, channels);

			history.replace(
				toRoute(Routes.SITES, {
					...(channel && {channelId: channel.id}),
					groupId
				})
			);
		}
	}, []);

	if (
		channelId &&
		!!channels.length &&
		!channels.some(({id}) => id === channelId)
	) {
		return <ErrorPage />;
	}

	return (
		<WrappedComponent
			{...otherProps}
			channelId={channelId}
			channels={channels}
			defaultChannelId={channelId}
			groupId={groupId}
			history={history}
			location={location}
		/>
	);
};

export default checkValidChannel;
