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
import React, {useEffect, useState} from 'react';

import {
	subscribeSectionQuery,
	unsubscribeSectionQuery,
} from '../utils/client.es';

export default ({
	onSubscription,
	section: {id: messageBoardSectionId, parentSection, subscribed},
}) => {
	const [subscription, setSubscription] = useState(false);

	useEffect(() => {
		setSubscription(
			subscribed || (parentSection && parentSection.subscribed)
		);
	}, [messageBoardSectionId, parentSection, subscribed]);

	const onCompleted = () => {
		setSubscription(!subscription);
		if (onSubscription) {
			onSubscription(!subscription);
		}
	};

	const update = (proxy) => {
		proxy.evict(`MessageBoardSection:${messageBoardSectionId}`);
		proxy.gc();
	};

	const [subscribeSection] = useMutation(subscribeSectionQuery, {
		onCompleted,
		update,
	});

	const [unsubscribeSection] = useMutation(unsubscribeSectionQuery, {
		onCompleted,
		update,
	});

	const changeSubscription = () => {
		if (subscription) {
			unsubscribeSection({variables: {messageBoardSectionId}});
		}
		else {
			subscribeSection({variables: {messageBoardSectionId}});
		}
	};

	const btnTitle = subscription
		? Liferay.Language.get('subscribed')
		: Liferay.Language.get('subscribe');

	return (
		<ClayButton
			displayType={subscription ? 'primary' : 'secondary'}
			onClick={changeSubscription}
			title={btnTitle}
		>
			<ClayIcon symbol="bell-on" />

			<span className="c-ml-2 d-none d-sm-inline-block">{btnTitle}</span>
		</ClayButton>
	);
};
