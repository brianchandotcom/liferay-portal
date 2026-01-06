/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import React from 'react';

import '../chat.scss';

const AssistantMessageBalloon: React.FC<{error: boolean; message: string}> = ({
	error,
	message,
}) => {
	return (
		<div
			className={`d-flex flex-row font-weight-semi-bold mb-2 rounded ${error ? 'ai-assistant-chat__ai-assistant-error-message-balloon' : 'ai-assistant-chat__ai-assistant-message-balloon'}`}
		>
			<div className="align-items-start d-inline-block ml-2 mt-2">
				<ClayIcon
					color={error ? '#FF0000' : '#0B5FFF'}
					height={12}
					spritemap={Liferay.Icons.spritemap}
					symbol={error ? 'exclamation-full' : 'stars'}
					width={12}
				/>
			</div>

			<span className="m-2">
				{error
					? Liferay.Language.get('generating-content-failed')
					: message}
			</span>
		</div>
	);
};

export default AssistantMessageBalloon;
