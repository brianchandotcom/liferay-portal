/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

export default function AIAssistantFooterDisclaimer() {
	return (
		<div className="ai-assistant-chat__footer-disclaimer">
			<span className="ai-assistant-chat__footer-disclaimer-text">
				{Liferay.Language.get(
					'ai-generated-responses-can-be-inaccurate'
				)}
			</span>
		</div>
	);
}
