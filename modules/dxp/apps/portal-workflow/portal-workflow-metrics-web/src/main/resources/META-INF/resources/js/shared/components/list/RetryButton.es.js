/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import React from 'react';

const RetryButton = (props) => {
	return (
		<ClayButton displayType="link" small {...props}>
			{Liferay.Language.get('retry')}
		</ClayButton>
	);
};

export default RetryButton;
