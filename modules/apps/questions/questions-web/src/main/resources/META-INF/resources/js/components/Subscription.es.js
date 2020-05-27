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
import React from 'react';

import {subscribeQuery, unsubscribeQuery} from '../utils/client.es';

export default ({onSubscription, question}) => {
	const onCompleted = () => {
		if (onSubscription) {
			onSubscription(!question.subscribed);
		}
	};

	const [subscribe] = useMutation(subscribeQuery, {onCompleted});
	const [unsubscribe] = useMutation(unsubscribeQuery, {onCompleted});

	const changeSubscription = () => {
		if (question.subscribed) {
			subscribe({variables: {messageBoardThreadId: question.id}});
		}
		else {
			unsubscribe({variables: {messageBoardThreadId: question.id}});
		}
	};

	return (
		<ClayButton
			displayType={question.subscribed ? 'primary' : 'secondary'}
			monospaced
			onClick={changeSubscription}
		>
			<ClayIcon symbol="bell-on" />
		</ClayButton>
	);
};
