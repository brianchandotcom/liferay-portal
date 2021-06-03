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

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import {useMutation} from 'graphql-hooks';
import React, {useEffect, useState} from 'react';

import {subscribeQuery, unsubscribeQuery} from '../utils/client.es';

export default ({
	onSubscription,
	question: {id: messageBoardThreadId, subscribed},
}) => {
	const [subscription, setSubscription] = useState(false);

	useEffect(() => {
		setSubscription(subscribed);
	}, [subscribed]);

	const [subscribe] = useMutation(subscribeQuery);
	const [unsubscribe] = useMutation(unsubscribeQuery);

	const changeSubscription = () => {
		const fn = subscription ? unsubscribe : subscribe;
		fn({variables: {messageBoardThreadId}}).then((_) => {
			setSubscription(!subscription);
			onSubscription?.();
		});
	};

	return (
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
	);
};
