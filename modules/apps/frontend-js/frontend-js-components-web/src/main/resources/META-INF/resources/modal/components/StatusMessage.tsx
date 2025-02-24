/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useEffect, useState} from 'react';

export default function StatusMessage({loading}: {loading: boolean}) {
	const [showMessage, setShowMessage] = useState(true);

	useEffect(() => {
		if (!loading) {
			setTimeout(() => setShowMessage(false), 1000);
		}
	}, [loading]);

	return showMessage ? (
		<span className="sr-only" role="status">
			{!loading && Liferay.Language.get('loaded')}
		</span>
	) : null;
}
