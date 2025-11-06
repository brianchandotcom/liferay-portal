/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import getInitials from '../utils/getUserInitials';

const Avatar: React.FC<{image?: string; name: string}> = ({image, name}) => {
	return (
		<>
			{image ? (
				<img
					alt={Liferay.Language.get('user-profile-image')}
					className="ai-assistant-chat__user-image ml-2"
					src={image}
				/>
			) : (
				<span className="ai-assistant-chat__user-initials ml-2">
					{getInitials(name)}
				</span>
			)}
		</>
	);
};

export default Avatar;
