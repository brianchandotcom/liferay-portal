/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import ThemeContext from './ThemeContext.es';
import SegmentEdit from './components/segment_edit/SegmentEdit';

export default function ({context, props}) {
	return (
		<ThemeContext.Provider value={context}>
			<div className="segments-root">
				<SegmentEdit {...props} />
			</div>
		</ThemeContext.Provider>
	);
}
