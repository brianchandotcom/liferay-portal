/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useKeyboardDNDText} from 'data-engine-js-components-web';
import React from 'react';

export default function KeyboardDNDText() {
	const text = useKeyboardDNDText();

	return text ? (
		<span className="sr-only" role="alert">
			{text}
		</span>
	) : null;
}
