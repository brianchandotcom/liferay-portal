/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import React from 'react';

const Asterisk = ({required}) =>
	required && (
		<span className="ml-1 reference-mark text-warning">
			<ClayIcon symbol="asterisk" />
		</span>
	);

export default Asterisk;
