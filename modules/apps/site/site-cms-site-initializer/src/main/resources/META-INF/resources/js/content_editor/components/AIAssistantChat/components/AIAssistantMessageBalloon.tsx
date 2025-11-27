/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import React from 'react';

import '../chat.scss';

const AssistantMessageBalloon: React.FC<{message: string}> = ({message}) => {
	return (
		<div className="ai-assistant-chat__ai-assistant-message-balloon d-flex flex-row font-weight-semi-bold mb-2 rounded">
			<div className="align-items-center d-flex ml-2">
				<ClayIcon
					color="#0B5FFF"
					height={12}
					spritemap={Liferay.Icons.spritemap}
					symbol="stars"
					width={12}
				/>
			</div>

			<span className="m-2">{message}</span>
		</div>
	);
};

export default AssistantMessageBalloon;
