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

import {subscribeQuery, unsubscribeQuery} from '../utils/client.es';

export default ({question: {id: messageBoardThreadId, subscribed}}) => {
	const [subscription, setSubscription] = useState(false);

	useEffect(() => {
		setSubscription(subscribed);
	}, [subscribed]);

	const onCompleted = () => {
		setSubscription(!subscription);
	};

	const [subscribe] = useMutation(subscribeQuery, {onCompleted});
	const [unsubscribe] = useMutation(unsubscribeQuery, {onCompleted});

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
