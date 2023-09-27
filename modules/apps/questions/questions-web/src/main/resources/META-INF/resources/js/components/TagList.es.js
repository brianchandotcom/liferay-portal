/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLabel from '@clayui/label';
import React from 'react';
import {withRouter} from 'react-router-dom';

import Link from '../components/Link.es';

export default withRouter(({tags = [], sectionTitle = ''}) => {
	if (tags.length) {
		tags = [...tags].sort();
	}

	return (
		<ul className="c-mb-0 d-flex flex-wrap list-unstyled stretched-link-layer">
			{tags.map((tag) => (
				<li key={tag}>
					<ClayLabel displayType="secondary">
						<Link
							key={tag}
							to={`/questions/${sectionTitle}?selectedtags=${tag}&taggedwith=some-specific-tag`}
						>
							{tag}
						</Link>
					</ClayLabel>
				</li>
			))}
		</ul>
	);
});
