/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import {useMutation} from 'graphql-hooks';
import React, {useEffect, useState} from 'react';

export default function SubscriptionButton({
	isSubscribed,
	onSubscription,
	parentSection = false,
	queryVariables,
	showTitle = false,
	subscribeQuery,
	unsubscribeQuery,
}) {
	const [subscription, setSubscription] = useState(false);

	useEffect(() => {
		setSubscription(
			isSubscribed || (parentSection && parentSection.subscribed)
		);
	}, [parentSection, isSubscribed]);

	const onCompleted = () => {
		setSubscription(!subscription);
		onSubscription?.(!subscription);
	};

	const [subscribe] = useMutation(subscribeQuery);
	const [unsubscribe] = useMutation(unsubscribeQuery);

	const changeSubscription = () => {
		const fn = subscription ? unsubscribe : subscribe;
		fn({variables: queryVariables}).then(onCompleted);
	};

	const btnTitle = subscription
		? Liferay.Language.get('subscribed')
		: Liferay.Language.get('subscribe');

	return (
		<ClayButton
			aria-label={btnTitle}
			data-tooltip-align="top"
			displayType={subscription ? 'primary' : 'secondary'}
			onClick={changeSubscription}
			title={btnTitle}
		>
			<ClayIcon symbol="bell-on" />

			{showTitle && (
				<span className="c-ml-2 d-none d-sm-inline-block">
					{btnTitle}
				</span>
			)}
		</ClayButton>
	);
}
