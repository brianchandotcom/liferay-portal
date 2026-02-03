/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useEffect, useState} from 'react';

import {getUserAccount} from '../services/getUserAccount';
import Avatar from './Avatar';

const UserChatItem: React.FC<{message: string}> = ({message}) => {
	const [userAccount, setUserAccount] = useState<any>(null);

	useEffect(() => {
		async function getCurrentUserAccount() {
			try {
				setUserAccount(
					await getUserAccount(
						Liferay.ThemeDisplay.getUserId().toString()
					)
				);
			}
			catch (error) {
				console.error('Error fetching user info:', error);
			}
		}

		getCurrentUserAccount();
	}, []);

	return (
		<div className="align-items-center d-flex justify-content-end mb-2">
			<span className="ai-assistant-chat__user-message col-auto p-2 text-break">
				{message}
			</span>

			<div className="flex-shrink-0 ml-2">
				<Avatar image={userAccount?.image} name={userAccount?.name} />
			</div>
		</div>
	);
};

export default UserChatItem;
