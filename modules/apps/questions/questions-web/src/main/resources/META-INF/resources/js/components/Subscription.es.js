/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import {useMutation} from '@apollo/client';
import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import {ClayTooltipProvider} from '@clayui/tooltip';
import React, {useEffect, useState} from 'react';

import {
	getThreadQuery,
	subscribeQuery,
	unsubscribeQuery,
} from '../utils/client.es';

export default ({
	question: {friendlyUrlPath, id: messageBoardThreadId, subscribed},
	siteKey,
}) => {
	const [subscription, setSubscription] = useState(false);

	useEffect(() => {
		setSubscription(subscribed);
	}, [subscribed]);

	const onCompleted = () => {
		setSubscription(!subscription);
	};

	const update = (cache) => {
		var question = cache.readQuery({
			query: getThreadQuery,
			variables: {
				friendlyUrlPath,
				siteKey,
			},
		});

		const newQuestion = {
			messageBoardThreadByFriendlyUrlPath: {
				...question.messageBoardThreadByFriendlyUrlPath,
				subscribed: !subscription,
			},
		};

		cache.writeQuery({
			context: {
				uri: '/o/graphql?nestedFields=lastPostDate',
			},
			data: {...newQuestion},
			query: getThreadQuery,
			variables: {
				friendlyUrlPath,
				siteKey,
			},
		});
	};

	const [subscribe] = useMutation(subscribeQuery, {onCompleted, update});
	const [unsubscribe] = useMutation(unsubscribeQuery, {onCompleted, update});

	const changeSubscription = () => {
		if (subscription) {
			unsubscribe({variables: {messageBoardThreadId}});
		}
		else {
			subscribe({variables: {messageBoardThreadId}});
		}
	};

	return (
		<ClayTooltipProvider>
			<ClayButton
				data-tooltip-align="top"
				displayType={subscription ? 'primary' : 'secondary'}
				monospaced
				onClick={changeSubscription}
				title={
					subscription
						? Liferay.Language.get('unsubscribe')
						: Liferay.Language.get('subscribe')
				}
			>
				<ClayIcon symbol="bell-on" />
			</ClayButton>
		</ClayTooltipProvider>
	);
};
