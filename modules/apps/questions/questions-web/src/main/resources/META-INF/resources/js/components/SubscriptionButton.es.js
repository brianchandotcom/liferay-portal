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

export default ({
	isSubscribed,
	onSubscription,
	parentSection = false,
	queryVariables,
	showTitle = false,
	subscribeQuery,
	unsubscribeQuery,
}) => {
	const [subscription, setSubscription] = useState(false);

	useEffect(() => {
		setSubscription(
			isSubscribed || (parentSection && parentSection.subscribed)
		);
	}, [parentSection, isSubscribed]);

	const onCompleted = () => {
		setSubscription(!subscription);
		onSubscription?.();
	};

	const [subscribe] = useMutation(subscribeQuery);
	const [unsubscribe] = useMutation(unsubscribeQuery);

	const changeSubscription = () => {
		const fn = subscription ? unsubscribe : subscribe;
		fn({variables: queryVariables}).then(onCompleted);
	};

	const btnTitle = showTitle
		? subscription
			? Liferay.Language.get('subscribed')
			: Liferay.Language.get('subscribe')
		: '';

	return (
		<ClayButton
			displayType={subscription ? 'primary' : 'secondary'}
			onClick={changeSubscription}
			title={btnTitle}
		>
			<ClayIcon symbol="bell-on" />

			{btnTitle && (
				<span className="c-ml-2 d-none d-sm-inline-block">
					{btnTitle}
				</span>
			)}
		</ClayButton>
	);
};
