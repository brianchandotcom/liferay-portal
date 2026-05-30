import ErrorPage from 'shared/pages/ErrorPage';
import React, {useEffect} from 'react';
import {Channel} from 'shared/components/channels-menu';
import {getDefaultChannel} from 'shared/components/channels-menu';
import {matchPath, useLocation, useNavigate} from 'react-router-dom';
import {Routes, toRoute} from 'shared/util/router';

interface IWrappedComponentProps {
	channelId: string;
	channels: Array<Channel>;
	defaultChannelId: string;
	groupId: string;
}

const checkValidChannel =
	(WrappedComponent: React.ComponentType<IWrappedComponentProps>) =>
	({
		channelId,
		channels,
		defaultChannelId,
		groupId,
		...otherProps
	}: IWrappedComponentProps & {[key: string]: any}) => {
		const location = useLocation();
		const navigate = useNavigate();

		useEffect(() => {
			const isHome = matchPath(
				{
					end: true,
					path: Routes.WORKSPACE_WITH_ID
				},
				location.pathname
			);

			if (isHome) {
				const channel = getDefaultChannel(defaultChannelId, channels);

				navigate(
					toRoute(Routes.SITES, {
						...(channel && {channelId: channel.id}),
						groupId
					}),
					{replace: true}
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
			/>
		);
	};

export default checkValidChannel;
