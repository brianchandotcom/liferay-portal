/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

const UserChatItem: React.FC<{message: string}> = ({message}) => {
	return (
		<div className="d-flex justify-content-end">
			<span>{message}</span>

			<img
				alt={Liferay.Language.get('user-profile-image')}
				className="ml-2"
				src=""
				style={{borderRadius: 12, height: 24, width: 24}}
			/>
		</div>
	);
};

export default UserChatItem;
