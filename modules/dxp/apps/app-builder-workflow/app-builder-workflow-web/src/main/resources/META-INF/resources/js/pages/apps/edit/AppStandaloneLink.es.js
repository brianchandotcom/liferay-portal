/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import ClayIcon from '@clayui/icon';
import ClayLink from '@clayui/link';
import React from 'react';

export default function AppStandaloneLink({appDeployments, href}) {
	const isStandalone = appDeployments.some(({type}) => type === 'standalone');

	if (!isStandalone) {
		return <></>;
	}

	return (
		<ClayLink href={href} target="_blank">
			{`${Liferay.Language.get('open-standalone-app')}. `}
			<ClayIcon symbol="shortcut" />
		</ClayLink>
	);
}
