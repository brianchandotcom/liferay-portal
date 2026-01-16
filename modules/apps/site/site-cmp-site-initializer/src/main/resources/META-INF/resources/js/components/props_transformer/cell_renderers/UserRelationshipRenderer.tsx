/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import ClaySticker from '@clayui/sticker';
import {fetch} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

const UserRelationshipRenderer = ({value}: {value: string}) => {
	const [userName, setUserName] = useState<string | null>(null);

	useEffect(() => {
		if (!value) {
			setUserName('');

			return;
		}

		fetch(
			`/o/headless-admin-user/v1.0/user-accounts/by-external-reference-code/${value}`,
			{
				method: 'GET',
			}
		)
			.then(async (data: Response) => {
				setUserName((await data.json()).name as string);
			})
			.catch(() => {
				setUserName('');
			});
	}, [value]);

	return (
		<span className="align-items-center d-flex">
			<ClaySticker
				className="c-mr-2"
				displayType="secondary"
				shape="circle"
				size="sm"
			>
				<ClayIcon symbol="user" />
			</ClaySticker>

			{userName}
		</span>
	);
};

export default UserRelationshipRenderer;
